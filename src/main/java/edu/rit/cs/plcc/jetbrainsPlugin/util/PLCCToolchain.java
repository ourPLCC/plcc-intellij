package edu.rit.cs.plcc.jetbrainsPlugin.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.download.DownloadableFileDescription;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.download.FileDownloader;
import com.intellij.util.io.ZipUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class PLCCToolchain {

    private final JComboBox<String> comboBox;
    private final Component parent;

    private static final String DOWNLOAD_PLCC = "Download and Install PLCC";
    private static final String ADD_PLCC = "Add PLCC from filesystem";

    public static final String PLCC_LOCATION_PROPERTY_KEY = "SelectedPLCCToolchain";

    public PLCCToolchain(JComboBox<String> comboBox, Component parent) {
        this.comboBox = comboBox;
        this.parent = parent;
    }

    public void populateToolchainComboBox() {
        comboBox.addItem(DOWNLOAD_PLCC);
        comboBox.addItem(ADD_PLCC);
        comboBox.setSelectedIndex(-1);

        comboBox.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                switch ((String) Objects.requireNonNull(comboBox.getSelectedItem())) {
                    case ADD_PLCC:
                        Optional<VirtualFile> plccDir = findPlccInstalation();
                        if (plccDir.isEmpty()) {
                            comboBox.setSelectedIndex(-1);
                        }
                        break;

                    case DOWNLOAD_PLCC:
                        Optional<VirtualFile> plccDirOpt = downloadAndInstallPlcc();
                        plccDirOpt.ifPresentOrElse(plccDir2 -> {
                            Optional<String> version = readVersion(plccDir2);
                            version.ifPresentOrElse(string -> formatAndAddSDKEntry(string, plccDir2.getPath()), () -> {
                                // already threw stack trace
                            });
                        }, () -> {
                            // already threw stack trace
                        });
                        break;

                    default:
                        var selectedItem = (String)comboBox.getSelectedItem();
                        var taggedVersion = selectedItem.split(" ")[0];
                        var plccVersionLocation = PropertiesComponent.getInstance().getValue(taggedVersion);
                        PropertiesComponent.getInstance().setValue(PLCC_LOCATION_PROPERTY_KEY, plccVersionLocation);
                }
            }
        });

        var homeDir = Paths.get(System.getProperty("user.home")).toFile();
        Optional<VirtualFile> virtualHomeDirOpt = Optional.ofNullable(LocalFileSystem.getInstance().findFileByIoFile(homeDir));
        virtualHomeDirOpt.ifPresent(virtualHomeDir -> {
            var plccDirOpt = Optional.ofNullable(virtualHomeDir.findChild(".plcc"));
            plccDirOpt.ifPresent(plccDir -> {
                VirtualFile[] plccInstalations = plccDir.getChildren();
                Arrays.stream(plccInstalations).forEach(child -> {
                    Optional<VirtualFile> possibSrcDir = Optional.ofNullable(child.findChild("src"));
                    if (possibSrcDir.isPresent()) {
                        if (possibSrcDir.get().findChild("plcc.py") != null) {
                            Optional<String> version = readVersion(possibSrcDir.get());
                            version.ifPresent(s -> formatAndAddSDKEntry(s, possibSrcDir.get().getPath()));
                        }
                    }
                });
            });
        });

        Optional<String> libPlccPath = Optional.ofNullable(System.getenv("LIBPLCC"));
        VirtualFile libPlccDir = null;
        if (libPlccPath.isPresent()) {
            libPlccDir = LocalFileSystem.getInstance().findFileByIoFile(new File(libPlccPath.get()));
        }

        if (libPlccDir != null && libPlccDir.findChild("plcc.py") != null) {
            Optional<String> version = readVersion(libPlccDir);
            version.ifPresentOrElse(versionPresent -> formatAndAddSDKEntry(versionPresent, libPlccPath.get()),
                    () -> new IOException("No version file found in LIBPLCC directory with a plcc.py file").printStackTrace());
        }
    }

    private Optional<VirtualFile> findPlccInstalation() {
        FileChooserDescriptor desc = new FileChooserDescriptor(false, true, false, false, false, false);
        Optional<VirtualFile> selectedDir = Optional.ofNullable(FileChooser.chooseFile(desc, null, null));

        if (selectedDir.isEmpty()) {
            return Optional.empty();
        }

        Optional<VirtualFile> possibSrcDir = Optional.ofNullable(selectedDir.get().findChild("src"));
        VirtualFile srcDir;

        if (possibSrcDir.isEmpty()) {
            if (selectedDir.get().getName().equals("src")) {
                srcDir = selectedDir.get();
            } else {
                JOptionPane.showMessageDialog(parent,
                        "Selected directory is not called 'src' and/or does not contain one",
                        null, JOptionPane.ERROR_MESSAGE);
                return Optional.empty();
            }
        } else {
            srcDir = possibSrcDir.get();
        }

        if (srcDir.findChild("plcc.py") != null) {
            return Optional.of(possibSrcDir.get());
        } else {
            JOptionPane.showMessageDialog(parent,
                    "Selected directory does not contain the PLCC tool", null,
                    JOptionPane.ERROR_MESSAGE);
        }
        return Optional.empty();
    }

    private void formatAndAddSDKEntry(String version, String path) {
        var taggedVerion = "plcc".concat("-").concat(version);
        var versionWithPath = taggedVerion.concat(" (").concat(path).concat(")");
        PropertiesComponent.getInstance().setValue(taggedVerion, path);
        PropertiesComponent.getInstance().setValue(PLCC_LOCATION_PROPERTY_KEY, path);
        comboBox.insertItemAt(versionWithPath, 0);
        comboBox.setSelectedIndex(0);
    }

    // stole most of this implementation from
    // https://github.com/bulenkov/RedlineSmalltalk/blob/master/src/st/redline/smalltalk/module/RsSdkPanel.java
    private Optional<VirtualFile> downloadAndInstallPlcc() {
        String plccFolderName = "plcc-2.0.1";
        String downloadedFileName = plccFolderName.concat(".zip");
        DownloadableFileService fileService = DownloadableFileService.getInstance();
        DownloadableFileDescription fileDescription = fileService.createFileDescription(
                "https://github.com/ourPLCC/plcc/archive/v2.0.1.zip", downloadedFileName);
        FileDownloader downloader = fileService.createDownloader(
                new ArrayList<>() {{add(fileDescription);}}, downloadedFileName);

        String directory = Paths.get(System.getProperty("user.home"), ".plcc").toString();
        @Nullable List<Pair<VirtualFile, DownloadableFileDescription>> files = downloader.downloadWithProgress(directory, null, null);
        if (files != null && files.size() == 1) {
            try {
                VirtualFile file = files.get(0).first;
                ZipUtil.extract(VfsUtil.virtualToIoFile(file), new File(directory), null);

                var srcDir = Paths.get(directory, plccFolderName, "src").toFile();
                var virtualSrcDir = LocalFileSystem.getInstance().findFileByIoFile(srcDir);

                return Optional.ofNullable(virtualSrcDir);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return Optional.empty();
            }
        } else {
            new IOException("File did not download correctly. There should be one file downloaded").printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<String> readVersion(VirtualFile file) {
        try {
            return Optional.of(Files.readString(Paths.get(file.getPath(), "VERSION")));
        } catch (IOException e) {
            System.err.println("File: " + file.getPath() + " does not contain a VERSION file!");
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

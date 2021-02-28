package edu.rit.cs.plcc.jetbrainsPlugin.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.download.DownloadableFileDescription;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.io.ZipUtil;
import lombok.val;
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
                        val plccDir = findPlccInstallation();
                        if (plccDir != null) {
                            if (!formatAndAddSDKEntry(plccDir)) {
                                comboBox.setSelectedIndex(-1);
                            }
                        } else {
                            comboBox.setSelectedIndex(-1);
                        }
                        break;

                    case DOWNLOAD_PLCC:
                        Optional<VirtualFile> plccDirOpt = downloadAndInstallPlcc();
                        if (plccDirOpt != null) {  // don't replace with Optional.isPresent()
                            if (plccDirOpt.isPresent()) {
                                if (!formatAndAddSDKEntry(plccDirOpt.get())) {
                                    comboBox.setSelectedIndex(-1);
                                }
                            } else {
                                JOptionPane.showMessageDialog(parent,
                                        "Failed to download PLCC", null,
                                        JOptionPane.ERROR_MESSAGE);
                                comboBox.setSelectedIndex(-1);
                            }
                        } else {
                            comboBox.setSelectedIndex(-1);
                        }
                        break;

                    default:
                        val selectedItem = (String)comboBox.getSelectedItem();
                        val taggedVersion = selectedItem.split(" ")[0];
                        val plccVersionLocation = PropertiesComponent.getInstance().getValue(taggedVersion);
                        PropertiesComponent.getInstance().setValue(PLCC_LOCATION_PROPERTY_KEY, plccVersionLocation);
                }
            }
        });

        val homeDir = Paths.get(System.getProperty("user.home")).toFile();
        val virtualHomeDir = LocalFileSystem.getInstance().findFileByIoFile(homeDir);

        if (virtualHomeDir != null) {
            val plccDir = virtualHomeDir.findChild(".plcc");

            if (plccDir != null) {
                val plccInstallations = plccDir.getChildren();

                if (plccInstallations != null) {
                    Arrays.stream(plccInstallations).forEach(plccInstallation -> {
                        val srcDir = plccInstallation.findChild("src");

                        if (srcDir != null) {
                            if (srcDir.findChild("plcc.py") != null) {
                                formatAndAddSDKEntry(srcDir);
                            }
                        }
                    });
                }
            }
        }

        val libPlccPath = System.getenv("LIBPLCC");
        if (libPlccPath != null) {
            val libPlccDir = LocalFileSystem.getInstance().findFileByIoFile(new File(libPlccPath));

            if (libPlccDir != null && libPlccDir.findChild("plcc.py") != null) {
                formatAndAddSDKEntry(libPlccDir);
            }
        } else {
            System.out.println("No LIBPLCC environment variable set. This is ok.");
        }
    }

    private VirtualFile findPlccInstallation() {
        FileChooserDescriptor desc = new FileChooserDescriptor(false, true, false, false, false, false);
        val selectedDir = FileChooser.chooseFile(desc, null, null);

        if (selectedDir == null) {
            return null;
        }

        VirtualFile srcDir = selectedDir.findChild("src");
        if (srcDir == null) {
            if (selectedDir.getName().equals("src")) {
                srcDir = selectedDir;
            } else {
                JOptionPane.showMessageDialog(parent,
                        "Selected directory is not called 'src' and/or does not contain one",
                        null, JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        if (srcDir.findChild("plcc.py") != null) {
            return srcDir;
        } else {
            JOptionPane.showMessageDialog(parent,
                    "Selected directory does not contain plcc.py", null,
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private boolean formatAndAddSDKEntry(VirtualFile plccDir) {
        val version = readVersion(plccDir);

        if (version != null) {
            val path = plccDir.getPath();
            val taggedVersion = "plcc".concat("-").concat(version);
            val versionWithPath = taggedVersion.concat(" (").concat(path).concat(")");
            PropertiesComponent.getInstance().setValue(taggedVersion, path);
            PropertiesComponent.getInstance().setValue(PLCC_LOCATION_PROPERTY_KEY, path);
            comboBox.insertItemAt(versionWithPath, 0);
            comboBox.setSelectedIndex(0);
            return true;
        } else {
            return false;
        }
    }

    // stole most of this implementation from
    // https://github.com/bulenkov/RedlineSmalltalk/blob/master/src/st/redline/smalltalk/module/RsSdkPanel.java
    private Optional<VirtualFile> downloadAndInstallPlcc() {
        val homeDir = Paths.get(System.getProperty("user.home"), ".plcc").toString();
        val plccFolderName = "plcc-2.0.1";
        val fileToDownload = Paths.get(homeDir, plccFolderName).toString();
        if (LocalFileSystem.getInstance().findFileByIoFile(Paths.get(fileToDownload).toFile()) != null) {
            JOptionPane.showMessageDialog(parent,
                    plccFolderName.concat(" is already downloaded in ").concat(homeDir),
                    null,
                    JOptionPane.ERROR_MESSAGE);
            return null; // yes I want to use null
        } else {
            val answer = JOptionPane.showConfirmDialog(
                    parent,
                    "PLCC will be downloaded in\n".concat(fileToDownload).concat("\nDo you wish to proceed?"),
                    null,
                    JOptionPane.YES_NO_OPTION
            );
            if (answer != JOptionPane.YES_OPTION) {
                return null; // yes I want to use null
            }
        }

        val downloadedFileName = plccFolderName.concat(".zip");
        val fileService = DownloadableFileService.getInstance();
        val fileDescription = fileService.createFileDescription(
                "https://github.com/ourPLCC/plcc/archive/v2.0.1.zip", downloadedFileName);
        val downloader = fileService.createDownloader(
                new ArrayList<>() {{add(fileDescription);}}, downloadedFileName);
        @Nullable List<Pair<VirtualFile, DownloadableFileDescription>> files = downloader.downloadWithProgress(homeDir, null, null);
        if (files != null && files.size() == 1) {
            try {
                ZipUtil.extract(Paths.get(fileToDownload.concat(".zip")), Paths.get(homeDir), null);

                val srcDir = Paths.get(homeDir, plccFolderName, "src").toFile();
                val virtualSrcDir = LocalFileSystem.getInstance().findFileByIoFile(srcDir);

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

    private String readVersion(VirtualFile file) {
        try {
            return Files.readString(Paths.get(file.getPath(), "VERSION"));
        } catch (IOException e) {
            System.err.println("Possible PLCC installation: " + file.getPath() + " does not contain a VERSION file!");
            e.printStackTrace();
            return null;
        }
    }
}

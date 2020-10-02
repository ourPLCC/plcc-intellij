package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
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
import java.util.*;
import java.util.List;

public class PLCCSdkPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox comboBox1;

    private Optional<Sdk> currentlySelectedSdk = Optional.empty();

    public static final String DOWNLOAD_PLCC = "Download and Install PLCC";
    public static final String ADD_PLCC = "Add PLCC from filesystem";

    public PLCCSdkPanel() {
        super(new BorderLayout());
        findSDKs();
        add(rootPanel, BorderLayout.CENTER);
    }

    private void findSDKs() {
        Optional<String> plccEnvVar = Optional.ofNullable(System.getenv("LIBPLCC"));

        VirtualFile plccLocation = null;
        if (plccEnvVar.isPresent()) {
            plccLocation = LocalFileSystem.getInstance().findFileByIoFile(new File(plccEnvVar.get()));
        }

        if (plccLocation == null || plccLocation.findChild("plcc.py") == null) {
            comboBox1.addItem(DOWNLOAD_PLCC);
            comboBox1.addItem(ADD_PLCC);
            comboBox1.setSelectedIndex(-1);

            comboBox1.addActionListener(e -> {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    switch ((String) Objects.requireNonNull(comboBox1.getSelectedItem())) {
                        case ADD_PLCC:
                            Optional<VirtualFile> plccDir = findPlccInstalation();
                            if (plccDir.isPresent()) {
                                plccDir.get();
                            }
                            break;

                        case DOWNLOAD_PLCC:
                            Optional<VirtualFile> plccDir2Opt = downloadAndInstallPlcc();
                            plccDir2Opt.ifPresentOrElse(plccDir2 -> {
                                Optional<String> version = readVersion(plccDir2);
                                version.ifPresentOrElse(string -> {
                                    String versionWithType = "plcc".concat("-").concat(string);
                                    String plccDir2Path = plccDir2.getPath();
                                    PropertiesComponent.getInstance().setValue(versionWithType, plccDir2Path);
                                    comboBox1.insertItemAt(versionWithType.concat(" (").concat(plccDir2Path).concat(")"), 0);
                                    comboBox1.setSelectedIndex(0);
                                }, () -> {
                                    // already threw stack trace
                                });
                            }, () -> {
                                // already threw stack trace
                            });
                            break;
                    }
                }
            });
        } else {

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
                JOptionPane.showMessageDialog(this,
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
            JOptionPane.showMessageDialog(this,
                    "Selected directory does not contain the PLCC tool", null,
                    JOptionPane.ERROR_MESSAGE);
        }
        return Optional.empty();
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


                return Optional.ofNullable(
                        LocalFileSystem.getInstance().findFileByIoFile(Paths.get(directory, plccFolderName, "src").toFile()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return Optional.empty();
            }
        } else {
            new IOException("File did not download correctly. There should be one file downloaded").printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Sdk> getSdk() {
        return currentlySelectedSdk;
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

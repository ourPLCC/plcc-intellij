package edu.rit.cs.plcc.jetbrainsPlugin.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.io.ZipUtil;
import edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases.GettingPLCCVersionsFromGithubException;
import edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases.GithubTag;
import edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases.PlccVersionsFromGithub;
import lombok.val;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PLCCToolchain {

    private final JComboBox<String> comboBox;
    private final JComponent parent;

    private static final String DOWNLOAD_PLCC_PRELUDE = "Download latest PLCC version: ";
    private final String downloadPlcc;
    private static final String ADD_PLCC = "Add PLCC from filesystem";
    private GithubTag latestPlccRelease;

    public static final String PLCC_LOCATION_PROPERTY_KEY = "edu.rit.cs.plcc.jetbrainsPlugin.SelectedPLCCToolchain";

    public PLCCToolchain(JComboBox<String> comboBox, JComponent parent) {
        this.comboBox = comboBox;
        this.parent = parent;

        latestPlccRelease = null;
        try {
            latestPlccRelease = new PlccVersionsFromGithub().findLatestVersion();
        } catch (GettingPLCCVersionsFromGithubException e) {
            e.printStackTrace();
        }
        downloadPlcc = latestPlccRelease == null ?
                "Unable to find latest PLCC version release" :
                DOWNLOAD_PLCC_PRELUDE + latestPlccRelease.getName();

        populateToolchainComboBox();
    }

    public void populateToolchainComboBox() {
        comboBox.addItem(downloadPlcc);
        comboBox.addItem(ADD_PLCC);
        comboBox.setSelectedIndex(-1);

        comboBox.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                val selectedItem = (String) Objects.requireNonNull(comboBox.getSelectedItem());
                if (selectedItem.equals(ADD_PLCC)) {
                    val plccDir = findPlccInstallation();
                    if (plccDir != null) {
                        if (!formatAndAddSDKEntry(plccDir)) {
                            comboBox.setSelectedIndex(-1);
                        }
                    } else {
                        comboBox.setSelectedIndex(-1);
                    }
                } else if (selectedItem.equals(downloadPlcc)) {
                    Optional<VirtualFile> plccDirOpt = downloadAndInstallPlcc();
                    if (plccDirOpt != null) {  // don't replace with Optional.isPresent()
                        if (plccDirOpt.isPresent()) {
                            if (!formatAndAddSDKEntry(plccDirOpt.get())) {
                                comboBox.setSelectedIndex(-1);
                            } else {
                                System.out.println("success");
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
                } else {
                    val taggedVersion = selectedItem.split(" ")[0];
                    val plccVersionLocation = PropertiesComponent.getInstance().getValue(taggedVersion);
                    // TODO remove this value in the global persistent state when the Project object is available for the first time
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
        val parentPlccDir = plccDir.getParent();
        System.out.println(parentPlccDir.getName());
        // TODO: what cases could this fail?

        val path = plccDir.getPath();
        val taggedVersion = parentPlccDir.getName();
        val versionWithPath = taggedVersion.concat(" (").concat(path).concat(")");
        PropertiesComponent.getInstance().setValue(taggedVersion, path);
        PropertiesComponent.getInstance().setValue(PLCC_LOCATION_PROPERTY_KEY, path);
        comboBox.insertItemAt(versionWithPath, 0);
        comboBox.setSelectedIndex(0);
        return true;
    }

    private Optional<VirtualFile> downloadAndInstallPlcc() {
        val homeDir = Paths.get(System.getProperty("user.home"), ".plcc").toString();
        val plccFolderName = "plcc-".concat(latestPlccRelease.getName());
        val finalFolderName = "plcc-".concat(latestPlccRelease.getName().substring(1));
        val finalFolderPath = Paths.get(homeDir, finalFolderName).toFile();
        val downloadedFile = LocalFileSystem.getInstance().findFileByIoFile(finalFolderPath);
        System.out.println("plccfoldername:" + plccFolderName);
        val fileToDownload = Paths.get(homeDir, plccFolderName).toString();
        System.out.println("filetodownload:" + fileToDownload);
        if (LocalFileSystem.getInstance().findFileByIoFile(finalFolderPath) != null) {
            JOptionPane.showMessageDialog(parent,
                    plccFolderName.concat(" is already downloaded in ").concat(homeDir),
                    null,
                    JOptionPane.ERROR_MESSAGE);
            return null; // yes I want to use null
        } else {
            val answer = JOptionPane.showConfirmDialog(
                    parent,
                    "PLCC will be downloaded in\n".concat(finalFolderPath.toString()).concat("\nDo you wish to proceed?"),
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
                "https://github.com/ourPLCC/plcc/archive/" + latestPlccRelease.getName() + ".zip", downloadedFileName);
        val downloader = fileService.createDownloader(
                new ArrayList<>() {{add(fileDescription);}}, downloadedFileName);
        val files = downloader.downloadFilesWithProgress(homeDir, null, parent);
        if (files != null && files.size() == 1) {
            try {
                ZipUtil.extract(Paths.get(fileToDownload.concat(".zip")), Paths.get(homeDir), null);
                new File(fileToDownload.concat(".zip")).delete();


                if (downloadedFile == null) {
                    System.out.println("downloaded file is null");
                    return Optional.empty();
                }
                val plccSrcDir = downloadedFile.findChild("src");
                if (plccSrcDir == null) {
                    System.out.println("plccDir does not have a src dir");
                }

                return Optional.ofNullable(plccSrcDir);
            } catch (IOException ioe) {
                System.out.println("!!!");
                ioe.printStackTrace();
                System.out.println("!!!");
                return Optional.empty();
            }
        } else {
            new IOException("File did not download correctly. There should be one file downloaded").printStackTrace();
            return Optional.empty();
        }
    }
}

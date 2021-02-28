package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PLCCConfigurationGui extends JPanel {

    private TextFieldWithBrowseButton plccFilePicker;
    private JPanel rootPanel;

    private final Project project;

    public static final String JDK_PROPERTY_VALUE = "jdk";

    public PLCCConfigurationGui(Project project) {
        super(new BorderLayout());
        this.project = project;

        plccFilePicker.addBrowseFolderListener(new TextBrowseFolderListener(
                new FileChooserDescriptor(true, false, false, false, false, false)
                        .withTitle("Choose PLCC File to Run")
                        .withRoots(ProjectRootManager.getInstance(project).getContentRootsFromAllModules())
        ));

        // this currently does not work because the settings editor initially resets the GUI after this constructor to I guess "initialize" the GUI?
        // I dont know, other plugins do this so I am not sure why I cant figure it out
//        val plccSrcDir = ProjectRootManager.getInstance(project).getContentRoots()[0].findChild("plcc");
//        val plccFileOpt = Arrays.stream(plccSrcDir.getChildren()).filter(e -> e.getName().endsWith(".plcc")).findFirst();
//        plccFileOpt.ifPresent(plccFile -> plccFilePicker.setText(plccFile.getPath()));
//        System.out.println("constructing");

        add(rootPanel, BorderLayout.CENTER);
    }

    public void checkValidConfiguration() throws ConfigurationException {
        val plccLocation = PropertiesComponent.getInstance().getValue(PLCCToolchain.PLCC_LOCATION_PROPERTY_KEY);
        if (isNull(plccLocation)) {
            throw new ConfigurationException("Select a PLCC installation in the project settings. You should have chosen one when you created the project");
        }
    }

    public String getPlccFile() {
        return plccFilePicker.getText();
    }

    public void setPlccFile(String text) {
        plccFilePicker.setText(text);
    }
}

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

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.util.Objects.isNull;

public class PLCCConfigurationGui extends JPanel {
    private JComboBox<String> plccComboBox;
    private JComboBox<Sdk> jdkComboBox;
    private TextFieldWithBrowseButton tfwbb;
    private JPanel rootPanel;

    private Project project;

    private static final String JDK_PROPERTY_VALUE = "jdk";

    public PLCCConfigurationGui(Project project) {
        super(new BorderLayout());
        this.project = project;

        new PLCCToolchain(plccComboBox, this).populateToolchainComboBox();
        for (Sdk item : ProjectJdkTable.getInstance().getAllJdks()) {
            if (item.getSdkType().toString().equals("JavaSDK")) {
                jdkComboBox.addItem(item);
            }
        }
        setSelectedItemAsJdk();

        jdkComboBox.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                setSelectedItemAsJdk();
            }
        });

        tfwbb.addBrowseFolderListener(new TextBrowseFolderListener(
                new FileChooserDescriptor(true, false, false, false, false, false)
                        .withTitle("Choose PLCC File to Run")
                        .withRoots(ProjectRootManager.getInstance(project).getContentRootsFromAllModules())
        ));

        add(rootPanel, BorderLayout.CENTER);
    }

    private void setSelectedItemAsJdk() {
        PropertiesComponent.getInstance().setValue(JDK_PROPERTY_VALUE,
                ((Sdk) Objects.requireNonNull(jdkComboBox.getSelectedItem(),
                        "For some reason the user selected an item in the dropdown that does not exist")).getHomePath());
    }

    public void checkValidConfiguration() throws ConfigurationException {
        if (!isNull(PropertiesComponent.getInstance().getValue(JDK_PROPERTY_VALUE))) {
            if (isNull(PropertiesComponent.getInstance().getValue(PLCCToolchain.PLCC_LOCATION_PROPERTY_KEY))) {
                throw new ConfigurationException("Select a PLCC instalation from the drop-down. If none exist, use the drop-down to automatically install one");
            }
        } else {
            throw new ConfigurationException("Select a JDK from the drop-down. If none exist, you can download one locally: https://www.jetbrains.com/help/idea/sdk.html#manage_sdks");
        }
    }
}

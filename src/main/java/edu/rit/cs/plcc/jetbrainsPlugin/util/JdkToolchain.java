package edu.rit.cs.plcc.jetbrainsPlugin.util;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import edu.rit.cs.plcc.jetbrainsPlugin.module.PLCCModuleWizardStep;

import javax.swing.*;
import java.util.Objects;

public class JdkToolchain {

    private final JComboBox jdkComboBox;

    private final PLCCModuleWizardStep wizardStep;

    public JdkToolchain(JComboBox jdkComboBox, PLCCModuleWizardStep wizardStep) {
        this.jdkComboBox = jdkComboBox;
        this.wizardStep = wizardStep;
    }

    public void populateToolchainComboBox() {

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
    }

    private void setSelectedItemAsJdk() {
        wizardStep.setJdk(
                (Sdk) Objects.requireNonNull(jdkComboBox.getSelectedItem(),
                        "For some reason the user selected an item in the dropdown that does not exist"));
    }
}

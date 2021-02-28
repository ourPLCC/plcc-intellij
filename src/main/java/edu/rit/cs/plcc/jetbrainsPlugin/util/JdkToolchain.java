package edu.rit.cs.plcc.jetbrainsPlugin.util;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import edu.rit.cs.plcc.jetbrainsPlugin.module.PLCCModuleWizardStep;
import lombok.val;

import javax.swing.*;
import java.util.Objects;

public class JdkToolchain {

    private final JComboBox<Sdk> jdkComboBox;

    private final PLCCModuleWizardStep wizardStep;

    public JdkToolchain(JComboBox<Sdk> jdkComboBox, PLCCModuleWizardStep wizardStep) {
        this.jdkComboBox = jdkComboBox;
        this.wizardStep = wizardStep;
    }

    public void populateToolchainComboBox() {
        for (val item : ProjectJdkTable.getInstance().getAllJdks()) {
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

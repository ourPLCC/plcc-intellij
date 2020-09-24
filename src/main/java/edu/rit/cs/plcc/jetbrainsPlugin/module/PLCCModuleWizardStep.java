package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

public class PLCCModuleWizardStep extends ModuleWizardStep {
    private final PLCCModuleBuilder builder;

    public PLCCModuleWizardStep(PLCCModuleBuilder plccBuilder) {
        this.builder = plccBuilder;
    }

    @Override
    public JComponent getComponent() {
        return new JLabel("Put your content here");
    }

    @Override
    public void updateDataModel() {

    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.*;

public class PLCCModuleWizardStep extends ModuleWizardStep {

    private final PLCCModuleBuilder builder;

    private final PLCCSdkPanel sdkPanel;

    public PLCCModuleWizardStep(PLCCModuleBuilder plccBuilder) {
        this.builder = plccBuilder;
        sdkPanel = new PLCCSdkPanel();
    }

    @Override
    public JComponent getComponent() {
        return sdkPanel;
    }

    @Override
    public void updateDataModel() {
//        builder.setSdk(sdkPanel.getSdk());
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (sdkPanel.getSdk().isEmpty()) {
            throw new ConfigurationException("Specify redline smalltalk SDK");
        }
        return super.validate();
    }
}

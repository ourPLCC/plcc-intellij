package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.Sdk;

import javax.swing.*;

import static java.util.Objects.isNull;

public class PLCCModuleWizardStep extends ModuleWizardStep {

    private final PLCCModuleBuilder builder;

    private final PLCCSdkPanel sdkPanel;

    private Sdk jdk;

    public PLCCModuleWizardStep(PLCCModuleBuilder plccBuilder) {
        this.builder = plccBuilder;
        sdkPanel = new PLCCSdkPanel(this);
    }

    @Override
    public JComponent getComponent() {
        return sdkPanel;
    }

    @Override
    public void updateDataModel() {
        builder.setModuleJdk(jdk);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (sdkPanel.getPlccLocationEntry().isEmpty()) {
            throw new ConfigurationException("Specify PLCC tool location");
        }
        if (isNull(builder.getModuleJdk())) {
            throw new ConfigurationException("Specify JDK. If none exist, download one on your computer");
        }
        return super.validate();
    }

    public void setJdk(Sdk jdk) {
        this.jdk = jdk;
        updateDataModel();
    }
}

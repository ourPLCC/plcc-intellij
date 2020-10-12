package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PLCCSettingsEditor extends SettingsEditor<PLCCRunConfiguration> {

    @Override
    protected void resetEditorFrom(@NotNull PLCCRunConfiguration s) {

    }

    @Override
    protected void applyEditorTo(@NotNull PLCCRunConfiguration s) throws ConfigurationException {

    }

    @Override
    protected @NotNull JComponent createEditor() {
        return new PLCCConfigurationForm();
    }
}

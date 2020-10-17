package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PLCCSettingsEditor extends SettingsEditor<PLCCRunConfiguration> {

    private PLCCConfigurationGui currConfigGui;

    private final Project project;

    public PLCCSettingsEditor(Project project) {
        super();
        this.project = project;
    }

    @Override
    protected void resetEditorFrom(@NotNull PLCCRunConfiguration s) {

    }

    @Override
    protected void applyEditorTo(@NotNull PLCCRunConfiguration s) throws ConfigurationException {
        currConfigGui.checkValidConfiguration();
    }

    @Override
    protected @NotNull JComponent createEditor() {
        currConfigGui = new PLCCConfigurationGui(project);
        return currConfigGui;
    }
}

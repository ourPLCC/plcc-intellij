package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

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

    // Discard all non-confirmed user changes made via the UI to the RunConfiguration object
    @Override
    protected void resetEditorFrom(@NotNull PLCCRunConfiguration dataObject) {
        currConfigGui.setPlccFile(dataObject.getPlccFile());
    }

    // Confirm the changes, i.e. copy current UI state into the target settings object.
    @Override
    protected void applyEditorTo(@NotNull PLCCRunConfiguration dataObject) {
        dataObject.setPlccFile(currConfigGui.getPlccFile());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        currConfigGui = new PLCCConfigurationGui(project);
        return currConfigGui;
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PLCCRunConfiguration extends RunConfigurationBase<RunConfigurationEntity> {

    private String jdkPath;

    private String plccFile;

    protected PLCCRunConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new PLCCSettingsEditor(getProject());
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new PLCCCommandLineState(environment, this);
    }

    public void setJdkPath(String jdkPath) {
        this.jdkPath = jdkPath;
    }

    public void setPlccFile(String plccFile) {
        this.plccFile = plccFile;
    }

    public String getJdkPath() {
        return jdkPath;
    }

    public String getPlccFile() {
        return plccFile;
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class PLCCConfigurationFactory extends ConfigurationFactory {

    protected PLCCConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new PLCCRunConfiguration(project, this, null);
    }

    @Override
    public @NotNull String getId() {
        return "Plcc";
    }
}

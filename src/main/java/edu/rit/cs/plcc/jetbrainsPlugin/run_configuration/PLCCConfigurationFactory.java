package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PLCCConfigurationFactory extends ConfigurationFactory {

    public PLCCConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    // called once per project
    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new PLCCRunConfiguration(project, this, null);
    }

    /**
     * Overriding this because we want the user to be presented with the current location and name of their PLCC file
     * whenever they want to create a new run configuration, not just the first time a run configuration is created.
     * params and return value meanings stay the same.
     */
    @Override
    public @NotNull RunConfiguration createConfiguration(@NlsSafe @Nullable String name, @NotNull RunConfiguration template) {
        val newConfiguration = (PLCCRunConfiguration)super.createConfiguration(name, template);
        newConfiguration.inferCurrentPlccFile();
        return newConfiguration;
    }

    @Override
    public @NotNull String getId() {
        return "Plcc";
    }
}

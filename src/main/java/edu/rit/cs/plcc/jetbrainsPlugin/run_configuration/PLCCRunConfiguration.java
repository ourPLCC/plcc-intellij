package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import lombok.val;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.isNull;

public class PLCCRunConfiguration extends RunConfigurationBase<RunConfigurationEntity> {

    private String plccFile;

    private static final String PLCC_CONFIGS_ELEMENT = "plcc";
    private static final String PLCC_FILE_ELEMENT_FIELD = "plccFile";

    protected PLCCRunConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
        plccFile = "";
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new PLCCSettingsEditor(getProject());
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) {
        return new PLCCCommandLineState(environment, this);
    }

    public void setPlccFile(String plccFile) {
        this.plccFile = plccFile;
    }

    public String getPlccFile() {
        return plccFile;
    }

    @Override
    public void writeExternal(@NotNull Element element) {
        super.writeExternal(element);

        Element plccParent = element.getChild(PLCC_CONFIGS_ELEMENT);

        // Create a new parent when there is no parent present.
        if (plccParent == null) {
            plccParent = new Element(PLCC_CONFIGS_ELEMENT);
            element.addContent(plccParent);
        }
        else {
            // Otherwise overwrite (remove + write).
            plccParent.removeContent();
        }

        val fileElem = new Element(PLCC_FILE_ELEMENT_FIELD);
        fileElem.setText(plccFile);
        plccParent.addContent(fileElem);
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);

        val parent = element.getChild(PLCC_CONFIGS_ELEMENT);
        if (isNull(parent)) {
            return;
        }

        plccFile = parent.getChildText(PLCC_FILE_ELEMENT_FIELD);
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.gutter;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCFileType;
import edu.rit.cs.plcc.jetbrainsPlugin.run_configuration.PLCCConfigurationFactory;
import edu.rit.cs.plcc.jetbrainsPlugin.run_configuration.PLCCConfigurationType;
import edu.rit.cs.plcc.jetbrainsPlugin.run_configuration.PLCCRunConfiguration;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public class PlccRunConfigurationProducer extends LazyRunConfigurationProducer<PLCCRunConfiguration> {

    public PlccRunConfigurationProducer() {
        super();
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull PLCCRunConfiguration plccRunConfiguration, @NotNull ConfigurationContext configurationContext, @NotNull Ref<PsiElement> ref) {
        val location = configurationContext.getLocation();
        if (location == null) {
            return false;
        }
        val container = location.getPsiElement().getContainingFile();
        if (container == null) {
            return false;
        }
        val mainFile = container.getVirtualFile();
        if (mainFile == null) {
            return false;
        }

        // Make sure the current file is a PLCC file
        val currFileExtension = mainFile.getExtension();
        val plccExtension = PLCCFileType.INSTANCE.getDefaultExtension();
        if (currFileExtension == null || !currFileExtension.equalsIgnoreCase(plccExtension)) {
            return false;
        }

        plccRunConfiguration.setPlccFile(mainFile.getPath());
        return true;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull PLCCRunConfiguration plccRunConfiguration, @NotNull ConfigurationContext configurationContext) {
        return true;
    }

    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
        return new PLCCConfigurationFactory(PLCCConfigurationType.getInstance());
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PLCCModuleBuilder extends ModuleBuilder implements ModuleBuilderListener {

    private Sdk plccSdk;

    @Override
    public ModuleType<?> getModuleType() {
        return PLCCModuleType.getInstance();
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        Project project = modifiableRootModel.getProject();
        var contentEntry = doAddContentEntry(modifiableRootModel);
        assert contentEntry != null;
        VirtualFile root = contentEntry.getFile();
        assert root != null;
        try {
            VirtualFile vf = root.createChildDirectory(this, "plcc");
            contentEntry.addSourceFolder(vf, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moduleCreated(@NotNull Module module) {
        // this does not hit for some reason because it is being blocked?
        System.out.println("HELLO, " + module.getName());
    }

    public void setSdk(Sdk sdk) {
        plccSdk = sdk;
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[] {new PLCCModuleWizardStep(this)};
    }
}

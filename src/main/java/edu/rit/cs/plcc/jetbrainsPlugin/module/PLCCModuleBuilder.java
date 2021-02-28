package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ModifiableRootModel;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class PLCCModuleBuilder extends ModuleBuilder implements ModuleBuilderListener {

    @Override
    public ModuleType<?> getModuleType() {
        return PLCCModuleType.getInstance();
    }

    @Override
    public @Nullable ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new PLCCModuleWizardStep(this);
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) {
        val project = modifiableRootModel.getProject();
        val contentEntry = doAddContentEntry(modifiableRootModel);
        assert contentEntry != null;
        val root = contentEntry.getFile();
        assert root != null;
        try {
            val plcc = root.createChildDirectory(this, "plcc");
            plcc.createChildData(this, project.getName().concat(".plcc"));
            val javaDir = plcc.createChildDirectory(this, "Java");
            contentEntry.addSourceFolder(javaDir, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        modifiableRootModel.setSdk(myJdk);
    }

    @Override
    public void moduleCreated(@NotNull Module module) {
        // this does not hit for some reason because it is being blocked?
    }

}

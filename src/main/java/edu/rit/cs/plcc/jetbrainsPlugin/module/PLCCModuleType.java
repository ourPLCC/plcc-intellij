package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PlccIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PLCCModuleType extends ModuleType<PLCCModuleBuilder> {

    // must be the same as "id" of moduleType extension in plugin.xml
    private static final String ID = "PLCC_MODULE_TYPE";

    public PLCCModuleType() {
        super(ID);
    }

    public static PLCCModuleType getInstance() {
        return (PLCCModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public PLCCModuleBuilder createModuleBuilder() {
        return new PLCCModuleBuilder();
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getName() {
        return "PLCC";
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getDescription() {
        return "Adds support for the PLCC tool";
    }

    @Override
    public @NotNull Icon getNodeIcon(boolean isOpened) {
        return PlccIcons.x16;
    }
}

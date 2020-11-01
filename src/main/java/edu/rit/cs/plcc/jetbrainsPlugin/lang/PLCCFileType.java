package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PlccIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PLCCFileType extends LanguageFileType {

    public static final PLCCFileType INSTANCE = new PLCCFileType();

    private PLCCFileType() {
        super(PLCCLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "PLCC File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PLCC language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "plcc";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return PlccIcons.x16;
    }

}

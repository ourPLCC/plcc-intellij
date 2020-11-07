package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import edu.rit.cs.plcc.jetbrainsPlugin.util.IJavaIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IJavaFileType extends LanguageFileType {

    public static final IJavaFileType INSTANCE = new IJavaFileType();

    protected IJavaFileType() {
        super(IJavaLanguage.INSTANCE);
    }

    @Override
    public @NotNull String getName() {
        return "IJava File";
    }

    @Override
    public @NotNull @NlsContexts.Label String getDescription() {
        return "IJava partial java files";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return ".ijava";
    }

    @Override
    public @Nullable Icon getIcon() {
        return IJavaIcons.x16;
    }
}

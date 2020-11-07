package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.IJavaFileType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.IJavaLanguage;
import org.jetbrains.annotations.NotNull;

public class IJavaFile extends PsiFileBase {

    public IJavaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, IJavaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return IJavaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "IJava File";
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model;


import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCFileType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCLanguage;
import org.jetbrains.annotations.NotNull;

public class PLCCFile extends PsiFileBase {

    public PLCCFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, PLCCLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PLCCFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "PLCC File";
    }

}

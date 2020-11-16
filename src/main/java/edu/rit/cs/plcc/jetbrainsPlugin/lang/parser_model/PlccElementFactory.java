package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCFileType;

public class PlccElementFactory {

    public static PLCCTokenDef createProperty(Project project, String name) {
        final PLCCFile file = createFile(project, name);
        return (PLCCTokenDef) file.getFirstChild();
    }

    public static PLCCFile createFile(Project project, String text) {
        String name = "dummy.plcc";
        return (PLCCFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, PLCCFileType.INSTANCE, text);
    }

}

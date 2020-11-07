package edu.rit.cs.plcc.jetbrainsPlugin.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.PsiDirectory;
import edu.rit.cs.plcc.jetbrainsPlugin.util.IJavaIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class NewIJavaFile extends CreateFileFromTemplateAction {

    public NewIJavaFile() {
        super("New IJava File", "Create a new IJava file", IJavaIcons.x16);
    }

    public NewIJavaFile(@NlsActions.ActionText String text, @NlsActions.ActionDescription String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        builder.addKind("File", IJavaIcons.x16, "IJava File");
    }

    @Override
    protected @NlsContexts.Command String getActionName(PsiDirectory directory, @NotNull String newName, String templateName) {
        return null;
    }
}

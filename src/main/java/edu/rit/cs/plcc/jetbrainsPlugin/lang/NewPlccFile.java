package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.PsiDirectory;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PlccIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class NewPlccFile extends CreateFileFromTemplateAction {

    public NewPlccFile() {
        super("New PLCC File", "Create a new PLCC file", PlccIcons.x16);
    }

    public NewPlccFile(@NlsActions.ActionText String text, @NlsActions.ActionDescription String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        builder.addKind("File", PlccIcons.x16, "PLCC File");
    }

    @Override
    protected @NlsContexts.Command String getActionName(PsiDirectory directory, @NotNull String newName, String templateName) {
        return null;
    }
}

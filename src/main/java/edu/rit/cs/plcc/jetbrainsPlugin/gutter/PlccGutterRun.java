package edu.rit.cs.plcc.jetbrainsPlugin.gutter;

import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.psi.PsiElement;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static edu.rit.cs.plcc.jetbrainsPlugin.util.Icons.runIcon;

// The user should have to only click the run button to run, not select the first item in the list after clicking it.
public class PlccGutterRun extends RunLineMarkerContributor {

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement psiElement) {
        // Only show one icon at the top of the file where the first regex definition is
        val elementType = psiElement.getNode().getElementType();
        if (elementType != PLCCTypes.SINGLE_MATCH_RULE) {
            return null;
        }

        val grammarRulesSection = psiElement.getParent().getParent().getParent();
        val firstGrammarRule = grammarRulesSection.getFirstChild();
        val currentGrammarRule = psiElement.getParent().getParent();
        if (!currentGrammarRule.equals(firstGrammarRule)) {
            return null;
        }

        val fileName = psiElement.getContainingFile().getName();

        // call setup run configuration context on the run configuration producer and call execute on the run configuration returned. wrap all that up in an anaction

        return new Info(
                runIcon,
                x -> "Run ".concat(fileName).concat(" with PLCC"),
                new RunFileFromGutterAction()
        );
    }
}

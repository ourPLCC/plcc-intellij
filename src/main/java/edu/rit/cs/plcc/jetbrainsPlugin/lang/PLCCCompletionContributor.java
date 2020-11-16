package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import org.jetbrains.annotations.NotNull;

public class PLCCCompletionContributor extends CompletionContributor {

    public PLCCCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(PLCCTypes.TOKEN),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("token"));
                    }
                }
        );
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        super.fillCompletionVariants(parameters, result);
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlccFindUsagesProvider implements FindUsagesProvider {

    @Override
    public @Nullable WordsScanner getWordsScanner() {
       return new DefaultWordsScanner(
               new PLCCLexerAdapter(),
               TokenSet.create(PLCCTypes.TOKEN_TYPE_NAME, PLCCTypes.GRAMMAR_DEF_NAME, PLCCTypes.GRAMMAR_USAGE_NAME),
               TokenSet.EMPTY,
               TokenSet.EMPTY
       );
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Override
    public @Nullable String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    public @Nls @NotNull String getType(@NotNull PsiElement element) {
        //TODO specify which elements
        return "plcc property";
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        return "Need to get key";
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return "Need to get node text";
    }
}

package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PlccNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class PlccNamedElementImpl extends ASTWrapperPsiElement implements PlccNamedElement {

    public PlccNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

}

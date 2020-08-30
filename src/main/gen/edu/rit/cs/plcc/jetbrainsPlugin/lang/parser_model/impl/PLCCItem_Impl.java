// This is a generated file. Not intended for manual editing.
package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.*;

public class PLCCItem_Impl extends ASTWrapperPsiElement implements PLCCItem_ {

  public PLCCItem_Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PLCCVisitor visitor) {
    visitor.visitItem_(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PLCCVisitor) accept((PLCCVisitor)visitor);
    else super.accept(visitor);
  }

}

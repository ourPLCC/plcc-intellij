// This is a generated file. Not intended for manual editing.
package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl.*;

public interface PLCCTypes {

  IElementType ITEM_ = new PLCCElementType("ITEM_");

  IElementType COMMENT = new PLCCTokenType("COMMENT");
  IElementType SECTION_SEPERATOR = new PLCCTokenType("SECTION_SEPERATOR");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ITEM_) {
        return new PLCCItem_Impl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

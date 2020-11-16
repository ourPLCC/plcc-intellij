package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.*;

public class PlccPsiImplUtil {

    public static String getIncludeFileName(PLCCIncludeStatement element) {
        ASTNode keyNode = element.getNode().findChildByType(PLCCTypes.FILE_NAME);
        if (keyNode != null) {
            return keyNode.getText();
        } else {
            return null;
        }
    }

    // PLCCTokenDef
    public static String getName(PLCCTokenDef tokenDef) {
        ASTNode valueNode = tokenDef.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (valueNode != null) {
            System.out.println("Getting nametd: " + valueNode.getText());
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static PsiElement getNameIdentifier(PLCCTokenDef element) {
        System.out.println("Getting name identifiertd");
        ASTNode keyNode = element.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(PLCCTokenDef element, String newName) {
        System.out.println("Setting nametd");
        ASTNode keyNode = element.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (keyNode != null) {
            var tokenDef = PlccElementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = tokenDef.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }
    //

    public static String getName(PLCCUncapturedTokenType tokenDef) {
        ASTNode valueNode = tokenDef.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (valueNode != null) {
            System.out.println("Getting nameutt: " + valueNode.getText());
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static PsiElement getNameIdentifier(PLCCUncapturedTokenType element) {
        System.out.println("Getting name identifierutt");
        ASTNode keyNode = element.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(PLCCUncapturedTokenType element, String newName) {
        System.out.println("Setting nameutt");
        ASTNode keyNode = element.getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
        if (keyNode != null) {
//            var tokenDef = PlccElementFactory.createProperty(element.getProject(), newName);
//            ASTNode newKeyNode = tokenDef.getFirstChild().getNode();
//            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }





}

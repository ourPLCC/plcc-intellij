package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model;

import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCLanguage;
import org.jetbrains.annotations.NotNull;

public class IJavaTokenType extends IElementType {

    public IJavaTokenType(@NotNull String debugName) {
        super(debugName, PLCCLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "IJavaTokenType." + super.toString();
    }

}

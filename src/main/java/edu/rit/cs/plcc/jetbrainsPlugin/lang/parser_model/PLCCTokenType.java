package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model;

import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCLanguage;
import org.jetbrains.annotations.NotNull;

public class PLCCTokenType extends IElementType {

    public PLCCTokenType(@NotNull String debugName) {
        super(debugName, PLCCLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "PLCCTokenType." + super.toString();
    }

}

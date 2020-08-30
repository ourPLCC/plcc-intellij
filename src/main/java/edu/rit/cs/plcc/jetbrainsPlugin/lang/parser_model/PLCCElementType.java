package edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model;


import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.PLCCLanguage;
import org.jetbrains.annotations.NotNull;

public class PLCCElementType extends IElementType {

    public PLCCElementType(@NotNull String debugName) {
        super(debugName, PLCCLanguage.INSTANCE);
    }

}

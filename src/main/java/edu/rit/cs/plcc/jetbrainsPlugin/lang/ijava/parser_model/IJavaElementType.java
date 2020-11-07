package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model;

import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.IJavaLanguage;
import org.jetbrains.annotations.NotNull;

public class IJavaElementType extends IElementType {

    public IJavaElementType(@NotNull String debugName) {
        super(debugName, IJavaLanguage.INSTANCE);
    }

}

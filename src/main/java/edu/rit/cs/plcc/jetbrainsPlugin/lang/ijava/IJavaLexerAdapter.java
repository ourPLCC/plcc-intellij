package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava;

import com.intellij.lexer.FlexAdapter;

public class IJavaLexerAdapter extends FlexAdapter {

    public IJavaLexerAdapter() {
        super(new IJavaLexer(null));
    }

}

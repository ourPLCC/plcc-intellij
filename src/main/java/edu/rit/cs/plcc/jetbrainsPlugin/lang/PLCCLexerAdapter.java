package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lexer.FlexAdapter;

public class PLCCLexerAdapter extends FlexAdapter {

    public PLCCLexerAdapter() {
        super(new PLCCLexer(null));
    }

}

package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lang.Language;

public class PLCCLanguage extends Language {

    public static final PLCCLanguage INSTANCE = new PLCCLanguage();

    private PLCCLanguage() {
        super("PLCC");
    }
}

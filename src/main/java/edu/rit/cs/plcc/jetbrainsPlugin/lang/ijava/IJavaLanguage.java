package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava;

import com.intellij.lang.Language;

public class IJavaLanguage extends Language {

    public static final IJavaLanguage INSTANCE = new IJavaLanguage();

    private IJavaLanguage() {
        super("IJava");
    }
}

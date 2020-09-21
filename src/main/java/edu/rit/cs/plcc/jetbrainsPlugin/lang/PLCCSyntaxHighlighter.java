package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class PLCCSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey TOKEN =
            createTextAttributesKey("PLCC_TOKEN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey SKIP =
            createTextAttributesKey("PLCC_SKIP", DefaultLanguageHighlighterColors.KEYWORD);

    private static final TextAttributesKey[] TOKEN_KEYS = new TextAttributesKey[] {TOKEN};
    private static final TextAttributesKey[] SKIP_KEYS = new TextAttributesKey[] {SKIP};

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new PLCCLexerAdapter();
    }

    @Override
    public @NotNull TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(PLCCTypes.TOKEN)) {
            return TOKEN_KEYS;
        } else if (tokenType.equals(PLCCTypes.SKIP)) {
            return SKIP_KEYS;
        }

        else {
            return EMPTY_KEYS;
        }
    }
}

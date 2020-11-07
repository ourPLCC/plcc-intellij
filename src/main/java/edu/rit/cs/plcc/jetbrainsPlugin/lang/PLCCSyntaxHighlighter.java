package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTokenType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class PLCCSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey TOKEN =
            createTextAttributesKey("PLCC_TOKEN", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey SKIP =
            createTextAttributesKey("PLCC_SKIP", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey REGEX =
            createTextAttributesKey("PLCC_REGEX", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("PLCC_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey TOKEN_TYPE_NAME =
            createTextAttributesKey("PLCC_TOKEN_TYPE_NAME", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey SINGLE_MATCH_RULE =
            createTextAttributesKey("PLCC_SINGLE_MATCH", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey ANY_MATCH_RULE =
            createTextAttributesKey("PLCC_ANY_MATCH", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey INCLUDE =
            createTextAttributesKey("PLCC_INCLUDE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey FILE_NAME =
            createTextAttributesKey("PLCC_FILE_NAME", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey PLUS =
            createTextAttributesKey("PLCC_PLUS", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey GRAMMAR_DEF_NAME =
            createTextAttributesKey("PLCC_GRAMMAR_DEF_NAME", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey GRAMMAR_USAGE_NAME =
            createTextAttributesKey("PLCC_GRAMMAR_USAGE_NAME", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey LEFT_ANGLE_BRACKET =
            createTextAttributesKey("PLCC_LEFT_ANGLE_BRACKET", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey RIGHT_ANGLE_BRACKET =
            createTextAttributesKey("PLCC_RIGHT_ANGLE_BRACKET", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey COLON =
            createTextAttributesKey("PLCC_COLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey GRAMMAR_NAME_CLASS_NAME =
            createTextAttributesKey("PLCC_GRAMMAR_NAME_CLASS_NAME", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey CODE_BLOCK_BOUNDARY =
            createTextAttributesKey("PLCC_CODE_BLOCK_BOUNDARY", DefaultLanguageHighlighterColors.BRACES);

    private static final TextAttributesKey[] TOKEN_KEYS = new TextAttributesKey[] {TOKEN};
    private static final TextAttributesKey[] SKIP_KEYS = new TextAttributesKey[] {SKIP};
    private static final TextAttributesKey[] REGEX_KEYS = new TextAttributesKey[] {REGEX};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
    private static final TextAttributesKey[] TOKEN_TYPE_NAME_KEYS = new TextAttributesKey[] {TOKEN_TYPE_NAME};
    private static final TextAttributesKey[] SINGLE_MATCH_RULE_KEYS = new TextAttributesKey[] {SINGLE_MATCH_RULE};
    private static final TextAttributesKey[] ANY_MATCH_RULE_KEYS = new TextAttributesKey[] {ANY_MATCH_RULE};
    private static final TextAttributesKey[] INCLUDE_KEYS = new TextAttributesKey[] {INCLUDE};
    private static final TextAttributesKey[] FILE_NAME_KEYS = new TextAttributesKey[] {FILE_NAME};
    private static final TextAttributesKey[] PLUS_KEYS = new TextAttributesKey[] {PLUS};
    private static final TextAttributesKey[] GRAMMAR_DEF_NAME_KEYS = new TextAttributesKey[] {GRAMMAR_DEF_NAME};
    private static final TextAttributesKey[] GRAMMAR_USAGE_NAME_KEYS = new TextAttributesKey[] {GRAMMAR_USAGE_NAME};
    private static final TextAttributesKey[] LEFT_ANGLE_BRACKET_KEYS = new TextAttributesKey[] {LEFT_ANGLE_BRACKET};
    private static final TextAttributesKey[] RIGHT_ANGLE_BRACKET_KEYS = new TextAttributesKey[] {RIGHT_ANGLE_BRACKET};
    private static final TextAttributesKey[] COLON_KEYS = new TextAttributesKey[] {COLON};
    private static final TextAttributesKey[] GRAMMAR_NAME_CLASS_NAME_KEYS = new TextAttributesKey[] {GRAMMAR_NAME_CLASS_NAME};
    private static final TextAttributesKey[] CODE_BLOCK_BOUNDARY_KEYS = new TextAttributesKey[] {CODE_BLOCK_BOUNDARY};

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(PLCCTypes.TOKEN)) {
            return TOKEN_KEYS;
        } else if (tokenType.equals(PLCCTypes.SKIP)) {
            return SKIP_KEYS;
        } else if (tokenType.equals(PLCCTypes.REGEX)) {
            return REGEX_KEYS;
        } else if (tokenType.equals(PLCCTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(PLCCTypes.TOKEN_TYPE_NAME)) {
            return TOKEN_TYPE_NAME_KEYS;
        } else if (tokenType.equals(PLCCTypes.SINGLE_MATCH_RULE)) {
            return SINGLE_MATCH_RULE_KEYS;
        } else if (tokenType.equals(PLCCTypes.ANY_MATCH_RULE)) {
            return ANY_MATCH_RULE_KEYS;
        } else if (tokenType.equals(PLCCTypes.INCLUDE)) {
            return INCLUDE_KEYS;
        } else if (tokenType.equals(PLCCTypes.FILE_NAME)) {
            return FILE_NAME_KEYS;
        } else if (tokenType.equals(PLCCTypes.PLUS)) {
            return PLUS_KEYS;
        } else if (tokenType.equals(PLCCTypes.GRAMMAR_DEF_NAME)) {
            return GRAMMAR_DEF_NAME_KEYS;
        } else if (tokenType.equals(PLCCTypes.GRAMMAR_USAGE_NAME)) {
            return GRAMMAR_USAGE_NAME_KEYS;
        } else if (tokenType.equals(PLCCTypes.LEFT_ANGLE_BRACKET)) {
            return LEFT_ANGLE_BRACKET_KEYS;
        } else if (tokenType.equals(PLCCTypes.RIGHT_ANGLE_BRACKET)) {
            return RIGHT_ANGLE_BRACKET_KEYS;
        } else if (tokenType.equals(PLCCTypes.COLON)) {
            return COLON_KEYS;
        } else if (tokenType.equals(PLCCTypes.GRAMMAR_NAME_CLASS_NAME)) {
            return GRAMMAR_NAME_CLASS_NAME_KEYS;
        } else if (tokenType.equals(PLCCTypes.CODE_BLOCK_BOUNDARY)) {
            return CODE_BLOCK_BOUNDARY_KEYS;
        }

        else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        else {
            return EMPTY_KEYS;
        }
    }

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new PLCCLexerAdapter();
    }
}

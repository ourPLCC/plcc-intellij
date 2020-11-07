package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model.IJavaFile;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model.IJavaParser;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model.IJavaTypes;
import org.jetbrains.annotations.NotNull;

public class IJavaParserDefinition implements ParserDefinition {

    public static final TokenSet WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE);

    public static final IFileElementType FILE = new IFileElementType(IJavaLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new IJavaLexerAdapter();
    }

    @Override
    public @NotNull TokenSet getWhitespaceTokens() {
        return WHITE_SPACE;
    }

    @Override
    public PsiParser createParser(Project project) {
        return new IJavaParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return IJavaTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new IJavaFile(viewProvider);
    }
}

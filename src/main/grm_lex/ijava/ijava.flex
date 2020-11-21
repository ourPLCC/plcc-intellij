package edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.ijava.parser_model.IJavaTypes;
import com.intellij.psi.TokenType;

%%

%class IJavaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}


WHITESPACE = \s

UPPER_CAMEL_CASE_NAME = [A-Z][a-zA-Z0-9]*

CODE_BLOCK_BOUNDARY = %%%

JAVA_LINE = .+

%state IN_CODE_BLOCK

%%

<YYINITIAL> {
    {UPPER_CAMEL_CASE_NAME} {
          return IJavaTypes.GRAMMAR_NAME_CLASS_NAME;
      }
    {CODE_BLOCK_BOUNDARY} {
          yybegin(IN_CODE_BLOCK);
          return IJavaTypes.CODE_BLOCK_BOUNDARY;
      }
}

<IN_CODE_BLOCK> {
    {CODE_BLOCK_BOUNDARY} {
          yybegin(YYINITIAL);
          return IJavaTypes.CODE_BLOCK_BOUNDARY;
      }
    {JAVA_LINE} {
          return IJavaTypes.JAVA_LINE;
      }
}

{WHITESPACE} {return TokenType.WHITE_SPACE; }
[^] { return TokenType.BAD_CHARACTER; }

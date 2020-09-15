package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import com.intellij.psi.TokenType;

%%

%class PLCCLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

EOL = (\r\n|\r|\n)
WHITESPACE_EXCEPT_NEWLINE = [^\S\r\n]
IDENTIFIER = [A-Za-z]\w*

COMMENT = #.*
SECTION_SEPERATOR = %

TOKEN = token
SKIP = skip
REGEX = (\'|\")\S+(\'|\")


// We are using YYINITIAL to be the lexical specification defining state
%state GRAMMAR_RULES
%state JAVA_INCLUDE

%%

<YYINITIAL> {
    {TOKEN} {
          return PLCCTypes.TOKEN;
      }
    {SKIP} {
          return PLCCTypes.SKIP;
      }
    {REGEX} {
          return PLCCTypes.REGEX;
      }
    {IDENTIFIER} {
          return PLCCTypes.IDENTIFIER;
      }
    {SECTION_SEPERATOR} {
          yybegin(GRAMMAR_RULES);
          return PLCCTypes.SECTION_SEPERATOR;
      }
}

<GRAMMAR_RULES> {
    {SECTION_SEPERATOR} {
          yybegin(JAVA_INCLUDE);
          return PLCCTypes.SECTION_SEPERATOR;
    }

}

({EOL}|{WHITESPACE_EXCEPT_NEWLINE})+ { return TokenType.WHITE_SPACE; }

{COMMENT} { return PLCCTypes.COMMENT; }

[^] { return TokenType.BAD_CHARACTER; }
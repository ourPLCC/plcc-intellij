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

EOL = ([\r\n]|\r|\n)
EOLS = {EOL}+
WHITESPACE_EXCEPT_NEWLINE = [^\S\r\n]
WHITESPACES_EXCEPT_NEWLINE = {WHITESPACE_EXCEPT_NEWLINE}+
IDENTIFIER = [A-Za-z]\w*

COMMENT = #.*
SECTION_SEPERATOR = %

TOKEN = token
SKIP = skip
REGEX = (\'|\")\S+(\'|\")

GRAMMAR_NAME = <{IDENTIFIER}>
GRAMMAR_NAME_ABSTRACT = {GRAMMAR_NAME}:{IDENTIFIER}

SINGLE_MATCH_RULE = ::=
ANY_MATCH_RULE = \*\*=

ANY_MATCH_SEPERATOR = \+{IDENTIFIER}


// We are using YYINITIAL to be the lexical specification defining state
%state GRAMMAR_RULES
%state JAVA_INCLUDE
//%state EOL_WAIT

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
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          return PLCCTypes.EOLS;
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
    {IDENTIFIER} {
          return PLCCTypes.IDENTIFIER;
      }
    {GRAMMAR_NAME} {
          return PLCCTypes.GRAMMAR_NAME;
      }
    {GRAMMAR_NAME_ABSTRACT} {
          return PLCCTypes.GRAMMAR_NAME_ABSTRACT;
      }
    {SINGLE_MATCH_RULE} {
          return PLCCTypes.SINGLE_MATCH_RULE;
      }
    {ANY_MATCH_RULE} {
          return PLCCTypes.ANY_MATCH_RULE;
      }
    {ANY_MATCH_SEPERATOR} {
          return PLCCTypes.ANY_MATCH_SEPERATOR;
      }
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          return PLCCTypes.EOLS;
      }
}

//<EOL_WAIT> {
//    {EOL} {
//          yybegin(GRAMMAR_RULES);
//          return PLCCTypes.EOL;
//      }
//    {IDENTIFIER} {
//
//      }
//    {GRAMMAR_NAME} {
//
//      }
//}

//({EOL}|{WHITESPACE_EXCEPT_NEWLINE})+ { return TokenType.WHITE_SPACE; }

{COMMENT} { return PLCCTypes.COMMENT; }

[^] { return TokenType.BAD_CHARACTER; }
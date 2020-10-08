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
REGEX = (\'|\").+(\'|\")

LEFT_ANGLE_BRACKET = <
RIGHT_ANGLE_BRACKET = >

//FULL_GRAMMAR_NAME = {LEFT_ANGLE_BRACKET}{IDENTIFIER}{RIGHT_ANGLE_BRACKET}
//GRAMMAR_NAME_ABSTRACT = {FULL_GRAMMAR_NAME}{COLON}{INSTANTIATED_NAME}
INSTANTIATED_NAME = [A-Z][a-zA-Z0-9]*

SINGLE_MATCH_RULE = ::=
ANY_MATCH_RULE = \*\*=

PLUS = \+

INCLUDE = include

TOKEN_TYPE_NAME = [A-Z][A-Z0-9]*

FILE_NAME = {IDENTIFIER}

COLON = :

LOWER_CAMEL_CASE_NAME = [a-z][a-zA-Z0-9]*

// We are using YYINITIAL to be the lexical specification defining state
%state GRAMMAR_RULE_LHS
%state JAVA_INCLUDE
%state GRAMMAR_RULE_RHS

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
    {TOKEN_TYPE_NAME} {
          return PLCCTypes.TOKEN_TYPE_NAME;
      }
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          return PLCCTypes.EOLS;
      }
    {SECTION_SEPERATOR} {
          yybegin(GRAMMAR_RULE_LHS);
          return PLCCTypes.SECTION_SEPERATOR;
      }
}

<GRAMMAR_RULE_LHS> {
    {SECTION_SEPERATOR} {
          yybegin(JAVA_INCLUDE);
          return PLCCTypes.SECTION_SEPERATOR;
    }
    {COLON} {
          return PLCCTypes.COLON;
      }
    {INSTANTIATED_NAME} {
          return PLCCTypes.INSTANTIATED_NAME;
      }
    {LEFT_ANGLE_BRACKET} {
          return PLCCTypes.LEFT_ANGLE_BRACKET;
      }
    {RIGHT_ANGLE_BRACKET} {
          return PLCCTypes.RIGHT_ANGLE_BRACKET;
      }
    {SINGLE_MATCH_RULE} {
          yybegin(GRAMMAR_RULE_RHS);
          return PLCCTypes.SINGLE_MATCH_RULE;
      }
    {ANY_MATCH_RULE} {
          yybegin(GRAMMAR_RULE_RHS);
          return PLCCTypes.ANY_MATCH_RULE;
      }
    {LOWER_CAMEL_CASE_NAME} {
          return PLCCTypes.GRAMMAR_DEF_NAME;
      }
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          return PLCCTypes.EOLS;
      }
}

<GRAMMAR_RULE_RHS> {
    {PLUS} {
          return PLCCTypes.PLUS;
      }
    {LEFT_ANGLE_BRACKET} {
          return PLCCTypes.LEFT_ANGLE_BRACKET;
      }
    {RIGHT_ANGLE_BRACKET} {
          return PLCCTypes.RIGHT_ANGLE_BRACKET;
      }
    {LOWER_CAMEL_CASE_NAME} {
          return PLCCTypes.GRAMMAR_USAGE_NAME;
      }
    {TOKEN_TYPE_NAME} {
          return PLCCTypes.TOKEN_TYPE_NAME;
      }
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          yybegin(GRAMMAR_RULE_LHS);
          return PLCCTypes.EOLS;
      }
}


<JAVA_INCLUDE> {
    {INCLUDE} {
          return PLCCTypes.INCLUDE;
      }
    {FILE_NAME} {
          return PLCCTypes.FILE_NAME;
      }
    {WHITESPACES_EXCEPT_NEWLINE} {
          return PLCCTypes.SPACES;
      }
    {EOLS} {
          return PLCCTypes.EOLS;
      }
}

{COMMENT} { return PLCCTypes.COMMENT; }

[^] { return TokenType.BAD_CHARACTER; }
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
//EOLS = {EOL}+
//WHITESPACE_EXCEPT_NEWLINE = [^\S\r\n]
//WHITESPACES_EXCEPT_NEWLINE = {WHITESPACE_EXCEPT_NEWLINE}+

WHITESPACE = \s
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
UPPER_CAMEL_CASE_NAME = [A-Z][a-zA-Z0-9]*

SINGLE_MATCH_RULE = ::=
ANY_MATCH_RULE = \*\*=

PLUS = \+

INCLUDE = include

TOKEN_TYPE_NAME = [A-Z][A-Z0-9]*

FILE_NAME = {IDENTIFIER}

COLON = :

LOWER_CAMEL_CASE_NAME = [a-z][a-zA-Z0-9]*

CODE_BLOCK_BOUNDARY = %%%

JAVA_CODE = [^%]+

// We are using YYINITIAL to be the lexical specification defining state
%state GRAMMAR_RULE_LHS
%state JAVA_INCLUDE
%state GRAMMAR_RULE_RHS
%state IN_CODE_BLOCK
%state IN_INCLUDE

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
    {UPPER_CAMEL_CASE_NAME} {
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
    {EOL} {
          yybegin(GRAMMAR_RULE_LHS);
          return TokenType.WHITE_SPACE;
      }
}


<JAVA_INCLUDE> {
    {INCLUDE} {
          yybegin(IN_INCLUDE);
          return PLCCTypes.INCLUDE;
      }
    {UPPER_CAMEL_CASE_NAME} {
          return PLCCTypes.GRAMMAR_NAME_CLASS_NAME;
      }
    {CODE_BLOCK_BOUNDARY} {
          yybegin(IN_CODE_BLOCK);
          return PLCCTypes.CODE_BLOCK_BOUNDARY;
      }
}
<IN_INCLUDE> {
    {FILE_NAME} {
          yybegin(JAVA_INCLUDE);
          return PLCCTypes.FILE_NAME;
      }
}
<IN_CODE_BLOCK> {
    {CODE_BLOCK_BOUNDARY} {
          yybegin(JAVA_INCLUDE);
          return PLCCTypes.CODE_BLOCK_BOUNDARY;
      }
    {JAVA_CODE} {
          return PLCCTypes.JAVA_CODE;
      }
}

{COMMENT} { return PLCCTypes.COMMENT; }
{WHITESPACE} {return TokenType.WHITE_SPACE; }

[^] { return TokenType.BAD_CHARACTER; }
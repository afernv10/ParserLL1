// Code, imports and packages
package lexLL1;

/* 	
	Autor: Ander J. Fernández Vega
	Practica: Practica 4 - Analizador Sintáctico Descendente Recursivo
*/

import java.io.*;

%%
// Options and Declarations
/* Notes:
 *	1. File is not of type 'standalone' since it is gona feed a parser 
 *	2. yylex() should return a Yytoken object with information of the current token: %type Yytoken
 */

%class Lexer 
%type Yytoken
%line
%column


DIGIT=[0-9]
INTEGER={DIGIT}+
ENDOFLINE=\n|\r|\n\r
BLANK={ENDOFLINE}+|[ \t\f]+

%{
int counter_semicolon=0;
int counter_leftbrackets=0;
int counter_rightbrackets=0;
int counter_int=0;
int counter_add=0;
int counter_sub=0;
int counter_times=0;
int counter_div=0;

String lexeme=null;
%}

%%
// Rules and Actions

";" {
	lexeme=yytext();
	System.out.println("[Lex] (SEMICOLON)"); 
	return new Yytoken(++counter_semicolon,lexeme,"SEMICOLON",(yyline+1),(yycolumn+1)); }
	
"(" {
	lexeme=yytext();
	System.out.println("[Lex] (LEFT_BRACKET)"); 
	return new Yytoken(++counter_leftbrackets,lexeme,"LEFT_BRACKET",(yyline+1),(yycolumn+1));}
	
")" {
	lexeme=yytext();
	System.out.println("[Lex] (RIGHT_BRACKET)"); 
	return new Yytoken(++counter_rightbrackets,lexeme,"RIGHT_BRACKET",(yyline+1),(yycolumn+1));}
	
"+" {
	lexeme=yytext();
	System.out.println("[Lex] (ADD)"); 
	return new Yytoken(++counter_add,lexeme,"ADD",(yyline+1),(yycolumn+1));}

"-" {
	lexeme=yytext();
	System.out.println("[Lex] (SUB)"); 
	return new Yytoken(++counter_sub,lexeme,"SUB",(yyline+1),(yycolumn+1));}

"*" {
	lexeme=yytext();
	System.out.println("[Lex] (TIMES)"); 
	return new Yytoken(++counter_times,lexeme,"TIMES",(yyline+1),(yycolumn+1));}

"/" {
	lexeme=yytext();
	System.out.println("[Lex] (DIV)"); 
	return new Yytoken(++counter_div,lexeme,"DIV",(yyline+1),(yycolumn+1));}

{INTEGER} {
			lexeme=yytext();
			System.out.println("[Lex] (INTEGER, "+lexeme+")");
			return new Yytoken(++counter_int,lexeme,"INTEGER",(yyline+1),(yycolumn+1));
		  }
{BLANK} {}
. { lexeme=yytext();
	System.out.println("[Lex] Error léxico en (línea " + (yyline+1) + ", columna "+ (yycolumn+1)+")("+lexeme+")"); 	  
  }
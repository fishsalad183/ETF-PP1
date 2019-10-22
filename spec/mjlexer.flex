package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
// <YYINITIAL> "+" 		{ return new_symbol(sym.PLUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }


"if"		{ return new_symbol(sym.IF, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"enum"		{ return new_symbol(sym.ENUM, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"["			{ return new_symbol(sym.LBRACK, yytext()); }
"]"			{ return new_symbol(sym.RBRACK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"extends"	{ return new_symbol(sym.EXTENDS, yytext()); }
"implements"	{ return new_symbol(sym.IMPLEMENTS, yytext()); }
"interface"		{ return new_symbol(sym.INTERFACE, yytext()); }
"for"		{ return new_symbol(sym.FOR, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"continue"	{ return new_symbol(sym.CONTINUE, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"=="		{ return new_symbol(sym.EQUALTO, yytext()); }
"!="		{ return new_symbol(sym.NOTEQUALTO, yytext()); }
">"			{ return new_symbol(sym.GREATERTHAN, yytext()); }
">="		{ return new_symbol(sym.GREATERTHANOREQUALTO, yytext()); }
"<"			{ return new_symbol(sym.LESSTHAN, yytext()); }
"<="		{ return new_symbol(sym.LESSTHANOREQUALTO, yytext()); }
"+"			{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.TIMES, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
'([a-z]|[A-Z])' 	{ return new_symbol(sym.CHARCONST, new Character(yytext().charAt(1))); }
"true" | "false"	{ return new_symbol(sym.BOOLCONST, new Boolean(yytext())); }
"this"		{ return new_symbol(sym.THIS, yytext()); }

"optarg"	{ return new_symbol(sym.OPTARG, yytext()); }


<YYINITIAL> "//" { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }



. { System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline + 1) + " i koloni " + (yycolumn + 1)); }







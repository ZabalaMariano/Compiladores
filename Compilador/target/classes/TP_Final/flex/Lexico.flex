package compilador;

import java_cup.runtime.Symbol;

%%
%state A
%cupsym sym
%cup
%public
%class Lexico
%line
%char

LineTerminator = \r|\n|\r\n

WhiteSpace     = {LineTerminator} | [ \t\f]

identificador = [a-z][a-zA-Z0-9]{1,9}

constEntera = [1-9][0-9]* | 0

constReal = [0-9]+"."[0-9]+

comilla = \"

constString = {comilla}({WhiteSpace}|{signo}|{operador}|[a-z]|[A-Z]|[0-9]|\.|\!|\¡|ñ|Ñ)*{comilla}

operador = (\+|-|\/|\*|>|<|\!=|<=|>=|=){1}

signo = ,|:|;

comentario = [<][/][^/]*[/]+([^/>][^/]*[/]+)*[>]

%%

<YYINITIAL> {

"string"  {return new Symbol(sym.palabrastring, yycolumn, yyline);}
"float"  {return new Symbol(sym.palabrafloat, yycolumn, yyline);}
"int"    {return new Symbol(sym.palabraint, yycolumn, yyline);}
"if"     {return new Symbol(sym.palabraif, yycolumn, yyline);}
"else"   {return new Symbol(sym.palabraelse, yycolumn, yyline);}
"while" {return new Symbol(sym.palabrawhile, yycolumn, yyline);}
"print" {return new Symbol(sym.palabraprint, yycolumn, yyline);}
"filter" {return new Symbol(sym.palabrafilter, yycolumn, yyline);}
"=="    {return new Symbol(sym.igual, yycolumn, yyline);}
"<"     {return new Symbol(sym.menor, yycolumn, yyline);}
">"     {return new Symbol(sym.mayor, yycolumn, yyline);}
"<="    {return new Symbol(sym.menorigual, yycolumn, yyline);}
">="    {return new Symbol(sym.mayorigual, yycolumn, yyline);}
"!="    {return new Symbol(sym.distinto, yycolumn, yyline);} 
":="    {return new Symbol(sym.asigna, yycolumn, yyline);}
"="     {return new Symbol(sym.asignaCTE, yycolumn, yyline);}
"+"     {return new Symbol(sym.mas, yycolumn, yyline);}
"-"     {return new Symbol(sym.menos, yycolumn, yyline);}
"/"     {return new Symbol(sym.dividido, yycolumn, yyline);}
"*"     {return new Symbol(sym.por, yycolumn, yyline);}
"("     {return new Symbol(sym.parentesisA, yychar, yyline);}
")"     {return new Symbol(sym.parentesisC, yychar, yyline);}
"{"     {return new Symbol(sym.bloqueA, yychar, yyline);}
"}"     {return new Symbol(sym.bloqueC, yychar, yyline);}
"]"     {return new Symbol(sym.corcheteC, yychar, yyline);}
"["     {return new Symbol(sym.corcheteA, yychar, yyline);}
";"     {return new Symbol(sym.eol, yychar, yyline);}
","     {return new Symbol(sym.coma, yychar, yyline);}
"_"     {return new Symbol(sym.guion_bajo, yychar, yyline);}
"DECVAR" {return new Symbol(sym.palabradecvar, yychar, yyline);}
"ENDDEC" {return new Symbol(sym.palabraenddec, yychar, yyline);}
{identificador} {return new Symbol(sym.id, yychar, yyline,new String(yytext()));}
{constEntera} {return new Symbol(sym.cte, yychar, yyline,new String(yytext()));}
{constReal} {return new Symbol(sym.cteReal, yychar, yyline,new String(yytext()));}
{constString} {return new Symbol(sym.constString, yychar, yyline,new String(yytext()));}
{WhiteSpace} { /* Así ignora los espacios en blanco */ }
{comentario} {/* Así ignora los comentarios */  }
}

[^] { throw new Error("Caracter no permitido: <" + yytext() + ">"); }

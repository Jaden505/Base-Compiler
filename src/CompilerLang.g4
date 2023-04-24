grammar CompilerLang;

program : statement+;

statement : let | show ;

let : VAR '=' INT ;
show : 'show' (INT | VAR) ;

PLUS : '+';
MINUS : '-';
MULTIPLY : '*';
DIVIDE : '/';
LEFT_PARENTHESIS : '(';
RIGHT_PARENTHESIS : ')';

VAR : [a-zA-Z] [a-zA-Z0-9_]* ;  // variable name can include letters, numbers and underscores, but must start with a letter
INT : ('+'|'-')? [0-9]+ ;
WS : [ \n\t\r]+ -> skip;

expression : term ((PLUS|MINUS) term)* ;
term : factor ((MULTIPLY|DIVIDE) factor)* ;
factor : INT | VAR | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

start : expression ;

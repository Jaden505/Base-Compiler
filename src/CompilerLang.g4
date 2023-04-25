grammar CompilerLang;

program : statement+;

statement : let | show ;

let : VAR '=' (INT | expression) ;
show : 'show' (expression | INT | VAR) ;

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


// Example of possible input:
// a = 1+2
// show 1+2*3
// show 1+2*(3+4)/5+6*(7+8)
// a = 1+2*(3+4)/5+6*(7+8)
// show a
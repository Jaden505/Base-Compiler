import LexicalAnalyser.Token;
import LexicalAnalyser.Tokenizer;
import LexicalAnalyser.TokenizerException;
import SemanticAnalyser.SemanticAnalyser;
import SyntaxAnalyser.Parser;
import SyntaxAnalyser.SyntaxException;
import SyntaxAnalyser.SyntaxTreeNode;

import java.util.List;
import java.util.Scanner;

public class MainCompiler {
    public static void main(String[] args) throws TokenizerException, SyntaxException {
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
        String input = "show (5*4)/2 + 3 - 1 \n a = 123 + (456/2) \n show -a";
        System.out.println(input);

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        System.out.println(tokens.toString());

        Parser parser = new Parser(tokens);
        parser.parseProgram();
        parser.showTree();

        SemanticAnalyser semanticAnalyser = new SemanticAnalyser(tokens);
        semanticAnalyser.analyse();
    }
}

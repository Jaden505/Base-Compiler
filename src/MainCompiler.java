import LexicalAnalyser.Token;
import LexicalAnalyser.Tokenizer;
import LexicalAnalyser.TokenizerException;
import SyntaxAnalyser.Parser;
import SyntaxAnalyser.SyntaxException;

import java.util.List;
import java.util.Scanner;

public class MainCompiler {
    public static void main(String[] args) throws TokenizerException, SyntaxException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        System.out.println(tokens.toString());

        Parser parser = new Parser(tokens);
        parser.parseProgram();
    }
}

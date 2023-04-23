import java.util.Scanner;

public class MainCompiler {
    public static void main(String[] args) throws TokenizerException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Tokenizer tokenizer = new Tokenizer(input);
        System.out.println(tokenizer.tokenize().toString());
    }
}

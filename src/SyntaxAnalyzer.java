import java.util.Scanner;

public class SyntaxAnalyzer {
    static int[] lexOut = LEX.getStorage();
    private static Scanner scanner = new Scanner(System.in);
    private static String scanInput = "";
    private int globalLexOut = 0;
    Symbol token = getToken();

    public static void main(String[] args) {
        LEX lexical = new LEX();
        lexical.initialize();

        String buff = "";
        while (true) {
            System.out.println("Write your expression \n");
            scanInput = scanner.nextLine();
            if (scanInput.equalsIgnoreCase("end")) {
                break;
            }
            buff += scanInput;
            System.out.println(buff);
            lexical.analyze(buff);
        }
        LEX.print(lexical.getsTable());
        System.out.println("==============================================>");
        LEX.printA(lexOut);

        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        sa.Z();
    }
    public void error(String s) {
        System.out.println("Error needed " + s);
    }
    public Symbol getToken() {
        Symbol sm = null;
        if (globalLexOut < lexOut.length) {
            int ss = lexOut[globalLexOut];
            sm = LEX.getsTable()[ss];
            globalLexOut++;
        }

        return sm;
    }

    public void Z() {
        start();
        if (!token.getName().equals("Start")){
            error("Start");
        }
        token = getToken();
        block();
        if (!token.getName().equals("Finish")) {
            error("Finish");
        }
        token = getToken();
    }
    public void start() {
        if (!token.getName().equals("Structure")) {
            error("Structure");
        }
        token = getToken();
        if (token.getTypeCode() != 1) {
            error("Ident");
        }
        token = getToken();
        if (!token.getName().equals("=>")) {
            error("=>");
        }
        token = getToken();
    }
    public void block() {
        stm();
        while (token.equals(";")) {
            token = getToken();
            stm();
        }
    }
    public void stm() {
        if (token.getTypeCode() == 1) {
            token = getToken();
            if (!token.getName().equals("$")) {
                error("$");
                token = getToken();
                exp();
            }
        } else if (token.getName().equals("Si")) {
            token = getToken();
            exp();

            if (!token.getName().equals("Then")) {
                error("Then");
                token = getToken();
                stm();
            }
            if (token.getName().equals("Aliud")) {
                token = getToken();
                stm();
            }
        } else {
               error("Aliud");
           }
    }
    public void exp() {
        term();
        while (token.getName().equals("+")) {
            token = getToken();
            term();
        }
    }
    public void term() {
        factor();
        while (token.getName().equals("*")) {
            token = getToken();
            factor();
        }
    }
    public void factor() {
        if (token.getTypeCode() == 1) {
            token = getToken();
        } else if(token.getTypeCode() == 2) {
            token = getToken();
        } else if(token.getName().equals("(")) {
            token = getToken();
            exp();
            if (!token.getName().equals(")")) {
                error(")");
            }
            token = getToken();
        } else {
            error("error");
        }
    }
}

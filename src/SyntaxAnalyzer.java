import java.util.Scanner;

public class SyntaxAnalyzer {
    static int[] lexOut = LEX.getStorage();
    private static Scanner scanner = new Scanner(System.in);
    private static String scanInput = "";
    private int globalLexOut = 0;
    Symbol token = getToken();

    public static void main(String[] args) {
        try {
            LEX lexical = new LEX();
            lexical.initialize();

            String buff = "";
            while (true) {
                System.out.println("Write your expression \n");
                scanInput = scanner.nextLine();
                //write 'end' when finish expression
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void error(String s) throws Exception {
        throw new Exception("Error needed" + s);
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
        if (!token.getName().equals("Start")) {
            throw new RuntimeException("Error needed Start");
        }

        token = getToken();
        block();
        if (!token.getName().equals("Finish")) {
            throw new RuntimeException("Error needed Finish");
        }
    }

    public void start() {
        if (!token.getName().equals("Structure")) {
            throw new RuntimeException("Error needed Structure");
        }

        token = getToken();
        if (token.getTypeCode() != 1) {
            throw new RuntimeException("Error needed Ident");
        }

        token = getToken();
        if (!token.getName().equals("=>")) {
            throw new RuntimeException("Error needed =>");
        }

        token = getToken();
    }

    public void block() {
        stm();
        while (token.getName().equals(";")) {
            token = getToken();
            stm();
        }
    }

    public void stm() {
        if (token.getTypeCode() == 1) {
            token = getToken();
            if (!token.getName().equals("$")) {
                throw new RuntimeException("Error needed $");
            }

            token = getToken();
            exp();
        } else if (token.getName().equals("Si")) {
            token = getToken();
            if (token.getName().equals("(")) {
                throw new RuntimeException("Error needed (");
            }

            token = getToken();
            check();
            if (token.getName().equals(")")) {
                throw new RuntimeException("Error needed )");
            }

            token = getToken();
            stm();

            if (!token.getName().equals("Then")) {
                throw new RuntimeException("Error needed Then");
            }

            if (token.getName().equals("Aliud")) {
                token = getToken();
                stm();
            }

        } else if (token.getName().equals("While")) {
            token = getToken();
            if (!token.getName().equals("(")) {
                throw new RuntimeException("Error needed (");
            }

            token = getToken();
            check();
            if (!token.getName().equals(")")) {
                throw new RuntimeException("Error needed )");
            }

            token = getToken();
            if (!token.getName().equals("{")) {
                throw new RuntimeException("Error needed {");
            }

            token = getToken();
            stm();
            if (!token.getName().equals("}")) {
                throw new RuntimeException("Error needed }");
            }

        } else if (token.getName().equals("print")) {
            token = getToken();
            out();
        } else if (token.getName().equals("scan")) {
            token = getToken();
            if (token.getTypeCode() != 1) {
                throw new RuntimeException("Error needed Ident");
            }
            token = getToken();
        } else {
            throw new RuntimeException("Error needed other");
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
        } else if (token.getTypeCode() == 2) {
            token = getToken();
        } else if (token.getName().equals("(")) {
            token = getToken();
            exp();
            if (!token.getName().equals(")")) {
                throw new RuntimeException("Error needed )");
            }
            token = getToken();
        } else {
            throw new RuntimeException("Error needed other");
        }
    }

    public void check() {
        exp();
        if (!token.getName().equals("==")) {
            throw new RuntimeException("Error needed ==");
        }

        token = getToken();
        exp();
    }

    public void out() {
        exp();
        while (token.getName().equals(",")) {
            token = getToken();
            exp();
        }
    }
}

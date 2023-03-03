import java.util.Scanner;

public class SyntaxAnalyzer {
    private static final String ERROR_NEEDED = "Error needed";
    static int[] lexOut = LEX.getStorage();
    private static final Scanner scanner = new Scanner(System.in);

    private int globalLexOut = 0;
    Symbol token = getToken();

    public static void main(String[] args) {
        String scanInput;
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

            LEX.print(LEX.getsTable());
            System.out.println("==============================================>");
            LEX.printA(lexOut);

            SyntaxAnalyzer sa = new SyntaxAnalyzer();
            sa.Z();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            throw new RuntimeException(ERROR_NEEDED + " Start ");
        }

        token = getToken();
        block();
        if (!token.getName().equals("Finish")) {
            throw new RuntimeException(ERROR_NEEDED + " Finish ");
        }
    }

    public void start() {
        if (!token.getName().equals("Structure")) {
            throw new RuntimeException(ERROR_NEEDED + " Structure ");
        }

        token = getToken();
        if (token.getTypeCode() != 1) {
            throw new RuntimeException(ERROR_NEEDED + " Ident ");
        }

        token = getToken();
        if (!token.getName().equals("=>")) {
            throw new RuntimeException(ERROR_NEEDED + " => ");
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
                throw new RuntimeException(ERROR_NEEDED + " $ ");
            }

            token = getToken();
            exp();
        } else if (token.getName().equals("Si")) {
            token = getToken();
            if (token.getName().equals("(")) {
                throw new RuntimeException(ERROR_NEEDED + " ) ");
            }

            token = getToken();
            check();
            if (token.getName().equals(")")) {
                throw new RuntimeException(ERROR_NEEDED + " ) ");
            }

            token = getToken();
            stm();
            if (!token.getName().equals("Then")) {
                throw new RuntimeException(ERROR_NEEDED + " Then ");
            }

            if (token.getName().equals("Aliud")) {
                token = getToken();
                stm();
            }

        } else if (token.getName().equals("While")) {
            token = getToken();
            if (!token.getName().equals("(")) {
                throw new RuntimeException(ERROR_NEEDED + " ( ");
            }

            token = getToken();
            check();
            if (!token.getName().equals(")")) {
                throw new RuntimeException(ERROR_NEEDED + ")");
            }

            token = getToken();
            if (!token.getName().equals("{")) {
                throw new RuntimeException(ERROR_NEEDED + "{");
            }

            token = getToken();
            stm();
            if (!token.getName().equals("}")) {
                throw new RuntimeException(ERROR_NEEDED + "}");
            }

        } else if (token.getName().equals("print")) {
            token = getToken();
            out();
        } else if (token.getName().equals("scan")) {
            token = getToken();
            if (token.getTypeCode() != 1) {
                throw new RuntimeException(ERROR_NEEDED + "Ident");
            }
            token = getToken();
        } else {
            throw new RuntimeException(ERROR_NEEDED + "other");
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
                throw new RuntimeException(ERROR_NEEDED + " ) ");
            }
            token = getToken();
        } else {
            throw new RuntimeException(ERROR_NEEDED + " other ");
        }
    }

    public void check() {
        exp();
        if (!token.getName().equals("==")) {
            throw new RuntimeException(ERROR_NEEDED + " == ");
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

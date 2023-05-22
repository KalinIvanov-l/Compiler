package com.code.compiler.project.core;

/**
 * @author kalin
 */
public class SyntaxAnalyzer {
    private static final String ERROR_MESSAGE = "Error needed";
    public static int[] lexOut = LEX.getStorage();

    private int globalLexOut = 0;
    Symbol token = getToken();

    private Symbol getToken() {
        Symbol sm = null;
        if (globalLexOut < lexOut.length) {
            int ss = lexOut[globalLexOut];
            sm = LEX.getsTable()[ss];
            globalLexOut++;
        }

        return sm;
    }

    public void analyze() {
        start();
        if (!token.getName().equals("Start")) {
            throw new RuntimeException(ERROR_MESSAGE + " Start ");
        }

        token = getToken();
        block();
        if (!token.getName().equals("Finish")) {
            throw new RuntimeException(ERROR_MESSAGE + " Finish ");
        }
    }

    private void start() {
        if (!token.getName().equals("Structure")) {
            throw new RuntimeException(ERROR_MESSAGE + " Structure ");
        }

        token = getToken();
        match();
        if (token.getTypeCode() != 1) {
            throw new RuntimeException(ERROR_MESSAGE + " Ident ");
        }

        token = getToken();
        match();
        if (!token.getName().equals("=>")) {
            throw new RuntimeException(ERROR_MESSAGE + " => ");
        }

        token = getToken();
    }

    private void block() {
        statement();
        while (token.getName().equals(";")) {
            token = getToken();
            statement();
        }
    }

    private void statement() {
        if (token.getTypeCode() == 1) {
            token = getToken();
            match();
            if (!token.getName().equals("$")) {
                throw new RuntimeException(ERROR_MESSAGE + " $ ");
            }

            token = getToken();
            expression();
        } else if (token.getName().equals("Si")) {
            token = getToken();
            match();
            if (token.getName().equals("(")) {
                throw new RuntimeException(ERROR_MESSAGE + " ( ");
            }

            token = getToken();
            match();
            if (token.getName().equals(")")) {
                throw new RuntimeException(ERROR_MESSAGE + " ) ");
            }

            token = getToken();
            statement();
            if (!token.getName().equals("Then")) {
                throw new RuntimeException(ERROR_MESSAGE + " Then ");
            }

            if (token.getName().equals("Aliud")) {
                token = getToken();
                statement();
            }

        } else if (token.getName().equals("While")) {
            token = getToken();
            match();
            if (!token.getName().equals("(")) {
                throw new RuntimeException(ERROR_MESSAGE + " ( ");
            }

            token = getToken();
            match();
            if (!token.getName().equals(")")) {
                throw new RuntimeException(ERROR_MESSAGE + ")");
            }

            token = getToken();
            match();
            if (!token.getName().equals("{")) {
                throw new RuntimeException(ERROR_MESSAGE + "{");
            }

            token = getToken();
            statement();
            if (!token.getName().equals("}")) {
                throw new RuntimeException(ERROR_MESSAGE + "}");
            }

        } else if (token.getName().equals("print")) {
            token = getToken();
            out();
        } else if (token.getName().equals("scan")) {
            token = getToken();
            match();
            if (token.getTypeCode() != 1) {
                throw new RuntimeException(ERROR_MESSAGE + "Ident");
            }
            token = getToken();
        } else {
            throw new RuntimeException(ERROR_MESSAGE + "other");
        }
    }

    private void expression() {
        term();
        while (token.getName().equals("+")) {
            token = getToken();
            term();
        }
    }

    private void term() {
        factor();
        while (token.getName().equals("*")) {
            token = getToken();
            factor();
        }
    }

    private void factor() {
        if (token.getTypeCode() == 1) {
            token = getToken();
        } else if (token.getTypeCode() == 2) {
            token = getToken();
        } else if (token.getName().equals("(")) {
            token = getToken();
            expression();
            if (!token.getName().equals(")")) {
                throw new RuntimeException(ERROR_MESSAGE + " ) ");
            }
            token = getToken();
        } else {
            throw new RuntimeException(ERROR_MESSAGE + " other ");
        }
    }

    private void match() {
        expression();
        if (!token.getName().equals("==")) {
            throw new RuntimeException(ERROR_MESSAGE + " == ");
        }
        token = getToken();
        expression();
    }

    private void out() {
        expression();
        while (token.getName().equals(",")) {
            token = getToken();
            expression();
        }
    }
}

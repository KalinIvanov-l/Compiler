package com.code.compiler.project.core;

/**
 * @author kalin
 */
public class SyntaxAnalyzer {
    private static final String ERROR_MESSAGE = "Error needed";
    private static final String ERROR = "Other";
    public static int[] lexOut = LEX.getStorage();

    private int globalLexOut = 0;
    Symbol token = getToken();

    private Symbol getToken() {
        Symbol symbol = null;
        if (globalLexOut < lexOut.length) {
            int ss = lexOut[globalLexOut];
            symbol = LEX.getsTable()[ss];
            globalLexOut++;
        }

        return symbol;
    }

    public void analyze() {
        start();
        checkTokenName("Start");
        token = getToken();
        block();
        checkTokenName("Finish");
    }

    private void start() {
        checkTokenName("Structure");
        checkTokenType();
        checkTokenName("=>");
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
        switch (token.getName()) {
            case "=>":
            case "+":
            case "*":
                throw new RuntimeException(ERROR_MESSAGE + ERROR);
            default:
                parseStatement();
                break;
        }
    }

    private void parseStatement() {
        if (token.getTypeCode() == 1) {
            assignmentStatement();
        } else if (token.getName().equals("Si")) {
            ifStatement();
        } else if (token.getName().equals("While")) {
            whileStatement();
        } else if (token.getName().equals("print")) {
            printStatement();
        } else if (token.getName().equals("scan")) {
            scanStatement();
        } else {
            throw new RuntimeException(ERROR_MESSAGE + ERROR);
        }
    }

    private void assignmentStatement() {
        token = getToken();
        checkTokenName("$");
        token = getToken();
        expression();
    }

    private void ifStatement() {
        token = getToken();
        checkTokenName("(");
        token = getToken();
        checkTokenName(")");
        token = getToken();
        statement();
        checkTokenName("Then");
        if (token.getName().equals("Aliud")) {
            token = getToken();
            statement();
        }
    }

    private void whileStatement() {
        token = getToken();
        checkTokenName("(");
        token = getToken();
        checkTokenName(")");
        token = getToken();
        checkTokenName("{");
        token = getToken();
        statement();
        checkTokenName("}");
    }

    private void printStatement() {
        token = getToken();
        out();
    }

    private void scanStatement() {
        token = getToken();
        match();
        checkTokenType();
        token = getToken();
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
        if (token.getTypeCode() == 1 || token.getTypeCode() == 2) {
            token = getToken();
        } else if (token.getName().equals("(")) {
            token = getToken();
            expression();
            checkTokenName(")");
            token = getToken();
        } else {
            throw new RuntimeException(ERROR_MESSAGE + ERROR);
        }
    }

    private void match() {
        expression();
        checkTokenName("==");
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

    private void checkTokenType() {
        if (token != null && token.getTypeCode() != 1) {
            throw new RuntimeException(ERROR_MESSAGE + " Ident ");
        }
        token = getToken();
    }

    private void checkTokenName(String name) {
        if (!token.getName().equals(name)) {
            throw new RuntimeException(ERROR_MESSAGE + " " + name + " ");
        }
    }
}
package com.code.compiler.project.core;

import static java.lang.Character.*;

/**
 * @author kalin
 */
public class LEX {
    static final Symbol[] sTable = new Symbol[100];
    private static int index = 0;
    public int global = 0;
    private static final int[] storage = new int[200];

    protected void analyze(String input) {
        StringBuilder buffer;

        while (index < input.length()) {
            char c = getNext(input);
            while (c == ' ') {
                c = getNext(input);
            }
            if (isLetter(c)) {
                buffer = new StringBuilder();
                while (isLetterOrDigit(c)) {
                    buffer.append(c);
                    c = getNext(input);
                }
                int token = STable.addToArray(buffer.toString(), sTable, 1);

                storage[global] = token;
                global++;

            } else if (isDigit(c)) {
                buffer = new StringBuilder();
                while (isDigit(c)) {
                    buffer.append(c);
                    c = getNext(input);
                }
                int token = STable.addToArray(buffer.toString(), sTable, 2);

                storage[global] = token;
                global++;

            } else if (isOperator(c)) {
                buffer = new StringBuilder();
                buffer.append(c);
                c = getNext(input);

                if (isOperator(c)) {
                    buffer.append(c);
                }

                int token = STable.addToArray(buffer.toString(), sTable, 3);
                storage[global] = token;
                global++;

            } else {
                System.out.println("Error at " + index);
                break;
            }
        }
    }

    private boolean isOperator(char allowedOperator) {
        return (allowedOperator == '+' || allowedOperator == '-' || allowedOperator == '*' || allowedOperator == '/' ||
                allowedOperator == '%' || allowedOperator == '^' || allowedOperator == '.' || allowedOperator == '=' ||
                allowedOperator == '<' || allowedOperator == '>' || allowedOperator == '$' || allowedOperator == '(' ||
                allowedOperator == ')' || allowedOperator == ';' || allowedOperator == '{' || allowedOperator == '}');
    }

    private char getNext(String input) {
        char nextChar;
        if (index < input.length()) {
            nextChar = input.charAt(index);
            index++;
            return nextChar;
        }
        return ' ';
    }

    protected static void print(Symbol[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.println(i + " - " + array[i].getName() + " type: " + array[i].getTypeCode());
            }
        }
    }

    protected static void printA(int[] storage) {
        for (int tokenCode : storage) {
            if (tokenCode != 0) {
                System.out.println(tokenCode);
            }
        }
    }

    protected static Symbol[] getsTable() {
        return sTable;
    }

    protected static int[] getStorage() {
        return storage;
    }

    protected void initialize() {
        //there are have words in latin
        String[] keyword = {"Start", "stttop", "Structure", "=>", ";", "Si", "Then", "Aliud", "While", "print", "scan",
                "Finish", "err"};

        for (String x : keyword) {
            System.out.println(STable.hashCode(x));
            int var = STable.hashCode(x);

            Symbol sm = new Symbol(x, 4);
            sTable[var] = sm;
        }
    }
}
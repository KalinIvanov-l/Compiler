package com.code.compiler.project.core;

import static java.lang.Character.*;

/**
 * @author kalin
 */
public class LEX {
    private static final Symbol[]sTable = new Symbol[100];
    public static int i = 0;
    int global = 0;
    private static final int[] storage = new int[200];

    public static void main(String[] args) {
        LEX l = new LEX();
        l.initialize();
        print(sTable);
    }

    void analyze(String input) {
        StringBuilder buff;

        while (i < input.length()) {
            char c = getNext(input);
            while (c == ' ') {
                c = getNext(input);
            }
            if (isLetter(c)) {
                buff = new StringBuilder();
                while (isLetterOrDigit(c)) {
                    buff.append(c);
                    c = getNext(input);
                }
                int token = STable.addToArray(buff.toString(), sTable, 1);

                storage[global] = token;
                global++;

            } else if (isDigit(c)) {
                buff = new StringBuilder();
                while (isDigit(c)) {
                    buff.append(c);
                    c = getNext(input);
                }
                int token = STable.addToArray(buff.toString(), sTable, 2);

                storage[global] = token;
                global++;

            } else if (isOperator(c)) {
                buff = new StringBuilder();
                buff.append(c);
                c = getNext(input);

                if (isOperator(c)) {
                    buff.append(c);
                }

                int token = STable.addToArray(buff.toString(), sTable, 3);
                storage[global] = token;
                global++;

            } else {
                System.out.println("Error at " + i);
                break;
            }
        }
    }

    private boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '.'
                || c == '=' || c == '<' || c == '>' || c == '$' || c == '(' || c == ')'
                || c == ';' || c == '{' || c == '}');
    }

    public char getNext(String input) {
        char nextChar;
        if (i < input.length()) {
            nextChar = input.charAt(i);
            i++;
            return nextChar;
        }
        return ' ';
    }

    public static void print(Symbol[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.println(i + " - " + array[i].getName() + " type: " + array[i].getTypeCode());
            }
        }
    }

    public static void printA(int[] storage) {
        for (int j : storage) {
            if (j != 0) {
                System.out.println(j);
            }
        }
    }

    public static Symbol[] getsTable() {
        return sTable;
    }

    public static int[] getStorage() {
        return storage;
    }

    public void initialize() {
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
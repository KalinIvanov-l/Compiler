package com.code.compiler.project.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Character.*;

/**
 * @author kalin
 */
public class LEX {
    private static final Logger LOGGER = LoggerFactory.getLogger(LEX.class);
    public static final Symbol[] sTable = new Symbol[100];
    private static int index = 0;
    public int global = 0;
    private static final int[] storage = new int[200];

    public void analyze(String input) {
        StringBuilder buffer;

        while (index < input.length()) {
            char letters = getNext(input);
            while (letters == ' ') {
                letters = getNext(input);
            }
            if (isLetter(letters)) {
                buffer = new StringBuilder();
                while (isLetterOrDigit(letters)) {
                    buffer.append(letters);
                    letters = getNext(input);
                }
                int token = STable.addToArray(buffer.toString(), sTable, 1);

                storage[global] = token;
                global++;

            } else if (isDigit(letters)) {
                buffer = new StringBuilder();
                while (isDigit(letters)) {
                    buffer.append(letters);
                    letters = getNext(input);
                }
                int token = STable.addToArray(buffer.toString(), sTable, 2);

                storage[global] = token;
                global++;

            } else if (isOperator(letters)) {
                buffer = new StringBuilder();
                buffer.append(letters);
                letters = getNext(input);

                if (isOperator(letters)) {
                    buffer.append(letters);
                }

                int token = STable.addToArray(buffer.toString(), sTable, 3);
                storage[global] = token;
                global++;

            } else {
                LOGGER.error("{} Error at ", index);
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

    public static void print(Symbol[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                LOGGER.info(String.valueOf(i), " - ", array[i].getName(), " type: ", array[i].getTypeCode());
            }
        }
    }

    public static void printA(int[] storage) {
        for (int tokenCode : storage) {
            if (tokenCode != 0) {
                LOGGER.info("{}", tokenCode);
            }
        }
    }

    public static Symbol[] getsTable() {
        return sTable;
    }

    protected static int[] getStorage() {
        return storage;
    }

    public void initialize() {
        //there are have words in latin
        String[] keyword = {"Start", "stttop", "Structure", "=>", ";", "Si", "Then", "Aliud", "While", "print", "scan",
                "Finish", "err"};

        for (String x : keyword) {
            LOGGER.info("{}", STable.hashCode(x));
            int var = STable.hashCode(x);

            Symbol sm = new Symbol(x, 4);
            sTable[var] = sm;
        }
    }
}
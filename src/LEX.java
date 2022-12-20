import java.util.Scanner;

import static java.lang.Character.*;

public class LEX {
    private static Scanner scanner = new Scanner(System.in);
    private static String scanInput = "";
    private static Symbol[] sTable = new Symbol[100];
    public static int i = 0;
    //    private int[] tol = new int[300];
    int global = 0;
    private static int[] storage = new int[200];

    public static void main(String[] args) {
        LEX l = new LEX();
        l.initialize();
        print(l.sTable);
    }

    void analyze(String input) {
        String buff;

        while (i < input.length()) {
            char c = getNext(input);
            while (c == ' ') {
                c = getNext(input);
            }
            if (isLetter(c)) {
                buff = "";
                while (isLetterOrDigit(c)) { // има ли вариант условието за край да е тук?
                    buff += c;
                    c = getNext(input);
                }
                int token = STable.addToArray(buff, sTable, 1);

                storage[global] = token;
                global++;

            } else if (isDigit(c)) {
                buff = "";
                while (isDigit(c)) {
                    buff += c;
                    c = getNext(input);
                }
                int token = STable.addToArray(buff, sTable, 2);

                storage[global] = token;
                global++;

            } else if (isOperator(c)) {
                buff = "";
                buff += c;
                c = getNext(input);
                if (isOperator(c)) {
                    buff += c;
                }
                int token = STable.addToArray(buff, sTable, 3);
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
                || c == '=' || c == '<' || c == '>' || c == '$' || c == '(' || c == ')' || c == ';');
    }

    public char getNext(String input) {
        char c;
        if (i < input.length()) {
            c = input.charAt(i);
            i++;
            return c;
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
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != 0) {
                System.out.println(storage[i]);
            }
        }
    }

    public static Symbol[] getsTable() {
        return sTable;
    }

    public static int[] getStorage() {
        return storage;
    }

    public String initialize() {
        String[] keyword = {"Start", "Finish", "Structure", "=>", ";", "Si", "Then", "Aliud"}; //има думи на латински
        for (String x : keyword) {
            System.out.println(STable.hashCode(x));
            int var = STable.hashCode(x);

            Symbol sm = new Symbol(x, 4);
            this.sTable[var] = sm;
        }

        return "";
    }
}
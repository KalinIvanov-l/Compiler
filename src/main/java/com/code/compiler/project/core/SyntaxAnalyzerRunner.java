package com.code.compiler.project.core;

import java.util.Scanner;

import static com.code.compiler.project.core.SyntaxAnalyzer.lexOut;

/**
 * @author kalin
 */
public class SyntaxAnalyzerRunner {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        LEX lexical = new LEX();
        lexical.initialize();

        try {
            String buffer;
            System.out.println("Write your expression \n");
            while(!"end".equalsIgnoreCase((buffer = scanner.nextLine()))) {
                lexical.analyze(buffer);
            }

            LEX.print(LEX.getsTable());
            System.out.println("==============================================>");
            LEX.printA(lexOut);

            SyntaxAnalyzer sa = new SyntaxAnalyzer();
            sa.analyze();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

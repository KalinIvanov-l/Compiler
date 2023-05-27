package com.code.compiler.project.core.runner;

import com.code.compiler.project.core.LEX;
import com.code.compiler.project.core.SyntaxAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static com.code.compiler.project.core.SyntaxAnalyzer.lexOut;

/**
 * @author kalin
 */
public class SyntaxAnalyzerRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyntaxAnalyzerRunner.class);

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        LEX lexical = new LEX();
        lexical.initialize();

        try {
            String buffer;
            LOGGER.info("Write your expression \n");
            while(!"end".equalsIgnoreCase((buffer = scanner.nextLine()))) {
                lexical.analyze(buffer);
            }

            LEX.print(LEX.getsTable());
            LOGGER.info("==============================================>");
            LEX.printA(lexOut);

            SyntaxAnalyzer sa = new SyntaxAnalyzer();
            sa.analyze();

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }
}

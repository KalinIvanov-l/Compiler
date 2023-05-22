package com.code.compiler.project.core;

import static com.code.compiler.project.core.LEX.print;
import static com.code.compiler.project.core.LEX.sTable;

public class LEXRunner {
    public static void main(String[] args) {
        LEX l = new LEX();
        l.initialize();
        print(sTable);
    }
}

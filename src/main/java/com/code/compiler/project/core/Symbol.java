package com.code.compiler.project.core;

/**
 * @author kalin
 */
public class Symbol {
    private final String name;
    private final int typeCode;

    public Symbol(String name, int typeCode) {
        this.name = name;
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
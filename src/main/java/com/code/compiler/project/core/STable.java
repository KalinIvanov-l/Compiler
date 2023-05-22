package com.code.compiler.project.core;

import java.nio.charset.StandardCharsets;

/**
 * @author kalin
 */
public class STable {
    private STable() {
    }

    /**
     * Generates a hash code for the given symbol string
     *
     * @param symbol the symbol string to generate a hash code
     * @return hash code for the given symbol
     */
    protected static int hashCode(String symbol) {
        byte[] symbolArray = symbol.getBytes(StandardCharsets.UTF_8);
        int sum = 0;

        for (int i : symbolArray) {
            sum += i + 10;
        }
        sum += ((symbolArray[0] + symbolArray[symbolArray.length - 1]) - 1);
        return (sum % 77 ^ symbolArray.length);
    }

    /**
     * Adds the given symbol to the symbol array. Either inserts it at the position calculated by the hash code,
     * or finds the next available position
     *
     * @param symbol the symbol name
     * @param array  the symbol array to add the symbol
     * @param type   symbol type
     * @return position in the array
     */
    public static int addToArray(String symbol, Symbol[] array, int type) {
        int position = hashCode(symbol);

        for (int i = 0; i < array.length; i++) {
            if (position == i && array[i] != null) {
                if (array[i].getName().equals(symbol)) {
                    return position;
                }
                position++;
            }
        }
        array[position] = new Symbol(symbol, type);
        return position;
    }
}
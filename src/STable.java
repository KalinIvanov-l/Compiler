//Byte option

import java.nio.charset.StandardCharsets;

public class STable {

    public static int hashCode(String symbol) {
        byte[] symbolArray = symbol.getBytes(StandardCharsets.UTF_8); //dividing String to array of byte with UTF value of the symbol
        int sum = 0;
        for (int i : symbolArray) {
            sum += i + 10;
        }

        sum += ((symbolArray[0] + symbolArray[symbolArray.length - 1]) - 1); //obtain large integer
        return (sum % 77 ^ symbolArray.length); //module type hashing
    }

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
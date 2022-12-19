public class Symbol {
    private String name;
    private int typeCode;

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
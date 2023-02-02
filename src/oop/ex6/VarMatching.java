package oop.ex6;

public class VarMatching {
    /**
     * If the source type is int, double, boolean, char, or String,
     * then the value type can be set to the source type
     *
     * @param sourceType The type of the variable you're trying to set.
     * @param valType The type of the value you're trying to set
     * @return A boolean value.
     */
    public static boolean canBeSetWith(String sourceType, String valType){
        switch (sourceType){
            case "int":
                return intSet(valType);
            case "double":
                return doubleSet(valType);
            case "boolean":
                return booleanSet(valType);
            case "char":
                return charSet(valType);
            case "String":
                return stringSet(valType);
        }
        return false;
    }
    private static boolean intSet(String valType){
        return valType.equals("int");
    }
    private static boolean doubleSet(String valType){
        return valType.equals("int")||valType.equals("double");
    }
    private static boolean booleanSet(String valType){
        return valType.equals("int")||valType.equals("double")||valType.equals("boolean");
    }
    private static boolean charSet(String valType){
        return valType.equals("char");
    }
    private static boolean stringSet(String valType){
        return valType.equals("String");
    }
}

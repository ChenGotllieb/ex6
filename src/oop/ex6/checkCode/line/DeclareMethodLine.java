package oop.ex6.checkCode.line;

import oop.ex6.VarMatching;
import oop.ex6.tables.MethodsTable;
import oop.ex6.validity.ValidMethodName;
import oop.ex6.validity.ValidVarName;

import java.util.ArrayList;
import java.util.HashSet;

public class DeclareMethodLine {
    private MethodsTable methodsTable;
    private int curToken;
    private ArrayList<String[]> varsList;
    public static final String INT = "0intVal";
    public static final String DOUBLE = "?doubleVal";
    public static final String CHAR = "?charVal";
    public static final String BOOLEAN = "?booleanVal";
    public static final String STRING = "?StringVal";
    public static final String FINAL = "f";
    public static final String DECLARED = "d";
    public static final String UNDECLARED = "u";
    private HashSet<String> parameterNames;

    public DeclareMethodLine(ArrayList<String> wordsLine, MethodsTable methodsTable)
            throws LineLogicException, InvalidNameException{
        String methodName;
        this.methodsTable = methodsTable;
        this.parameterNames = new HashSet<>();
        varsList = new ArrayList<>();
        curToken = 1;
        try {
            if(!(ValidMethodName.isValidName(wordsLine.get(curToken)))){
                throw new InvalidNameException("bad method name");
            }
            methodName = wordsLine.get(curToken);
            curToken++;
            if(wordsLine.get(curToken).equals("(")){
                curToken++;
                varsInput(wordsLine);
                if(wordsLine.get(curToken).equals("{")&&curToken==wordsLine.size()-1){
                    methodsTable.addMethod(methodName, varsList);
                }
                else {
                    throw new LineLogicException("function declaration not ended with {");
                }
            }
            else {
                throw new LineLogicException("function declaration not open (");

            }
        }
        catch (IndexOutOfBoundsException e){
            throw new LineLogicException("bad declare vars line");
        }
    }

    /**
     * The function checks if the type of the variable is one of the 5 types that are allowed in Java
     *
     * @param wordsLine the line of code split into words
     */
    private void checkType(ArrayList<String> wordsLine) throws IndexOutOfBoundsException,
            LineLogicException {
        String type = wordsLine.get(curToken);
        switch (type){
            case "int":
            case "String":
            case "double":
            case "boolean":
            case "char":
                break;
            default:
                throw new LineLogicException("bad declare vars line");
        }
    }

    /**
     * It returns the type of the value
     *
     * @param val The value of the variable
     * @return The type of the variable.
     */
    private String valType(String val) throws LineLogicException {
        switch (val){
            case INT:
                return "int";
            case DOUBLE:
                return "double";
            case BOOLEAN:
                return "boolean";
            case CHAR:
                return "char";
            case STRING:
                return "String";
        }
        return "null";
    }

    /**
     * This function is responsible for parsing the input parameters of a function
     *
     * @param wordsLine the line of code that is being parsed
     */
    private void varsInput(ArrayList<String> wordsLine) throws LineLogicException, InvalidNameException {
        String isFinal = DECLARED;
        String name = "";
        String type = "";
        if (wordsLine.get(curToken).equals(")")){
            curToken++;
            return;
        }
        while (true){
            if (wordsLine.get(curToken).equals("final")) {
                isFinal = FINAL;
                curToken++;
            }
            checkType(wordsLine);
            type = isFinal+wordsLine.get(curToken);
            curToken++;
            if(ValidVarName.isValidName(wordsLine.get(curToken))
                    &&(!parameterNames.contains(wordsLine.get(curToken)))){
                name = wordsLine.get(curToken);
                parameterNames.add(name);
                curToken++;
            }
            else {
                throw new InvalidNameException("bad parameter name");
            }
            if(wordsLine.get(curToken).equals(")")||wordsLine.get(curToken).equals(",")){
                String[] nameAndType = new String[]{name, type};
                varsList.add(nameAndType);
                if(wordsLine.get(curToken).equals(")")){
                    curToken++;
                    break;
                }
                curToken++;
            }
            else {
                throw new InvalidNameException("bad declare func logic");
            }
        }
    }

}

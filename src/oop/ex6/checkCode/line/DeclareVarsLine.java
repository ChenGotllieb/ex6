package oop.ex6.checkCode.line;

import oop.ex6.VarMatching;
import oop.ex6.tables.VarsTable;
import oop.ex6.validity.ValidVarName;

import java.util.ArrayList;


public class DeclareVarsLine {
    public static final String FINAL = "f";
    public static final String DECLARED = "d";
    public static final String UNDECLARED = "u";
    public static final String INT = "0intVal";
    public static final String DOUBLE = "?doubleVal";
    public static final String CHAR = "?charVal";
    public static final String BOOLEAN = "?booleanVal";
    public static final String STRING = "?StringVal";
    private String type;
    private VarsTable varsTable;
    private int curToken = 0;

    // This is the constructor of the class. It gets an array of strings and a vars table.
    // It checks if the first word is final, if it is, it calls the finalDeclare method, if not, it calls the
    // regularDeclare method.
    public DeclareVarsLine(ArrayList<String> wordsLine,VarsTable varsTable) throws LineLogicException, InvalidNameException {
        this.varsTable = varsTable;
        try {
            if(wordsLine.get(0).equals("final")){
                curToken++;
                setType(wordsLine);
                curToken++;
                finalDeclare(wordsLine);
            }
            else {
                setType(wordsLine);
                curToken++;
                regularDeclare(wordsLine);
            }
            if (curToken!=wordsLine.size()-1){
                throw new LineLogicException("declare line not ended with ;");
            }
        }
        catch (IndexOutOfBoundsException e){
            throw new LineLogicException("bad declare vars line");
        }

    }
    /**
     * The function sets the type of the variable to the current token in the line
     *
     * @param wordsLine the line of code split into words
     */
    private void setType(ArrayList<String> wordsLine) throws IndexOutOfBoundsException,
            LineLogicException {
        type = wordsLine.get(curToken);
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
     * The function receives an ArrayList of strings, and checks if the variable name is valid, and if the
     * variable is assigned a value that matches its type. If so, the variable is added to the symbol table
     *
     * @param wordsLine the line split into words
     */
    private void regularDeclare(ArrayList<String> wordsLine) throws IndexOutOfBoundsException,
            InvalidNameException, LineLogicException {
        while (true) {
            String status = UNDECLARED;
            if (!ValidVarName.isValidName(wordsLine.get(curToken))) {
                throw new InvalidNameException("bad var name");
            }
            String name = wordsLine.get(curToken);
            curToken++;
            // It checks if the variable is assigned a value, then checks if the value is of the correct type.
            if (wordsLine.get(curToken).equals("=")) {
                curToken++;
                if (VarMatching.canBeSetWith(type, valType(wordsLine.get(curToken)))||
                        (varsTable.containsVar(wordsLine.get(curToken))
                                && varsTable.varType(wordsLine.get(curToken)).charAt(0) != 'u'
                                && VarMatching.canBeSetWith(type,
                                varsTable.varType(wordsLine.get(curToken)).substring(1)))) {
                    status = DECLARED;
                    curToken++;
                } else {
                    throw new LineLogicException("wrong assignment in declaration line");
                }
            }
            if (wordsLine.get(curToken).equals(";") || wordsLine.get(curToken).equals(",")) {
                if (!varsTable.addVar(name, status + type)) {
                    throw new LineLogicException("declared twice var");
                }
                if (wordsLine.get(curToken).equals(";")) {
                    break;
                }
                curToken++;
            }
            else {
                throw new LineLogicException("bad declare vars line");
            }
        }
    }

    /**
     * It checks if the variable name is valid, then checks if the variable is assigned a value, then checks
     * if the value is of the correct type, then adds the variable to the table
     *
     * @param wordsLine the line of code, split into words
     */
    private void finalDeclare(ArrayList<String> wordsLine) throws IndexOutOfBoundsException,
            InvalidNameException, LineLogicException {
        if(!ValidVarName.isValidName(wordsLine.get(curToken))){
            throw new InvalidNameException("bad var name");
        }
        String name = wordsLine.get(curToken);
        curToken ++;
        if (wordsLine.get(curToken).equals("=")){
            curToken ++;
            if(VarMatching.canBeSetWith(type, valType(wordsLine.get(curToken)))||
                    (varsTable.containsVar(wordsLine.get(curToken))
                            &&varsTable.varType(wordsLine.get(curToken)).charAt(0)!='u'
                            &&VarMatching.canBeSetWith(type,
                            varsTable.varType(wordsLine.get(curToken)).substring(1)))){
                curToken ++;
            }
            else {
                throw new LineLogicException("wrong assignment in declaration line");
            }
        }
        else {
            throw new LineLogicException("no assignment in final declaration");
        }
        if (!varsTable.addVar(name, FINAL + type)) {
            throw new LineLogicException("declared twice var");
        }
        if (!wordsLine.get(curToken).equals(";")){
            throw new LineLogicException("declare line not ended with ;");
        }
    }
}

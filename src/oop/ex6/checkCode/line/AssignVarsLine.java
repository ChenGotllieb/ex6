package oop.ex6.checkCode.line;

import oop.ex6.VarMatching;
import oop.ex6.tables.VarsTable;
import oop.ex6.validity.ValidVarName;

import java.util.ArrayList;

public class AssignVarsLine {
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
    // This is the constructor of the class. It gets an array of strings and a vars table. It checks
    // if the line is valid and if it is, it assigns the variables in the line.
    public AssignVarsLine(ArrayList<String> wordsLine,VarsTable varsTable) throws LineLogicException, InvalidNameException {
        this.varsTable = varsTable;
        try {
                Assignment(wordsLine);
            if (curToken!=wordsLine.size()-1){
                throw new LineLogicException("declare line not ended with ;");
            }
        }
        catch (IndexOutOfBoundsException e){
            throw new LineLogicException("bad declare vars line");
        }

    }

    /**
     * It takes a string and returns the type of the variable
     *
     * @param val the value to be assigned
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
            default:
                throw new LineLogicException("wrong assignment in assigment line");
        }
    }

    /**
     * The function checks if the variable name is valid, if it is already declared, and if the assignment is
     * valid
     *
     * @param wordsLine the line of code, split into words.
     */
    private void Assignment(ArrayList<String> wordsLine) throws IndexOutOfBoundsException,
            InvalidNameException, LineLogicException {
        while (true) {
            if (!ValidVarName.isValidName(wordsLine.get(curToken))) {

                throw new InvalidNameException("bad var name");
            }
            String name = wordsLine.get(curToken);
            if (varsTable.containsVar(wordsLine.get(curToken))){
                type = varsTable.varType(wordsLine.get(curToken)).substring(1);
            }
            else {
                throw new LineLogicException("wrong assignment in assigment line");
            }
            curToken++;
            if (wordsLine.get(curToken).equals("=")) {
                curToken++;
                if (VarMatching.canBeSetWith(type, valType(wordsLine.get(curToken)))||
                        (varsTable.containsVar(wordsLine.get(curToken))
                                && varsTable.varType(wordsLine.get(curToken)).charAt(0) != 'u'
                                && VarMatching.canBeSetWith(type,
                                varsTable.varType(wordsLine.get(curToken)).substring(1)))) {
                    curToken++;
                } else {
                    throw new LineLogicException("wrong assignment in assigment line");
                }
            }
            else {
                throw new LineLogicException("no assignment in assigment line");
            }
            if (wordsLine.get(curToken).equals(";") || wordsLine.get(curToken).equals(",")) {
                if (!varsTable.setVal(name, DECLARED + type)) {
                    throw new LineLogicException("declared twice var");
                }
                if (wordsLine.get(curToken).equals(";")) {
                    break;
                }
                curToken++;
            }
            else {
                throw new LineLogicException("bad assign vars line");
            }
        }
    }
}

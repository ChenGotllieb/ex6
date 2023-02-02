package oop.ex6.checkCode.line;

import oop.ex6.VarMatching;
import oop.ex6.tables.VarsTable;

import java.util.ArrayList;

/**
 * It checks if the if/while line is correct
 */
public class IfWhileLine {
    private VarsTable varsTable;
    private int curToken;
    public static final String INT = "0intVal";
    public static final String DOUBLE = "?doubleVal";
    public static final String BOOLEAN = "?booleanVal";


    // It checks if the if/while line is correct.
    public IfWhileLine(ArrayList<String> wordsLine, VarsTable varsTable)throws LineLogicException{
        this.varsTable = varsTable;
        curToken = 1;
        try {
            if(wordsLine.get(curToken).equals("(")){
                curToken++;
                condition(wordsLine);
                if(wordsLine.get(curToken).equals("{")&&curToken==wordsLine.size()-1){
                    varsTable.addLayer();
                }
                else {
                    throw new LineLogicException("if/while not ended with {");
                }
            }
            else {
                throw new LineLogicException("if/while not open (");

            }
        }
        catch (IndexOutOfBoundsException e){
            throw new LineLogicException("bad declare vars line");
        }
    }
    /**
     * It returns the type of the value passed to it
     *
     * @param val The value to be checked
     * @return The type of the value.
     */
    private String valType(String val) throws LineLogicException {
        switch (val){
            case INT:
                return "int";
            case DOUBLE:
                return "double";
            case BOOLEAN:
                return "boolean";
        }
        return "null";
    }

    /**
     * It checks if the condition in if/while is correct
     *
     * @param wordsLine the line of code, split into words
     */
    private void condition(ArrayList<String> wordsLine) throws LineLogicException {
        String type = "boolean";
        while (true){
            if (VarMatching.canBeSetWith(type, valType(wordsLine.get(curToken)))||
                    (varsTable.containsVar(wordsLine.get(curToken))
                            && varsTable.varType(wordsLine.get(curToken)).charAt(0) != 'u'
                            && VarMatching.canBeSetWith(type,
                            varsTable.varType(wordsLine.get(curToken)).substring(1)))) {
                curToken++;
            }
            else {
                throw new LineLogicException("wrong expression in if/while condition");
            }
            if(wordsLine.get(curToken).equals("||")||wordsLine.get(curToken).equals("&&")){
                curToken++;
            }
            else {
                if (wordsLine.get(curToken).equals(")")){
                    curToken++;
                    break;
                }
                else {
                    throw new LineLogicException("condition in if/while not ended with )");
                }
            }
        }
    }

}

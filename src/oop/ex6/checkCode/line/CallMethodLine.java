package oop.ex6.checkCode.line;

import oop.ex6.VarMatching;
import oop.ex6.tables.MethodsTable;
import oop.ex6.tables.VarsTable;
import oop.ex6.validity.ValidMethodName;

import java.util.ArrayList;

public class CallMethodLine {
    public static final String INT = "0intVal";
    public static final String DOUBLE = "?doubleVal";
    public static final String CHAR = "?charVal";
    public static final String BOOLEAN = "?booleanVal";
    public static final String STRING = "?StringVal";
    private ArrayList<String> varsToPut;
    private MethodsTable methodsTable;
    private int curToken;
    private VarsTable varsTable;

    public CallMethodLine(ArrayList<String> wordsLine, MethodsTable methodsTable, VarsTable varsTable)
            throws LineLogicException, InvalidNameException{
        this.varsTable = varsTable;
        varsToPut = new ArrayList<>();
        this.methodsTable = methodsTable;
        String methodName;
        curToken = 0;
        try {
            methodName = wordsLine.get(curToken);
            curToken++;
            if(wordsLine.get(curToken).equals("(")){
                curToken++;
                varsAdd(wordsLine);
                if(wordsLine.get(curToken).equals(";")&&curToken==wordsLine.size()-1){
                    if(!methodsTable.containsMethod(methodName, varsToPut)){
                        throw new LineLogicException("cant find matching declared function to the call");
                    }
                }
                else {
                    throw new LineLogicException("function declaration not ended with {");
                }
            }
            else {
                throw new LineLogicException("function call not open (");

            }
        }
        catch (IndexOutOfBoundsException e){
            throw new LineLogicException("bad call func line");
        }


    }
    private String valType(String val) throws LineLogicException {
        switch (val){
            case INT:
                return "dint";
            case DOUBLE:
                return "ddouble";
            case BOOLEAN:
                return "dboolean";
            case CHAR:
                return "dchar";
            case STRING:
                return "dString";
        }
        return "null";
    }
    private void varsAdd(ArrayList<String> wordsLine) throws LineLogicException {
        if (wordsLine.get(curToken).equals(")")) {
            curToken++;
            return;
        }
        String var;
        while (true){
            if (!valType(wordsLine.get(curToken)).equals("null")||
                    varsTable.containsVar(wordsLine.get(curToken))) {
                var = valType(wordsLine.get(curToken));
                if (varsTable.containsVar(wordsLine.get(curToken))){
                    var = varsTable.varType(wordsLine.get(curToken));
                }
                varsToPut.add(var);
                curToken++;
            }
            else {
                throw new LineLogicException("wrong parameter in function call");
            }
            if(wordsLine.get(curToken).equals(",")){
                curToken++;
            }
            else {
                if (wordsLine.get(curToken).equals(")")){
                    curToken++;
                    break;
                }
                else {
                    throw new LineLogicException("parameter list in function cal not end with )");
                }
            }
        }

    }
}

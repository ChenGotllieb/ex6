package oop.ex6.checkCode;

import oop.ex6.checkCode.line.*;
import oop.ex6.tables.MethodsTable;
import oop.ex6.tables.VarsTable;

import java.util.ArrayList;

public class CheckCode {
    private ArrayList<ArrayList<String>> lines;
    private ArrayList<String> externalScope;
    private ArrayList<String> methodScope;
    private VarsTable varsTable;
    private MethodsTable methodsTable;
    private AssignVarsLine assignVarsLine;
    private CallMethodLine callMethodLine;
    private DeclareMethodLine declareMethodLine;
    private DeclareVarsLine declareVarsLine;
    private EndOfScopeLine endOfScopeLine;
    private IfWhileLine ifWhileLine;
    private ReturnLine returnLine;
    private boolean wasReturn = false;

    // This is the constructor of the class. It gets an array of lines and creates a new instance of the class.
    public CheckCode( ArrayList<ArrayList<String>>lines) throws ScopeLogicException,
            InvalidNameException, LineLogicException {
        this.lines = lines;
        externalScope = new ArrayList<>();
        methodScope = new ArrayList<>();
        varsTable = new VarsTable();
        varsTable.addLayer();
        methodsTable = new MethodsTable();
        iterateExternalScope();
        iterateInternalScopes();
    }
    /**
     * It checks if the number of opening and closing parentheses is equal
     */
    private void iterateExternalScope() throws ScopeLogicException, InvalidNameException,
            LineLogicException {
        int parCounter = 0;
        for (ArrayList<String> line:this.lines){
            if(parCounter==0){
                checkExternalScopeLine(line);
            }
            for (String token:line){
                if(token.equals("{")){
                    parCounter++;
                }
                if (token.equals("}")){
                    parCounter--;
                }
                if (parCounter<0){
                    throw new ScopeLogicException("too many }");
                }
            }
        }
        if (parCounter>0){
            throw new ScopeLogicException("not all scopes were closed, missing }");
        }
    }

    /**
     * It iterates over the lines of the method, and checks each line for validity
     */
    private void iterateInternalScopes() throws ScopeLogicException, InvalidNameException,
            LineLogicException {
        int parCounter = 0;
        for (ArrayList<String> line:this.lines){
            if(parCounter==0){
                if (line.get(0).equals("void")){
                    varsTable.addLayer();
                    for (String[] nameType: methodsTable.getVars(line.get(1))){
                        varsTable.addVar(nameType[0], nameType[1]);
                    }
                }
            }
            if (parCounter>0){
                checkInternalScopeLine(line);
            }
            for (String token:line){
                if(token.equals("{")){
                    parCounter++;
                }
                if (token.equals("}")){
                    if (parCounter>1||this.wasReturn) {
                        parCounter--;
                    }
                    else{
                        throw new ScopeLogicException("missing return before end of method");
                    }
                }
            }
            wasReturn = line.get(0).equals("return");
        }
    }



    /**
     * The function checks if the line is a variable declaration, a method declaration or an assignment to a
     * variable
     *
     * @param line the line to be checked
     */
    private void checkExternalScopeLine(ArrayList<String> line) throws InvalidNameException,
            LineLogicException, ScopeLogicException {
        switch (line.get(0)){
            case "final":
            case "int":
            case "String":
            case "double":
            case "boolean":
            case "char":
                declareVarsLine=new DeclareVarsLine(line, varsTable);
                break;
            case "void":
                declareMethodLine=new DeclareMethodLine(line, methodsTable);
                break;
            default:
                if(varsTable.containsVar(line.get(0))){
                    assignVarsLine = new AssignVarsLine(line,varsTable);
                    break;
                }
                else {
                    throw new ScopeLogicException("wrong line kind in external scope");
                }
        }
    }

    /**
     * The function checks the first word of the line and decides which line type it is
     *
     * @param line the line we want to check
     */
    private void checkInternalScopeLine(ArrayList<String> line) throws InvalidNameException,
            LineLogicException, ScopeLogicException {
        switch (line.get(0)){
            case "final":
            case "int":
            case "String":
            case "double":
            case "boolean":
            case "char":
                declareVarsLine=new DeclareVarsLine(line, varsTable);
                break;
            case "if":
            case "while":
                ifWhileLine=new IfWhileLine(line, varsTable);
                break;
            case "return":
                returnLine = new ReturnLine(line);
                break;
            case "}":
                endOfScopeLine = new EndOfScopeLine(line,varsTable);
                break;
            default:
                if(line.size()>2){
                    if(varsTable.containsVar(line.get(0))&&line.get(1).equals("=")){
                        assignVarsLine = new AssignVarsLine(line,varsTable);
                        break;
                    }
                    if(line.get(1).equals("(")){
                        callMethodLine = new CallMethodLine(line, methodsTable,varsTable);
                        break;
                    }
                }
                throw new ScopeLogicException("cant define internal scope line kind");

        }
    }
}

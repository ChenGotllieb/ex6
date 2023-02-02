package oop.ex6.tables;

import oop.ex6.VarMatching;
import oop.ex6.checkCode.line.ReturnLine;

import java.util.ArrayList;
import java.util.HashMap;

public class VarsTable {

    public ArrayList<HashMap<String, String>> vars;
    public VarsTable(){
        vars = new ArrayList<>();
    }
    public void addLayer(){
        vars.add(new HashMap<>());
    }
    public void deleteLayer(){
        vars.remove(vars.size()-1);
    }
    /**
     * This function adds a variable to the current scope
     *
     * @param name The name of the variable
     * @param type The type of the variable.
     * @return A boolean value.
     */
    public boolean addVar(String name, String type){
        if (vars.get(vars.size()-1).containsKey(name)){
            return false;
        }
        vars.get(vars.size()-1).put(name, type);
        return true;
    }
    /**
     * If the variable name is in the current scope, return true, otherwise, return false.
     *
     * @param name The name of the variable you want to check for.
     * @return The value of the variable.
     */
    public boolean containsVar(String name){
        for (int i = vars.size()-1; i>=0; i--){
            if(vars.get(i).containsKey(name)){
                return true;
            }
        }
        return false;
    }
    /**
     * It returns the type of the variable with the given name, or "null" if the variable is not found
     *
     * @param name the name of the variable
     * @return The type of the variable.
     */
    public String varType(String name){
        for (int i = vars.size()-1; i>=0; i--){
            if(vars.get(i).containsKey(name)){
                return vars.get(i).get(name);
            }
        }
        return "null";
    }
    /**
     * If the variable exists and is not a function, then set the variable to the given type
     *
     * @param name the name of the variable
     * @param type The type of the variable.
     * @return A boolean value.
     */
    public boolean setVal(String name, String type){
        if(containsVar(name)&&varType(name).charAt(0)!='f'){
            String sourceType = varType(name).substring(1);
            String valType = type.substring(1);
            if(VarMatching.canBeSetWith(sourceType, valType)){
                vars.get(vars.size()-1).put(name, "d"+sourceType);
                return true;
            }
        }
        return false;
    }
}

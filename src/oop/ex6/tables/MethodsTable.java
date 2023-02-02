package oop.ex6.tables;

import oop.ex6.VarMatching;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodsTable {
    public HashMap<String, ArrayList<String[]>> methods;
    public MethodsTable(){
        methods = new HashMap<>();
    }
    /**
     * This function adds a method to the class
     *
     * @param name the name of the method
     * @param vars ArrayList of String arrays. Each String array is a variable.
     *            The first element is the variable
     * name, the second is the variable type.
     * @return A boolean value.
     */
    public boolean addMethod(String name, ArrayList<String[]> vars){
        if (methods.containsKey(name)){
            return false;
        }
        methods.put(name, vars);
        return true;
    }
    /**
     * It checks if the class contains a method with the given name and parameter types
     *
     * @param name the name of the method
     * @param types The types of the parameters of the method.
     * @return A boolean value.
     */
    public boolean containsMethod(String name, ArrayList<String> types){
        if(!methods.containsKey(name)|methods.get(name).size()!=types.size()){
            return false;
        }
        ArrayList<String[]> source = methods.get(name);
        for (int i = 0; i<types.size();i++) {
            if (types.get(i).charAt(0) != 'u') {
                String sourceType = source.get(i)[1].substring(1);
                String valType = types.get(i).substring(1);
                if (!VarMatching.canBeSetWith(sourceType, valType)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }
    /**
     * It returns an ArrayList of String arrays,
     * which are the variables of the method with the name passed in
     *
     * @param name The name of the method
     * @return An ArrayList of String arrays.
     */
    public ArrayList<String[]> getVars(String name){
        return methods.get(name);
    }
}

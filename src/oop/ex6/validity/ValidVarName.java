package oop.ex6.validity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidVarName {
    /**
     * It matches a string that starts with a letter or an underscore, followed by zero or more letters,
     * numbers, or underscores
     *
     * @param name The name of the variable.
     * @return A boolean value.
     */
    static public boolean isValidName(String name){
        Pattern pattern = Pattern.compile("([a-zA-Z]([0-9a-zA-Z_])*)|(_([0-9a-zA-Z_])+)");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}

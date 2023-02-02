package oop.ex6.validity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidMethodName {
    /**
     * It checks if the string is a valid Java identifier
     *
     * @param name The name of the variable.
     * @return A boolean value.
     */
    static public boolean isValidName(String name){
        Pattern pattern = Pattern.compile("([a-zA-Z]([0-9a-zA-Z_])*)");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}

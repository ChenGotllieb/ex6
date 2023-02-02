package oop.ex6.main;

import oop.ex6.checkCode.CheckCode;
import oop.ex6.checkCode.ScopeLogicException;
import oop.ex6.checkCode.line.*;
import oop.ex6.tables.MethodsTable;
import oop.ex6.tables.VarsTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sjavac {
    /**
     * It reads the file, deletes comments, checks for invalid characters, adds whitespace, and then sends the
     * lines to the CheckCode class
     */
    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        if (!readFile(args[0], lines)){
            return;
        }
        deleteComments(lines);
        checkString(lines);
        checkChar(lines);
        checkDouble(lines);
        checkInt(lines);
        checkBoolean(lines);
        addWhiteSpace(lines);
        toWords(words, lines);

        try {
            CheckCode checkCode = new CheckCode(words);
        }
        catch (InvalidNameException | ScopeLogicException | LineLogicException e) {
            System.out.println("1");
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("0");
    }
    /**
     * Reads a file and stores each line in an ArrayList
     *
     * @param fileName The name of the file to read.
     * @param lines This is the ArrayList that will contain the lines of the file.
     * @return The method is returning a boolean value.
     */
    static private boolean readFile(String fileName, ArrayList<String> lines){
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("2");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    /**
     * It removes all lines that start with "//" or are empty
     *
     * @param lines The ArrayList of lines from the file.
     */
    static private void deleteComments(ArrayList<String> lines) {
        Iterator<String> itr = lines.iterator();
        while (itr.hasNext()) {
            String line = itr.next();
            Pattern pattern = Pattern.compile("^//|^\\s*$");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                itr.remove();
            }
        }
    }
    /**
     * It takes an ArrayList of Strings, and replaces all the strings in the ArrayList with the String
     * "?StringVal"
     *
     * @param lines the ArrayList of lines from the file
     */
    static private void checkString(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("\"([^\"]*)\"", "?StringVal");
            lines.set(i,line);
            }
    }
    /**
     * It replaces all single quotes with the string ?charVal
     *
     * @param lines the ArrayList of lines from the file
     */
    static private void checkChar(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("'([^'])'", "?charVal");
            lines.set(i,line);
        }
    }
    /**
     * This function takes an ArrayList of strings and replaces all double values with the string "?doubleVal"
     *
     * @param lines the ArrayList of lines from the file
     */
    static private void checkDouble(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("[+-]?(\\d*\\.\\d+)|(\\d+\\.\\d*)", "?doubleVal");
            lines.set(i,line);
        }
    }
    /**
     * This function takes in an ArrayList of Strings and replaces all integers in the ArrayList with the String
     * "0intVal"
     *
     * @param lines the ArrayList of lines of code
     */
    static private void checkInt(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("[+-]?(\\d+)", "0intVal");
            lines.set(i,line);
        }
    }
    /**
     * This function replaces all instances of the words "true" and "false" with the string "?booleanVal" in the
     * ArrayList of Strings
     *
     * @param lines The lines of code that you want to check for boolean values.
     */
    static private void checkBoolean(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("true|false", "?booleanVal");
            lines.set(i,line);
        }

    }
    /**
     * It takes an ArrayList of strings, and adds whitespace around all the characters that are used to separate
     * tokens in Java
     *
     * @param lines the list of lines in the file
     */
    static private void addWhiteSpace(ArrayList<String> lines){
        for (int i = 0; i< lines.size(); i++) {
            String line = lines.get(i).replaceAll("([;={}(),])", " $1 ");
            line = line.replaceAll("(&&|\\|\\|)", " $1 ");
            line = line.strip();
            lines.set(i,line);
        }
    }

    /**
     * It takes an ArrayList of ArrayLists of Strings (words) and an ArrayList of Strings (lines) and adds to
     * words an ArrayList of Strings for each line in lines
     *
     * @param words The ArrayList of ArrayLists of Strings that will be returned.
     * @param lines The lines of the file
     */
    static private void toWords(ArrayList<ArrayList<String>> words, ArrayList<String> lines){
        for (String line:lines) {
            ArrayList<String> wordsLine =  new ArrayList<>(Arrays.asList(line.split("\\s+")));
            words.add(wordsLine);
        }
    }
}

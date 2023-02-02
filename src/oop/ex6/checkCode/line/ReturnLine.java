package oop.ex6.checkCode.line;

import oop.ex6.tables.VarsTable;

import java.util.ArrayList;

public class ReturnLine {
    // It checks if the return line is valid.
    public ReturnLine(ArrayList<String> wordsLine) throws LineLogicException{
        if(!(wordsLine.size()==2||wordsLine.get(1).equals(";"))) {
            throw new LineLogicException("Bad return line");
        }
    }

}

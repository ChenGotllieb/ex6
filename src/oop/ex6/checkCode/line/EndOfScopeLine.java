package oop.ex6.checkCode.line;

import oop.ex6.tables.VarsTable;

import java.util.ArrayList;

public class EndOfScopeLine {
    // This is the constructor of the class EndOfScopeLine. It gets an ArrayList of strings and a VarsTable.
    // It checks if the ArrayList has only one string in it, if so it deletes a layer from the VarsTable.
    // If not, it throws an exception.
    public EndOfScopeLine(ArrayList<String> wordsLine, VarsTable varsTable) throws LineLogicException{
        if(wordsLine.size()==1) {
            varsTable.deleteLayer();
        }
        else {
            throw new LineLogicException("bad end of scope line");
        }
    }
}

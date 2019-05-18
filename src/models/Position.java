package models;

import javafx.util.Pair;

public class Position {
    private Pair<Integer, Integer> p;
    Position(int row , int column){
        p = new Pair<>(row,column);
    }
    public int getRow(){
        return p.getKey();
    }
    public int getColumn(){
        return p.getValue();
    }
    public String toString(){
        return "(row: "+p.getKey()+" , column: "+p.getValue()+")";
    }
}

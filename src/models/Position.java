package models;

import javafx.util.Pair;

public class Position {
    private Pair<Integer, Integer> p;

    public Position(int row, int column) {
        p = new Pair<>(row, column);
    }

    public int getRow() {
        return p.getKey();
    }

    public int getColumn() {
        return p.getValue();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (other.getRow() != getRow() || other.getColumn() != getColumn())
            return false;
        return true;
    }

    public int hashCode(){
        return getColumn()+getRow();
    }

    public String toString() {

        return "(row: " + p.getKey() + " , column: " + p.getValue() + ")";
    }
}

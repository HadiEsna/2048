package models;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    int row, column;
    private Player player;
    private int table[][];
    private boolean lost;
    private int score = 0;

    private ArrayList<Pair<Position, Position>> downOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> upOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> leftOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> rightOptions = new ArrayList<>();

    Game(Player player, int row, int column) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.table = new int[row][column];
    }

    private ArrayList<Position> countEmptyBlocks() {
        ArrayList<Position> emptyBlocks = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (table[i][j] == 0)
                    emptyBlocks.add(new Position(i, j));
            }
        }
        return emptyBlocks;
    }

    public void applychanges(ArrayList<Pair<Position, Position>> changes){
        for (Pair<Position, Position> pair:changes){
            Position destination = pair.getValue();
            score+=table[destination.getRow()][destination.getColumn()]*2;
            table[destination.getRow()][destination.getColumn()] += table[pair.getKey().getRow()][pair.getKey().getColumn()];
            table[pair.getKey().getRow()][pair.getKey().getColumn()] = 0;
        }
        action();
    }

    public void action() {
        Random random = new Random();
        ArrayList<Position> emptyBlocks = countEmptyBlocks();
        if (!emptyBlocks.isEmpty()) {
            Position randomBlock = emptyBlocks.get(random.nextInt(emptyBlocks.size()));
            table[randomBlock.getRow()][randomBlock.getColumn()] += 2;
        }
        downOptions = findDownOptions();
        upOptions = findUpOptions();
        leftOptions = findLeftOptions();
        rightOptions = findRightOptions();
        if (downOptions.isEmpty() && upOptions.isEmpty() && leftOptions.isEmpty() && rightOptions.isEmpty()) {
            this.lost = true;
        }
    }



    public ArrayList<Pair<Position, Position>> findUpOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = table.clone();
        for (int i = 0; i < column; i++) {
            for (int j = 1; j < row; j++) {
                if (tempTable[j][i] > 0) {
                    int finalJ = j;
                    while (true) {
                        finalJ--;
                        if (finalJ <= 0)
                            break;
                        if (tempTable[finalJ][i] == 0)
                            continue;
                        if (tempTable[finalJ][i] == table[j][i])
                            break;
                    }
                    tempTable[finalJ][i] += table[j][i];
                    table[j][i] = 0;
                    changes.add(new Pair<>(new Position(j, i), new Position(finalJ, i)));
                }
            }
        }
        return changes;
    }

    public ArrayList<Pair<Position, Position>> findDownOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = table.clone();
        for (int i = 0; i < column; i++) {
            for (int j = row - 2; j >= 0; j--) {
                if (tempTable[j][i] > 0) {
                    int finalJ = j;
                    while (true) {
                        finalJ++;
                        if (finalJ == row - 1)
                            break;
                        if (tempTable[finalJ][i] == 0)
                            continue;
                        if (tempTable[finalJ][i] == table[j][i])
                            break;
                    }
                    tempTable[finalJ][i] += table[j][i];
                    table[j][i] = 0;
                    changes.add(new Pair<>(new Position(j, i), new Position(finalJ, i)));
                }
            }
        }
        return changes;
    }

    public ArrayList<Pair<Position, Position>> findLeftOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = table.clone();
        for (int j = 0; j < row; j++) {
            for (int i = 1; i < column; i++) {
                if (tempTable[j][i] > 0) {
                    int finalI = i;
                    while (true) {
                        finalI++;
                        if (finalI == 0)
                            break;
                        if (tempTable[j][finalI] == 0)
                            continue;
                        if (tempTable[j][finalI] == table[j][i])
                            break;
                    }
                    tempTable[j][finalI] += table[j][i];
                    table[j][i] = 0;
                    changes.add(new Pair<>(new Position(j, i), new Position(j, finalI)));
                }
            }
        }
        return changes;
    }

    public ArrayList<Pair<Position, Position>> findRightOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = table.clone();
        for (int i = 0; i < row; i++) {
            for (int j = column - 2; j >= 0; j--) {
                if (table[i][j] > 0) {
                    int finalI = i;
                    while (true) {
                        finalI--;
                        if (finalI == column - 1)
                            break;
                        if (tempTable[j][finalI] == 0)
                            continue;
                        if (tempTable[j][finalI] == table[j][i])
                            break;
                    }
                    tempTable[j][finalI] += table[j][i];
                    table[j][i] = 0;
                    changes.add(new Pair<>(new Position(j, i), new Position(j, finalI)));

                }
            }
        }
        return changes;
    }

}

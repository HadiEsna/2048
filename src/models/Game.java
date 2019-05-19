package models;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    int row, column;
    Random random = new Random();
    private int table[][];
    private boolean lost;
    private int score = 0;
    private ArrayList<Pair<Position, Position>> downOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> upOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> leftOptions = new ArrayList<>();
    private ArrayList<Pair<Position, Position>> rightOptions = new ArrayList<>();

    public Game(int row, int column) {
        this.row = row;
        this.column = column;
        this.table = new int[row][column];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(1, 20);
        game.action();
        game.action();
        printTable(game);
        while (true) {
            int x = scanner.nextInt();
            if (x == 8) {
                game.applychanges(game.upOptions);
            }
            if (x == 2) {
                game.applychanges(game.downOptions);
            }
            if (x == 4) {
                game.applychanges(game.leftOptions);
            }
            if (x == 6) {
                game.applychanges(game.rightOptions);
            }
            printTable(game);
        }
    }

    private static void printTable(Game game) {
        for (int i = 0; i < game.row; i++) {
            for (int j = 0; j < game.column; j++) {
                System.out.print(" " + game.table[i][j]);
            }
            System.out.println();
        }
        System.out.println(game.score);
    }

    private ArrayList<Position> countEmptyBlocks() {
        ArrayList<Position> emptyBlocks = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (table[i][j] == 0)
                    emptyBlocks.add(new Position(i, j));
            }
        }
        return emptyBlocks;
    }

    public void applychanges(ArrayList<Pair<Position, Position>> changes) {
        for (Pair<Position, Position> pair : changes) {
            Position destination = pair.getValue();
            score += table[destination.getRow()][destination.getColumn()] * 2;
            table[destination.getRow()][destination.getColumn()] += table[pair.getKey().getRow()][pair.getKey().getColumn()];
            table[pair.getKey().getRow()][pair.getKey().getColumn()] = 0;
        }
        action();
    }

    public void action() {
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
        int[][] tempTable = new int[row][column];
        for (int i = 0; i < column; i++) for (int j = 0; j < row; j++) tempTable[j][i] = table[j][i];

        for (int i = 0; i < column; i++) {
            for (int j = 1; j < row; j++) {
                if (tempTable[j][i] > 0) {
                    int finalJ = j;
                    while (true) {
                        if (finalJ == 0) {
                            tempTable[finalJ][i] = tempTable[j][i];
                            break;
                        }
                        if (tempTable[finalJ - 1][i] > 0) {
                            if (tempTable[finalJ - 1][i] == tempTable[j][i]) {
                                finalJ--;
                                tempTable[finalJ][i] = 1;
                            } else {
                                tempTable[finalJ][i] = tempTable[j][i];
                            }
                            break;
                        }
                        finalJ--;
                    }
                    if (finalJ != j) {
                        tempTable[j][i] = 0;
                        changes.add(new Pair<>(new Position(j, i), new Position(finalJ, i)));
                    }
                }
            }
        }
        return changes;
    }


    public ArrayList<Pair<Position, Position>> findDownOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = new int[row][column];
        for (int i = 0; i < column; i++) for (int j = 0; j < row; j++) tempTable[j][i] = table[j][i];
        for (int i = 0; i < column; i++) {
            for (int j = row - 2; j >= 0; j--) {
                if (tempTable[j][i] > 0) {
                    int finalJ = j;
                    while (true) {

                        if (finalJ == row - 1) {
                            tempTable[finalJ][i] = tempTable[j][i];
                            break;
                        }
                        if (tempTable[finalJ + 1][i] > 0) {
                            if (tempTable[finalJ + 1][i] == tempTable[j][i]) {
                                finalJ++;
                                tempTable[finalJ][i] = 1;
                            } else {
                                tempTable[finalJ][i] = tempTable[j][i];
                            }
                            break;
                        }
                        finalJ++;
                    }
                    if (finalJ != j) {
                        tempTable[j][i] = 0;
                        changes.add(new Pair<>(new Position(j, i), new Position(finalJ, i)));
                    }
                }
            }
        }
        return changes;
    }

    public ArrayList<Pair<Position, Position>> findLeftOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = new int[row][column];
        for (int i = 0; i < column; i++) for (int j = 0; j < row; j++) tempTable[j][i] = table[j][i];

        for (int j = 0; j < row; j++) {
            for (int i = 1; i < column; i++) {
                if (tempTable[j][i] > 0) {
                    int finalI = i;
                    while (true) {
                        if (finalI == 0) {
                            tempTable[j][finalI] = tempTable[j][i];
                            break;
                        }
                        if (tempTable[j][finalI - 1] > 0) {
                            if (tempTable[j][finalI - 1] == tempTable[j][i]) {
                                finalI--;
                                tempTable[j][finalI] = 1;
                            } else {
                                tempTable[j][finalI] = tempTable[j][i];
                            }
                            break;
                        }
                        finalI--;
                    }
                    if (finalI != i) {
                        tempTable[j][i] = 0;
                        changes.add(new Pair<>(new Position(j, i), new Position(j, finalI)));
                    }
                }
            }
        }
        return changes;
    }

    public ArrayList<Pair<Position, Position>> findRightOptions() {
        ArrayList<Pair<Position, Position>> changes = new ArrayList<>();
        int[][] tempTable = new int[row][column];
        for (int i = 0; i < column; i++) for (int j = 0; j < row; j++) tempTable[j][i] = table[j][i];


        for (int j = 0; j < row; j++) {
            for (int i = column - 2; i >= 0; i--) {
                if (tempTable[j][i] > 0) {
                    int finalI = i;
                    while (true) {
                        if (finalI == column - 1) {
                            tempTable[j][finalI] = tempTable[j][i];
                            break;
                        }
                        if (tempTable[j][finalI + 1] > 0) {
                            if (tempTable[j][finalI + 1] == tempTable[j][i]) {
                                finalI++;
                                tempTable[j][finalI] = 1;
                            } else {
                                tempTable[j][finalI] = tempTable[j][i];
                            }
                            break;
                        }
                        finalI++;
                    }
                    if (finalI != i) {
                        tempTable[j][i] = 0;
                        changes.add(new Pair<>(new Position(j, i), new Position(j, finalI)));
                    }

                }
            }
        }
        return changes;
    }

}

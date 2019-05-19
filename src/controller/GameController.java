package controller;

public class GameController {
    private static GameController ourInstance = new GameController();

    public static GameController getInstance() {
        return ourInstance;
    }

    private GameController() {
    }
}

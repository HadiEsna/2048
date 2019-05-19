package view;

public class GameView {
    private static GameView ourInstance = new GameView();

    public static GameView getInstance() {
        return ourInstance;
    }

    private GameView() {
    }
}

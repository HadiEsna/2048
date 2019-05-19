package controller;

import javafx.scene.Scene;
import models.Game;
import view.GameView;

public class GameController extends P {
    private static GameController ourInstance = new GameController();
    Game game;
    GameView view = GameView.getInstance();
    public void mewGame(int row,int column){
        game = new Game(row,column);
    }
    private GameController() {
    }

    public static GameController getInstance() {
        return ourInstance;
    }

    @Override
    public Scene getScene() {
        view.reset();
        return view.getScene();
    }
}

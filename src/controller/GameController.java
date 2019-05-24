package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import models.Game;
import view.GameView;
import view.MainView;

import java.util.ArrayList;

public class GameController extends P {
    private static GameController ourInstance = new GameController();
    Game game;
    GameView view = GameView.getInstance();
    private long lastChangeTime = 0;

    private GameController() {
    }

    public static GameController getInstance() {
        return ourInstance;
    }

    public void mewGame(int row, int column) {
        view.setColumn(column);
        view.setRow(row);
        game = new Game(row, column);
        game.action();
        game.action();
        view.animation(game.getAnimations());
        Game.printTable(game);
    }

    @Override
    public Scene getScene() {
        view.reset();
        Scene scene = view.getScene();
        ArrayList<KeyCode> input = new ArrayList<>();
        scene.setOnKeyPressed(event -> {
            if (!input.contains(event.getCode())) {
                input.add(event.getCode());
            }
        });
        scene.setOnKeyReleased(event -> {
            input.remove(event.getCode());
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - lastChangeTime > 500) {
                    if (input.contains(KeyCode.UP)) {
                        game.applychanges(game.getUpOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        lastChangeTime = System.currentTimeMillis();
                    } else if (input.contains(KeyCode.DOWN)) {
                        game.applychanges(game.getDownOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        lastChangeTime = System.currentTimeMillis();
                    } else if (input.contains(KeyCode.LEFT)) {
                        game.applychanges(game.getLeftOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        lastChangeTime = System.currentTimeMillis();
                    } else if (input.contains(KeyCode.RIGHT)) {
                        game.applychanges(game.getRightOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        lastChangeTime = System.currentTimeMillis();
                    }
                }
            }
        };
        animationTimer.start();


        return scene;
    }

    public void exit() {
        MainView.getInstance().setLabel(new Label("you lost :( score: " + game.getScore()));
        Controller.getInstance().setScene(MainController.getInstance());
    }
}

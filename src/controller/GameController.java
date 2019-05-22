package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import models.Game;
import view.GameView;

import java.util.ArrayList;

public class GameController extends P {
    private static GameController ourInstance = new GameController();
    Game game;
    GameView view = GameView.getInstance();

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
        try {
            view.animation(game.getAnimations());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                try {
                    if (input.contains(KeyCode.UP)) {
                        game.applychanges(game.getUpOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        Thread.sleep(500);
                    }
                    if (input.contains(KeyCode.DOWN)) {
                        game.applychanges(game.getDownOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        Thread.sleep(500);
                    }
                    if (input.contains(KeyCode.LEFT)) {
                        game.applychanges(game.getLeftOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        Thread.sleep(500);
                    }
                    if (input.contains(KeyCode.RIGHT)) {
                        game.applychanges(game.getRightOptions());
                        Game.printTable(game);
                        view.animation(game.getAnimations());
                        Thread.sleep(500);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();


        return scene;
    }
}

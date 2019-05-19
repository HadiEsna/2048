package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import sun.java2d.FontSupport;


public class GameView extends PView {
    private static GameView ourInstance = new GameView();
    private Group root = new Group();
    private Scene scene = new Scene(root, 400, 300);

    private GameView() {
    }

    public static GameView getInstance() {
        return ourInstance;
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public void reset() {
        root = new Group();
        scene = new Scene(root, 800, 600);
        scene.setFill(Color.grayRgb(20));
        Label label = new Label("score:");
        label.relocate(600, 200);
        Label score = new Label();
        score.relocate(720, 200);
        score.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        score.setTextFill(Color.GHOSTWHITE);
        label.setTextFill(Color.GHOSTWHITE);
        label.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        Button exitButton = new Button("exit");
        exitButton.relocate(690, 450);
        Button pauseButton = new Button("pause");
        pauseButton.relocate(690, 400);
        root.getChildren().addAll(label, score, exitButton, pauseButton);
    }
}

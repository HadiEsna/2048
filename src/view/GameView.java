package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.util.Pair;
import models.Position;

import java.util.ArrayList;
import java.util.HashMap;


public class GameView extends PView {

    private static GameView ourInstance = new GameView();
    private int row, column;
    private Group root = new Group();
    private Scene scene = new Scene(root, 800, 600);
    private HashMap<Position, Pair<Rectangle, Label>> blocks = new HashMap<>();

    private int blockWidth, blockH;

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

    public void animation(ArrayList<Pair<Position, Position>> changes) {
        for (Pair<Position, Position> change : changes) {
            if (change.getKey().getColumn() < 0) {
                Position des = getPosiotion(change.getValue());
                Rectangle rectangle = new Rectangle(des.getColumn() + blockWidth / 2, des.getRow() + blockH / 2, 0, 0);
                int integer = 2;
                Label label = new Label(Integer.toString(integer));
                label.relocate(des.getColumn() + blockWidth / 2, des.getRow() + blockH / 2);
                label.setTextFill(Color.GHOSTWHITE);
                root.getChildren().add(rectangle);
                root.getChildren().add(label);
                blocks.put(change.getValue(), new Pair<>(rectangle, label));
                KeyValue rHValue = new KeyValue(rectangle.heightProperty(), blockH);
                KeyValue rWValue = new KeyValue(rectangle.widthProperty(), blockWidth);
                KeyValue rXValue = new KeyValue(rectangle.xProperty(), des.getColumn());
                KeyValue rYValue = new KeyValue(rectangle.yProperty(), des.getRow());
                KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), rXValue, rYValue, rHValue, rWValue);
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                rectangle.setFill(Color.GRAY);
                label.setFont(Font.font(30));
            } else {
                int blockScore = 0;
                Rectangle mainRectangle = blocks.get(change.getKey()).getKey();
                Label label = blocks.get(change.getKey()).getValue();
                blocks.remove(change.getKey());
                blockScore += Integer.parseInt(label.getText());
                if (blocks.containsKey(change.getValue())) {
                    Rectangle rectangle = blocks.get(change.getValue()).getKey();
                    Label otherLabel = blocks.get(change.getValue()).getValue();
                    root.getChildren().remove(rectangle);
                    root.getChildren().remove(otherLabel);
                    blockScore += Integer.parseInt(otherLabel.getText());
                }
                KeyValue xValue = new KeyValue(mainRectangle.xProperty(), getPosiotion(change.getValue()).getColumn());
                KeyValue yValue = new KeyValue(mainRectangle.yProperty(), getPosiotion(change.getValue()).getRow());
                KeyValue xLValue = new KeyValue(label.layoutXProperty(), getPosiotion(change.getValue()).getColumn() + blockWidth / 2);
                KeyValue yLValue = new KeyValue(label.layoutYProperty(), getPosiotion(change.getValue()).getRow() + blockH / 2);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(500), xValue, yValue, xLValue, yLValue);
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                int finalBlockScore = blockScore;
                timeline.setOnFinished(event -> {
                    label.setText(Integer.toString(finalBlockScore));
                    mainRectangle.setFill(getColor(label.getText()));
                });
                blocks.put(change.getValue(), new Pair<>(mainRectangle, label));

            }
        }
    }

    private Color getColor(String text) {
        if (Integer.parseInt(text) == 2) {
            return Color.color((double) 120 / 255, (double) 131 / 255, (double) 142 / 255);
        } else if (Integer.parseInt(text) == 4) {
            return Color.color((double) 69 / 255, (double) 184 / 255, (double) 172 / 255);
        } else if (Integer.parseInt(text) == 8) {
            return Color.color((double) 148 / 255, (double) 96 / 255, (double) 55 / 255);
        } else if (Integer.parseInt(text) == 16) {
            return Color.color((double) 200 / 255, 0, (double) 161 / 255);
        } else if (Integer.parseInt(text) == 32) {
            return Color.color(1, (double) 181 / 255, (double) 17 / 255);
        } else if (Integer.parseInt(text) == 64) {
            return Color.color(0, (double) 175 / 255, (double) 215 / 255);
        } else if (Integer.parseInt(text) == 128) {
            return Color.color(0, (double) 132 / 255, (double) 61 / 255);
        } else if (Integer.parseInt(text) == 256) {
            return Color.color(1, (double) 105 / 255, 0);
        } else if (Integer.parseInt(text) == 512) {
            return Color.color((double) 196 / 255, (double) 214 / 255, 0);
        } else if (Integer.parseInt(text) == 1024) {
            return Color.color((double) 55 / 255, (double) 81 / 255, (double) 114 / 255);
        } else
            return Color.color((double) 239 / 255, (double) 66 / 255, (double) 111 / 255);


    }


    public void reset() {
        root = new Group();
        scene = new Scene(root, 800, 600);
        scene.setFill(Color.grayRgb(20));
        Label label = new Label("score:");
        label.relocate(650, 200);
        Label score = new Label("1");
        score.relocate(720, 200);
        score.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        score.setTextFill(Color.GHOSTWHITE);
        label.setTextFill(Color.GHOSTWHITE);
        label.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        Button exitButton = new Button("exit");
        exitButton.relocate(690, 450);
        Button pauseButton = new Button("pause");
        pauseButton.relocate(690, 400);
        Line line = new Line(600, 0, 600, 600);
        line.setFill(Color.GHOSTWHITE);
        root.getChildren().addAll(label, score, exitButton, pauseButton, line);
    }

    private Position getPosiotion(Position d) {
        return new Position(d.getRow() * (600 / row), d.getColumn() * ((600 / column)));
    }

    public void setRow(int row) {
        this.row = row;
        this.blockH = (600 / row) - 10;
    }

    public void setColumn(int column) {
        this.column = column;
        this.blockWidth = (600 / column) - 10;
    }
}

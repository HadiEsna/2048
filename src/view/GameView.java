package view;

import controller.Controller;
import controller.GameController;
import controller.MainController;
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
    private Label label;
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
                Position des = getPosition(change.getValue());
                Rectangle rectangle = new Rectangle(des.getColumn() + blockWidth / 2, des.getRow() + blockH / 2, 0, 0);
                int integer = 2;
                Label label = new Label(Integer.toString(integer));
                label.relocate(des.getColumn() + blockWidth / 3, des.getRow() + blockH / 3);
                label.setTextFill(Color.GHOSTWHITE);
                root.getChildren().add(rectangle);
                root.getChildren().add(label);
                blocks.put(change.getValue(), new Pair<>(rectangle, label));
                KeyValue rHValue = new KeyValue(rectangle.heightProperty(), blockH);
                KeyValue rWValue = new KeyValue(rectangle.widthProperty(), blockWidth);
                KeyValue rXValue = new KeyValue(rectangle.xProperty(), des.getColumn());
                KeyValue rYValue = new KeyValue(rectangle.yProperty(), des.getRow());
                KeyFrame keyFrame = new KeyFrame(Duration.millis(400), rXValue, rYValue, rHValue, rWValue);
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                rectangle.setFill(getColor("2"));
                label.setFont(Font.font(30));
                rectangle.setArcHeight(5);
                rectangle.setArcWidth(5);
            } else {
                int blockScore = 0;
                Rectangle mainRectangle = blocks.get(change.getKey()).getKey();
                Label label = blocks.get(change.getKey()).getValue();
                blocks.remove(change.getKey());
                blockScore += Integer.parseInt(label.getText());
                Rectangle other = null;
                Label otherLabel = null;
                if (blocks.containsKey(change.getValue())) {
                    other = blocks.get(change.getValue()).getKey();
                    otherLabel = blocks.get(change.getValue()).getValue();
                    blockScore += Integer.parseInt(otherLabel.getText());
                    int x = Integer.parseInt(this.label.getText()) + blockScore;
                    this.label.setText("" + x);
                }
                KeyValue xValue = new KeyValue(mainRectangle.xProperty(), getPosition(change.getValue()).getColumn());
                KeyValue yValue = new KeyValue(mainRectangle.yProperty(), getPosition(change.getValue()).getRow());
                KeyValue xLValue = new KeyValue(label.layoutXProperty(), getPosition(change.getValue()).getColumn() + blockWidth / 3);
                KeyValue yLValue = new KeyValue(label.layoutYProperty(), getPosition(change.getValue()).getRow() + blockH / 3);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(400), xValue, yValue, xLValue, yLValue);
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
                int finalBlockScore = blockScore;
                Rectangle finalOther = other;
                Label finalOtherLabel = otherLabel;
                timeline.setOnFinished(event -> {
                    if (finalOther != null) {
                        root.getChildren().remove(finalOther);
                        root.getChildren().remove(finalOtherLabel);
                    }
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
        Label score = new Label("0");
        score.relocate(720, 200);
        this.label = score;
        score.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        score.setTextFill(Color.GHOSTWHITE);
        label.setTextFill(Color.GHOSTWHITE);
        label.setFont(Font.font("chilanka", FontWeight.BOLD, 17));
        Button exitButton = new Button("exit");
        exitButton.setOnMousePressed(event -> {
            GameController.getInstance().exit();
        });
        exitButton.relocate(690, 450);

        Line line = new Line(600, 0, 600, 600);
        line.setFill(Color.GHOSTWHITE);
        root.getChildren().addAll(label, score, exitButton, line);
    }

    private Position getPosition(Position d) {
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

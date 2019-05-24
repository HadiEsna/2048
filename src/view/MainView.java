package view;

import controller.Controller;
import controller.GameController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class MainView extends PView {
    private static MainView ourInstance = new MainView();
    int row = 4;
    int column = 4;
    private Spinner<Integer> rowSpinner;
    private Spinner<Integer> columnSpinner;
    private Label label;
    private Group root = new Group();
    private Scene scene = new Scene(root, 400, 300);

    private MainView() {
    }

    public static MainView getInstance() {
        return ourInstance;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getRow() {
        return row = rowSpinner.getValue();
    }

    public int getColumn() {
        return column = columnSpinner.getValue();
    }

    public void reset() {
        root = new Group();
        scene = new Scene(root, 400, 300);
        Stop[] stops = new Stop[]{
                new Stop(1, Color.GREEN),
                new Stop(0.5, Color.DARKCYAN),
                new Stop(0, Color.DARKSLATEBLUE)
        };

        LinearGradient linearGradient =
                new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        scene.setFill(linearGradient);
        rowSpinner = new Spinner<>(3, 15, 4);
        columnSpinner = new Spinner<>(3, 15, 4);
        rowSpinner.relocate(160, 60);
        columnSpinner.relocate(160, 100);
        Button button = new Button("enter battle");
        button.relocate(150, 200);
        button.setOnMousePressed(event -> {
            Controller.getInstance().setScene(GameController.getInstance());
            GameController.getInstance().mewGame(MainView.getInstance().getRow(), MainView.getInstance().getColumn());

        });
        if (label != null) {
            root.getChildren().add(label);
            label.relocate(100, 50);
            label.setTextFill(Color.GHOSTWHITE);
        }
        root.getChildren().addAll(button, rowSpinner, columnSpinner);
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }
}
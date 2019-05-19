package controller;

import javafx.stage.Stage;

public class Controller {
    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("X vs O");

        primaryStage.show();
    }
}

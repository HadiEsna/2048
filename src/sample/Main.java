package sample;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    int score = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller.getInstance().start(primaryStage);
    }
}

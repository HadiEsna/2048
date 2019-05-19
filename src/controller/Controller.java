package controller;

import javafx.stage.Stage;

public class Controller {
    Stage stage;
    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("2048");
        setScene(MainController.getInstance());
        primaryStage.show();
    }

    public void setScene(P aClass){
        stage.setScene(aClass.getScene());
    }

}

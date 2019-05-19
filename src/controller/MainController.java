package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import view.MainView;

public class MainController extends P {
    private static MainController ourInstance = new MainController();
    private MainView view = MainView.getInstance();

    private MainController() {
    }

    public static MainController getInstance() {
        return ourInstance;
    }

    public MainView getView() {
        return view;
    }

    @Override
    public Scene getScene() {
        view.reset();
        MainView.getInstance().getRoot().getChildren().stream().filter(node -> node.getClass().equals(Button.class)).forEach(node -> node.setOnMouseClicked(event -> {
                    GameController.getInstance().mewGame(MainView.getInstance().getRow(),MainView.getInstance().getColumn());
                    Controller.getInstance().setScene(GameController.getInstance());
                })
        );
        return view.getScene();
    }
}

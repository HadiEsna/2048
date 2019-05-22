package controller;

import javafx.scene.Scene;
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
        return view.getScene();
    }
}

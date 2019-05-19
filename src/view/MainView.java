package view;

public class MainView {
    private static MainView ourInstance = new MainView();

    public static MainView getInstance() {
        return ourInstance;
    }

    private MainView() {
    }
}

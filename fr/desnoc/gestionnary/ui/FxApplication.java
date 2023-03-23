package fr.desnoc.gestionnary.ui;

import fr.desnoc.gestionnary.managers.Manager;
import javafx.application.Application;
import javafx.stage.Stage;

public class FxApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new Manager().init(stage);
    }

}

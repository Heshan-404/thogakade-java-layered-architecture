package edu.iuhs.crm;

import edu.iuhs.crm.utils.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage = StageManager.getStage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash_bord.fxml"))));
        stage.show();
    }
}

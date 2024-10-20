package edu.iuhs.crm.utils;

import javafx.stage.Stage;

public class StageManager {
    private static Stage stage;

    private StageManager() {
    }

    public static Stage getStage() {
        return stage != null ? stage : (stage = new Stage());
    }
}

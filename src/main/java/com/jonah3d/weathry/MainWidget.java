package com.jonah3d.weathry;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.jonah3d.weathry.Controllers.MainWidgetController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class MainWidget extends Application {
    private MainWidgetController controller;
    private FXTrayIcon trayIcon;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWidget.class.getResource("/FXML/MainWidget.fxml"));
        Pane root = fxmlLoader.load();
        Image icon = new Image(getClass().getResourceAsStream("/IMAGES/weathericon.png"));
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 320, 240);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Weathery");

        scene.setFill(null);
        Rectangle clip = new Rectangle(320, 240);
        clip.setArcWidth(80);
        clip.setArcHeight(80);
        root.setClip(clip);

        trayIcon = new FXTrayIcon(stage, getClass().getResource("/IMAGES/icon.png"));


        MenuItem changeCityItem = new MenuItem("Change City");
        changeCityItem.setOnAction(e -> promptForCity());

        trayIcon.addMenuItem(changeCityItem);
        trayIcon.addExitItem("Exit");
        trayIcon.show();

        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);

        controller.setStage(stage);

        stage.show();
    }

    private void promptForCity() {
        TextInputDialog dialog = new TextInputDialog(controller.getCity());
        dialog.setTitle("Change City");
        dialog.setHeaderText("Enter the name of the city:");
        dialog.setContentText("City:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(city -> {
            controller.updateWeather(city);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}

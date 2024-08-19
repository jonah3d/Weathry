package com.jonah3d.weathry.Controllers;

import com.jonah3d.weathry.Model.WeatherAPI;
import com.jonah3d.weathry.Model.wConditions;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainWidgetController implements Initializable {
    public Label country_lbl;
    public Label city_lbl;
    public Label temperature_lbl;
    public ImageView bg_image;

    private WeatherAPI weather;
    private String city = "Barcelona";

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0; // Default city

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateWeather(city);
    }

    public void updateWeather(String newCity) {
        city = newCity;
        weather = new WeatherAPI(city);
        setWeather(city, weather.getCountry(), weather.getTemperature());
        bgImage();
    }

    public String getCity() {
        return city;
    }

    private void setWeather(String City, String country, String temperature) {
        country_lbl.setText(country);
        city_lbl.setText(City);
        temperature_lbl.setText(temperature + "°");
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.getScene().getRoot().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        stage.getScene().getRoot().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void bgImage() {
        String condition = weather.getCondition();
        LocalTime currentTime = LocalTime.now();


        if (condition.equals(wConditions.THUNDERSTORM.toString())) {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/Thunderstorm.png")));
        } else if (condition.equals(wConditions.DRIZZLE.toString())) {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/RAIN.png")));
        } else if (condition.equals(wConditions.RAIN.toString())) {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/RAIN.png")));
        } else if (condition.equals(wConditions.SNOW.toString())) {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/SNOW.png")));
        } else if (condition.equals(wConditions.CLEAR.toString())) {
            if (currentTime.isAfter(LocalTime.of(19, 0)) || currentTime.isBefore(LocalTime.of(6, 0))) {
                bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/CLEAR_NIGHT.png")));
            } else {
                bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/CLEAR_SUNNY.png")));
            }
        } else if (condition.equals(wConditions.CLOUDS.toString())) {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/CLOUDS.png")));
        } else {
            bg_image.setImage(new Image(getClass().getResourceAsStream("/IMAGES/OTHER_CONDITIONS.png")));
        }
    }
}
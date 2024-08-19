package com.jonah3d.weathry.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class WeatherAPI {

    private String City;
    private String Country;
    private double Temperature;
    private String Condition;

    public WeatherAPI(String City){
        this.setCity(City);
        JSONObject cityLocationData = (JSONObject) getLocationData(City);
        double latitude = (double) cityLocationData.get("latitude");
        double longitude = (double) cityLocationData.get("longitude");

        displayWeatherData(latitude, longitude);
    };

    public static JSONObject getLocationData(String city){
        city = city.replaceAll(" ", "+");

        String urlString =  "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        try{

            HttpURLConnection apiConnection = fetchApiResponse(urlString);



            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }


            String jsonResponse = readApiResponse(apiConnection);


            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);


            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return (JSONObject) locationData.get(0);

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Location Error");
            alert.setContentText("Check Your Input Location");
            alert.showAndWait();


        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {

        try{

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");

            return conn;
        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("API Error");
            alert.setContentText("Could not connect to API");
            alert.showAndWait();
        }


        return null;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {

        try {

            StringBuilder resultJson = new StringBuilder();


            Scanner scanner = new Scanner(apiConnection.getInputStream());


            while (scanner.hasNext()) {

                resultJson.append(scanner.nextLine());
            }


            scanner.close();

            return resultJson.toString();

        } catch (IOException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("API Error");
            alert.setContentText("Could not read API response");
            alert.showAndWait();
        }

        return null;
    }

    private  void displayWeatherData(double latitude, double longitude){
        try{

            String apicode = "c86c64cb70cbad7f47f7e50efc6bf3e8";

            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude +  "&appid=" + apicode;

            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return;
            }


            String jsonResponse = readApiResponse(apiConnection);


            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("main");
            JSONObject currentWeatherJsonCount = (JSONObject) jsonObject.get("sys");
            JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
            JSONObject currentWeatherJsonCondition = (JSONObject) weatherArray.get(0);

            this.setTemperature((double) currentWeatherJson.get("temp"));
            this.setCountry((String) currentWeatherJsonCount.get("country"));
            this.setCondition((String) currentWeatherJsonCondition.get("main"));



        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Weather Error");
            alert.setContentText("Could not get weather data");
            alert.showAndWait();
        }
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        if(Country.equals(CountryCode.valueOf(CountryCode.class, Country).toString())){
            return CountryCode.valueOf(CountryCode.class, Country).getCountryName();
        }

        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTemperature() {
        DecimalFormat df = new DecimalFormat("#.##");
        String ans = df.format(Temperature - 273.15);
        return ans;
    }

    public String getCondition() {
        return this.Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }
}

package org.example.module04assignment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;

/**
 * # Weather Data Analyzer - JavaFX Edition
 *
 * This application reads weather data from a CSV file and displays insights in a JavaFX GUI.
 *
 * ## Features:
 * - Calculates average temperature for a given month.
 * - Finds days with temperatures above a threshold.
 * - Counts rainy days.
 * - Uses modern Java features with a graphical user interface.
 */
public class WeatherDataAnalyzerGUI extends Application {

    /**
     * A record representing a weather data entry.
     * @param date          The date of the recording.
     * @param temperature   Temperature in Celsius.
     * @param humidity      Humidity percentage.
     * @param precipitation Precipitation in millimeters.
     */
    record WeatherRecord(LocalDate date, double temperature, int humidity, double precipitation) {}

    private List<WeatherRecord> records;

    // FXML elements
    @FXML private VBox layout;
    @FXML private TextArea outputArea;
    @FXML private Button avgTempButton;
    @FXML private Button hotDaysButton;
    @FXML private Button rainyDaysButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        records = readWeatherData("weatherdata.csv");

        // Load the FXML layout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("weather_data_analyzer.fxml"));
        loader.setController(this);
        layout = loader.load();

        // Set up the scene
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Weather Data Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<WeatherRecord> readWeatherData(String filePath) {
        try {
            return Files.lines(Path.of(filePath))
                    .skip(1) // Skip header
                    .map(line -> line.split(","))
                    .map(parts -> new WeatherRecord(
                            LocalDate.parse(parts[0]),
                            Double.parseDouble(parts[1]),
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3])
                    ))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    @FXML
    private void handleAvgTempButton() {
        outputArea.setText(getAverageTemperature(records, 8));
    }

    @FXML
    private void handleHotDaysButton() {
        outputArea.setText(getDaysAboveTemperature(records, 32.0));
    }

    @FXML
    private void handleRainyDaysButton() {
        outputArea.setText(getRainyDaysCount(records));
    }

    private String getAverageTemperature(List<WeatherRecord> records, int month) {
        OptionalDouble avg = records.stream()
                .filter(r -> r.date.getMonthValue() == month)
                .mapToDouble(r -> r.temperature)
                .average();

        if (avg.isPresent()) {
            return String.format("Average temperature for month %d: %.2f°C", month, avg.getAsDouble());
        } else {
            return "No data available for month " + month;
        }
    }


    private String getDaysAboveTemperature(List<WeatherRecord> records, double threshold) {
        List<String> hotDays = records.stream()
                .filter(r -> r.temperature > threshold)
                .map(r -> r.date.toString())
                .toList();

        return "Days above " + threshold + "°C: " + hotDays;
    }

    private String getRainyDaysCount(List<WeatherRecord> records) {
        long count = records.stream()
                .filter(r -> r.precipitation > 0)
                .count();

        return "Number of rainy days: " + count;
    }
}
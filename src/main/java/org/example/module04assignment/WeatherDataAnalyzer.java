package org.example.module04assignment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * JavaFX application for analyzing weather data.
 * Provides insights such as average temperature, hot days,
 * rainy days count, extreme temperatures, and humidity analysis.
 */
public class WeatherDataAnalyzer extends Application {

    /**
     * Record representing a weather data entry.
     */
    record WeatherRecord(LocalDate date, double temperature, int humidity, double precipitation) {}

    private List<WeatherRecord> records;
    private String lastButtonClicked = "";

    @FXML
    private TextArea outputTextArea;
    @FXML
    private Button averageTemperatureButton, showHotDaysButton, showColdDaysButton, numberRainyDaysButton, extremeTemperaturesButton, humidityAnalysisButton;
    @FXML
    private ComboBox<String> monthComboBox;

    /**
     * Handles the selection of a month from the ComboBox.
     * Updates the output area based on the selected month and the last clicked button.
     */
    @FXML
    private void handleMonthSelection() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
            return;
        }

        if (!lastButtonClicked.isEmpty()) {
            switch (lastButtonClicked) {
                case "averageTemperature":
                    handleAverageTemperature();
                    break;
                case "showHotDays":
                    handleShowHotDays();
                    break;
                case "showColdDays":
                    handleShowColdDays();
                    break;
                case "numberRainyDays":
                    handleNumberRainyDays();
                    break;
                case "extremeTemperatures":
                    handleExtremeTemperatures();
                    break;
                case "humidityAnalysis":
                    handleHumidityAnalysis();
                    break;
            }
        } else {
            outputTextArea.setText("Select an analysis option");
        }
    }

    /**
     * Converts month name (e.g., "January") to its corresponding month number (1 for January, 2 for February, etc.).
     *
     * @param monthName The name of the month.
     * @return The month number (1-12).
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * int monthNumber = convertMonthNameToNumber("March"); // Returns 3
     * }
     * </pre>
     */
    private int convertMonthNameToNumber(String monthName) {
        return switch (monthName) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/module04assignment/gui/weather_data_gui.fxml"));
        VBox layout = loader.load();
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/module04assignment/styling/weather_data_style.css")).toExternalForm());
        primaryStage.setTitle("Weather Data Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the controller. Reads weather data and clears the month selection.
     */
    @FXML
    public void initialize() {
        records = readWeatherData();
        monthComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Reads weather data from a CSV file and converts it into a list of WeatherRecord objects.
     *
     * @return List of weather records.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * List<WeatherRecord> weatherData = readWeatherData();
     * }
     * </pre>
     */
    private List<WeatherRecord> readWeatherData() {
        URL fileUrl = getClass().getResource("/org/example/module04assignment/data/weather_data.csv");
        if (fileUrl == null) throw new RuntimeException("Weather data file not found.");
        try {
            return Files.lines(Path.of(fileUrl.toURI()))
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(",");
                        try {
                            return new WeatherRecord(
                                    LocalDate.parse(parts[0]),
                                    Double.parseDouble(parts[1]),
                                    Integer.parseInt(parts[2]),
                                    Double.parseDouble(parts[3])
                            );
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    /**
     * Handles the action of the average temperature button.
     * Displays the average temperature for the selected month.
     */
    @FXML
    private void handleAverageTemperature() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getAverageTemperature.apply(monthNumber, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "averageTemperature";
        }
    }

    /**
     * Handles the action of the show hot days button.
     * Displays the days with temperature greater than or equal to 30°C for the selected month.
     */
    @FXML
    private void handleShowHotDays() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getDaysAboveTemperature.apply(30.0, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "showHotDays";
        }
    }

    /**
     * Handles the action of the show cold days button.
     * Displays the days with temperature less than 15°C for the selected month.
     */
    @FXML
    private void handleShowColdDays() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getDaysBelowTemperature.apply(15.0, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "showColdDays";
        }
    }

    /**
     * Handles the action of the number of rainy days button.
     * Displays the number of rainy days for the selected month.
     */
    @FXML
    private void handleNumberRainyDays() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getRainyDaysCount.apply(monthNumber, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "numberRainyDays";
        }
    }

    /**
     * Handles the action of the extreme temperatures button.
     * Displays the extreme temperatures (hottest and coldest days) for the selected month.
     */
    @FXML
    private void handleExtremeTemperatures() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getExtremeTemperatures.apply(monthNumber, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "extremeTemperatures";
        }
    }

    /**
     * Handles the action of the humidity analysis button.
     * Displays the humidity analysis for the selected month.
     */
    @FXML
    private void handleHumidityAnalysis() {
        String selectedMonth = monthComboBox.getValue();

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            outputTextArea.setText("No month selected");
        } else {
            int monthNumber = convertMonthNameToNumber(selectedMonth);
            String result = getHumidityAnalysis.apply(monthNumber, selectedMonth);
            outputTextArea.setText(result);
            lastButtonClicked = "humidityAnalysis";
        }
    }

    /**
     * Function to categorize temperature using pattern matching in an enhanced switch statement.
     */
    Function<Double, String> categorizeTemperature = temp -> switch (temp) {
        case double t when t >= 30 -> "Hot";
        case double t when t >= 15 -> "Warm";
        default -> "Cold";
    };

    /**
     * Computes the average temperature for a given month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String avgTemp = getAverageTemperature.apply(7, "July");
     * // Returns "Average temperature in July:
     * 25.50°C (Warm)"
     * }
     * </pre>
     */
    BiFunction<Integer, String, String> getAverageTemperature = (month, monthName) -> {
        List<WeatherRecord> monthRecords = records.stream()
                .filter(r -> r.date.getMonthValue() == month)
                .toList();

        if (monthRecords.isEmpty()) {
            return "No temperature data available for " + monthName;
        }

        OptionalDouble avg = monthRecords.stream()
                .mapToDouble(WeatherRecord::temperature)
                .average();

        return avg.isPresent() ?
                """
                        Average temperature in %s:
                        %.2f°C (%s)
                        """.formatted(monthName, avg.getAsDouble(), categorizeTemperature.apply(avg.getAsDouble()))
                :
                "No data available for " + monthName;
    };

    /**
     * Gets days with temperature above the given threshold for a specific month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String hotDays = getDaysAboveTemperature.apply(30.0, "July");
     * // Returns "Days above 30.0°C in July:
     * 2023-07-15
     * 2023-07-16"
     * }
     * </pre>
     */
    BiFunction<Double, String, String> getDaysAboveTemperature = (threshold, monthName) -> {
        int monthNumber = convertMonthNameToNumber(monthName);

        List<String> hotDays = records.stream()
                .filter(r -> r.date.getMonthValue() == monthNumber)
                .filter(r -> r.temperature >= threshold)
                .map(r -> r.date.toString())
                .toList();

        if (hotDays.isEmpty()) {
            return "No days above " + threshold + "°C in " + monthName;
        }

        return hotDays.stream()
                .collect(Collectors.joining("\n", "Days above " + threshold + "°C in " + monthName + ":\n", ""));
    };

    /**
     * Gets days with temperature below the given threshold for a specific month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String coldDays = getDaysBelowTemperature.apply(15.0, "January");
     * // Returns "Days below 15.0°C in January:
     * 2023-01-05
     * 2023-01-06"
     * }
     * </pre>
     */
    BiFunction<Double, String, String> getDaysBelowTemperature = (threshold, monthName) -> {
        int monthNumber = convertMonthNameToNumber(monthName);

        List<String> coldDays = records.stream()
                .filter(r -> r.date.getMonthValue() == monthNumber)
                .filter(r -> r.temperature < threshold)
                .map(r -> r.date.toString())
                .toList();

        if (coldDays.isEmpty()) {
            return "No days below " + threshold + "°C in " + monthName;
        }

        return coldDays.stream()
                .collect(Collectors.joining("\n", "Days below " + threshold + "°C in " + monthName + ":\n", ""));
    };

    /**
     * Counts the number of rainy days in a specific month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String rainyDaysCount = getRainyDaysCount.apply(4, "April");
     * // Returns "Number of rainy days in April: 5"
     * }
     * </pre>
     */
    BiFunction<Integer, String, String> getRainyDaysCount = (month, monthName) -> {
        long count = records.stream()
                .filter(r -> r.date.getMonthValue() == month)
                .filter(r -> r.precipitation > 0)
                .count();

        return (count > 0) ? "Number of rainy days in " + monthName + ": " + count : "No precipitation data available for " + monthName;
    };

    /**
     * Finds extreme temperatures (hottest and coldest days) for a specific month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String extremeTemps = getExtremeTemperatures.apply(1, "January");
     * // Returns "Extreme temperatures in January:\nHottest day: 2023-01-10 (10.00°C, Cold)\nColdest day: 2023-01-15 (-5.00°C, Cold)"
     * }
     * </pre>
     */
    BiFunction<Integer, String, String> getExtremeTemperatures = (month, monthName) -> {
        List<WeatherRecord> monthRecords = records.stream()
                .filter(r -> r.date.getMonthValue() == month)
                .toList();

        if (monthRecords.isEmpty()) {
            return "No temperature data available for " + monthName;
        }

        Optional<WeatherRecord> hottest = monthRecords.stream()
                .max(Comparator.comparingDouble(WeatherRecord::temperature));

        Optional<WeatherRecord> coldest = monthRecords.stream()
                .min(Comparator.comparingDouble(WeatherRecord::temperature));

        return """
                Extreme temperatures in %s:
                Hottest day: %s (%.2f°C, %s)
                Coldest day: %s (%.2f°C, %s)
                """.formatted(
                monthName,
                hottest.map(r -> r.date.toString()).orElse("N/A"),
                hottest.map(r -> r.temperature).orElse(0.0),
                hottest.map(r -> categorizeTemperature.apply(r.temperature)).orElse("N/A"),
                coldest.map(r -> r.date.toString()).orElse("N/A"),
                coldest.map(r -> r.temperature).orElse(0.0),
                coldest.map(r -> categorizeTemperature.apply(r.temperature)).orElse("N/A")
        );
    };

    /**
     * Analyzes humidity data for a specific month.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * {@code
     * String humidityAnalysis = getHumidityAnalysis.apply(6, "June");
     * // Returns "Humidity Analysis for June:
     * Average Humidity: 65.50%\nNumber of days with Humidity > 70%: 10
     * Number of days with Humidity < 60%: 8"
     * }
     * </pre>
     */
    BiFunction<Integer, String, String> getHumidityAnalysis = (month, monthName) -> {
        List<WeatherRecord> monthRecords = records.stream()
                .filter(r -> r.date.getMonthValue() == month)
                .toList();

        if (monthRecords.isEmpty()) {
            return "No humidity data available for " + monthName;
        }

        OptionalDouble avgHumidity = monthRecords.stream()
                .mapToInt(WeatherRecord::humidity)
                .average();

        long highHumidityDays = monthRecords.stream()
                .filter(r -> r.humidity > 70)
                .count();

        long lowHumidityDays = monthRecords.stream()
                .filter(r -> r.humidity < 60)
                .count();

        return """
                Humidity Analysis for %s:
                Average Humidity: %.2f%%
                Number of days with Humidity > 70%%: %d
                Number of days with Humidity < 60%%: %d
                """.formatted(monthName, avgHumidity.orElse(0.0), highHumidityDays, lowHumidityDays);
    };
}
# Weather Data Analyzer Application

This application is a Weather Data Analyzer developed using JavaFX and modern Java features. It allows users to analyze weather data from a CSV file, providing insights into average temperatures, hot/cold days, rainy days, extreme temperatures, and humidity analysis.

## Features Implemented

* **Data Parsing:** Reads weather data from a CSV file (`weather_data.csv`) and stores it in memory using Java Records.
* **Average Temperature Analysis:** Calculates and displays the average temperature for a selected month, categorized as "Hot," "Warm," or "Cold" using an enhanced switch statement.
* **Hot/Cold Days Analysis:** Lists days with temperatures above a specified threshold (30°C for hot days, 15°C for cold days) for a selected month.
* **Rainy Days Count:** Counts and displays the number of rainy days (precipitation > 0) for a selected month.
* **Extreme Temperatures Analysis:** Identifies and displays the hottest and coldest days for a selected month, including their dates and temperatures.
* **Humidity Analysis:** Calculates and displays average humidity, as well as the number of days with high (> 70%) and low (< 60%) humidity for a selected month.
* **GUI:** A graphical user interface built with JavaFX, including a ComboBox for month selection, buttons for analysis functions, and a TextArea for output.
* **Modern Java Features:**
    * **Java Records:** Used to represent weather data.
    * **Enhanced Switch Statement (w/ Pattern Matching):** Used for temperature categorization.
    * **Text Blocks:** Used for generating output strings.
    * **Lambdas and Streams:** Used for efficient data processing.
    * **Markdown Javadoc:** All methods are documented with Markdown Javadoc, including code examples.

### Prerequisites

* Java Development Kit (JDK) 18 or higher
* JavaFX SDK
* Maven

### Installation

1.  **Clone the Repository:**
    ```bash
    git clone [repository URL]
    cd [repository directory]
    ```

2.  **Build the Project:**
        ```
        mvn clean install
        ```

3.  **Run the Application:**
    * Navigate to the `target/classes` directory.
    * Run the application using the following command:
        ```bash
        java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml org.example.module04assignment.WeatherDataAnalyzer
        ```
        (Replace `/path/to/javafx/lib` with the actual path to your JavaFX SDK lib directory.)

### Usage

1.  **Select a Month:** Choose a month from the ComboBox.
2.  **Choose an Analysis Option:** Click one of the analysis buttons (Average Temperature, Hot Days, Cold Days, Number of Rainy Days, Extreme Temperatures, Humidity Analysis).
3.  **View Results:** The results will be displayed in the TextArea.

## Project Structure

```
WeatherDataAnalyzer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/module04assignment/
│   │   │       └── WeatherDataAnalyzer.java
│   │   ├── resources/
│   │   │   ├── org/example/module04assignment/
│   │   │   │   ├── data/
│   │   │   │   │   └── weather_data.csv
│   │   │   │   ├── gui/
│   │   │   │   │   └── weather_data_gui.fxml
│   │   │   │   └── styling/
│   │   │   │       └── weather_data_style.css
├── pom.xml
└── README.md
```

## Modern Java Features Demonstrated

* **Java Records:** The `WeatherRecord` record is used to store weather data efficiently.
* **Enhanced Switch Statement:** The `categorizeTemperature` lambda function uses an enhanced switch statement with pattern matching to categorize temperatures.
* **Text Blocks:** Text blocks are used to format output strings in the analysis functions.
* **Lambdas and Streams:** Lambdas and streams are used throughout for data processing and analysis.
* **Markdown Javadoc:** All methods are documented using Markdown Javadoc with code examples.

## Javadoc Examples

### `convertMonthNameToNumber`

```java
int monthNumber = convertMonthNameToNumber("March"); // Returns 3
```

### `readWeatherData`

```java
List<WeatherRecord> weatherData = readWeatherData(); // Parses CSV file
```

### `getAverageTemperature`

```java
String avgTemp = getAverageTemperature.apply(7, "July");
// Returns "Average temperature in July:
// 25.50°C (Warm)"
```

### `getDaysAboveTemperature`

```java
String hotDays = getDaysAboveTemperature.apply(30.0, "July");
// Returns "Days above 30.0°C in July:
// 2023-07-15
// 2023-07-16"
```

### `getDaysBelowTemperature`

```java
String coldDays = getDaysBelowTemperature.apply(15.0, "January");
// Returns "Days below 15.0°C in January:
// 2023-01-05
// 2023-01-06"
```

### `getRainyDaysCount`

```java
String rainyDaysCount = getRainyDaysCount.apply(4, "April");
// Returns "Number of rainy days in April: 5"
```

### `getExtremeTemperatures`

```java
String extremeTemps = getExtremeTemperatures.apply(1, "January");
// Returns "Extreme temperatures in January:
// Hottest day: 2023-01-10 (10.00°C, Cold)
// Coldest day: 2023-01-15 (-5.00°C, Cold)"
```

### `getHumidityAnalysis`

```java
String humidityAnalysis = getHumidityAnalysis.apply(6, "June");
// Returns "Humidity Analysis for June:
// Average Humidity: 65.50%
// Number of days with Humidity > 70%: 10
// Number of days with Humidity < 60%: 8"
```

## GUI and CSS

The application includes a GUI built with JavaFX and styled using CSS. The CSS file (`weather_data_style.css`) provides a clean and modern look, enhancing the user experience.
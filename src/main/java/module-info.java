module org.example.module04assignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.module04assignment to javafx.fxml;
    exports org.example.module04assignment;
}
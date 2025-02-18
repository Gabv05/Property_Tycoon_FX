module org.example.property_tycoon_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.apache.poi.ooxml;

    opens org.example.property_tycoon_fx to javafx.fxml;
}
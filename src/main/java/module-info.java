module com.quannm.hsf_licenseshop_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.quannm.hsf_licenseshop_app to javafx.fxml;
    exports com.quannm.hsf_licenseshop_app;
}
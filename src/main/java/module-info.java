module com.jonah3d.weathry {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires com.dustinredmond.fxtrayicon;
    requires java.desktop;
    requires org.controlsfx.controls;

    opens com.jonah3d.weathry to javafx.fxml;
    exports com.jonah3d.weathry;
    exports com.jonah3d.weathry.Controllers;


}
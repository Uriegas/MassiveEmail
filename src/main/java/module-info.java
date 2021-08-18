module massiveMail{
    requires javafx.fxml;
    requires javafx.controls;
    requires com.calendarfx.view;
    requires javax.mail.api;
    requires poi;
    requires poi.ooxml;
    requires html2pdf;
    requires pdfa;

    opens com.uriegas to javafx.fxml;
    exports com.uriegas.Model to com.uriegas;
    exports com.uriegas;
}

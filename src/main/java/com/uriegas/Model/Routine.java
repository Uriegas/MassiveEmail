package com.uriegas.Model;

import java.io.Serializable;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.uriegas.Cuenta;
import com.uriegas.Mensaje;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.DatePicker;

/**
 * Program mails to send in a specific date
 * TODO: Messages not implemented yet
 */
public class Routine implements Serializable {
    private LocalDateTime fecha;
    private DatePicker date = new DatePicker();
    private final StringProperty rutina = new SimpleStringProperty();
    private Account cuenta;
    private final ObservableList<Mail> mails = FXCollections.observableArrayList();

	/**
	 * Array of mails
	 */
	// ObservableList<Mail> mails = FXCollections.observableArrayList(mail ->
	// 	new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});

    public Routine(LocalDateTime f, String rutina, Account c, ArrayList<Mail> msj) {
        cuenta.contraseniaProperty().addListener((o, oldVal,newVal) -> {});
        cuenta.emailProperty().addListener((o, oldVal,newVal) -> {});
        date.valueProperty().addListener((o, oldVal, newVal) -> {});
        //mails.addListener((ListChangeListener.Change<? extends Mail> change) -> {});
        this.mailsProperty().setAll(msj);
    }


    public StringProperty rutinaProperty(){return rutina;}
    public ObservableList<Mail> mailsProperty(){return this.mails;}

    /**
     * Rutina setter
     * @param rutina
     */
    public void setRutina(String rutina){this.rutina.set(rutina);}
    /**
     * Date setter
     * @param fecha
     */
    public void setDate(LocalDate fecha){this.date.setValue(fecha);}
    /**
     * Cuenta setter
     * @param cuenta
     */
    public void setCuenta(Cuenta cuenta){
        this.cuenta.setEmail(cuenta.getEmail());
        this.cuenta.setContrasenia(cuenta.getContrasenia());
    };
    /**
     * Get Date
     * @return {@code LocalDate}
     */
    public LocalDate getFecha(){return this.date.getValue();}
}

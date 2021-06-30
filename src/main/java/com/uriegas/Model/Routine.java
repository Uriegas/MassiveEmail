package com.uriegas.Model;

import java.io.Serializable;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.uriegas.Cuenta;
import com.uriegas.Mensaje;

import javafx.beans.Observable;
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
    /*private String rutina;*/
    private Account cuenta;
	/**
	 * Array of mails
	 */
	ObservableList<Mail> mails = FXCollections.observableArrayList(mail ->
		new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});

    public Routine(LocalDateTime f/*, String rutina*/, Cuenta c, Mensaje msj) {
        cuenta.contraseniaProperty().addListener((o, oldVal,newVal) -> {});
        cuenta.emailProperty().addListener((o, oldVal,newVal) -> {});
        date.valueProperty().addListener((o, oldVal, newVal) -> {});
        mails.addListener((ListChangeListener.Change<? extends Mail> change) -> {});
    }
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

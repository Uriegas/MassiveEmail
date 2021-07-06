package com.uriegas.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;

/**
 * Program mails to send in a specific date
 * TODO: Messages not implemented yet
 */
public class Routine implements Serializable {
    // private LocalDateTime fecha;
    // private DatePicker date = new DatePicker();
    private transient ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
    private transient StringProperty rutina = new SimpleStringProperty();
    private transient ObjectProperty<Account> cuenta = new SimpleObjectProperty<>();
    private transient ObservableList<Mail> mails = FXCollections.observableArrayList();

	/**
	 * Array of mails
	 */
	// ObservableList<Mail> mails = FXCollections.observableArrayList(mail ->
	// 	new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});

    public Routine(LocalDateTime f, String rutina, Account cuenta, ArrayList<Mail> msj) {
        this.dateProperty().set(f);
        this.rutinaProperty().set(rutina);
        this.cuentaProperty().set(cuenta);
        this.mailsProperty().setAll(msj);
    }


    public ObjectProperty<LocalDateTime> dateProperty(){return date;}
    public StringProperty rutinaProperty(){return rutina;}
    public ObjectProperty<Account> cuentaProperty(){return cuenta;}
    public ObservableList<Mail> mailsProperty(){return mails;}


    public void setDate(LocalDateTime fecha){this.date.set(fecha);}
    public void setRutina(String rutina){this.rutina.set(rutina);}
    public void setCuenta(Account cuenta){this.cuenta.set(cuenta);}
    public void setMails(ArrayList<Mail> msjs){this.mails.setAll(msjs);}
    

    public LocalDateTime getFecha(){return this.date.get();}
    public String getRutina(){return this.rutina.get();}
    public Account getCuenta(){return this.cuenta.get();}
    public ArrayList<Mail> getMails(){
        ArrayList<Mail> mensajes = new ArrayList<>();
        for(Mail i : this.mails)
            mensajes.add(i);
        return mensajes;
    }

    private void writeObject(ObjectOutputStream s) throws IOException, ClassNotFoundException {
        s.defaultWriteObject();
        s.writeObject(getFecha());
        s.writeUTF(getRutina());
        s.writeObject(getCuenta());
        s.writeObject(getMails());
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        date = new SimpleObjectProperty<>((LocalDateTime) s.readObject());
        rutina = new SimpleStringProperty(s.readUTF());
        cuenta = new SimpleObjectProperty<>((Account) s.readObject());
        mails = FXCollections.observableArrayList((List<Mail>) s.readObject());
    }
}

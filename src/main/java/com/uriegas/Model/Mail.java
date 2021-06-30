package com.uriegas.Model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.*;
import javafx.collections.*;
/**
 * Mail class represents an email
 */
public class Mail implements Serializable {
    private final StringProperty destinatario = new SimpleStringProperty();
    private final StringProperty asunto = new SimpleStringProperty();
    private final StringProperty cuerpo = new SimpleStringProperty();
    private final ObservableList<String> adjuntos = FXCollections.observableArrayList();

    public StringProperty destinatarioProperty(){return this.destinatario;}
    public StringProperty asuntoProperty(){return this.asunto;}
    public StringProperty cuerpoProperty(){return this.cuerpo;}
    public ObservableList<String> adjuntosProperty(){return this.adjuntos;}

    public void setDestinatario(String destinatario){this.destinatario.set(destinatario);}
    public void setAsunto(String asunto){this.asunto.set(asunto);}
    public void setCuerpo(String cuerpo){this.cuerpo.set(cuerpo);}
    public void setAdjuntos(ArrayList<String> archivo){this.adjuntos.addAll(archivo);}

    public String getDestinatario(){ return this.destinatario.get();}
    public String getAsunto(){return this.asunto.get();}
    public String getCuerpo(){return this.cuerpo.get();}
    public ArrayList<String> getAdjuntos(){
        ArrayList<String> list = new ArrayList<>();
        for(String i : this.adjuntos)
            list.add(i);
        return list;
    }

    public Mail(String toEmail, String subject, String body, ArrayList<String> archivo){
        this.destinatarioProperty().set(toEmail);
        this.asuntoProperty().set(subject);
        this.cuerpoProperty().set(body);
        this.adjuntosProperty().setAll(archivo);
    }
}

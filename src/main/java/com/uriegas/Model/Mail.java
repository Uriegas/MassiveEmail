package com.uriegas.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;
/**
 * Mail class represents an email that can be render (instantiated).<p>
 * Template: {@code Hi, my name is <name> and I am a(n) <profession>.}
 * Renders to: {@code Hi, my name is Uriegas and I am a(n) economist.}
 * @author Uriegas & Germany
 */
public class Mail implements Serializable {
    private transient StringProperty destinatario = new SimpleStringProperty();
    private transient StringProperty asunto = new SimpleStringProperty();
    private transient StringProperty cuerpo = new SimpleStringProperty();
    private transient ObservableList<String> adjuntos = FXCollections.observableArrayList();

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
    /**
     * Simple instantiate constructor
     * @param toEmail
     * @param subject
     * @param body
     * @param archivos
     */
    public Mail(String toEmail, String subject, String body, ArrayList<String> archivos){
        this.setAll(toEmail, subject, body, archivos);
    }
    /**
     * Instantiate Mail with rendered mail
     * @param destinatario
     * @param asunto
     * @param cuerpo
     * @param vars variables used to render the mail
     * @param archivos
     */
    public Mail(String destinatario, String asunto, String cuerpo, HashMap<String, String> vars, ArrayList<String> archivos){
        this.setAll(destinatario, asunto, cuerpo, archivos);
        this.render(asunto, cuerpo, vars);
    }
    /**
     * Send this email
     */
    public boolean send(){
        try{
            UseJavaMail.sendEmail(this);
            return true;
        }catch(Exception e){return false;}
    }
    /**
     * Render this email (Instantiate it)
     */
    public void render(String asunto, String cuerpo, HashMap<String, String> vars){
        for( Map.Entry<String, String> entry : vars.entrySet() ){//Replace each variable by its instance value
            String key = entry.getKey();
            String value = entry.getValue();
            asunto = asunto.replaceAll("<" + key + ">", value);
            cuerpo = cuerpo.replaceAll("<" + key + ">", value);
        }
        //-->Set render values
        this.setAsunto(asunto);
        this.setCuerpo(cuerpo);
        //<--Set render values
    }
    /**
     * Vars setter, internal use
     */
    private void setAll(String toEmail, String subject, String body, ArrayList<String> archivos){
        this.destinatarioProperty().set(toEmail);
        this.asuntoProperty().set(subject);
        this.cuerpoProperty().set(body);

        if(archivos != null)
            if( !archivos.isEmpty())
                for( String archivo : archivos ) 
                    if( !archivo.isEmpty())
                        if( archivo != null )
                            this.adjuntosProperty().add(archivo);
    }


    private void writeObject(ObjectOutputStream s) throws IOException, ClassNotFoundException {
        s.defaultWriteObject();
        s.writeUTF(getAsunto());
        s.writeUTF(getCuerpo());
        s.writeObject(getAdjuntos());
        // s.writeUTF(getDestinatario());
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        asunto = new SimpleStringProperty(s.readUTF());
        cuerpo = new SimpleStringProperty(s.readUTF());
        adjuntos = FXCollections.observableArrayList((List<String>) s.readObject());
    }
}

package com.uriegas.Model;

import java.io.*;
import javax.mail.*;
import javafx.beans.property.*;
/**
 * Account class implememting data binding
 * TODO: Implement the keys here as code in writter, reader and constructor
 */
public class Account implements Serializable {
    private transient StringProperty email = new SimpleStringProperty();
    private transient StringProperty contrasenia = new SimpleStringProperty();

    public Account(String e, String c){
        setEmail(e);
        setContrasenia(c);
    }

    public StringProperty emailProperty(){
        return email;
    }
    public StringProperty contraseniaProperty(){
        return contrasenia;
    }

    public void setEmail(String email){
        this.emailProperty().set(email);
    }
    public void setContrasenia(String contrasenia){
        this.contraseniaProperty().set(contrasenia);
    }

    public String getEmail(){ return this.email.get();}
    public String getContrasenia(){return this.contrasenia.get();}

    private void writeObject(ObjectOutputStream s) throws IOException, ClassNotFoundException {
        s.defaultWriteObject();
        s.writeUTF(getEmail());
        s.writeUTF(getContrasenia());
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        email = new SimpleStringProperty(s.readUTF());
        contrasenia = new SimpleStringProperty(s.readUTF());
    }
    public void requestLogin() throws AuthenticationFailedException, MessagingException {
        UseJavaMail.Login(this);
    }
    /**
     * Compare current account with another one
     * @param other
     * @return boolean
     */
    public boolean equals(Account other){
        if(this.getEmail().equals(other.getEmail()) && this.getContrasenia().equals(other.getContrasenia()) )
            return true;
        return false;
    }
}
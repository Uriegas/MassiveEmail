package com.uriegas.Model;

import java.io.*;
import javax.mail.*;
import javafx.beans.property.*;
/**
 * Account class implememting data binding
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

    private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeUTF(getEmail());
        s.writeUTF(getContrasenia());
    }
    private void readObject(ObjectInputStream s) throws Exception {
        emailProperty().set(s.readUTF());
        contraseniaProperty().set(s.readUTF());
    }
    public void requestLogin() throws AuthenticationFailedException, MessagingException {
        UseJavaMail.Login(this);
    }
}
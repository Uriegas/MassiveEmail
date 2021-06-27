package com.uriegas;

import java.io.Serializable;
import javafx.beans.property.*;
/**
 * Account class implememting data binding
 */
public class Account implements Serializable {
    private StringProperty email = new SimpleStringProperty();
    private StringProperty contrasenia = new SimpleStringProperty();


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
}
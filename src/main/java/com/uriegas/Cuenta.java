package com.uriegas;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private String email;
    private String contrasenia;


    public Cuenta(String e, String c){
        setEmail(e);
        setContrasenia(c);
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }

    public String getEmail(){ return this.email;}
    public String getContrasenia(){return this.contrasenia;}
}
package com.uriegas;

import java.io.File;
import java.util.ArrayList;

public class Evento extends Mensaje{
    private String fecha;
    private String hora;
    private String rutina;

    public Evento(String toEmail, String subject, String body, ArrayList<File> Archivo) {
        super(toEmail, subject, body, Archivo);
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public void setHora(String hora){
        this.hora = hora;
     }
    public void setRutina(String rutina){this.rutina = rutina;}

    public String getFecha(){return this.fecha;}
    public String getHora(){return this.hora;}
    public String getRutina(){return this.rutina;}
}
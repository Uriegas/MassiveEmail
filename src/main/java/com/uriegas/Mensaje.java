package com.uriegas;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Mensaje {
    private String destinatario;
    private String asunto;
    private String cuerpo;
    private ArrayList<File> adjuntos = new ArrayList<>();

    //public Mensaje(String toEmail, String subject, String body, File archivo){
    public Mensaje(String toEmail, String subject, String body, ArrayList<File> archivo){
        setDestinatario(toEmail);
        setAsunto(subject);
        setCuerpo(body);
        setAdjuntos(archivo);

    }

    public void setDestinatario(String destinatario){
        this.destinatario = destinatario;
    }
    public void setAsunto(String asunto){
        this.asunto = asunto;
    }
    public void setCuerpo(String cuerpo){
        this.cuerpo = cuerpo;
    }
    public void setAdjuntos(ArrayList<File> archivo){
        adjuntos.addAll(archivo);
    }

    public String getDestinatario(){ return this.destinatario;}
    public String getAsunto(){return this.asunto;}
    public String getCuerpo(){return this.cuerpo;}
    public ArrayList<File> getAdjuntos(){return this.adjuntos;}
}

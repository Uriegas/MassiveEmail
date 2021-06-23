package com.uriegas;

import java.util.ArrayList;

public class Mensaje {
    private String destinatario;
    private String asunto;
    private String cuerpo;
    private ArrayList<String> adjuntos;

    public Mensaje(String toEmail, String subject, String body/*, ArrayList<String> archivos*/){
        setDestinatario(toEmail);
        setAsunto(subject);
        setCuerpo(body);
        /*setAdjuntos(archivos);*/

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
    public void setAdjuntos(ArrayList<String> adjuntos){this.adjuntos = adjuntos;}

    public String getDestinatario(){ return this.destinatario;}
    public String getAsunto(){return this.asunto;}
    public String getCuerpo(){return this.cuerpo;}
    public ArrayList<String> getAdjuntos(){return this.adjuntos;}
}

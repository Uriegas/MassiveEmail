package com.uriegas.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {
    private LocalDateTime fecha;
    /*private String rutina;*/
    private Account cuenta;
    private Mail mensaje;

    public Evento(LocalDateTime f/*, String rutina*/, Account c, Mail msj) {
        setFecha(f);
        /*setRutina(rutina);*/
        setCuenta(c);
        setMensaje(msj);
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }
    /*public void setRutina(String rutina){this.rutina = rutina;}*/
    public void setCuenta(Account cuenta){this.cuenta = cuenta;}
    public void setMensaje(Mail msj){this.mensaje = msj;}

    public LocalDateTime getFecha(){return this.fecha;}
    /*public String getRutina(){return this.rutina;}*/
    public Account getCuenta() {return cuenta;}
    public Mail getMensaje() {return mensaje;}
}

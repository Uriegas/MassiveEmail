package com.uriegas;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {
    private LocalDateTime fecha;
    /*private String rutina;*/
    private Cuenta cuenta;
    private Mensaje mensaje;

    public Evento(LocalDateTime f/*, String rutina*/, Cuenta c, Mensaje msj) {
        setFecha(f);
        /*setRutina(rutina);*/
        setCuenta(c);
        setMensaje(msj);
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }
    /*public void setRutina(String rutina){this.rutina = rutina;}*/
    public void setCuenta(Cuenta cuenta){this.cuenta = cuenta;}
    public void setMensaje(Mensaje msj){this.mensaje = msj;}

    public LocalDateTime getFecha(){return this.fecha;}
    /*public String getRutina(){return this.rutina;}*/
    public Cuenta getCuenta() {return cuenta;}
    public Mensaje getMensaje() {return mensaje;}
}

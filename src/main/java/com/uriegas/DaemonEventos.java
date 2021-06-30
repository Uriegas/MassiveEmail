package com.uriegas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.uriegas.Model.UseJavaMail;

public class DaemonEventos extends Thread{

    private static ArrayList<Evento> listaEventos;

    /**
     * Manda a deserealizar el ArrayList<Evento> y lo almacena
     */
    public static void LeerEventos(){
        listaEventos = EventosSerializer.deserealizarEventos();
    }

    /**
     * Recorre el ArrayList comparando la fecha asignada con la fecha actual
     * espera 30 segundos despues de correr el arreglo para volver a comparar.
     * Cuando se cumple la condicion crea la sesion, envia el mensaje, remueve el
     * evento del ArrayList y lo serealiza sobreescribiendo el contenido del archivo
     */
    public void run(){
        while(true) {
            for(int i = 0; i < listaEventos.size(); i++) {
                if (listaEventos.get(i).getFecha().isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
                    UseJavaMail.Login(listaEventos.get(i).getCuenta()); //Se crea la sesion...
                    UseJavaMail.sendEmail(listaEventos.get(i).getMensaje()); //Se envia el correo

                    listaEventos.remove(i); //remuevo el evento que ya se realizo

                    EventosSerializer.serealizarEventos(listaEventos); //Actualizo el archivo serealizado

                    break;
                } else {
                    System.out.println("Fechas distintas");
                }
            }
            try {
                TimeUnit.SECONDS.sleep(30);// Espera 30 segundos para volver a comparar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

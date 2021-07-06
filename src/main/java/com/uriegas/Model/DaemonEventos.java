package com.uriegas.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class DaemonEventos extends Thread{

    private static ArrayList<Routine> listaEventos;
    protected static RoutineModel modelR;

    /**
     * Manda a deserealizar el ArrayList<Evento> y lo almacena
     */
    public static void LeerEventos(){
        File fileIn = new File(System.getProperty("user.home") + "/.MassiveMail/RoutineModel.ser");
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(fileIn))){
            modelR = (RoutineModel)out.readObject();
            System.out.println("Deserialized data from /RoutineModel.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        
        listaEventos = modelR.getRoutines();
        System.out.println("Eventos leidos");
    }

    /**
     * Recorre el ArrayList comparando la fecha asignada con la fecha actual
     * espera 30 segundos despues de correr el arreglo para volver a comparar.
     * Cuando se cumple la condicion crea la sesion, envia el mensaje, remueve el
     * evento del ArrayList y lo serealiza sobreescribiendo el contenido del archivo
     */
    @Override
    public void run(){
        System.out.println("Demonio ejecutado");
        while(true) {
            for(int i = 0; i < listaEventos.size(); i++) {
                if(listaEventos.isEmpty()){
                    /*interrupt();*/
                    System.out.println("Sin eventos pendientes");
                    break;
                }
                if(listaEventos.get(i).getRutina().equals("Solo una vez")){

                    System.out.println("Solo una vez");

                    System.out.println("Fecha definida: "+listaEventos.get(i).getFecha());
                    System.out.println("Fecha actual: "+LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                    if(listaEventos.get(i).getFecha().isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))){
                        ejecutarEvento(listaEventos.get(i), i);
                    }
                }
                else if(listaEventos.get(i).getRutina().equals("Diariamente")) {

                    System.out.println("Diariamente");

                    String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                    if (listaEventos.get(i).getFecha().format(DateTimeFormatter.ofPattern("HH:mm")).equals(fechaActual)) {
                        ejecutarEvento(listaEventos.get(i), i);
                    }
                }
                else if(listaEventos.get(i).getRutina().equals("Semanalmente")){

                    System.out.println("Semanalmente");

                    String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("E HH:mm"));
                    if (listaEventos.get(i).getFecha().format(DateTimeFormatter.ofPattern("E HH:mm")).equals(fechaActual)) {
                        ejecutarEvento(listaEventos.get(i), i);
                    }
                }
                else if(listaEventos.get(i).getRutina().equals("Mensualmente")){

                    System.out.println("Mensualmente");

                    String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd HH:mm"));
                    if (listaEventos.get(i).getFecha().format(DateTimeFormatter.ofPattern("dd HH:mm")).equals(fechaActual)) {
                        ejecutarEvento(listaEventos.get(i), i);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(30);// Espera 30 segundos para volver a comparar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void ejecutarEvento(Routine evento, int i){

        try {
            UseJavaMail.Login(evento.getCuenta());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int j = 0; j < listaEventos.size(); j++)//Send messages
            UseJavaMail.sendEmail(evento.getMails().get(j));

        /*UseJavaMail.sendEmail(evento.getMensaje()); //Se envia el correo*/

        listaEventos.remove(i); //remuevo el evento que ya se realizo
        //Actualizo el archivo serealizado
    }
}

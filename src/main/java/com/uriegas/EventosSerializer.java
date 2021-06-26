package com.uriegas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class EventosSerializer {

    /**
     * Serealiza el ArrayList recibido
     * @param ListaEventos recibe un ArrayList de tipo Evento
     */
    public static void serealizarEventos(ArrayList<Evento> ListaEventos){
        try{
            ObjectOutputStream escribiendo_eventos = new ObjectOutputStream(new FileOutputStream("src/main/resources/Eventos.dat", false));
            escribiendo_eventos.writeObject(ListaEventos);
            escribiendo_eventos.close();
        }catch(Exception ex){ex.printStackTrace();}
    }

    /**
     * Deserealiza el ArrayList
     * @return devuelve el ArrayList de Eventos
     */
    public static ArrayList<Evento> deserealizarEventos() {
        ArrayList<Evento> ListaEventos = null;
        try{
            ObjectInputStream recuperando_eventos = new ObjectInputStream(new FileInputStream("src/main/resources/Eventos.dat"));
            ListaEventos =  (ArrayList<Evento>)recuperando_eventos.readObject();
            recuperando_eventos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ListaEventos;
    }
}

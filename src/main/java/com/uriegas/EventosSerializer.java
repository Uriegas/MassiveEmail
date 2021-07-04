package com.uriegas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class EventosSerializer <T> {

    /**
     * Serealiza el ArrayList recibido
     */
    public void serealizarEventos(T serealizar, String path){
        try{
            ObjectOutputStream escribiendo_eventos = new ObjectOutputStream(new FileOutputStream(path, false));
            escribiendo_eventos.writeObject(serealizar);
            escribiendo_eventos.close();
        }catch(Exception ex){ex.printStackTrace();}
    }

    /**
     * Deserealiza el ArrayList
     * @return devuelve el ArrayList de Eventos
     */
    public T deserealizarEventos(String path) {
        T deserealizado = null;
        try{
            ObjectInputStream recuperando_eventos = new ObjectInputStream(new FileInputStream(path));
            deserealizado =  (T)recuperando_eventos.readObject();
            recuperando_eventos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return deserealizado;
    }
}

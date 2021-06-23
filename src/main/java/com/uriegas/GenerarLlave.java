package com.uriegas;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GenerarLlave {

    /**
     * File donde se almacenera la clave de autenticacion
     */
    private File file = new File("src/main/resources/Private_Keys.key");

    /**
     * Comprueba si existe el archivo que almacena la clave de autenticación
     * @return true cuando existe el archvio o false cuando no
     */
    public boolean Existe(){
        if(this.file.exists()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Se encarga de encriptar la contraseña de autenticacion
     * @param pass recibe la clave introducida en la vista Autenticación
     */
    public void EncriptarLlave(String pass){

        String llave = null;

        EncryptAccounts.setClave_encriptacion("Private_Keys.key"); //Esta es la contraseña para desencriptar la clave de autenticación jajaja
        EncryptAccounts.setLlave();
        try {
            llave = EncryptAccounts.encriptar(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter salida = new FileWriter("src/main/resources/Private_Keys.key", true); //Se escribe la clave encriptada en el archivo
            salida.write(llave);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * Compara la clave introducida en la vista Autenticación con la clave almacenada en el archivo
     * @param pass recibe la clave introducida en la vista Autenticacion
     * @return retorna true cuando la clave coincide, false cuando no
     */
    public boolean CompararLlave(String pass){
        EncryptAccounts.setClave_encriptacion("Private_Keys.key");
        EncryptAccounts.setLlave();

        String llave = null;
        String desencriptada = null;

        File file = new File("src/main/resources/Private_Keys.key");
        try {
            Scanner s = new Scanner(file);
            llave = s.nextLine();
            desencriptada = EncryptAccounts.desencriptar(llave);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(pass.equals(desencriptada)){
            return true;
        }
        return false;
    }
}

package com.uriegas;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GenerarLlave {

    private File file = new File("src/main/resources/Private_Keys.key");

    public boolean Existe(){
        if(this.file.exists()) {
            return true;
        }else {
            return false;
        }
    }

    public void EncriptarLlave(String pass){

        String llave = null;

        EncryptAccounts.setClave_encriptacion("Private_Keys.key");
        EncryptAccounts.setLlave();
        try {
            llave = EncryptAccounts.encriptar(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileWriter salida = new FileWriter("src/main/resources/Private_Keys.key", true);
            salida.write(llave);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

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

package com.uriegas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class GenerarLlave {

    private File file = new File("src/main/resources/llave.key");

    public boolean Existe(){
        if(this.file.exists()) {
            return true;
        }
        return false;
    }

    public void GuardarLlave(String pass){
        try {
            FileWriter salida = new FileWriter("src/main/resources/llave.key", true);
            salida.write(pass);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public boolean CompararLlave(String pass){
        String llave = null;
        File file = new File("src/main/resources/llave.key");
        try {
            Scanner s = new Scanner(file);
            llave = s.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(pass.equals(llave)){
            return true;
        }
        return false;
    }
}

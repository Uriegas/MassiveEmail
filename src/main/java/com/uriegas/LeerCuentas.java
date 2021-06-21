package com.uriegas;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LeerCuentas {
    private ArrayList<String> Cuentas = new ArrayList<>();
    private File file = new File("src/main/resources/cuentas.txt");

    public boolean Existe(){
        if(this.file.exists()) {
            return true;
        }
        return false;
    }

    public void GenerarArchivo(){
        try {
            FileWriter salida = new FileWriter("src/main/resources/cuentas.txt", true);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public int getNumCuentas(){
        return this.Cuentas.size();
    }

    public ArrayList<String> getCuentas(){
        return this.Cuentas;
    }

    public void Leer(){
        Scanner s = null;

        try{
            s = new Scanner(file);
            while(s.hasNextLine()){
                String cuentas = s.nextLine();
                if(!cuentas.isEmpty())
                {
                    if(cuentas.contains("@upv.edu.mx")){
                        this.Cuentas.add(cuentas);
                    }
                    else if(!cuentas.contains("@upv.edu.mx") && !cuentas.equals("----------------------------------------")){
                        String desencriptado = EncryptAccounts.desencriptar(cuentas);
                        this.Cuentas.add(desencriptado);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("No se encotró el archivo de cuentas");
            e.printStackTrace();
        } finally {
            try {
                if (s != null){
                    s.close();
                }
            } catch (Exception e) {
                System.out.println("Problemas para cerrar el archivo");
            }
        }
    }
}

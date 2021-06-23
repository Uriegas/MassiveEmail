package com.uriegas;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LeerCuentas {
    private ArrayList<String> Cuentas = new ArrayList<>();
    private File file = new File("src/main/resources/cuentas.txt");

    /**
     * Comprueba si existe el archivo que almacena las cuentas
     * @return true cuando existe el archvio o false cuando no
     */
    public boolean Existe(){
        if(this.file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Crea el archivo que almacenara las cuentas
     */
    public void GenerarArchivo(){
        try {
            FileWriter salida = new FileWriter("src/main/resources/cuentas.txt", true);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * Compara la cuenta recibida con las cuentas almacenadas en el archivo cuentas.txt
     * En caso de que dicha cuenta no exista la almacena en el archivo
     * @param cuentaNueva recibe la dirección de correo introducida en la vista Login
     * @return retorna true cuando la cuenta existe en el archivo, false cuando no
     */
    public boolean CompararCuentas(String cuentaNueva){
        Scanner s = null;

        try{
            s = new Scanner(file);
            while(s.hasNextLine()){
                String cuentas = s.nextLine();
                if(cuentas.equals(cuentaNueva))
                {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("No se encontró el archivo de cuentas");
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

        return false;
    }

    /**
     *
     * @return retorna la cantidad de cuentas guardadas en el archivo
     */
    public int getNumCuentas(){
        return this.Cuentas.size();
    }

    /**
     *
     * @return retorna el ArrayList con las cuentas guardadas en el archivo
     */
    public ArrayList<String> getCuentas(){
        return this.Cuentas;
    }

    /**
     * lee las cuentas almacenadas en el archivo y las almacena en un ArrayList
     * Tambien manda a desencriptar las contraseñas y las almacen en el ArrayList
     */
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
            System.out.println("No se encontró el archivo de cuentas");
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

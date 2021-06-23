package com.uriegas;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.security.Key;
import java.util.Base64;
import java.util.Scanner;

public class Keys {

    private static File file = new File("src/main/resources/Private_Keys.key");
    private static Key llave;

    /**
     * Se encarga de asignar la contraseña de encriptación
     * @param clave contraseña introducida por el usuario en la vista Autenticacion
     */
    public static void definirLlave(String clave){
        llave = new SecretKeySpec(clave.getBytes(), "AES");
    }

    /**
     * Metodo encargado de almacenar la llave maestra encriptada en un archivo
     * @param pass
     */
    public static void almacenarLlave(String pass){
        String llave = null;

        definirLlave("Private_Keys.key");
        try {
            llave = encriptar(pass);
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
     * Se encarga de encriptar la contraseña del correo del usuario
     * @param datos recibe la contraseña introducida en la vista Login
     * @return retorna la contraseña encriptada
     */
    public static String encriptar(String datos){

        String encriptado = null;

        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, llave);

            byte[] datosEncriptar = datos.getBytes("UTF-8");
            byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
            encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
        }catch(Exception e){
            e.printStackTrace();
        }
        return encriptado;
    }



    /**
     * Se encarga de desencriptar la contraseña de la cuenta seleccionada en el ListView
     * @param datosEncriptados recibe la contraseña encriptada para posteriormente desencriptarla
     * @return retorna la contraseña desencriptada
     */
    public static String desencriptar(String datosEncriptados) {

        String datos = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, llave);

            byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
            byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
            datos = new String(datosDesencriptados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }


    /**
     * Compara la llave ingresada con la llave almacenada
     * @param pass
     * @return retorna true cuando las llaves coinciden, false cuando no
     */
    public static boolean comparar(String pass){
        definirLlave("Private_Keys.key");

        String llave = null;
        String desencriptada = null;

        File file = new File("src/main/resources/Private_Keys.key");
        try {
            Scanner s = new Scanner(file);
            llave = s.nextLine();
            desencriptada = desencriptar(llave);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(pass.equals(desencriptada)){
            return true;
        }
        return false;
    }

    /**
     * Comprueba si existe el archivo que almacena la llave maestra (Private_Keys.key)
     * @return retorna true si existe, false cuando no
     */
    public static boolean Existe(){
        if(file.exists()) {
            return true;
        }else {
            return false;
        }
    }
}

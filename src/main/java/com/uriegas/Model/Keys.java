package com.uriegas.Model;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.*;
/**
 * Handles 2 methods to encrypt and decrypt a string
 */
public class Keys {
    private static File file = new File("src/main/resources/Private_Keys.key");
    private Key llave;
    private Cipher cipher;
    /**
     * Constructor, just initialize the key to encrypt all the data
     * @param clave
     * @return
     */
    public Keys(String key) throws Exception{
        this.llave = definirLlave(key);
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }
    /**
     * Se encarga de asignar la contraseña de encriptación
     * @param clave contraseña introducida por el usuario en la vista Autenticacion
     */
    public Key definirLlave(String clave) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return llave = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(clave.getBytes("UTF-8")), "AES");
    }
    /**
     * Metodo encargado de almacenar la llave maestra encriptada en un archivo
     * @param pass
     * @deprecated
     */
    public void almacenarLlave(String pass) throws Exception {
        String llave = null;

        definirLlave("Private_Keys.key");
        try {
            llave = encriptar(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter salida = new FileWriter("src/main/resources/Private_Keys.key", false); //Se escribe la clave encriptada en el archivo
            salida.write(llave);
            salida.close();
        }catch(Exception e){e.printStackTrace();}
    }

//------------USEFUL METHODS
    /**
     * Se encarga de encriptar la contraseña del correo del usuario
     * @param datos recibe la contraseña introducida en la vista Login
     * @return retorna la contraseña encriptada
     */
    public String encriptar(String datos) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, llave);
        return Base64.getEncoder().encodeToString(cipher.doFinal(datos.getBytes("UTF-8")));
    }
    /**
     * Se encarga de desencriptar la contraseña de la cuenta seleccionada en el ListView
     * @param datosEncriptados recibe la contraseña encriptada para posteriormente desencriptarla
     * @return retorna la contraseña desencriptada
     */
    public String desencriptar(String datosEncriptados) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, llave);
        return new String(cipher.doFinal(Base64.getDecoder().decode(datosEncriptados)));
    }
//------------USEFUL METHODS


    /**
     * Compara la llave ingresada con la llave almacenada
     * @param pass
     * @return retorna true cuando las llaves coinciden, false cuando no
     * @deprecated
     */
    public boolean comparar(String pass) throws Exception {
        definirLlave("Private_Keys.key");

        String llave = null;
        String desencriptada = null;

        File file = new File("src/main/resources/Private_Keys.key");
        try {
            Scanner s = new Scanner(file);
            llave = s.nextLine();
            desencriptada = desencriptar(llave);
            s.close();
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
     * @deprecated
     */
    public static boolean Existe(){
        if(file.exists()) {
            return true;
        }else {
            return false;
        }
    }
}

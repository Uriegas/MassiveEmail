package com.uriegas;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class EncryptAccounts {

    private static String clave_encriptacion;

    private static Key llave;

    /**
     * Se encarga de asignar la contraseña de encriptación
     * @param clave contraseña introducida por el usuario en la vista Autenticacion
     */
    public static void setClave_encriptacion(String clave){
        clave_encriptacion = clave;
    }

    /**
     * Genera la key a partir de la contraseña assignada en setClave_encriptacion
     */
    public static void setLlave(){
        llave = new SecretKeySpec(clave_encriptacion.getBytes(), "AES");
    }

    /**
     * Se encarga de encriptar la contraseña del correo del usuario
     * @param datos recibe la contraseña introducida en la vista Login
     * @return retorna la contraseña encriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encriptar(String datos) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, llave);

        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        return encriptado;
    }

    /**
     * Se encarga de desencriptar la contraseña de la cuenta seleccionada en el ListView
     * @param datosEncriptados recibe la contraseña encriptada para posteriormente desencriptarla
     * @return retorna la contraseña desencriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String desencriptar(String datosEncriptados) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, llave);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
        return datos;
    }
}

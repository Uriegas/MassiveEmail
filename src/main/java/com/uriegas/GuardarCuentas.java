package com.uriegas;

import java.io.FileWriter;
import java.io.IOException;

public class GuardarCuentas {

    /**
     * Almacena las cuentas en el archivo cuentas.txt
     * @param email recibe la direccion de correo electronico
     * @param pass recibe la contraseña encriptada
     */
    public void Escribir_Cuentas(String email, String pass) {
        try {
            FileWriter salida = new FileWriter("src/main/resources/cuentas.txt", true);

            salida.write("----------------------------------------");
            salida.write(System.lineSeparator());
            salida.write(email);
            salida.write(System.lineSeparator());
            salida.write(pass);
            salida.write(System.lineSeparator());

            salida.close();
        } catch (IOException e) {
            System.out.println("\nAlgo salió mal. Vuelva a intentarlo");
            e.printStackTrace();
        }
    }
}
package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AutenticacionController {
    LeerCuentas lcuentas = new LeerCuentas();

    @FXML
    private PasswordField TfPass;

    @FXML
    private Label lbMensaje ;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(lcuentas.Existe()){
            lbMensaje.setText("Ingresa la contraseña de autenticación");
        }
        else{
            lbMensaje.setText("Crea una contraseña de autenticación(16 caracteres)");
        }
    }

    @FXML
    protected void AuthClicked(ActionEvent e){
        String pass = TfPass.getText();
        GenerarLlave LlavePriv = new GenerarLlave();

        //Comprueba si existen cuentas almacenadas, si no existen crea el archivo para almacenarlas
        //lo hago aqui porque el initialize no jalo, de rato lo corrijo
        if(!lcuentas.Existe()){
            lcuentas.GenerarArchivo();
        }

        System.out.println('"'+pass+'"');

        /*Si no existe el archivo con la llave privada, lo crea, almacena la clave y
        utiliza la llave para encriptar las cuentas ingresadas*/
        if(LlavePriv.Existe() == false){
            LlavePriv.GuardarLlave(pass);
            EncryptAccounts.setClave_encriptacion(pass);
            EncryptAccounts.setLlave();
            goToLogin();
        }

        /*Si existe el archivo con la clave privada, la compara con la ingresada,
        si la clave es correcta, la asigan como llave de desencriptado*/
        else if(LlavePriv.Existe() == true){
            if(LlavePriv.CompararLlave(pass) == true){
                EncryptAccounts.setClave_encriptacion(pass);
                EncryptAccounts.setLlave();
                goToLogin();
            }
            else{
                System.out.print("Contraseña incorrecta");
            }
        }

    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL path = new URL("file:src/main/resources/Login.fxml");
            loader.setLocation(path);
            Scene scene = loader.load();

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Inicia Sesión");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

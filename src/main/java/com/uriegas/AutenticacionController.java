package com.uriegas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller of the first window to show: A simple login window
 */
public class AutenticacionController implements Initializable {
    LeerCuentas lcuentas = new LeerCuentas();

    @FXML private PasswordField TfPass;

    @FXML private Label lbMensaje;
    @FXML private Label lbAdvertencia;
<<<<<<< HEAD

    /**
     * Compruba si existe el archivo que almacena la clave de autenticacion,
     * Si no existe cambia el contenido del label para solicitar crear clave
     * Si existe cambia el contenido del label para solicitar introducir la clave
     * @param url
     * @param resourceBundle
=======
    /**
     * Initializer method for the controller 
>>>>>>> 649e3bcf6051bdf38f15450668197baebfe868f7
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(lcuentas.Existe()){
            lbMensaje.setText("Introduzca la contraseña para desbloquearlo");
            lbAdvertencia.setText("Esta intentando acceder al deposito de contraseñas, pero está bloqueado");
        }
        else{
            lbMensaje.setText("Crea una contraseña de autenticación(16 caracteres)");
            lbAdvertencia.setText("Esta asegura la integridad de las cuentas utilizadas dentro de este programa");
            lcuentas.GenerarArchivo();
        }
    }
<<<<<<< HEAD

    /**
     * cierra el programa
=======
    /**
     * Cancel button = exit current window (stage)
     * Actually makes a request to exit (pops the alert exit window)
>>>>>>> 649e3bcf6051bdf38f15450668197baebfe868f7
     * @param e
     */
    @FXML
    protected void CancelClicked(ActionEvent e){
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.getOnCloseRequest().handle(null);
        stage.close();
    }
<<<<<<< HEAD


    /**
     * Si la el archivo de autenticacion no existe toma la cadena introducida, la almacena en el archivo Private_key.key
     * y la utiliza como clave de autenticacion
     *
     * Si el archivo existe, compara la cadena introducida con la clave de autenticacion almacenada
=======
    /**
     * authentication button, once clicked the program validates if the password is correct
>>>>>>> 649e3bcf6051bdf38f15450668197baebfe868f7
     * @param e
     */
    @FXML
    protected void AuthClicked(ActionEvent e){
        String pass = TfPass.getText();
        GenerarLlave LlavePriv = new GenerarLlave();

        System.out.println("CLICK");

        if(LlavePriv.Existe() == true){
            if(LlavePriv.CompararLlave(pass) == true){
                System.out.println("LLAVE CORRECTA");
                EncryptAccounts.setClave_encriptacion(pass);
                EncryptAccounts.setLlave();

                goToLogin();

                Node source = (Node) e.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else if(LlavePriv.CompararLlave(pass) == false){
                System.out.print("LLAVE INCORRECTA");
            }
        }
        else{
            System.out.println("LLAVE GENERADA");
            LlavePriv.EncriptarLlave(pass);
            EncryptAccounts.setClave_encriptacion(pass);
            EncryptAccounts.setLlave();
            goToLogin();
        }
    }

    /**
     * Abre la vista Login
     */
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

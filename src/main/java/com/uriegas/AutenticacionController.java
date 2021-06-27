package com.uriegas;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.*;

/**
 * Controller of the first window to show: A simple login window
 */
public class AutenticacionController extends Window {
    AccountsUtilities lcuentas = new AccountsUtilities();

    @FXML private PasswordField TfPass;

    @FXML private Label lbMensaje;
    @FXML private Label lbAdvertencia;
    @FXML private Scene auth;
    /**
     * Compruba si existe el archivo que almacena la clave de autenticacion,
     * Si no existe cambia el contenido del label para solicitar crear clave
     * Si existe cambia el contenido del label para solicitar introducir la clave
     */
    public void initialize() {
        if(lcuentas.existe()){
            lbMensaje.setText("Introduzca la contraseña maestra");
            lbAdvertencia.setText("Necesaria para acceder a la aplicación");
        }
        else{
            lbMensaje.setText("Cree una contraseña maestra");
            lbAdvertencia.setText("Esta asegura la seguridad de la aplicación");
            lcuentas.GenerarArchivo();
        }
        /**
         * Check for this if the key is pressed in this PasswordField
         */
        TfPass.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER)
                if(Keys.comparar(TfPass.getText())){
                    Node source = (Node)event.getSource();
                    Stage stage = (Stage)source.getScene().getWindow();
                    stage.close();
                }
        });
    }
    /**
     * Cancel button = exit current window (stage)
     * Actually makes a request to exit (pops the alert exit window)
     * @param e
     */
    @FXML
    protected void CancelClicked(ActionEvent e){
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.getOnCloseRequest().handle(null);
        stage.close();
    }

    /**
     * Si la el archivo de autenticacion no existe toma la cadena introducida, la almacena en el archivo Private_key.key
     * y la utiliza como clave de autenticacion
     *
     * Si el archivo existe, compara la cadena introducida con la clave de autenticacion almacenada
     * @param e
     */
    @FXML
    protected void AuthClicked(ActionEvent e){
        String pass = TfPass.getText();

        if(Keys.Existe() == true){
            if(Keys.comparar(pass) == true){
                System.out.println("LLAVE CORRECTA");
                Keys.definirLlave(pass);
                
                Node source = (Node)e.getSource();
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();

            } else if(Keys.comparar(pass) == false){
                System.out.print("LLAVE INCORRECTA");
            }
        }
        else{
            System.out.println("LLAVE GENERADA");
            Keys.almacenarLlave(pass);
            Keys.definirLlave(pass);
            Node source = (Node)e.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
        }
    }
}

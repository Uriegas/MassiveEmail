package com.uriegas;

import com.uriegas.Model.MailModel;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.*;

/**
 * Controller of the authentication alert window 
 */
public class AutenticacionController extends Window {
    @FXML private PasswordField TfPass;
    @FXML private Label lbMensaje;
    @FXML private Label lbAdvertencia;
    @Override
    public void initModel(MailModel m){
        super.initModel(m);
    }
    /**
     * Compruba si existe el archivo que almacena la clave de autenticacion,
     * Si no existe cambia el contenido del label para solicitar crear clave
     * Si existe cambia el contenido del label para solicitar introducir la clave
     */
    public void initialize() {
        lbMensaje.setText("Introduzca/Cree la contraseña maestra");
        lbAdvertencia.setText("Necesaria para acceder a la aplicación");
        /**
         * Check for this if the key is pressed in this PasswordField
         */
        TfPass.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER)
                if( this.model.validateMasterPasswd(TfPass.getText()) ){
                    exit(event);
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
        stage.getOnCloseRequest().handle( null );
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
        if(this.model.validateMasterPasswd(TfPass.getText()))
            exit(e);
    }
    private void exit(Event e){
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}

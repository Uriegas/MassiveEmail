package com.uriegas;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.net.URL;
import java.util.*;
/**
 * Controller of the first window to show: A simple login window
 */
public class AutenticacionController implements Initializable {
    LeerCuentas lcuentas = new LeerCuentas();

    @FXML private PasswordField TfPass;

    @FXML private Label lbMensaje;
    @FXML private Label lbAdvertencia;

    /**
     * Compruba si existe el archivo que almacena la clave de autenticacion,
     * Si no existe cambia el contenido del label para solicitar crear clave
     * Si existe cambia el contenido del label para solicitar introducir la clave
     * @param url
     * @param resourceBundle
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
            //Aqui estoy haciendo trampa, hice la clase configuration global
            //Para poder usarla en todos los lugares donde se cree una nueva ventana (scene)
            //Ya que no pude crear el data binding para todos los nodos desde el metodo start en App.java
            scene.getRoot().styleProperty().bind(Configuration.cssProperty());

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

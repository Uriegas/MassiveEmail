package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    GuardarCuentas save = new GuardarCuentas();

    @FXML
    private TextField TfUsuario;

    @FXML
    private Label lError;

    @FXML
    private PasswordField TfContra;

    @FXML
    private ListView<String> LvCuentas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeerCuentas lcuentas = new LeerCuentas();
        lcuentas.Leer();
        
        for(int i = 0; i < lcuentas.getNumCuentas(); i++) {
            if(lcuentas.getCuentas().get(i).contains("@upv.edu.mx")) {
                LvCuentas.getItems().add(lcuentas.getCuentas().get(i));
            }
        }
    }

    @FXML
    protected void submitClicked(ActionEvent e) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String email = TfUsuario.getText();
        String pass = TfContra.getText();

        if(!email.contains("@upv.edu.mx")){
            lError.setText("Esta cuenta no pertenece a la poderosisima UPV");
        }
        else {
            String encriptado = EncryptAccounts.encriptar(pass);
            //String desencriptado = encrypt.desencriptar(encriptado);
            save.Escribir_Cuentas(email, encriptado);

        /*Login login = new Login();
        login.setFromEmail(email);
        login.setPassword(pass);

        goToVentanaPrincipal();
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();*/
        }
    }

    private void goToVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL path = new URL("file:src/main/resources/Ventana_Principal.fxml");
            loader.setLocation(path);
            Scene scene = loader.load();

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Escribe mensaje");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

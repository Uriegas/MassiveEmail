package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    LeerCuentas cuentas = new LeerCuentas();

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
        cuentas.Leer();
        
        for(int i = 0; i < cuentas.getNumCuentas(); i++) {
            if(cuentas.getCuentas().get(i).contains("@upv.edu.mx")) {
                LvCuentas.getItems().add(cuentas.getCuentas().get(i));
            }
        }
    }


    @FXML
    protected void SelectCuenta(){
        TfUsuario.setText(LvCuentas.getSelectionModel().getSelectedItem());
        System.out.println(LvCuentas.getSelectionModel().getSelectedItem());
        int x = LvCuentas.getSelectionModel().getSelectedIndex();
        TfContra.setText(cuentas.getCuentas().get(x+1));
        System.out.println(cuentas.getCuentas().get(x+1));
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
            if(cuentas.CompararCuentas(email)){
            }
            else {
                save.Escribir_Cuentas(email, encriptado);
            }

        /*Login login = new Login();
        login.setFromEmail(email);
        login.setPassword(pass);*/

        goToVentanaPrincipal();
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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

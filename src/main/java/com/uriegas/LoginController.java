package com.uriegas;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

import javax.mail.MessagingException;

import com.uriegas.Model.*;
/**
 * Controller of the Login View
 * TODO: Add master password window when this Controller is started
 * TODO: Test data binding between windows adding a new view that request user and password and save them, then return to the main window and check if it is saved
 */
public class LoginController extends Window {
    @FXML
    private TextField TfUsuario;
    @FXML
    private Label lError;
    @FXML
    private PasswordField TfContra;
    @FXML
    private ListView<Account> LvCuentas;
    /**
     * Initialize model
     */
    @Override
    public void initModel(MailModel m){
        super.initModel(m);
        LvCuentas.setItems(model.getAccountList());//Link the list view to the account list
        /**
         * Render the cells in the list view. In this case each cell displays only the email of an account
         */
        LvCuentas.setCellFactory(lv -> new ListCell<Account>(){
            @Override public void updateItem(Account account, boolean empty){
                super.updateItem(account, empty);
                if(empty)
                    setText(null);
                else//If not empty then render account to show the email
                    setText(account.getEmail());
            }
        });
    }
    /**
     * Inserta las cuentas almacenadas en el archivo cuentas.txt
     * TODO: When a user is pressed twice then try to login instead of putting text
     */
    public void initialize() {
        /**
         * Change to the Password textfield when pressed enter
         */
        TfUsuario.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode() == KeyCode.ENTER)
                TfContra.requestFocus();
            if(TfUsuario.isFocused())
                lError.setText("");
        });
        /**
         * Save current email and password in model when pressed enter
         */
        TfContra.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode() == KeyCode.ENTER)
                saveAccount(e);
        });
        LvCuentas.setOnMouseClicked(event->{
            if( event.getButton().equals(MouseButton.PRIMARY) ){
                if( event.getClickCount() == 2 ){
                    saveAccount(event);
                }else if( event.getClickCount() == 1 ){
                    TfUsuario.setText(LvCuentas.getSelectionModel().getSelectedItem().getEmail());
                    TfContra.setText(LvCuentas.getSelectionModel().getSelectedItem().getContrasenia());
                }
            }
        });
    }
    /**
     * Cuando se da un click añade los datos a los textfields, con dos clicks inicia sesión
     */
    @FXML
    protected void SelectCuenta(ActionEvent e){
        TfUsuario.setText(LvCuentas.getSelectionModel().getSelectedItem().getEmail());
        TfContra.setText(LvCuentas.getSelectionModel().getSelectedItem().getContrasenia());
    }
    /**
     * Se encarga de extraer los datos introducidos, crear la sesion por medio de UseJavaMail,
     * Encriptar la contraseña extraida haciendo uso de EncryptAccounts y almacenar las cuentas
     * en un archivo mediante la clase GuardarCuentas
     * @param e recibe un ActionEvent cuando el usuario da clic en el boton Iniciar Sesión
     */
    @FXML
    protected void submitClicked(ActionEvent e) {
        saveAccount(e);
    }
    /**
     * Save the entered account to the account list in the model
     * TODO: Exception handling for: InvalidEmail, NoInternetConnection, ActivateInsecureApps
     * Change stage to {@link VentanaPrincipalController} when session is succesful
     */
    private void saveAccount(Event e){
        if( !TfUsuario.getText().isEmpty() && !TfContra.getText().isEmpty() && TfUsuario.getText().contains("@") ){
            Account account = new Account(TfUsuario.getText(), TfContra.getText());
            try{//Validate the account session
                account.requestLogin();
                if(!this.model.isInAccounts(account))//If the current account is not in the saved list, then save it
                    model.getAccountList().add(account);
                System.out.println("Saving account...");
                switchScene(e, this.model, "/Ventana_Principal.fxml");//Switch to main window
            }catch(Exception ex){lError.setText("No se pudo iniciar sesión: Active apps inseguras"); ex.printStackTrace();}//Could be bad passwd or not third party enabled
            
        }else{
            System.out.println("Could not save this account");
            lError.setText("Cuenta y/o contraseña no validas");
        }
        TfUsuario.clear();
        TfContra.clear();
    }
}

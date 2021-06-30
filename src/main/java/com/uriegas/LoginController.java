package com.uriegas;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.util.Callback;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
/**
 * Controller of the Login View
 * TODO 1: Enter a name and a password, when clicked login display it to the menu
 */
public class LoginController extends Window {
    // AccountsUtilities utilidades = new AccountsUtilities();
    @FXML
    private TextField TfUsuario;
    @FXML
    private Label lError;
    @FXML
    private PasswordField TfContra;
    @FXML
    private ListView<Cuenta> LvCuentas;
    // @FXML
    // private Scene login;

    /**
     * Inserta las cuentas almacenadas en el archivo cuentas.txt
     */
    public void initialize() {
        // utilidades.LeerCuentas();

        // //Con el ciclo agrego las instancias de tipo Cuenta en el ListView
        // for(int i = 0; i < utilidades.getNumCuentas(); i++) {
        //     if(utilidades.getCuentas().get(i).getEmail().endsWith("@upv.edu.mx")) {
        //         LvCuentas.getItems().add(utilidades.getCuentas().get(i));
        //     }
        // }

        // /*Con esto modifico el contenido que se muestra en el ListView
        // ya que mostraba la direccion en memoria de la instancia y no su contenido*/
        // LvCuentas.setCellFactory(new Callback<ListView<Cuenta>, ListCell<Cuenta>>() {
        //     @Override
        //     public ListCell<Cuenta> call(ListView<Cuenta> param) {
        //         ListCell<Cuenta> cell = new ListCell<Cuenta>() {
        //             @Override
        //             protected void updateItem(Cuenta item, boolean empty) {
        //                 super.updateItem(item, empty);
        //                 if(item != null) {
        //                     setText(item.getEmail());
        //                 } else {
        //                     setText(null);
        //                 }
        //             }
        //         };
        //         return cell;
        //     }
        // });
        /**
         * Change to the Password textfield when pressed enter
         */
        TfUsuario.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode() == KeyCode.ENTER)
                TfContra.requestFocus();
        });
        /**
         * Save current email and password in model when pressed enter
         */
        TfContra.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode() == KeyCode.ENTER)
                saveAccount();
        });
    }

    /**
     * Obtiene los datos de la cuenta seleccionada en el ListView
     */
    @FXML
    protected void SelectCuenta(){
        // TfUsuario.setText(LvCuentas.getSelectionModel().getSelectedItem().getEmail());
        // TfContra.setText(LvCuentas.getSelectionModel().getSelectedItem().getContrasenia());
    }

    /**
     * Se encarga de extraer los datos introducidos, crear la sesion por medio de UseJavaMail,
     * Encriptar la contraseña extraida haciendo uso de EncryptAccounts y almacenar las cuentas
     * en un archivo mediante la clase GuardarCuentas
     * @param e recibe un ActionEvent cuando el usuario da clic en el boton Iniciar Sesión
     */
    @FXML
    protected void submitClicked(ActionEvent e) {
        saveAccount();
        // String email = TfUsuario.getText();
        // String pass = TfContra.getText();

        // if(!email.endsWith("@upv.edu.mx")){
        //     lError.setText("Esta cuenta no pertenece a la poderosisima UPV");
        // }
        // else {
        //     Cuenta sesion = new Cuenta(email, pass);

        //     //-------Guarda la instancia cuenta en un archivo
        //     try{
        //         ObjectOutputStream escribiendo_eventos = new ObjectOutputStream(new FileOutputStream("src/main/resources/CuentaTemp.tmp"));
        //         escribiendo_eventos.writeObject(sesion);
        //         escribiendo_eventos.close();
        //     }catch(Exception ex){ex.printStackTrace();}
        //     //----------------------------------------------

        //     //INICIA SESIÓN CON LOS DATOS INGRESADOS
        //     UseJavaMail mail = new UseJavaMail();
        //     UseJavaMail.Login(sesion);

        //     String encriptado = null; //Encripta la contraseña para almacenarla en el archivo
        //     try {
        //         encriptado = Keys.encriptar(pass);
        //     } catch (Exception exception) {exception.printStackTrace(); }

        //     if(utilidades.CompararCuentas(email)){ //Si la cuenta ya existe no la almacena
        //     }
        //     else {
        //         utilidades.Escribir_Cuentas(sesion);
        //     }
        // switchScene(e, "/Ventana_Principal.fxml");
        // }
    }
    private void saveAccount(){
        System.out.println("Saving account...");
    }
}

package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
/**
 * Settings window
 * TODO: Don't know why doesn't open when requested first time but in second it pops-up
 */
public class SettingsController extends Window{

    String home = System.getProperty("user.home");
    private ArrayList<Cuenta> listaCuentas = new ArrayList<>();

    @FXML private PasswordField CurrentPassword;
    @FXML private PasswordField NewPassword;
    @FXML private ColorPicker Color1;
    @FXML private ColorPicker Color2;

    @FXML
    protected void ClickCancelar(ActionEvent e){//Funciona pero saca de la app
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void ClickAplicar(ActionEvent e){
        // if(!(NewPassword.getText().isEmpty()) || Keys.comparar(CurrentPassword.getText())) {
        //     utilidades.LeerCuentas();

        //     //Con el ciclo agrego las instancias de tipo Cuenta en el ListView
        //     for(int i = 0; i < utilidades.getNumCuentas(); i++) {
        //         if(utilidades.getCuentas().get(i).getEmail().endsWith("@upv.edu.mx")) {
        //             listaCuentas.add(utilidades.getCuentas().get(i)); //Almacena las cuentas en un arraylist
        //         }
        //     }

        //     Keys.almacenarLlave(NewPassword.getText()); //Actualiza la llave
        //     Keys.definirLlave(NewPassword.getText());   //Se define la llave para encriptar
        //     //Eliminar cuentas.txt
        //     File cuentas = new File(home+"/.MassiveMail/Accounts.encrypt");
        //     cuentas.delete(); //Borra el fichero

        //     for(int i = 0; i < listaCuentas.size(); i++) {
        //         utilidades.Escribir_Cuentas(listaCuentas.get(i)); //Guarda la cuenta usando la nueva clave
        //     }
			System.out.println("Click aplicar");
        System.out.println("CAMBIOS APLICADOS");
        System.out.println("Color1: "+ toRgba(Color1.getValue()));
        System.out.println("Color2: "+ toRgba(Color2.getValue()));
    }

    private String toRgba(Color color) {
        int r = (int) (255 * color.getRed());
        int g = (int) (255 * color.getGreen());
        int b = (int) (255 * color.getBlue());
        int a = (int) (255 * color.getOpacity());
        return String.format("#%02x%02x%02x%02x", r, g, b, a);
    }
}
package com.uriegas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Settings window
 * TODO: Don't know why doesn't open when requested first time but in second it pops-up
 */
public class SettingsController extends Window{
    @FXML private PasswordField CurrentPassword;
    @FXML private PasswordField NewPassword;
    @FXML private ColorPicker Color1;
    @FXML private ColorPicker Color2;
    @FXML private Label errorLabel;
    // private String actualPassword = this.model.getMasterPassword();

    public void initialize(){
        /**
         * Change to the NewPassword textfield when pressed enter
         */
        CurrentPassword.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode() == KeyCode.ENTER){
                if(CurrentPassword.getText().equals(this.model.getMasterPassword())){
                    errorLabel.setText("Contraseña correcta");
                    errorLabel.setStyle("-fx-text-fill: green");
                    NewPassword.requestFocus();
                    //Test button color change
                }
                else{
                    errorLabel.setText("Contraseña incorrecta");
                    errorLabel.setStyle("-fx-text-fill: red");
                }
            }
        });
    }
    /**
     * Get out of the settings
     * @param e
     */
    @FXML
    protected void ClickCancelar(ActionEvent e){
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
    /**
     * Apply proposed changes if correct and exit.
     * When clicked verify if passwd match and change passwd, add the colors config
     * @param e
     */
    @FXML
    protected void ClickAplicar(ActionEvent e){
        //-->Change password
        if(this.model.getMasterPassword().equals(CurrentPassword.getText()))
            if(!NewPassword.getText().isEmpty())
                this.model.setMasterPassword(NewPassword.getText());
        System.out.println("Contraseña actualizada");
        //<--Change password
        //-->Change colors
        //<--Change colors
        System.out.println("Color1: "+ toRgba(Color1.getValue()));
        System.out.println("Color2: "+ toRgba(Color2.getValue()));
        Node source = (Node)e.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    private String toRgba(Color color) {
        int r = (int) (255 * color.getRed());
        int g = (int) (255 * color.getGreen());
        int b = (int) (255 * color.getBlue());
        int a = (int) (255 * color.getOpacity());
        return String.format("#%02x%02x%02x%02x", r, g, b, a);
    }
}
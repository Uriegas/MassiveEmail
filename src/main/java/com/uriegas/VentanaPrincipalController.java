package com.uriegas;

import com.uriegas.Model.*;
import javafx.event.*;
import javafx.stage.*;
import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
/**
 * Controller of the Ventana Principal view
 */
public class VentanaPrincipalController extends Window {
    @FXML
    private TextField TfDestinatarios;
    @FXML
    private TextField TfAsunto;
    @FXML
    private TextArea TaMensaje;
    @FXML
    private ListView<String> lvAdjuntos;
    @FXML
    private Button btnConfig;
    @FXML
    private Button btnCambiarCuenta;
    @FXML
    private ListView<String> lastViewedExcels;
    /**
     * Initialize model
     */
    @Override
    public void initModel(MailModel m){
        super.initModel(m);
        lvAdjuntos.setItems(this.model.adjuntosProperty());
        lvAdjuntos.setCellFactory(lv -> new ListCell<String>(){
            @Override public void updateItem(String s, boolean empty){
                super.updateItem(s, empty);
                if(empty)
                    setText(null);
                else
                    setText(s.substring(s.lastIndexOf("/") +1, s.length()));
            }
        });
        lastViewedExcels.setItems(this.model.lastViewedExcelsProperty());
        lastViewedExcels.setCellFactory(lv -> new ListCell<String>(){
            @Override public void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty)
                    setText(null);
                else
                    setText(item.substring(item.lastIndexOf("/") +1, item.length()));
            }
        });
    }
    /**
     * Init method
     */
    public void initialize() {
        btnCambiarCuenta.setOnAction(e ->{//Return to login window
            switchScene(e, this.model, "/Login.fxml");
        });
        lastViewedExcels.setOnMouseClicked(event->{
            if( event.getButton().equals(MouseButton.PRIMARY) ){
                if( event.getClickCount() == 2 ){
                    try {
                        this.model.setExcelTable( Utilities.readExcel( lastViewedExcels.getSelectionModel().getSelectedItem() ) );
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
    /**
     * Extrae el destinatario, asunto y cuerpo del mensaje introducidos por el usuario
     * y posteriormente los envia al metodo senEmail de la clase EmailUtil.
     * When clicked 2 things happen: the mail is rendered as many rows there are,
     * then this messages are send
     * @param e recibe un ActionEvent cuando el usuario da click en el boton enviar
     */
    @FXML
    protected void ClickEnviar(ActionEvent e) {
        this.model.mailsProperty().clear();//Empty the mails before processing new ones
        this.model.adjuntosProperty().clear();//Empty adjuntos before processing new ones
        //-->Send one mail
        // String destinatario = TfDestinatarios.getText();
        // String asunto = TfAsunto.getText();
        // String cuerpo = TaMensaje.getText();
        // Mail mensaje = new Mail(destinatario, asunto, cuerpo, this.model.getAdjuntos());
        // mensaje.send();
        //<--Send one mail

        //-->Render mails
        ArrayList<String> dest = this.model.excelTableProperty().getReceivers();//Array of strings
        ArrayList<HashMap<String, String>> vars = this.model.excelTableProperty().getVars();//Array of hashmaps
        for( int i = 0; i < vars.size() && i < dest.size(); i++ )
            this.model.addMail( new Mail(dest.get(i), TfAsunto.getText(), TaMensaje.getText(), vars.get(i), this.model.getAdjuntos()) );
        //<--Render mails
        
        //-->Send many mails
        for( Mail m : this.model.mailsProperty() )
            m.send();
        //<--Send many mails
    }
    /**
     * Abre la vista Envio_Rutinas
     * @param e
     */
    @FXML
    protected void ClickRutina(ActionEvent e) {

        this.model.mailsProperty().clear();//Empty the mails before processing new ones
        // this.model.adjuntosProperty().clear();//Empty adjuntos before processing new ones

        //-->Render mails
        ArrayList<String> dest = this.model.excelTableProperty().getReceivers();//Array of strings
        ArrayList<HashMap<String, String>> vars = this.model.excelTableProperty().getVars();//Array of hashmaps
        for( int i = 0; i < vars.size() && i < dest.size(); i++ )
            this.model.addMail( new Mail(dest.get(i), TfAsunto.getText(), TaMensaje.getText(), vars.get(i), this.model.getAdjuntos()) );
        //<--Render mails

        //--> Serializa mails
        File file = new File(System.getProperty("user.home") + "/.MassiveMail/PendingMails.ser");
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(model);
            System.out.println("Serialized data is in /PendingMails.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        //<-- Serializa mails
        try {
            FXMLLoader loader = new FXMLLoader();
            String RutaFXML = getClass().getResource("/Envios_rutinas.fxml").toExternalForm();
            //loader.setLocation(new URL("file:src/main/resources/Envios_rutinas.fxml"));
            loader.setLocation(new URL(RutaFXML));
            Scene scene = loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //switchScene(e, this.model, "/Envios_rutinas.fxml");
    }
    /**
     * Abre el selector de archivos
     * @param e
     */
    @FXML
    protected void ClickAdjuntar(ActionEvent e){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona el archivo deseado");

        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            if(selectedFile.getAbsolutePath().endsWith(".html"))   //Si es .html ....
                selectedFile = HTMLutilites.convertir(selectedFile);    // ... llama al metodo convertir
            // this.model.adjuntosProperty().add(selectedFile.getAbsolutePath());//Save current file to adjuntos
            this.model.setAdjuntos(selectedFile.getAbsolutePath());//Save current file to adjuntos
        }else{
            System.out.println("No se encontro el archivo");
        }
    }
    /**
     * Load the Excel file into a table view and save file path to future use in displaying recent used files
     * @param e
     */
    @FXML
    protected void ClickAddXLSX(ActionEvent e){
        System.out.println("Clicked XLSX");
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Seleccion un archivo XLSX");
        filechooser.setInitialDirectory( new File(System.getProperty("user.dir")) );
        File selectedFile = filechooser.showOpenDialog(null);
        
        try{//TODO: Verify that the program can only load one excel file (show warning)
            this.model.setExcelTable( Utilities.readExcel(selectedFile.getAbsolutePath()) );//Save table
            System.out.println("Excel loaded succesfully");
            this.model.setLastViewedExcels(selectedFile.getAbsolutePath());//Add this file to last viewed list
        }catch(IOException ex){//If not an excel or a problem occurs
            Alert error = new Alert(
                Alert.AlertType.ERROR,
                "El archivo seleccionado no puede ser abierto"
            );
            error.show();
        }
    }
    /**
     * Change view to settings
     * @param e
     */
    @FXML
    protected void ClickSettings(ActionEvent e){
        // switchScene(e, this.model, "/Settings.fxml");
        createPopUp(e, "/Settings.fxml");
    }
}

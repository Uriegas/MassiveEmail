package com.uriegas;

import com.uriegas.Model.*;
import javafx.event.*;
import javafx.stage.*;
import java.io.*;
import javafx.fxml.*;
import javafx.scene.control.*;
/**
 * Controller of the Ventana Principal view
 * TODO: Add updates of main branch
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
    }
    /**
     * Init method
     */
    public void initialize() {
        btnCambiarCuenta.setOnAction(e ->{//Return to login window
            switchScene(e, this.model, "/Login.fxml");
        });
        btnConfig.setOnMouseClicked(e ->{//Open configuration window
                Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "¿Está seguro que desea salir?"
                );
                //Try multiple color configurations
                // Configuration conf = new Configuration();
                // conf.setBase( conf.getBase().saturate() );
                closeConfirmation.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css
                closeConfirmation.setHeaderText("Confirmación de Salida");
                closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                closeConfirmation.showAndWait();
        });
    }
    /**
     * Extrae el destinatario, asunto y cuerpo del mensaje introducidos por el usuario
     * y posteriormente los envia al metodo senEmail de la clase EmailUtil
     * @param e recibe un ActionEvent cuando el usuario da click en el boton enviar
     */
    @FXML
    protected void ClickEnviar(ActionEvent e) {
        String destinatario = TfDestinatarios.getText();
        String asunto = TfAsunto.getText();
        String cuerpo = TaMensaje.getText();

        Mail mensaje = new Mail(destinatario, asunto, cuerpo, this.model.getAdjuntos());
        mensaje.send();
    }
    /**
     * Abre la vista Envio_Rutinas
     * @param e
     */
    @FXML
    protected void ClickRutina(ActionEvent e) {
        switchScene(e, this.model, "/Envios_rutinas.fxml");
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

            //lvAdjuntos.setFixedCellSize(60.0);
            // lvAdjuntos.getItems().add(selectedFile.getName()); //Agrega el nombre del archivo al ListView
            // adjuntos.add(new File(selectedFile.getAbsolutePath())); //Agrega el archivo al Array de archivos a enviar
            this.model.adjuntosProperty().add(selectedFile.getAbsolutePath());//Save current file to adjuntos
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
        File selectedFile = filechooser.showOpenDialog(null);
        
        try{
            this.model.setExcelTable( Utilities.readExcel(selectedFile.getAbsolutePath()) );//Save table
            System.out.println("Excel loaded succesfully");
            // Stage dialog = new Stage(); // new stage
            // dialog.initModality(Modality.APPLICATION_MODAL);

            // BorderPane pane = new BorderPane(Utilities.readExcelToList(App.excel));//Pane to save the table
            // Scene scene = new Scene(pane,500,500);
            // scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Dynamic Css
            // dialog.setScene(scene);
            // dialog.show();
            this.model.setLastViewedExcels(selectedFile.getAbsolutePath());
        }catch(IOException ex){//If not an excel or a problem occurs
            Alert error = new Alert(
                Alert.AlertType.ERROR,
                "El archivo seleccionado no pudo ser abierto"
            );
            error.show();
        }
    }
}

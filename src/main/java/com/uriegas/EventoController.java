package com.uriegas;

import com.uriegas.Model.Routine;
import com.uriegas.Model.Account;
import com.uriegas.Model.DaemonEventos;
import com.uriegas.Model.Mail;
import com.uriegas.Model.MailModel;
import com.uriegas.Model.RoutineModel;
import com.calendarfx.view.YearMonthView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventoController implements Initializable {

    protected MailModel model;
    protected RoutineModel modelR = new RoutineModel();

    ArrayList<Routine> eventos = new ArrayList<>();
    Account cuenta;
    ArrayList<Mail> mensaje;

    @FXML private YearMonthView Calendario;
    @FXML private DatePicker DpFecha;
    @FXML private TextField TfHora;
    @FXML private ListView<String> LvEventosPen;
    @FXML private ComboBox<String> CbRutinas;

    /**
     * Inicializa la vista colocando la fecha y hora actuales
     * en los campos correspondientes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalTime hora = LocalTime.now();
        LocalDate fecha = LocalDate.now();

        TfHora.setText(hora.truncatedTo(ChronoUnit.MINUTES).toString()); //Asingo la hora al TextFeld
        DpFecha.setValue(fecha);  //Asingo la hora al DatePicker

        //-->Load data model from file
        File fileIn = new File(System.getProperty("user.home") + "/.MassiveMail/RoutineModel.ser");
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(fileIn))){
            this.modelR = (RoutineModel)out.readObject();
            System.out.println("Deserialized data from /RoutineModel.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        //<--Load data model from file
    }

    /**
     * Obtiene la fecha seleccionada en el calendario
     */
    public void SelectFecha(){
        /* Al traer la fecha seleccionada en el calendario devuelve un ObservableSet<LocalDate> al castearlo a String obtiene
         "[2001-06-21]", por eso es que subtraigo solo el contenido dentro de los corchetes y despues lo parseo a LocalDate */
        String fecha = Calendario.getSelectedDates().toString().substring(1, Calendario.getSelectedDates().toString().length()-1);
        LocalDate localDate1 = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DpFecha.setValue(localDate1);
    }

    @FXML
    protected void ClickCancelar(ActionEvent e){
        //switchScene(e, "/Ventana_Principal.fxml");
    }


    /**
     * Obtiene la fecha y hora colocadas en los campos,
     * recupera la instancia cuenta y mensaje almacenada en los archivos,
     * crea la instancia de tipo evento agregandola al ArrayList<Evento> y serealiza el arreglo
     * Manda a llamar el metodo LeerEventos() de la clase DaemonEventos
     * Crea el demonio y lo corre
     * @param e
     */
    @FXML
    protected void ClickDefRutina(ActionEvent e){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //Se define el formato
                                                //Une la fecha y hora seleccionadas en la vista y se aplica el formato
        LocalDateTime fechaEnvio = LocalDateTime.parse(DpFecha.getValue() + " " + TfHora.getText(), formatter);


        //-->Load data model from file
        File fileIn = new File(System.getProperty("user.home") + "/.MassiveMail/PendingMails.ser");
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(fileIn))){
            this.model = (MailModel)out.readObject();
            System.out.println("Deserialized data from /PendingMails.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        //<--Load data model from file

        modelR.addRoutine(new Routine(fechaEnvio, CbRutinas.getValue(), model.getAccountList().get(0), model.getMails()));


        //-->Guarda el modelo Rutina
        File fileOut = new File(System.getProperty("user.home") + "/.MassiveMail/RoutineModel.ser");
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileOut))){
            out.writeObject(modelR);
            System.out.println("Serialized data is in /RoutineModel.ser");
        } catch (Exception i) {
            i.printStackTrace();
        }
        //<--Guarda el modelo Rutina

        LvEventosPen.getItems().add(model.getMails().get(0).getAsunto()+"\tFecha: "+fechaEnvio); //Se escribe el evento en el ListView

        DaemonEventos.LeerEventos(); //Cargamos el ArrayList en la clase del demonio

        Thread t = new DaemonEventos(); //Creamos el hilo
        t.setDaemon(true); //lo convertimos en demonio
        t.start(); //Ejecutamos el demonio (se ejecuta lo que esta en el metodo run() )
    }
}
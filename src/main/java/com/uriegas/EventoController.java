package com.uriegas;

import com.calendarfx.view.YearMonthView;
import com.uriegas.Model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventoController implements Initializable {

    ArrayList<Evento> eventos = new ArrayList<>();
    Account cuenta;
    Mail mensaje;

    @FXML
    private YearMonthView Calendario;

    @FXML
    private DatePicker DpFecha;

    @FXML
    private TextField TfHora;

    @FXML
    private ListView LvEventosPen;

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

        //Obtenemos la cuenta y el mensaje almacenados
        try{
            ObjectInputStream recuperando_cuenta = new ObjectInputStream(new FileInputStream("src/main/resources/CuentaTemp.tmp"));
            cuenta = (Account)recuperando_cuenta.readObject();
            recuperando_cuenta.close();
            ObjectInputStream recuperando_msj = new ObjectInputStream(new FileInputStream("src/main/resources/MensajeTemp.tmp"));
            mensaje = (Mail)recuperando_msj.readObject();
            recuperando_msj.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        eventos.add(new Evento(fechaEnvio, cuenta, mensaje)); //Se agrega el evento al ArrayList
        // EventosSerializer.serealizarEventos(eventos);   //Se serealiza el ArrayList
        LvEventosPen.getItems().add(mensaje.getAsunto()+"\tFecha: "+fechaEnvio); //Se escribe el evento en el ListView

        DaemonEventos.LeerEventos(); //Cargamos el ArrayList en la clase del demonio

        Thread t = new DaemonEventos(); //Creamos el hilo
        t.setDaemon(true); //lo convertimos en demonio
        t.start(); //Ejecutamos el demonio (se ejecuta lo que esta en el metodo run() )
    }
}

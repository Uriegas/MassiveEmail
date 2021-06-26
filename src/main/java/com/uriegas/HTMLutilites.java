package com.uriegas;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Optional;

class HTMLutilites {

    /**
     * Muestra una ventana modal que ofrece la posibilidad de convertir el archivo html a pdf
     * @param archivo recibe el archivo html seleccionado por el usuario
     * @return retorna el archivo convertido en .pdf si el usuario así lo deseo, en caso contrario devuelve el .html
     */
    public static File convertir(File archivo){

        //CREO LA VENTANA MODAL
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opciones de envio HTML");
        alert.setHeaderText("Se detecto un archivo HTML");
        alert.setContentText("¿Qué desea hacer con el?");
        alert.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css

        ButtonType btnEnviarHtml = new ButtonType("Enviar .html");
        ButtonType btnConvertir = new ButtonType("Convertir a .pdf");

        alert.getButtonTypes().setAll(btnEnviarHtml, btnConvertir);
        Optional<ButtonType> result = alert.showAndWait();

        //Si dio clic en enviar como html, devuelve el archivo html recibido
        if (result.get() == btnEnviarHtml){
            return archivo;

        //Si dio clic en convertir, renderiza el html y lo guarda en el pdf, devuelve el archivo pdf de salida
        } else if (result.get() == btnConvertir) {
            try {
                String CarpetaTrabajo = archivo.getPath(); //Establezco la carpeta de trabajo

                ConverterProperties converterProperties = new ConverterProperties(); //Para incluir css
                converterProperties.setBaseUri(CarpetaTrabajo);

                HtmlConverter.convertToPdf(new FileInputStream(archivo.getAbsolutePath()),
                        new FileOutputStream("src/main/resources/Salida.pdf"), converterProperties);

                archivo = new File("src/main/resources/Salida.pdf");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return archivo;
    }
}

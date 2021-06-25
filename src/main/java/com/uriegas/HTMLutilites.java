package com.uriegas;

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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opciones de envio HTML");
        alert.setHeaderText("Se detecto un archivo HTML");
        alert.setContentText("¿Qué desea hacer con el?");
        alert.getDialogPane().styleProperty().bind(Configuration.cssProperty());//Add dynamic css

        ButtonType btnEnviarHtml = new ButtonType("Enviar .html");
        ButtonType btnConvertir = new ButtonType("Convertir a .pdf");

        alert.getButtonTypes().setAll(btnEnviarHtml, btnConvertir);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnEnviarHtml){
            return archivo;

        } else if (result.get() == btnConvertir) {
            try {
                HtmlConverter.convertToPdf(new FileInputStream(archivo.getAbsolutePath()),
                        new FileOutputStream("src/main/resources/Salida.pdf"));
                archivo = new File("src/main/resources/Salida.pdf");
            }catch(Exception ex){ex.printStackTrace();}
        }
        return archivo;
    }
}

package com.uriegas;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.*;
/**
 * Abstract Window class, needed because of switching scenes and implementing global CSS properties
 */
public abstract class Window {
    private MailModel model;
    /**
     * Switch the current scene (window) to the specified FXML file
	 * @param FXML file
     * @param e
     */
    public void switchScene(Event e, String FXML){
        Stage switchscene = (Stage) ((Node)e.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(FXML));
            Scene scene = loader.load();
            scene.getRoot().styleProperty().bind(Configuration.cssProperty());//Add dynamic css
            switchscene.setScene(scene);
        }catch(IOException ex){ex.printStackTrace();}
        //Another approach
        // try {
        //     Parent root = FXMLLoader.load(getClass().getResource(FXML));
        //     Stage stage = new Stage();
        //     stage.setScene(new Scene(root));
        //     stage.initModality(Modality.APPLICATION_MODAL);
        //     stage.show();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
    public void initModel(MailModel m){
        if(this.model != null)
            throw new IllegalStateException("Model only can be instantiated once");
        else{
            this.model = m;
        }
    }
}

package com.uriegas;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.*;

import com.uriegas.Model.MailModel;
/**
 * Abstract Window class, needed because of switching scenes and implementing global CSS properties
 */
public abstract class Window {
    protected MailModel model;
    /**
     * Switch the current scene (window) to the specified FXML file
	 * @param FXML file
     * @param e
     */
    public void switchScene(Event e, MailModel m, String FXML){
        Stage switchscene = (Stage) ((Node)e.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(FXML));
            Scene scene = loader.load();
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());//Add css
            Window w = loader.getController();
            w.initModel(m);
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
    /**
     * Abstract method for initializing the model
     * @param m
     */
    public void initModel(MailModel m){
        if(this.model != null)
            throw new IllegalStateException("Model only can be instantiated once");
        else{
            this.model = m;
        }
    }
    /**
     * Creates a new pop up from the current window
     * @param e event to handle this pop up
     * @param FXML the fxml file to load this pop up
     */
    public void createPopUp(Event e, String FXML){
        Stage dialog = new Stage(); // new stage
        dialog.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(FXML));
        try{
            Scene scene = loader.load();
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());//Add css
            Window x = loader.getController();
            x.initModel(model);
            dialog.setScene(scene);
        }catch(IOException ex){ex.printStackTrace();}
        // Defines a modal window that blocks events from being
        // delivered to any other application window.
        dialog.initOwner(((Node)e.getTarget()).getScene().getWindow());
        // dialog.setOnCloseRequest(event ->{
        //     Node source = (Node)event.getSource();
        //     Stage stage = (Stage)source.getScene().getWindow();
        //     stage.close();
        // });
        dialog.show();
    }

    // public void initModel(RoutineModel r){
    //     if(this.modelR != null)
    //         throw new IllegalStateException("Model only can be instantiated once");
    //     else{
    //         this.modelR = r;
    //     }
    // }
}

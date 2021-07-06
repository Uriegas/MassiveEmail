package com.uriegas.Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoutineModel implements Serializable  {

    // private final ObservableList<Routine> routines = FXCollections.observableArrayList(routine ->
    //     new Observable[]{routine.dateProperty(), routine.rutinaProperty(), routine.cuentaProperty(), routine.mailsProperty()});
    
    // public ObservableList<Routine> getRoutineList(){
    //     return routines;
    // }

        
    //private ObservableList<Routine> routines = FXCollections.observableArrayList();
	private transient ObservableList<Routine> routines = FXCollections.observableArrayList(routine ->
		new Observable[]{routine.dateProperty(), routine.rutinaProperty(), routine.cuentaProperty(), routine.mailsProperty()});

    	//-->routines methods
	public ObservableList<Routine> routinesProperty(){
		return this.routines;
	}
	public ArrayList<Routine> getRoutines(){
		ArrayList<Routine> routinesList = new ArrayList<Routine>();
		for(Routine a : this.routines)
			routinesList.add(a);
		return routinesList;
	}
	public void delRoutine(int i){
		routines.remove(i);
	}
	public void addRoutines(ArrayList<Routine> rutinas){
		this.routinesProperty().addAll(rutinas);
	}
	public void addRoutine(Routine r){
		this.routinesProperty().add(r);
	}
	//<--routines methods


    	/**
	 * Serialize this object, expect for the Routine
	 * @param s
	 * @throws Exception
	 */
    private void writeObject(ObjectOutputStream s) throws Exception {
		s.writeObject(new ArrayList<Routine>(routines));
    }
	/**
	 * Serialize this object, expect for the Routine
	 * @param s
	 * @throws Exception
	 */
    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
		routines = FXCollections.observableArrayList((List<Routine>) s.readObject());
		//<--Initialize not serialized objects(if not initialized they are null)
    }
}

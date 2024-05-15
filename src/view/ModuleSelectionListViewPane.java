package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.Module;

public class ModuleSelectionListViewPane extends VBox {
	private ListView<Module> modulesListView;
	private ObservableList<Module> moduleObservableList = FXCollections.observableArrayList();
	private Label lblTerm;
	
	public ModuleSelectionListViewPane() {
		this.setSpacing(10);
		lblTerm = new Label("");
		lblTerm.setPadding(new Insets(5,5,5,5));
		modulesListView = new ListView<Module>();
		modulesListView.setPrefWidth(400);
		modulesListView.setPrefHeight(120);
		modulesListView.setTranslateX(5); //moves the listview slightly to the right so it doesn't touch the left edge of the window
		modulesListView.getSelectionModel().select(0);
		modulesListView.setItems(moduleObservableList);
		this.getChildren().addAll(lblTerm, modulesListView);
	}
	
	public void setTermLabel(String label) {
		lblTerm.setText(label);
	}
	
	public void addModuleToList(Module module) {
		moduleObservableList.add(module);
	}
	
	public void removeModuleFromList(Module module) {
		moduleObservableList.remove(module);
	}
	
	public void removeModuleFromList(int i) {
		moduleObservableList.remove(i);
	}
	
	public void clearModules() {
		moduleObservableList.clear();
	}
	
	public ObservableList<Module> getContents() {
		return moduleObservableList;
	}
	
	public ListView<Module> getListView() {
		return modulesListView;
	}
	
	/**
	 * This method allows the controller to pass in a Collection of modules from a course
	 * to populate the ListView control. It uses the 
	 * @param modules A collection of modules, each module should be of Module type
	 */
	public void populateUnselectedModuleList(Collection<Module> moduleCol) {
		if (lblTerm.getText().equals("Unselected Term 1 Modules")) {
			for (Module m: moduleCol) {
				if (m.getDelivery().toString().equals("TERM_1")) {
					this.addModuleToList(m);
				} else {
					continue;
				}
			}
		} else if (lblTerm.getText().equals("Unselected Term 2 Modules")) {
			for (Module m: moduleCol) {
				if (m.getDelivery().toString().equals("TERM_2")) {
					this.addModuleToList(m);
				} else {
					continue;
				}
			}
		}
	} 
	
	/**
	 * Can set max height of the listview control
	 * @param width of double type, width of the listview control.
	 */
	public void setListMaxHeight(double h) {
		modulesListView.setMaxHeight(h);
	}
	
	
} // end of class

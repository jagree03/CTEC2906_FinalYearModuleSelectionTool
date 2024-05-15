package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TermReservedModulePane extends BorderPane {

	private ModuleSelectionListViewPane UnselectedTermModules;
	private ModuleSelectionListViewPane ReservedTermModules;
	private Label lblMessage;
	private Button btnAdd;
	private Button btnRemove;
	private Button btnConfirm;
	
	public TermReservedModulePane() {
		VBox outer = new VBox(5);
		HBox inner = new HBox(10);
		UnselectedTermModules = new ModuleSelectionListViewPane();
		UnselectedTermModules.setTermLabel("");
		UnselectedTermModules.setMaxHeight(200);
		ReservedTermModules = new ModuleSelectionListViewPane();
		ReservedTermModules.setTermLabel("");
		ReservedTermModules.setMaxHeight(200);
		inner.getChildren().addAll(UnselectedTermModules, ReservedTermModules);
		outer.getChildren().add(inner);
		
		HBox buttons = new HBox(5);
		lblMessage = new Label("");
		btnAdd = new Button("Add");
		btnRemove = new Button("Remove");
		btnConfirm = new Button("Confirm");
		buttons.getChildren().addAll(lblMessage, btnAdd, btnRemove, btnConfirm);
		buttons.setAlignment(Pos.CENTER);
		outer.getChildren().add(buttons);
		this.setCenter(outer);
	}
	
	public ModuleSelectionListViewPane getUnselectedTermModules() {
		return UnselectedTermModules;
	}
	
	public ModuleSelectionListViewPane getReservedTermModules() {
		return ReservedTermModules;
	}
	
	public void setUnselectedTermModulesLabel(String label) {
		UnselectedTermModules.setTermLabel(label);
	}
	
	public void setReservedTermModulesLabel(String label) {
		ReservedTermModules.setTermLabel(label);
	}
	
	public void setMessageLabel(String label) {
		lblMessage.setText(label);
	}
	
	public void addAddButtonHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	
	public void addRemoveButtonHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}
	
	public void addConfirmButtonHandler(EventHandler<ActionEvent> handler) {
		btnConfirm.setOnAction(handler);
	}	
}

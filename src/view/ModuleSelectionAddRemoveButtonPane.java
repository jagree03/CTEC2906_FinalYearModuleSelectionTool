package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ModuleSelectionAddRemoveButtonPane extends HBox {
	private Button btnAdd;
	private Button btnRemove;
	private Label lblTerm;
	
	public ModuleSelectionAddRemoveButtonPane() {
		this.setSpacing(10);
		btnAdd = new Button("Add");
		btnRemove = new Button("Remove");
		lblTerm = new Label("");
		this.getChildren().addAll(lblTerm, btnAdd, btnRemove);
	}
	
	public void setTermLabel(String label) {
		lblTerm.setText(label);
	}
	
	public void addAddingHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	
	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}
}

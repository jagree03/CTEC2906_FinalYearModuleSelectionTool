package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;

public class ModuleSelectionPane extends HBox {
	
	private ModuleSelectionListViewPane term1List;
	private ModuleSelectionListViewPane term2List;
	private ModuleSelectionListViewPane selectedYearLongModulesList;
	private ModuleSelectionListViewPane selectedterm1List;
	private ModuleSelectionListViewPane selectedterm2List;
	private ModuleSelectionAddRemoveButtonPane AddRemoveButtonPane_Term1;
	private ModuleSelectionAddRemoveButtonPane AddRemoveButtonPane_Term2;
	private TextField Term1Credits;
	private TextField Term2Credits;
	private Button btnReset;
	private Button btnSubmit;
	
	
	public ModuleSelectionPane() {
		///////////////////////
		//    TERM 1
		///////////////////////
		VBox vbox_left_1 = new VBox(10);
		vbox_left_1.setAlignment(Pos.TOP_LEFT);
		term1List = new ModuleSelectionListViewPane();
		term1List.setTermLabel("Unselected Term 1 Modules");
		vbox_left_1.getChildren().add(term1List);

		// Create a subcontainer Horizontal box containing Term 1 label, add and remove buttons
		// I pass in a AddRemoveButtonPane Class which can be re-used.
		AddRemoveButtonPane_Term1 = new ModuleSelectionAddRemoveButtonPane();
		AddRemoveButtonPane_Term1.setTermLabel("Term 1");
		AddRemoveButtonPane_Term1.setAlignment(Pos.CENTER);
		vbox_left_1.getChildren().add(AddRemoveButtonPane_Term1);
		
		///////////////////////
		//    TERM 2
		///////////////////////
		term2List = new ModuleSelectionListViewPane();
		term2List.setTermLabel("Unselected Term 2 Modules");
		vbox_left_1.getChildren().add(term2List);
	
		// Create a subcontainer Horizontal box containing Term 2 label, add and remove buttons
		// I pass in a AddRemoveButtonPane Class which can be re-used.
		AddRemoveButtonPane_Term2 = new ModuleSelectionAddRemoveButtonPane();
		AddRemoveButtonPane_Term2.setTermLabel("Term 2");
		AddRemoveButtonPane_Term2.setAlignment(Pos.CENTER);
		vbox_left_1.getChildren().add(AddRemoveButtonPane_Term2);
		
		///////////////////////
		//    Current Term 1 Credits + Reset button
		///////////////////////
		HBox term1_credits = new HBox(5);
		Label lblTerm1Credits = new Label("Current Term 1 credits: ");
		Term1Credits = new TextField("0");
		Term1Credits.setMaxWidth(30);
		Term1Credits.setEditable(false);
		term1_credits.setAlignment(Pos.CENTER);
		term1_credits.getChildren().addAll(lblTerm1Credits, Term1Credits);
		vbox_left_1.getChildren().add(term1_credits);
		
		
		HBox resetButtonContainer = new HBox(0);
		btnReset = new Button("Reset");
		resetButtonContainer.getChildren().add(btnReset);
		resetButtonContainer.setAlignment(Pos.CENTER_RIGHT);
		vbox_left_1.getChildren().add(resetButtonContainer);
		
		
		///////////////////////
		//    Selected modules lists for term 1 and term 2... new vbox as well as current term 2 credits
		///////////////////////
		VBox vbox_right_2 = new VBox(10);
		selectedYearLongModulesList = new ModuleSelectionListViewPane();
		selectedYearLongModulesList.setTermLabel("Selected Year Long modules");
		selectedYearLongModulesList.setListMaxHeight(30);
		vbox_right_2.getChildren().add(selectedYearLongModulesList);

		selectedterm1List = new ModuleSelectionListViewPane();
		selectedterm1List.setTermLabel("Selected Term 1 modules");
		selectedterm1List.setListMaxHeight(117);
		
		selectedterm2List = new ModuleSelectionListViewPane();
		selectedterm2List.setTermLabel("Selected Term 2 modules");
		selectedterm2List.setListMaxHeight(117);
		
		vbox_right_2.getChildren().addAll(selectedterm1List, selectedterm2List);
		vbox_right_2.setPadding(new Insets(0,0,0,50));
		
		
		HBox term2_credits = new HBox(5);
		Label lblTerm2Credits = new Label("Current Term 2 credits: ");
		Term2Credits = new TextField("0");
		Term2Credits.setMaxWidth(30);
		Term2Credits.setEditable(false);
		term2_credits.setAlignment(Pos.CENTER);
		term2_credits.getChildren().addAll(lblTerm2Credits, Term2Credits);
		vbox_right_2.getChildren().add(term2_credits);
		
		
		//////
		// submit button held in hbox container
		//////
		HBox submitButtonContainer = new HBox(0);
		btnSubmit = new Button("Submit");
		submitButtonContainer.getChildren().add(btnSubmit);
		submitButtonContainer.setAlignment(Pos.CENTER_LEFT);
		vbox_right_2.getChildren().add(submitButtonContainer);
		
		
		this.getChildren().addAll(vbox_left_1, vbox_right_2);
	}
	
	public ModuleSelectionListViewPane getTerm1List() {
		return term1List;
	}
	
	public ModuleSelectionListViewPane getTerm2List() {
		return term2List;
	}
	
	public ModuleSelectionListViewPane getSelectedYearLongList() {
		return selectedYearLongModulesList;
	}
	
	public ModuleSelectionListViewPane getSelectedTerm1List() {
		return selectedterm1List;
	}
	
	public ModuleSelectionListViewPane getSelectedTerm2List() {
		return selectedterm2List;
	}
	
	public ModuleSelectionAddRemoveButtonPane getAddRemovePaneTerm1() {
		return AddRemoveButtonPane_Term1;
	}
	
	public ModuleSelectionAddRemoveButtonPane getAddRemovePaneTerm2() {
		return AddRemoveButtonPane_Term2;
	}
	
	public TextField getTerm1Credits() {
		return Term1Credits;
	}
	
	public TextField getTerm2Credits() {
		return Term2Credits;
	}
	
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
	
}

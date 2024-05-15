package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class OverviewSelectionPane extends VBox {
	private TextArea profileInfo;
	private TextArea selectedModules;
	private TextArea reservedModules;
	
	public OverviewSelectionPane() {
		this.setSpacing(15);
		
		profileInfo = new TextArea();
		selectedModules = new TextArea();
		reservedModules = new TextArea();
		
		this.getChildren().add(profileInfo);
		
		HBox container = new HBox(15);
		container.getChildren().addAll(selectedModules, reservedModules);
		this.getChildren().add(container);
	}
	
	public void profileInfoTextArea_SetText(String text) {
		profileInfo.setText(text);
	}
	
	public void selectedModulesTextArea_SetText(String text) {
		selectedModules.setText(text);
	}
	
	public void reservedModulesTextArea_SetText(String text) {
		reservedModules.setText(text);
	}

}

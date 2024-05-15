package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class FinalYearOptionsRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private ModuleSelectionPane msp;
	private ReservedModuleSelectionPane rm;
	private OverviewSelectionPane os;
	private FinalYearOptionsMenuBar mstmb;
	private TabPane tp;
	
	public FinalYearOptionsRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		// **************
		//  create panes
		// **************
		//this is the first form (Create Profile in the view)
		cspp = new CreateStudentProfilePane();
		//this is the second form (Select Modules in the view)
		msp = new ModuleSelectionPane();
		//this is the third form (Reserve modules in the view)
		rm = new ReservedModuleSelectionPane();
		//this is the fourth form (Overview selection in the view)
		os = new OverviewSelectionPane();
		
		//create tabs with panes added
		//this is the first tab which contains text "Create Profile" and content is the CreateStudentProfilePane (form)
		Tab t1 = new Tab("Create Profile", cspp);
		//this is the second tab which contains text "Select Modules" and content is the ModuleSelectionPane (form)
		Tab t2 = new Tab("Select Modules", msp);
		//this is the third tab which contains text "Reserve Modules" and content is the ReservedModuleSelectionPane (form)
		Tab t3 = new Tab("Reserve Modules", rm);
		//this is the fourth and final tab which contains text "Overview Selection" and content is the OverViewSelectionPane (form)
		Tab t4 = new Tab("Overview Selection", os);
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);
		
		//create menu bar
		mstmb = new FinalYearOptionsMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(mstmb); //the menu bar is set at top position
		this.setCenter(tp); //the tab pane is set at middle position
		
	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	
	public FinalYearOptionsMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}
	
	// msp module selection pane
	public ModuleSelectionPane getModuleSelectionPane() {
		return msp;
	}
	
	// rm reserved modules selection pane
	public ReservedModuleSelectionPane getReservedModuleSelectionPane() {
		return rm;
	}
	
	// os overview selection pane
	public OverviewSelectionPane getOverviewSelectionPane() {
		return os;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
	
}

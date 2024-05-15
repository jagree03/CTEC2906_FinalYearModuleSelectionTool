package view;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class ReservedModuleSelectionPane extends Accordion {
	
	private TermReservedModulePane t1m;
	private TermReservedModulePane t2m;
	private TitledPane t1, t2; //declared as fields so they can be accessed to expand in method below

	public ReservedModuleSelectionPane() {
		
		t1m = new TermReservedModulePane();
		t2m = new TermReservedModulePane();
		
		t1m.setUnselectedTermModulesLabel("Unselected Term 1 Modules");
		t1m.setReservedTermModulesLabel("Reserved Term 1 Modules");
		t1m.setMessageLabel("Reserve 30 credits worth of term 1 modules.");
		
		t2m.setUnselectedTermModulesLabel("Unselected Term 2 Modules");
		t2m.setReservedTermModulesLabel("Reserved Term 2 Modules");
		t2m.setMessageLabel("Reserve 30 credits worth of term 2 modules.");
		
		// create titled panes with panes added
		t1 = new TitledPane("Term 1 Modules", t1m);
		t2 = new TitledPane("Term 2 Modules", t2m);

		// add title panes to tab accordion
		
		this.getPanes().addAll(t1, t2);

	}
	
	public TermReservedModulePane getTermReservedModulePane_Term1() {
		return t1m;
	}
	
	public TermReservedModulePane getTermReservedModulePane_Term2() {
		return t2m;
	}
}

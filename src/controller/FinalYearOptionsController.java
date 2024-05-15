package controller;

import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import model.Course;
import model.RunPlan;
import model.Module;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.ModuleSelectionPane;
import view.OverviewSelectionPane;
import view.ReservedModuleSelectionPane;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;

public class FinalYearOptionsController {

	//fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb; // module selection tool menu bar
	private ModuleSelectionPane ms; // module selection pane
	private ReservedModuleSelectionPane rm; // reserved module selection pane
	private OverviewSelectionPane os;

	public FinalYearOptionsController(StudentProfile model, FinalYearOptionsRootPane view) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		ms = view.getModuleSelectionPane();
		rm = view.getReservedModuleSelectionPane();
		os = view.getOverviewSelectionPane();
		

		//add courses to combobox in create student profile pane using the buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());
		
		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		
		//attach an event handler to the menu item of the menu bar that shows about information
		mstmb.addAboutHandler(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) {
				Alert about = new Alert(AlertType.INFORMATION);
				about.setTitle("About the Program");
				about.setHeaderText("Final Year Module Selection Tool");
				about.setContentText("This tool allows a student to create a student profile, select modules and provide an overview of your student profile."
						+ "\n\n" + "COURSEWORK BY P2652829");
				about.show();
			}
		});
		
		//attach an event handler to the menu item of the menu bar that allows you to load student data from a file.
		mstmb.addLoadHandler(new MyFileHandler());
		
		// this makes the buttons functional by adding necessary AddHandlers, RemoveHandlers, ResetButton event handler and Submit button event handler.
		ms.getAddRemovePaneTerm1().addAddingHandler(new AddingHandler_Term1());
		ms.getAddRemovePaneTerm1().addRemoveHandler(new RemoveHandler_Term1());
		ms.getAddRemovePaneTerm2().addAddingHandler(new AddingHandler_Term2());
		ms.getAddRemovePaneTerm2().addRemoveHandler(new RemoveHandler_Term2());
		ms.addResetHandler(new ResetHandler());
		ms.addSubmitHandler(new SubmitHandler());
		
		// this makes the buttons of the Reserve Module selection form functional by adding the add button eventhandler, remove button event handler, confirm button event handler
		rm.getTermReservedModulePane_Term1().addAddButtonHandler(new AddReserveModuleHandler());
		rm.getTermReservedModulePane_Term1().addRemoveButtonHandler(new RemoveReserveModuleHandler());
		rm.getTermReservedModulePane_Term1().addConfirmButtonHandler(new ConfirmReserveModuleHandler());
		rm.getTermReservedModulePane_Term2().addAddButtonHandler(new AddReserveModuleHandler_term2());
		rm.getTermReservedModulePane_Term2().addRemoveButtonHandler(new RemoveReserveModuleHandler_term2());
		rm.getTermReservedModulePane_Term2().addConfirmButtonHandler(new ConfirmReserveModuleHandler_term2());
	}
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			//create the student profile so we set the model (StudentProfile) and call the set methods for each of the 
			//attributes of the StudentProfile, set them to the data inputted in the text fields of the CreateStudentProfile pane/form.
			
			// validation code
			/*
			 * it essentially retrieves the text in the textfields via the get methods of the CreateStudentProfilePane
			 * if they equal to blank string then set the message label to a basic error message
			 * if a string is valid and contains something, the boolean filled variable for that field will
			 * become true, all boolean variables must be true in order for the student profile to be created
			 * successfully + successfully setting the student's course for their profile.
			 */
			boolean NameFilled = false, PnumberFilled = false, EmailFilled = false;
			
			if (cspp.getStudentName().getFirstName().toString().equals("") || cspp.getStudentName().getFamilyName().toString().equals("")) {
				cspp.setMessageLabel("Student Name Invalid");
			} else {
				model.setStudentName(cspp.getStudentName());
				NameFilled = true;
			}
			
			if (cspp.getStudentPnumber().toString().equals("")) {
				cspp.setMessageLabel("Student P Number Required");
			} else {
				model.setStudentPnumber(cspp.getStudentPnumber());
				PnumberFilled = true;
			}
			
			if (cspp.getStudentEmail().equals("")) {
				cspp.setMessageLabel("Student Email Required");
			} else {
				model.setStudentEmail(cspp.getStudentEmail());
				EmailFilled = true;
			}
			
			if (cspp.getStudentDate() != null) {
				model.setSubmissionDate(cspp.getStudentDate());
			} else {
				cspp.setMessageLabel("Student Date Required");
			}
			
			if (NameFilled == true && PnumberFilled == true && EmailFilled == true) {
				model.setStudentCourse(cspp.getSelectedCourse());
				cspp.setMessageLabel("Profile Created Successfully");
				
				// now that the profile has been created succesfully.. now its time to populate the listview
				// call the add modules to list view method in the ModuleSelectionPane, pass it the 
				// student course that the student selected, the course contains a map of modules which
				// should be retrieved via getAllModulesOnCourse method. -> contained in a method called PopulateLists();
				PopulateLists();
			}
		}
	}


	//helper method - builds modules and course data and returns courses within an array
	private Course[] buildModulesAndCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, RunPlan.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, RunPlan.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, RunPlan.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, RunPlan.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, RunPlan.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, RunPlan.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, RunPlan.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, RunPlan.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, RunPlan.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, RunPlan.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, RunPlan.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, RunPlan.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, RunPlan.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, RunPlan.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, RunPlan.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, RunPlan.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, RunPlan.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, RunPlan.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
	public void PopulateLists() { // populate listview controls in Module Selection Pane (the 2nd form)
		ms.getTerm1List().populateUnselectedModuleList(model.getStudentCourse().getAllModulesOnCourse());
		ms.getTerm2List().populateUnselectedModuleList(model.getStudentCourse().getAllModulesOnCourse());
		
		for (Module m : model.getStudentCourse().getAllModulesOnCourse()) {
			if (m.getDelivery().toString().equals("YEAR_LONG") && m.isMandatory() == true) {
				ms.getSelectedYearLongList().addModuleToList(m);
			}
			if (m.getDelivery().toString().equals("TERM_1") && m.isMandatory() == true) {
				ms.getSelectedTerm1List().addModuleToList(m);
				ms.getTerm1List().removeModuleFromList(m);
			}
			if (m.getDelivery().toString().equals("TERM_2") && m.isMandatory() == true) {
				ms.getSelectedTerm2List().addModuleToList(m);
				ms.getTerm2List().removeModuleFromList(m);
			}
		}
		calculateSelectedModuleCredits();
	}
	
	public void PopulateReservedModuleLists() {
		Set<Module> SelectedModules = model.getAllSelectedModules();
		Collection<Module> allCourseModules = model.getStudentCourse().getAllModulesOnCourse();
		for (Module m: allCourseModules) {
			if (!SelectedModules.contains(m) && m.getDelivery().toString().equals("TERM_1")) {
				rm.getTermReservedModulePane_Term1().getUnselectedTermModules().addModuleToList(m);
			}
			if (!SelectedModules.contains(m) && m.getDelivery().toString().equals("TERM_2")) {
				rm.getTermReservedModulePane_Term2().getUnselectedTermModules().addModuleToList(m);
			}
		}	
	}
	
	public void PopulateOverviewSelection() {
		os.profileInfoTextArea_SetText("Name:" + model.getStudentName() + '\n'
				+ "PNo: " + model.getStudentPnumber() + '\n'
				+ "Email: " + model.getStudentEmail() + '\n'
				+ "Date: " + model.getSubmissionDate() + '\n' 
				+ "Course: " + model.getStudentCourse() + '\n');
		
		String selectedModules = "Selected Modules:" + '\n' 
				+ "==========" + '\n';
		
		
		for (Module m: model.getAllSelectedModules()) {
			selectedModules += "Module code: " + m.getModuleCode() + ',' + "Module name: " +m.getModuleName() + '\n'
			+ "Credits: " + m.getModuleCredits() + '\n'
			+ "Mandatory on your course?: " + m.isMandatory() + '\n'
			+ "Delivery: " + m.getDelivery() + '\n' + '\n';
		}
		os.selectedModulesTextArea_SetText(selectedModules);
		
		
		
		String reservedModules = "Reserved Modules:" + '\n' 
				+ "==========" + '\n';
		
		
		for (Module m: model.getAllReservedModules()) {
			reservedModules += "Module code: " + m.getModuleCode() + ',' + "Module name: " +m.getModuleName() + '\n'
			+ "Credits: " + m.getModuleCredits() + '\n'
			+ "Delivery: " + m.getDelivery() + '\n' + '\n';
		}
		os.reservedModulesTextArea_SetText(reservedModules);
	}
	
	
	private class MyFileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
	
			}
		}
	}
	
	public void calculateSelectedModuleCredits() {
		ObservableList<Module> list_term1 = ms.getSelectedTerm1List().getContents();
		ObservableList<Module> list_term2 = ms.getSelectedTerm2List().getContents();
		int term1Credits = 15, term2Credits = 15; // as the compulsory year long development project module is picked
		// for both software engineering and computer science students, it contributes 15 credits to each term, so it's best
		// to start at 15 initially for both credit holding variables.
		for (Module m: list_term1) {
			term1Credits += m.getModuleCredits();
		}
		for (Module m: list_term2) {
			term2Credits += m.getModuleCredits();
		}
		ms.getTerm1Credits().setText(String.valueOf(term1Credits));
		ms.getTerm2Credits().setText(String.valueOf(term2Credits));
		
	}
	
	private class AddingHandler_Term1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = ms.getTerm1List().getListView().getSelectionModel().getSelectedItem();
			ms.getSelectedTerm1List().addModuleToList(m);
			ms.getTerm1List().removeModuleFromList(m);
			calculateSelectedModuleCredits();
		}
	}
	
	private class RemoveHandler_Term1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = ms.getSelectedTerm1List().getListView().getSelectionModel().getSelectedItem();
			ms.getTerm1List().addModuleToList(m);
			ms.getSelectedTerm1List().removeModuleFromList(m);
			calculateSelectedModuleCredits();
		}
	}
	
	private class AddingHandler_Term2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = ms.getTerm2List().getListView().getSelectionModel().getSelectedItem();
			ms.getSelectedTerm2List().addModuleToList(m);
			ms.getTerm2List().removeModuleFromList(m);
			calculateSelectedModuleCredits();
		}
	}
	
	private class RemoveHandler_Term2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = ms.getSelectedTerm2List().getListView().getSelectionModel().getSelectedItem();
			ms.getTerm2List().addModuleToList(m);
			ms.getSelectedTerm2List().removeModuleFromList(m);
			calculateSelectedModuleCredits();
		}
	}
	
	private class ResetHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			ms.getTerm1List().clearModules();
			ms.getTerm2List().clearModules();
			ms.getSelectedTerm1List().clearModules();
			ms.getSelectedTerm2List().clearModules();
			ms.getSelectedYearLongList().clearModules();
			PopulateLists();
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Resetted module selection form");
			alert.show();
		}
	}
	
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			int term1creds = Integer.parseInt(ms.getTerm1Credits().getText());
			int term2creds = Integer.parseInt(ms.getTerm2Credits().getText());
			boolean validated = false;
			if (term1creds < 60 || term1creds > 60) {
				Alert error = new Alert(AlertType.ERROR, "You may only select 60 credits per term");
				error.show();
			} else if (term2creds < 60 || term2creds > 60) {
				Alert error = new Alert(AlertType.ERROR, "You may only select 60 credits per term");
				error.show();
			} else {
				validated = true;
			}
			
			if (validated == true) {
				for (Module m : ms.getSelectedTerm1List().getContents()) {
					model.addSelectedModule(m);
				}
				for (Module m : ms.getSelectedTerm2List().getContents()) {
					model.addSelectedModule(m);
				}
				for (Module m : ms.getSelectedYearLongList().getContents()) {
					model.addSelectedModule(m);
				}	
				Alert succeed = new Alert(AlertType.INFORMATION, "Submitted.");
				succeed.show();
				PopulateReservedModuleLists();
			}
			
		}
	}
	
	private class AddReserveModuleHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = rm.getTermReservedModulePane_Term1().getUnselectedTermModules().getListView().getSelectionModel().getSelectedItem();
			rm.getTermReservedModulePane_Term1().getReservedTermModules().getContents().add(m);
			rm.getTermReservedModulePane_Term1().getUnselectedTermModules().getContents().remove(m);
		}
	}
	
	private class RemoveReserveModuleHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = rm.getTermReservedModulePane_Term1().getReservedTermModules().getListView().getSelectionModel().getSelectedItem();
			rm.getTermReservedModulePane_Term1().getUnselectedTermModules().getContents().add(m);
			rm.getTermReservedModulePane_Term1().getReservedTermModules().getContents().remove(m);
		}
	}
	
	private class ConfirmReserveModuleHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			int creditsAmount = 0;
			for (Module m: rm.getTermReservedModulePane_Term1().getReservedTermModules().getContents()) {
				creditsAmount += m.getModuleCredits();
				model.addReservedModule(m);
				
				if (creditsAmount > 30 || creditsAmount < 30) {
					/*
					Alert error = new Alert(AlertType.WARNING, "You can only reserve 30 credits worth of modules for this term.");
					error.show();
					*/
				} else {
					
				}
				
			}
			Alert msg = new Alert(AlertType.INFORMATION, "Reserved Modules Confirmed");
			PopulateOverviewSelection();
		}
	}
	
	private class AddReserveModuleHandler_term2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = rm.getTermReservedModulePane_Term2().getUnselectedTermModules().getListView().getSelectionModel().getSelectedItem();
			rm.getTermReservedModulePane_Term2().getReservedTermModules().getContents().add(m);
			rm.getTermReservedModulePane_Term2().getUnselectedTermModules().getContents().remove(m);
		}
	}
	
	private class RemoveReserveModuleHandler_term2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = rm.getTermReservedModulePane_Term2().getReservedTermModules().getListView().getSelectionModel().getSelectedItem();
			rm.getTermReservedModulePane_Term2().getUnselectedTermModules().getContents().add(m);
			rm.getTermReservedModulePane_Term2().getReservedTermModules().getContents().remove(m);
		}
	}
	
	private class ConfirmReserveModuleHandler_term2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			int creditsAmount = 0;
			for (Module m: rm.getTermReservedModulePane_Term2().getReservedTermModules().getContents()) {
				creditsAmount += m.getModuleCredits();
				model.addReservedModule(m);
				/*
				if (creditsAmount > 30 || creditsAmount < 30) {
					Alert error = new Alert(AlertType.WARNING, "You can only reserve 30 credits worth of modules for this term.");
					error.show();
				} else {
					
				}
				*/
			}
			Alert msg = new Alert(AlertType.INFORMATION, "Reserved Modules Confirmed");
			PopulateOverviewSelection();
		}
	}
	
	
	
	
	
	
}

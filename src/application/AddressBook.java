// Name: Caitlin Lamirez
// Date: 1/29/2023
// Program: Address Book GUI
package application;

import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AddressBook extends Application{

	// Declare private Stages
	private Stage stage;
	private Scene sceneMain;
	private Scene sceneCreate;
	private Scene sceneView; 
	private Scene sceneEdit;
	
	// Declare an Observable List of Contact objects
	private ObservableList<Contact> contactsList = FXCollections.observableArrayList(
			new Contact ("Caitlin", "Lamirez", "708-222-5555", "lamirezc@dupage.edu", "222 W. 100th St.", "Oak Lawn", "IL", "60453", "USA"),
			new Contact ("Jane", "Doe", "708-111-4444", "doejane@dupage.edu", "111 W. 200th St.", "Chicago", "IL", "60602", "USA"),
			new Contact ("John", "Smith", "708-333-1111", "smithjohn@dupage.edu", "777 W. 300th St.", "Oak Lawn", "IL", "60453", "USA")
			);
	
	// Declare a TableView of Contact objects
	private TableView<Contact> contactsTable = new TableView<Contact>();
	
	
	public static void main(String[] args) {
		// Launch the application
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		// Get Scenes
		sceneMain = getSceneMain();
		sceneCreate = getSceneCreate();
		
		// Places main scene on stage
		stage.setTitle("Address Book App");
		stage.setScene(sceneMain);
		stage.show();
	}
	
	// getSceneMain: returns the Scene of the main page when the app opens. 
	@SuppressWarnings("unchecked")
	private Scene getSceneMain() {
		// Create Panes
		VBox menuPane = new VBox();
		VBox titlePane = new VBox();
		HBox searchPane = new HBox();
		HBox optionsPane = new HBox();
		VBox tablePane = new VBox();
		VBox mainPane = new VBox(); 
		
		/*----------------------- Menu Pane ---------------------------*/
		// Declare Controls
		Menu fileMenu = new Menu("File");
		MenuBar menuBar = new MenuBar();
		RadioMenuItem choiceOpen = new RadioMenuItem("Open File...");
		RadioMenuItem choiceSave = new RadioMenuItem("Save");
		RadioMenuItem choiceExit = new RadioMenuItem("Exit");
		ToggleGroup toggleGroup = new ToggleGroup();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open an Address Book/Contact File");
		
		// Add choices to a ToggleGroup and a Menu
		toggleGroup.getToggles().addAll(choiceOpen, choiceSave, choiceExit);
		fileMenu.getItems().addAll(choiceOpen, choiceSave, choiceExit);
		
		// Add event handling to Open File RadioMenuItem
		choiceOpen.setOnAction(e -> {
			// Opens a file chooser dialog box
			fileChooser.showOpenDialog(stage);
			// Unselects choiceOpen
			choiceOpen.setSelected(false);
		});
		
		// Create alert dialog box when user clicks the Save RadioMenuItem
		Alert alertSave = new Alert(AlertType.CONFIRMATION);
		ButtonType btYesSave = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType btCancelSave = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alertSave.setTitle("Save Confirmation");
		alertSave.setHeaderText("Save changes to Address Book?");
		alertSave.setContentText("Are you sure you would like save changes to Address Book?");
		alertSave.getDialogPane().getButtonTypes().setAll(btYesSave, btCancelSave);
		
		// Adds event handling to the Save Button
		choiceSave.setOnAction(e -> 
		{
			// Shows dialog box
			alertSave.showAndWait();
			// Unselects choiceSave
			choiceSave.setSelected(false);
		});
		
		// Create alert dialog box when user clicks the Exit RadioMenuItem
		Alert alertExit = new Alert(AlertType.CONFIRMATION);
		ButtonType btYesExit = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType btCancelExit = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alertExit.setTitle("Exit Confirmation");
		alertExit.setHeaderText("Exit application?");
		alertExit.setContentText("Are you sure you would like to exit the application?");
		alertExit.getDialogPane().getButtonTypes().setAll(btYesExit, btCancelExit);
		
		// Add event handling to the Exit Button
		choiceExit.setOnAction(e -> 
		{
			Optional<ButtonType> result = alertExit.showAndWait();
			// If user clicks "Yes", it exits the App
			if (result.get() == btYesExit)
				stage.close();
			
			// Unselects choiceExit
			choiceExit.setSelected(false);
		});
		
		// Add a menu to the MenuBar object
		menuBar.getMenus().add(fileMenu);
		menuPane.getChildren().addAll(menuBar);
		
		/*----------------------- Title Pane ---------------------------*/
		// Declare Label for title
		Label lblTitle = new Label("Address Book"); 
		
		// Set lblTitle properties
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Sets font
		lblTitle.setPadding(new Insets(7)); // Sets padding

		// Add to Pane
		titlePane.getChildren().addAll(lblTitle);
		titlePane.setAlignment(Pos.CENTER);
		
		/*----------------------- Search Pane ---------------------------*/
		// Declare controls
		TextField tfSearch = new TextField();
		Button btnSearch = new Button("Search");
		FilteredList<Contact> filteredContacts = new FilteredList<>(contactsList, b-> true);
		tfSearch.setPrefWidth(250);
		
		/* When the btnSearch is clicked, it will search through the contacts 
		 * and save the results to a FilteredList */
		btnSearch.setOnAction(e -> 
		{
			filteredContacts.setPredicate(contact -> {
			if (tfSearch.getText().isEmpty() || tfSearch.getText() == null) {
				return true;
			}
			String lowerSearchVal = tfSearch.getText().toLowerCase();
			
			if (contact.getFirst().toLowerCase().indexOf(lowerSearchVal) != -1)
				return true;
			else if (contact.getLast().toLowerCase().indexOf(lowerSearchVal) != -1)
				return true;
			else 
				return false;
			});
			
		// Set the contactsTable values to the filtered list of contacts
		contactsTable.setItems(filteredContacts);
		});
		
		// Set Pane properties
		searchPane.getChildren().addAll(tfSearch, btnSearch);
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setSpacing(10);
		searchPane.setPadding(new Insets(5));
		
		/*----------------------- Options Pane ---------------------------*/
		// Declare controls
		Button btnCreate = new Button("Create Contact");
		Button btnView = new Button("View Contact");
		
		// Add event handling to Buttons
		btnCreate.setOnAction(e -> stage.setScene(sceneCreate));
		btnView.setOnAction(e -> {
			Contact con = contactsTable.getSelectionModel().getSelectedItem();
			// If there is no Contact selected
			if (con != null)
				stage.setScene(getSceneView(con));
		});
		
		// Set Pane properties
		optionsPane.getChildren().addAll(btnCreate, btnView);
		optionsPane.setAlignment(Pos.CENTER);
		optionsPane.setSpacing(10);
		optionsPane.setPadding(new Insets(5));
		
		/*----------------------- Table Pane ---------------------------*/
		contactsTable.setEditable(true); // makes TableView editable
		
		// Add columns and declare their property names
		TableColumn<Contact, String> colFirst = new TableColumn<Contact, String>("First Name");
		TableColumn<Contact, String> colLast = new TableColumn<Contact, String>("Last Name");
		colFirst.setCellValueFactory(new PropertyValueFactory<>("first"));
		colLast.setCellValueFactory(new PropertyValueFactory<>("last"));
		
		// Set TableView proprties
		contactsTable.setItems(contactsList);
		contactsTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		contactsTable.getColumns().addAll(colFirst, colLast);
		
		// Set Pane Proprties
		tablePane.setPadding(new Insets(5)); // Sets padding
		tablePane.getChildren().addAll(contactsTable);
		
		
		/*----------------------- Main Pane ---------------------------*/
		// Add all Panes to the Main Pane
		mainPane.getChildren().addAll(menuPane, titlePane, searchPane, optionsPane, tablePane);
		// Return sceneMain
		sceneMain = new Scene(mainPane, 400, 475);
		return sceneMain;
	}
	
	// getSceneCreate: returns the Scene of the "Create a Contact" page.
	private Scene getSceneCreate() {
		// Create panes
		VBox mainPane = new VBox();
		VBox titlePane = new VBox();
		GridPane formPane = new GridPane();
		HBox buttonsPane = new HBox();
		
		/*----------------------- Title Pane ---------------------------*/
		// Declare Label for title
		Label lblTitle = new Label("Create a Contact"); 
		// Set lblTitle properties
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Sets font
		lblTitle.setPadding(new Insets(20)); // Sets padding
		
		// Add to Pane
		titlePane.getChildren().addAll(lblTitle);
		titlePane.setAlignment(Pos.CENTER);
		
		/*----------------------- Form Pane ---------------------------*/
		// Declare Labels
		Label lblFirst = new Label("First Name");
		Label lblLast = new Label("Last Name");
		Label lblPhone = new Label("Phone Number");
		Label lblEmail = new Label("Email Address");
		Label lblStreet = new Label("Street Address");
		Label lblCity = new Label("City");
		Label lblState = new Label("State");
		Label lblZip = new Label("Zip Code");
		Label lblCountry = new Label("Country");
		Label lblHomeAddress = new Label("Home Address");
		
		// Declare TextFields
		TextField tfFirst = new TextField();
		TextField tfLast = new TextField();
		TextField tfPhone = new TextField();
		TextField tfEmail = new TextField();
		TextField tfStreet = new TextField();
		TextField tfCity = new TextField();
		TextField tfState = new TextField();
		TextField tfZip = new TextField(); 
		TextField tfCountry = new TextField();
		
		// Make all labels bold
		lblFirst.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblLast.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblPhone.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblEmail.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblStreet.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCity.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblState.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblZip.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCountry.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		// Make home address label underlined and bold
		lblHomeAddress.setUnderline(true);
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 13));

		tfStreet.setPrefColumnCount(15);
		
		// Add all labels to the form
		formPane.add(lblFirst, 0, 0);
		formPane.add(lblLast, 0, 1);
		formPane.add(lblPhone, 0, 2);
		formPane.add(lblEmail, 0, 3);
		formPane.add(lblHomeAddress, 0, 4);
		formPane.add(lblStreet, 0, 5);
		formPane.add(lblCity, 0, 6);
		formPane.add(lblState, 0, 7);
		formPane.add(lblZip, 0, 8);
		formPane.add(lblCountry, 0, 9);
		
		// Add all text fields
		formPane.add(tfFirst, 1, 0);
		formPane.add(tfLast, 1, 1);
		formPane.add(tfPhone, 1, 2);
		formPane.add(tfEmail, 1, 3);
		formPane.add(tfStreet, 1, 5);
		formPane.add(tfCity, 1, 6);
		formPane.add(tfState, 1, 7);
		formPane.add(tfZip, 1, 8);
		formPane.add(tfCountry, 1, 9);
		
		formPane.setAlignment(Pos.CENTER);
		formPane.setHgap(75); // Set horizontal gap
		formPane.setVgap(10); // Set vertical gap
		
		/*----------------------- Form Pane ---------------------------*/
		// Declare Buttons
		Button btnBack = new Button("Back");
		Button btnSave = new Button("Save Contact");
		
		// Adds event handling to btnBack
		btnBack.setOnAction(e -> {
			// Clear all fields
			tfFirst.clear();
			tfLast.clear();
			tfPhone.clear();
			tfEmail.clear();
			tfStreet.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfCountry.clear();
			
			// Switches back to main scene
			stage.setScene(sceneMain);
			
		});
		
		// Adds event handling to btnSave
		btnSave.setOnAction(e -> {
			// Gets all user's input
			String first = tfFirst.getText();
			String last = tfLast.getText();
			String phone = tfPhone.getText();
			String email = tfEmail.getText();
			String street = tfStreet.getText();
			String city = tfCity.getText();
			String state = tfState.getText();
			String zip = tfZip.getText();
			String country = tfCountry.getText();
			
			// Clears all TextFields
			tfFirst.clear();
			tfLast.clear();
			tfPhone.clear();
			tfEmail.clear();
			tfStreet.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfCountry.clear();
			
			// Creates new Contact object with user's input
			Contact c = new Contact(first, last, phone, email, street, city, state, zip,country);
			// Adds it to Observable List
			contactsList.add(c);
			
			// Switches back to main scene
			stage.setScene(sceneMain);
		});
		
		// Adds to Pane and set Pane proprties
		buttonsPane.getChildren().addAll(btnBack, btnSave);
		buttonsPane.setSpacing(10);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setPadding(new Insets(25));
		
		/*----------------------- Main Pane ---------------------------*/
		// Adds all Panes to mainPane
		mainPane.getChildren().addAll(titlePane, formPane, buttonsPane);
		
		// Return sceneCreate
		sceneCreate = new Scene(mainPane, 400, 475);
		return sceneCreate;
	}
	
	// getSceneView: returns the Scene of the "View Contact Information" page.
	private Scene getSceneView(Contact con) {
		// Create Panes
		VBox mainPane = new VBox();
		VBox titlePane = new VBox();
		GridPane infoPane = new GridPane();
		HBox buttonsPane = new HBox();
		HBox prevNextPane = new HBox();
		
		/*----------------------- Title Pane ---------------------------*/
		// Declare title Label
		Label lblTitle = new Label(con.getFirst() + " " + con.getLast()); 
		// Set title Label properties
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Sets font
		lblTitle.setPadding(new Insets(20)); // Sets padding
		
		// Add to Pane
		titlePane.getChildren().addAll(lblTitle);
		titlePane.setAlignment(Pos.CENTER);
		
		/*----------------------- Info Pane ---------------------------*/
		// Declare Labels
		Label lblFirst = new Label("First Name");
		Label lblLast = new Label("Last Name");
		Label lblPhone = new Label("Phone Number");
		Label lblEmail = new Label("Email Address");
		Label lblStreet = new Label("Street Address");
		Label lblCity = new Label("City");
		Label lblState = new Label("State");
		Label lblZip = new Label("Zip Code");
		Label lblCountry = new Label("Country");
		Label lblHomeAddress = new Label("Home Address");
		
		// Declare Label properties
		lblFirst.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblLast.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblPhone.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblEmail.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblStreet.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCity.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblState.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblZip.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCountry.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblHomeAddress.setUnderline(true);
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		
		// Add all labels for categories
		infoPane.add(lblFirst, 0, 0);
		infoPane.add(lblLast, 0, 1);
		infoPane.add(lblPhone, 0, 2);
		infoPane.add(lblEmail, 0, 3);
		infoPane.add(lblHomeAddress, 0, 4);
		infoPane.add(lblStreet, 0, 5);
		infoPane.add(lblCity, 0, 6);
		infoPane.add(lblState, 0, 7);
		infoPane.add(lblZip, 0, 8);
		infoPane.add(lblCountry, 0, 9);
		
		// Add all labels for Contact's information
		infoPane.add(new Label(con.getFirst()), 1, 0);
		infoPane.add(new Label(con.getLast()), 1, 1);
		infoPane.add(new Label(con.getPhone()), 1, 2);
		infoPane.add(new Label(con.getEmail()), 1, 3);
		infoPane.add(new Label(con.getStreet()), 1, 5);
		infoPane.add(new Label(con.getCity()), 1, 6);
		infoPane.add(new Label(con.getState()), 1, 7);
		infoPane.add(new Label(con.getZip()), 1, 8);
		infoPane.add(new Label(con.getCountry()), 1, 9);
		
		// Set Pane properties
		infoPane.setAlignment(Pos.CENTER);
		infoPane.setHgap(100); // Set horizontal gap
		infoPane.setVgap(10); // Set vertical gap
		infoPane.setPadding(new Insets(5));
		
		/*----------------------- Buttons Pane ---------------------------*/
		// Declare Buttons
		Button btnBack = new Button("Back");
		Button btnEdit = new Button("Edit");
		Button btnDelete = new Button("Delete");
		
		// Adds event handling to Buttons
		btnBack.setOnAction(e -> stage.setScene(sceneMain));
		btnEdit.setOnAction(e -> stage.setScene(getSceneEdit(con)));
		
		// Create alert dialog box when user clicks the delete button
		Alert alertDelete = new Alert(AlertType.CONFIRMATION);
		ButtonType btYes = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType btCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alertDelete.setTitle("Contact Deletion Confirmation");
		alertDelete.setHeaderText("Delete " + con.getFirst() + " " + con.getLast() + "?");
		alertDelete.setContentText("Are you sure you would like to delete this contact?");
		alertDelete.getDialogPane().getButtonTypes().setAll(btYes, btCancel);
		
		// Adds event handling to the Delete Button
		btnDelete.setOnAction(e -> {
			Optional<ButtonType> result = alertDelete.showAndWait();
			if (result.get() == btYes) {
				contactsList.remove(con);
				stage.setScene(sceneMain);
			}
		});
		
		// Set Pane properties and add to Pane
		btnDelete.setStyle("-fx-background-color: #ff6d7a; -fx-border-color: grey;");
		buttonsPane.getChildren().addAll(btnBack, btnEdit, btnDelete);
		buttonsPane.setSpacing(10);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setPadding(new Insets(10));
		
		/*----------------------- Previous/Next Pane ---------------------------*/
		// Declare Buttons
		Button btnPrev = new Button("Previous Contact");
		Button btnNext = new Button("Next Contact");
		
		// If user clicks "Previous Contact" Button, it goes to previous contact if there is one
		btnPrev.setOnAction(e -> {
			int prevIndex = contactsList.indexOf(con) - 1 ;
			if (prevIndex > -1)
				stage.setScene(getSceneView(contactsList.get(prevIndex)));
		});
		
		// If user clicks "Next Contact" Button, it goes to next contact if there is one
		btnNext.setOnAction(e -> {
			int nextIndex = contactsList.indexOf(con) + 1 ;
			if (nextIndex < contactsList.size())
				stage.setScene(getSceneView(contactsList.get(nextIndex)));
		});
		
		// Set Pane properties and add to Pane
		prevNextPane.getChildren().addAll(btnPrev, btnNext);
		prevNextPane.setSpacing(10);
		prevNextPane.setAlignment(Pos.CENTER);
		
		/*----------------------- Main Pane ---------------------------*/
		// Adds all Panes to Main Pane
		mainPane.getChildren().addAll(titlePane, infoPane, buttonsPane, prevNextPane);
		
		// Returns sceneView
		sceneView = new Scene(mainPane, 400, 475);
		return sceneView;
	}
	
	// getSceneEdit: returns the Scene of the "Edit Contact Information" page.
	private Scene getSceneEdit(Contact con) {
		// Create panes
		VBox mainPane = new VBox();
		VBox titlePane = new VBox();
		GridPane formPane = new GridPane();
		HBox buttonsPane = new HBox();
		
		/*----------------------- Title Pane ---------------------------*/
		// Declare Label for title
		Label lblTitle = new Label("Update Contact Information"); 
		// Set lblTitle properties
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Sets font
		lblTitle.setPadding(new Insets(20)); // Sets padding
		
		// Add to Pane
		titlePane.getChildren().addAll(lblTitle);
		titlePane.setAlignment(Pos.CENTER);
		
		/*----------------------- Form Pane ---------------------------*/
		// Declare Labels
		Label lblFirst = new Label("First Name");
		Label lblLast = new Label("Last Name");
		Label lblPhone = new Label("Phone Number");
		Label lblEmail = new Label("Email Address");
		Label lblStreet = new Label("Street Address");
		Label lblCity = new Label("City");
		Label lblState = new Label("State");
		Label lblZip = new Label("Zip Code");
		Label lblCountry = new Label("Country");
		Label lblHomeAddress = new Label("Home Address");
		
		// Declare TextFields
		TextField tfFirst = new TextField();
		TextField tfLast = new TextField();
		TextField tfPhone = new TextField();
		TextField tfEmail = new TextField();
		TextField tfStreet = new TextField();
		TextField tfCity = new TextField();
		TextField tfState = new TextField();
		TextField tfZip = new TextField(); 
		TextField tfCountry = new TextField();
		
		// Make all Labels bold
		lblFirst.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblLast.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblPhone.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblEmail.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblStreet.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCity.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblState.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblZip.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		lblCountry.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		// Make Home Address label underlined and bold
		lblHomeAddress.setUnderline(true);
		lblHomeAddress.setFont(Font.font("Arial", FontWeight.BOLD, 13));

		tfStreet.setPrefColumnCount(15);
		
		// Set all text fields to current contact's information
		tfFirst.setText(con.getFirst());
		tfLast.setText(con.getLast());
		tfPhone.setText(con.getPhone());
		tfEmail.setText(con.getEmail());
		tfStreet.setText(con.getStreet());
		tfCity.setText(con.getCity());
		tfState.setText(con.getState());
		tfZip.setText(con.getZip());
		tfCountry.setText(con.getCountry());
		
		// Add all labels to the form
		formPane.add(lblFirst, 0, 0);
		formPane.add(lblLast, 0, 1);
		formPane.add(lblPhone, 0, 2);
		formPane.add(lblEmail, 0, 3);
		formPane.add(lblHomeAddress, 0, 4);
		formPane.add(lblStreet, 0, 5);
		formPane.add(lblCity, 0, 6);
		formPane.add(lblState, 0, 7);
		formPane.add(lblZip, 0, 8);
		formPane.add(lblCountry, 0, 9);
		
		// Add all text fields
		formPane.add(tfFirst, 1, 0);
		formPane.add(tfLast, 1, 1);
		formPane.add(tfPhone, 1, 2);
		formPane.add(tfEmail, 1, 3);
		formPane.add(tfStreet, 1, 5);
		formPane.add(tfCity, 1, 6);
		formPane.add(tfState, 1, 7);
		formPane.add(tfZip, 1, 8);
		formPane.add(tfCountry, 1, 9);
		
		// Set Pane properties
		formPane.setAlignment(Pos.CENTER);
		formPane.setHgap(75); // Set horizontal gap
		formPane.setVgap(10); // Set vertical gap
		
		/*----------------------- Buttons Pane ---------------------------*/
		// Declare Buttons
		Button btnBack = new Button("Back");
		Button btnSave = new Button("Save Contact");
		
		// Add event handling to btnBack
		btnBack.setOnAction(e -> {
			// Clear all fields
			tfFirst.clear();
			tfLast.clear();
			tfPhone.clear();
			tfEmail.clear();
			tfStreet.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfCountry.clear();
			
			// Switches back to main scene
			stage.setScene(getSceneView(con));
			
		});
		// Add event handling to btnSave
		btnSave.setOnAction(e -> {
			// Uses Contact's setter methods to mutate fields
			con.setFirst(tfFirst.getText());
			con.setLast(tfLast.getText());
			con.setPhone(tfPhone.getText());
			con.setEmail(tfEmail.getText());
			con.setStreet(tfStreet.getText());
			con.setCity(tfCity.getText());
			con.setState(tfState.getText());
			con.setZip(tfZip.getText());
			con.setCountry(tfCountry.getText());
			
			// Clear TextFields
			tfFirst.clear();
			tfLast.clear();
			tfPhone.clear();
			tfEmail.clear();
			tfStreet.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfCountry.clear();
			
			// Replace contact with changed contact
			int conIndex = contactsList.indexOf(con);
			contactsList.set(conIndex, con);
			
			// Switches back to sceneView with updated information
			stage.setScene(getSceneView(con));
		});
		
		// Adds to Pane and set Pane properties
		buttonsPane.getChildren().addAll(btnBack, btnSave);
		buttonsPane.setSpacing(10);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setPadding(new Insets(25));
		
		/*----------------------- Main Pane ---------------------------*/
		// Add all Panes to mainPane
		mainPane.getChildren().addAll(titlePane, formPane, buttonsPane);
		
		// Return sceneEdit
		sceneEdit = new Scene(mainPane, 400, 475);
		return sceneEdit;
	}
	
}

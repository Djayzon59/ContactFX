package fr.jason.contactfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Personne;
import outils.DateOutils;
import pdf.PrintPDF;

import java.io.IOException;

public class PersonneController {

    @FXML
    private TableView<Personne> personTable;
    @FXML
    private TableColumn<Personne, String> firstNameColumn;
    @FXML
    private TableColumn<Personne, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;



    private MainApp mainApp;



    public PersonneController() {
    }


    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        showPersoneDetails(null);
        personTable.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> showPersoneDetails(newValue));
        personTable.widthProperty().addListener((observableValue, oldValue, newValue) -> ajustColumns(newValue));
    }

    private void ajustColumns (Number newValue){
        firstNameColumn.setPrefWidth(newValue.doubleValue() * 0.5);
        lastNameColumn.setPrefWidth(newValue.doubleValue() * 0.5);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getContactBean().getAllPersonnes());
    }

    private void showPersoneDetails(Personne personne) {
        if (personne != null) {
            firstNameLabel.setText(personne.getFirstName());
            lastNameLabel.setText(personne.getLastName());
            streetLabel.setText(personne.getStreet());
            postalCodeLabel.setText(Integer.toString(personne.getPostalCode()));
            cityLabel.setText(personne.getCity());
            birthdayLabel.setText(DateOutils.format(personne.getBirthday()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void deletePersonne() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            personTable.getItems().remove(selectedIndex);
            mainApp.getContactBean().markFileAsModified();
        }

        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Aucune selection");
            alert.setHeaderText("Pas de contact sélectioné");
            alert.setContentText("Veuillez sélectionner un contact dans la liste");
            alert.showAndWait();
        }
    }
    @FXML
    private void ajouterNewPerson() {
        Personne tempPersonne = new Personne();
        boolean okClicked = mainApp.showPersonEditDialog(tempPersonne);
        if (okClicked) {
            mainApp.getContactBean().getAllPersonnes().add(tempPersonne);
            mainApp.getContactBean().markFileAsModified();
        }
    }

    @FXML
    private void modifierPersonne() {
        Personne selectedPersonne = personTable.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPersonne);
            if (okClicked) {
                showPersoneDetails(selectedPersonne);
                mainApp.getContactBean().markFileAsModified();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void openPdf() throws IOException {
        PrintPDF printPDF = new PrintPDF(mainApp.getContactBean());
        printPDF.openPdf();
    }

    


}

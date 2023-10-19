package fr.jason.contactfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Personne;
import outils.DateOutils;



public class PersonneEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    private Stage dialogStage;
    private Personne personne;
    protected boolean okClicked = false;


    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void pushCancel(){
        dialogStage.close();
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
        firstNameField.setText(personne.getFirstName());
        lastNameField.setText(personne.getLastName());
        streetField.setText(personne.getStreet());
        postalCodeField.setText(Integer.toString(personne.getPostalCode()));
        cityField.setText(personne.getCity());
        birthdayField.setText(DateOutils.format(personne.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    @FXML
    private void isPushOK(){
        if(isInputValid()){
            personne.setFirstName(firstNameField.getText());
            personne.setLastName(lastNameField.getText());
            personne.setStreet(streetField.getText());
            personne.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            personne.setCity(cityField.getText());
            personne.setBirthday(DateOutils.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        } else {

            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Saisie invalide ! (doit-être un numéro)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Saisie invalide !\n";
        } else {
            if (!DateOutils.isDateValid((birthdayField.getText()))) {
                errorMessage += "Date invalide. format: dd.mm.yyyy !\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;

        }
    }


}

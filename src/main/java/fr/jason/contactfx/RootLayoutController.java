package fr.jason.contactfx;

import bean.ContactBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;


public class RootLayoutController {

    @FXML
    private MenuItem itemOpenFile;
    @FXML
    private MenuItem itemHome;
    @FXML
    private MenuItem itemClose;
    @FXML
    private MenuItem itemSave;
    @FXML
    private Menu menuEdit;
    @FXML
    private Menu menuHelp;
    private MainApp mainApp;


    public RootLayoutController() {
    }


    @FXML
    private void initialize() {
        menuEdit.setDisable(true);
        menuHelp.setDisable(true);
        itemHome.setDisable(true);
        itemSave.setDisable(true);
        itemClose.setDisable(true);
    }

    @FXML
    private void onItemSaveClicked() {
        mainApp.getContactBean().save();
        mainApp.getContactBean().markFileAsSaved();
    }

    @FXML
    private void onItemHomeClicked() {
        if (isFileModifiedButNotSave()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Close");
            alert.setHeaderText("Vous êtes sur le point de fermer l'application");
            alert.setContentText("Voulez-vous enregistrer les modifications ? ");
            ButtonType enregistrerButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.APPLY);
            ButtonType fermerButton = new ButtonType("Fermer", ButtonBar.ButtonData.NO);
            ButtonType annulerButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(enregistrerButton, fermerButton, annulerButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && !result.get().getText().equals("Annuler")) {
                switch (result.get().getText()) {
                    case "Enregistrer" -> mainApp.getContactBean().save();
                    case "Fermer" -> {
                        mainApp.initRootLayout();
                        mainApp.getPrimaryStage().setTitle("Contacts App");
                    }
                }
            }
        } else {
            mainApp.initRootLayout();
            mainApp.getPrimaryStage().setTitle("Contacts App");
        }

    }

    @FXML
    private void onItemCLoseClicked() {
        if (isFileModifiedButNotSave()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Close");
            alert.setHeaderText("Vous êtes sur le point de fermer l'application");
            alert.setContentText("Voulez-vous enregistrer les modifications ? ");
            ButtonType enregistrerButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.APPLY);
            ButtonType fermerButton = new ButtonType("Fermer", ButtonBar.ButtonData.NO);
            ButtonType annulerButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(enregistrerButton, fermerButton, annulerButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && !result.get().getText().equals("Annuler")) {
                switch (result.get().getText()) {
                    case "Enregistrer" -> mainApp.getContactBean().save();
                    case "Fermer" -> mainApp.getPrimaryStage().close();
                }
                mainApp.getPrimaryStage().close();
            }
        } else {
            mainApp.getPrimaryStage().close();
        }
    }

    @FXML
    private void onOpenFileClicked() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load file from windows");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = chooser.showOpenDialog(mainApp.getPrimaryStage());
        if (selectedFile != null) {
            mainApp.getPrimaryStage().setTitle(String.format("Contacts App - %s",
                    selectedFile.getAbsolutePath()));
            itemHome.setDisable(false);
            itemClose.setDisable(false);
            mainApp.setContactBean(new ContactBean(selectedFile));
            mainApp.showPersonOverview();
            mainApp.getContactBean().isFileModifiedProperty().addListener((observable, oldValue, newValue) -> {
                itemSave.setDisable(!newValue);
            });
        }
    }

    private boolean isFileModifiedButNotSave() {
        return mainApp.getContactBean().isIsFileModified() == true && mainApp.getContactBean().isIsFileSaved() == false;
    }

    public MenuItem getItemSave() {
        return itemSave;
    }

    public MenuItem getItemHome() {
        return itemHome;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}


package fr.jason.contactfx;

import bean.ContactBean;
import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Personne;

public class MainApp extends Application {

    @FXML
    private BorderPane rootLayout;
    private Stage primaryStage;
    private ContactBean contactBean;



    public MainApp() {
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Contacts App");
        this.primaryStage.setHeight(650);
        this.primaryStage.setWidth(900);
        this.primaryStage.getIcons().add(new Image("C:\\Env\\intelli J-workspace\\contactFX\\src\\main\\resources\\images\\contact.png"));
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController rootController = loader.getController();
            rootController.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("root.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("personneOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            Scene scene = primaryStage.getScene();
            PersonneController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public boolean showPersonEditDialog(Personne personne) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("personneEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonneEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPersonne(personne);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ContactBean getContactBean() {
        return contactBean;
    }

    public void setContactBean(ContactBean contactBean) {
        this.contactBean = contactBean;
    }


    public static void main(String[] args) {
        launch();
    }
}
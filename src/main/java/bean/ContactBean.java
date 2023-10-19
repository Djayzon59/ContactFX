package bean;

import dao.ContactDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Personne;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactBean {

    private File file;
    private ObservableList<Personne> allPersonnes;
    private BooleanProperty isFileModified;
    private BooleanProperty isFileSaved;
    List<Personne> listePersonnes;



    public ContactBean(File file){
        this.file = file;
        ContactDAO contactDao = new ContactDAO(file);
        allPersonnes = FXCollections.observableArrayList(contactDao.getAll());
        listePersonnes = allPersonnes.stream().collect(Collectors.toList());
        isFileModified = new SimpleBooleanProperty(false);
        isFileSaved = new SimpleBooleanProperty(true);
    }

    public void save(){
        ContactDAO contactDAO = new ContactDAO(file);
        ArrayList<Personne> listePersonnes = new ArrayList<>(allPersonnes);
        contactDAO.saveAll(listePersonnes);
    }

    public void markFileAsModified() {
        setIsFileModified(true);
        setIsFileSaved(false);
    }
    public void markFileAsSaved() {
        setIsFileSaved(true);
        setIsFileModified(false);
    }


    public ObservableList<Personne> getAllPersonnes() {
        return allPersonnes;
    }

    public boolean isIsFileModified() {
        return isFileModified.get();
    }

    public BooleanProperty isFileModifiedProperty() {
        return isFileModified;
    }

    public void setIsFileModified(boolean isFileModified) {
        this.isFileModified.set(isFileModified);
    }

    public boolean isIsFileSaved() {
        return isFileSaved.get();
    }

    public BooleanProperty isFileSavedProperty() {
        return isFileSaved;
    }

    public void setIsFileSaved(boolean isFileSaved) {
        this.isFileSaved.set(isFileSaved);
    }

    public File getFile() {
        return file;
    }

    public List<Personne> getListePersonnes() {
        return listePersonnes;
    }

}

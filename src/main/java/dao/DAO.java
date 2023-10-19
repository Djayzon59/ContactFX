package dao;

import java.io.File;
import java.util.ArrayList;

public abstract class DAO<T> {
    private FichierTexte fichierTexte;

    public DAO(File file) {
        this.fichierTexte = new FichierTexte(file);
    }

    protected abstract String toLigne(T object);

    protected abstract T toObject(String ligne);

    public ArrayList<T> getAll() {
        ArrayList<T> liste = new ArrayList<>();
        ArrayList<String> lignes = fichierTexte.lire();
        for (String ligne : lignes) {
            T object = toObject(ligne);
            liste.add(object);
        }
        return liste;
    }
    public void saveAll(ArrayList<T> liste) {
        ArrayList<String> lignes = new ArrayList<>();
        for (T object : liste) {
            lignes.add(toLigne(object));
        }
        fichierTexte.ecrire(lignes);
    }



}




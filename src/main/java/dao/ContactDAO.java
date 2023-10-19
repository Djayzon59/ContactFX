package dao;

import model.Personne;
import outils.DateOutils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ContactDAO extends DAO<Personne> {
    public ContactDAO(File file) {
        super(file);
    }

    @Override
    protected String toLigne(Personne personne) {
        StringBuilder ligneBuilder = new StringBuilder();
        ligneBuilder.append(personne.getFirstName());
        ligneBuilder.append("|");
        ligneBuilder.append(personne.getLastName());
        ligneBuilder.append("|");
        ligneBuilder.append(personne.getStreet());
        ligneBuilder.append("|");
        ligneBuilder.append(personne.getPostalCode());
        ligneBuilder.append("|");
        ligneBuilder.append(personne.getCity());
        ligneBuilder.append("|");
        ligneBuilder.append(DateOutils.format(personne.getBirthday()));

        return ligneBuilder.toString();
    }

    @Override
    protected Personne toObject(String ligne) {
        String delimitor = "\\|";
        List<String> personneString = Arrays.asList(ligne.split(delimitor));
        Personne personne = new Personne();
        personne.setFirstName(personneString.get(0));
        personne.setLastName(personneString.get(1));
        personne.setStreet(personneString.get(2));
        personne.setPostalCode(Integer.parseInt(personneString.get(3)));
        personne.setCity(personneString.get(4));
        personne.setBirthday(DateOutils.parse(personneString.get(5)));
        return personne;
    }

}


package dao;

import java.io.*;
import java.util.ArrayList;

public class FichierTexte {
    File file;

    public FichierTexte(File file) {
        this.file = file;
    }


    public ArrayList<String> lire() {
        ArrayList<String> lignes = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                lignes.add(ligne);
            }
        } catch (IOException ioException) {
            System.out.println("Erreur lors de la lecture : " + ioException.getMessage());
        }
        return lignes;
    }

    protected void ecrire(ArrayList<String> lignes) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String ligne : lignes) {
                if (ligne != null){
                    bufferedWriter.append(ligne);
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture : " + e.getMessage());
        }

    }
}







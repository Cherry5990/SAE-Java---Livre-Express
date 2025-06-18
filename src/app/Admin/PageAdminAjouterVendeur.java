package app.Admin;
import modele.Client;

import java.io.IOError;
import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PageAdminAjouterVendeur {
    private Scene scene;

    public PageAdminAjouterVendeur(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminAjouterVendeur.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminAjouterVendeur");
        }

        
    }

    public boolean ajouterVendeur(){
        TextField tfPrenom = (TextField) scene.lookup("#entrerPrenomVendeur");
        TextField tfNom = (TextField) scene.lookup("#entrerNomVendeur");
        TextField tfMagasin = (TextField) scene.lookup("#entrerIdMagasin");
        String prenom = tfPrenom.getText();
        String nom = tfNom.getText();
        String idMagasin = tfMagasin.getText();
        System.out.println(prenom + " " + nom + " " + idMagasin);
        return true;

    }

    public Scene getScene(){
        return this.scene;
    }
}
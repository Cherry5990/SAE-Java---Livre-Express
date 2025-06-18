package app.Admin;
import modele.Client;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import BD.VendeurBD;

public class PageAdminAjouterVendeur {
    private Scene scene;

    public PageAdminAjouterVendeur(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminAjouterVendeur.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminAjouterVendeur");
        }

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(e -> this.ajouterVendeur());
    }

    public void ajouterVendeur(){
        TextField tfPrenom = (TextField) scene.lookup("#entrerPrenomVendeur");
        TextField tfNom = (TextField) scene.lookup("#entrerNomVendeur");
        TextField tfMagasin = (TextField) scene.lookup("#entrerIdMagasin");
        String prenom = tfPrenom.getText();
        String nom = tfNom.getText();
        Integer idMagasin = Integer.parseInt(tfMagasin.getText());
        try{
            if (App.vendeurBD.vendeurPresent(nom, prenom, idMagasin)){
                this.popUpVendeurDejaPresent();
            } else {
                App.vendeurBD.insererVendeur(prenom, nom, idMagasin);
            }
        } catch (SQLException e){
            System.err.println("Erreur dans PageAdminAjouterVendeur.ajouterVendeur() " + e.getMessage());
        }
    }

    public void popUpVendeurDejaPresent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce vendeur est déjà dans la base", ButtonType.OK);
        alert.setTitle("Vendeur déjà présent");
        alert.setHeaderText("Vendeur déjà présent");
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }
}
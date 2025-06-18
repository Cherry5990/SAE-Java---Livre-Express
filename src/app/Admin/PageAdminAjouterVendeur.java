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
    private TextField tfPrenom;
    private TextField tfNom;
    private TextField tfMagasin;

    public PageAdminAjouterVendeur(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminAjouterVendeur.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminAjouterVendeur");
        }

        this.tfPrenom = (TextField) scene.lookup("#entrerPrenomVendeur");
        this.tfNom = (TextField) scene.lookup("#entrerNomVendeur");
        this.tfMagasin = (TextField) scene.lookup("#entrerIdMagasin");

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(e -> this.ajouterVendeur());

        Button reset = (Button) scene.lookup("#reset");
        reset.setOnAction(e -> this.resetTextFields());
    }

    public void ajouterVendeur(){
        try{
            String prenom = this.tfPrenom.getText();
            String nom = this.tfNom.getText();
            Integer idMagasin = Integer.parseInt(this.tfMagasin.getText());
            if (App.vendeurBD.vendeurPresent(nom, prenom, idMagasin)){
                this.popUpVendeurDejaPresent();
            } else {
                App.vendeurBD.insererVendeur(prenom, nom, idMagasin);
            }
        } catch (SQLException e){
            System.err.println("Erreur dans PageAdminAjouterVendeur.ajouterVendeur() " + e.getMessage());
        } catch (NumberFormatException e2){
            this.popUpValeursNonValides();
        }
    }

    public void resetTextFields(){
        this.tfPrenom.setText("");
        this.tfNom.setText("");
        this.tfMagasin.setText("");
    }

    public void popUpVendeurDejaPresent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce vendeur est déjà dans la base", ButtonType.OK);
        alert.setTitle("Vendeur déjà présent");
        alert.setHeaderText("Vendeur déjà présent");
        alert.showAndWait();
    }

    public void popUpValeursNonValides(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : veuillez rentrer des valeurs valides", ButtonType.OK);
        alert.setTitle("Valeurs non valides");
        alert.setHeaderText("Valeurs non valides");
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }
}
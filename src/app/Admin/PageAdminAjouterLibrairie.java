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

public class PageAdminAjouterLibrairie {
    private Scene scene;
    private TextField tfNom;
    private TextField tfVille;

    public PageAdminAjouterLibrairie(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminAjouterLibrairie.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminAjouterLibrairie");
        }

        this.tfNom = (TextField) scene.lookup("#entrerNomLibrairie");
        this.tfVille = (TextField) scene.lookup("#entrerVilleLibrairie");

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(e -> this.ajouterMagasin());

        Button reset = (Button) scene.lookup("#reset");
        reset.setOnAction(e -> this.resetTextFields());
    }

    public void retirerMagasin(){
        try{
            String ville = this.tfVille.getText();
            String nom = this.tfNom.getText();
            if (App.magasinBD.getMagasin(nom) == null){
                this.popUpMagasinNonPresent();
            } else {
                App.vendeurBD.deleteVendeur(nom, prenom, idMagasin);
            }
        } catch (SQLException e){
            System.err.println("Erreur dans PageAdminAjouterVendeur.retirerVendeur() " + e.getMessage());
        } catch (NumberFormatException e2){
            this.popUpValeursNonValides();
        }
    }

    public void ajouterMagasin(){
        try{
            String prenom = this.tf.getText();
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
        this.tfVille.setText("");
        this.tfNom.setText("");
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

    public void popUpVendeurNonPresent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce vendeur n'est pas dans la base", ButtonType.OK);
        alert.setTitle("Vendeur non présent");
        alert.setHeaderText("Vendeur non présent");
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }
}
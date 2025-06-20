package app.Admin;
import modele.Client;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

        Button deconnexion = (Button) this.scene.lookup("#deconnexion");
        deconnexion.setOnMouseEntered(e -> {
                deconnexion.setScaleX(1.1);
                deconnexion.setScaleY(1.1);
            });
            deconnexion.setOnMouseExited(e -> {
                deconnexion.setScaleX(1.0);
                deconnexion.setScaleY(1.0);
            });
        deconnexion.setOnAction(e -> app.popUpMessageDeconnexion());

        Button retour = (Button) this.scene.lookup("#retour");
        retour.setOnMouseEntered(e -> {
                retour.setScaleX(1.1);
                retour.setScaleY(1.1);
            });
            retour.setOnMouseExited(e -> {
                retour.setScaleX(1.0);
                retour.setScaleY(1.0);
            });
        retour.setOnAction(e -> {
            try {
                app.sceneAdmin();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });
    }

    public void ajouterMagasin(){
        try{
            String nom = this.tfNom.getText();
            String ville = this.tfVille.getText();
            if (!(App.magasinBD.getMagasin(nom) == null)){
                this.popUpMagasinDejaPresent(nom);
            } else if (nom.equals("") || ville.equals("")) {
                this.popUpValeursNonValides();
            } else {
                App.magasinBD.insererMagasin(nom, ville);
                this.popUpMagasinAjoute(nom, ville);
            }
        } catch (SQLException e){
            System.err.println("Erreur dans PageAdminAjouterLibrairie.ajouterMagasin() " + e.getMessage());
        }
    }

    public void resetTextFields(){
        this.tfVille.setText("");
        this.tfNom.setText("");
    }

    private void popUpMagasinDejaPresent(String nom){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : Un magasin du nom de " + nom + "\n existe déjà dans la base", ButtonType.OK);
        alert.setTitle("Magasin déjà présent");
        alert.setHeaderText("Magasin déjà présent");
        alert.showAndWait();
    }

    private void popUpValeursNonValides(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : veuillez rentrer des valeurs valides", ButtonType.OK);
        alert.setTitle("Valeurs non valides");
        alert.setHeaderText("Valeurs non valides");
        alert.showAndWait();
    }

    private void popUpMagasinNonPresent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce magasin n'est pas dans la base", ButtonType.OK);
        alert.setTitle("Magasin non présent");
        alert.setHeaderText("Magasin non présent");
        alert.showAndWait();
    }

    private void popUpMagasinAjoute(String nom, String ville){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le magasin " + nom + " situé à " + ville + " a bien été ajouté");
        alert.showAndWait();
    }

    private void popUpMagasinRetire(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le magasin a bien été retiré");
        alert.showAndWait();
    }


    public Scene getScene(){
        return this.scene;
    }
}
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import BD.VendeurBD;

public class PageAdminAjouterVendeur {
    private Scene scene;
    private TextField tfPrenom;
    private TextField tfNom;
    private TextField tfMagasin;
    private TextField nomcompte;
    private PasswordField mdp;
    private PasswordField mdpC;

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
        this.nomcompte = (TextField)scene.lookup("#nomcompte");
        this.mdp = (PasswordField)scene.lookup("#mdp");
        this.mdpC = (PasswordField)scene.lookup("#mdpconfirmation");

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(e -> this.ajouterVendeur());

        Button reset = (Button) scene.lookup("#reset");
        reset.setOnAction(e -> this.resetTextFields());

        Button retirer = (Button) scene.lookup("#retirer");
        retirer.setOnAction(e -> this.retirerVendeur());

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

    public void retirerVendeur(){
        try{
            String prenom = this.tfPrenom.getText();
            String nom = this.tfNom.getText();
            Integer idMagasin = Integer.parseInt(this.tfMagasin.getText());
            String nomcompte = this.nomcompte.getText();
            String mdp = this.mdp.getText();
            String mdpC = this.mdpC.getText();
            if(mdp.equals(mdpC)){
                if (!App.vendeurBD.vendeurPresent(nom, prenom,nomcompte,mdp,idMagasin)){
                    this.popUpVendeurNonPresent();
                } else {
                    App.vendeurBD.deleteVendeur(nom, prenom, idMagasin);
                    this.popUpVendeurRetire();
                }
            }
            else{
                alertMDP();
            }
        } catch (SQLException e){
            System.err.println("Erreur dans PageAdminAjouterVendeur.retirerVendeur() " + e.getMessage());
        } catch (NumberFormatException e2){
            this.popUpValeursNonValides();
        }
    }

    public void ajouterVendeur(){
        try{
            String prenom = this.tfPrenom.getText();
            String nom = this.tfNom.getText();
            Integer idMagasin = Integer.parseInt(this.tfMagasin.getText());
            String nomcompte = this.nomcompte.getText();
            String mdp = this.mdp.getText();
            String mdpC = this.mdpC.getText();
            if (App.vendeurBD.vendeurPresent(nom, prenom,nomcompte,mdpC, idMagasin)){
                this.popUpVendeurDejaPresent();
            } else {
                if(mdp.equals(mdpC)){
                    App.vendeurBD.insererVendeur(prenom, nom, idMagasin,nomcompte,mdp);
                this.popUpVendeurAjoute();
                }
                else{
                    this.alertMDP();
                }
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
        this.nomcompte.setText("");
        this.mdp.setText("");
        this.mdpC.setText("");
    }

    public void popUpVendeurDejaPresent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce vendeur est déjà dans la base", ButtonType.OK);
        alert.setTitle("Vendeur déjà présent");
        alert.setHeaderText("Vendeur déjà présent");
        alert.showAndWait();
    }

    public void alertMDP(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : ce vendeur est déjà dans la base", ButtonType.OK);
        alert.setTitle("Erreur création");
        alert.setHeaderText("Le mot de passe doit être identique");
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

    public void popUpVendeurAjoute(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le vendeur a bien été ajouté");
        alert.showAndWait();
    }

    public void popUpVendeurRetire(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le vendeur a bien été retiré");
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }
}
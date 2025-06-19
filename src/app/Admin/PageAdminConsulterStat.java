package app.Admin;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PageAdminConsulterStat {
    private Scene scene;

    public PageAdminConsulterStat(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminConsulterStat.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminConsulterStat");
        }

        /*
        this.tfPrenom = (TextField) scene.lookup("#entrerPrenomVendeur");
        this.tfNom = (TextField) scene.lookup("#entrerNomVendeur");
        this.tfMagasin = (TextField) scene.lookup("#entrerIdMagasin");

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(e -> this.ajouterVendeur());

        Button reset = (Button) scene.lookup("#reset");
        reset.setOnAction(e -> this.resetTextFields());

        Button retirer = (Button) scene.lookup("#retirer");
        retirer.setOnAction(e -> this.retirerVendeur());
        */
    }

    public Scene getScene(){
        return this.scene;
    }
    
}

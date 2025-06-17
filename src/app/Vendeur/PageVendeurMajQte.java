package app.Vendeur;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Vendeur;

public class PageVendeurMajQte {
    private Scene scene;
    private Vendeur vendeur;
    
    public PageVendeurMajQte(App app, Vendeur v)throws IOException{
        this.vendeur = v;
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurMajQte.fxml"));
        this.scene = new Scene(root);

        Button deconnexion = (Button) this.scene.lookup("#deconnexion");
        deconnexion.setOnMouseEntered(e -> {
                deconnexion.setScaleX(1.1);
                deconnexion.setScaleY(1.1);
            });
            deconnexion.setOnMouseExited(e -> {
                deconnexion.setScaleX(1.0);
                deconnexion.setScaleY(1.0);
            });
        deconnexion.setOnAction(e -> app.sceneAcceuil());

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
            //try {
            //    app.scenePageVendeurGererStocks();
            //} catch (IOException ex) {
            //    System.out.println("Problème");
            //}
        });


        //Nom du livre
        ComboBox<String> nomLivre = (ComboBox<String>) this.scene.lookup("#nomLivre");
        

        //Nouvelle quantité
        TextField newQteLivre = (TextField) this.scene.lookup("#newQteLivre");


        //Bouton réinitialisé
        Button reset = (Button) this.scene.lookup("#reset");
        reset.setOnMouseEntered(e -> {
                reset.setScaleX(1.1);
                reset.setScaleY(1.1);
            });
            reset.setOnMouseExited(e -> {
                reset.setScaleX(1.0);
                reset.setScaleY(1.0);
            });

        //Bouton ajoute
        Button ajouter = (Button) this.scene.lookup("#ajouter");
        ajouter.setOnMouseEntered(e -> {
                ajouter.setScaleX(1.1);
                ajouter.setScaleY(1.1);
            });
            ajouter.setOnMouseExited(e -> {
                ajouter.setScaleX(1.0);
                ajouter.setScaleY(1.0);
            });

        //Les labels
        Label isbn = (Label) this.scene.lookup("#isbn");
        Label prix = (Label) this.scene.lookup("#prix");
        Label nbPages = (Label) this.scene.lookup("#nbPages");
        Label datePubli = (Label) this.scene.lookup("#datePubli");
        Label theme = (Label) this.scene.lookup("#theme");
        Label auteur = (Label) this.scene.lookup("#auteur");
        Label qte = (Label) this.scene.lookup("#qte");


    }

    public Scene getScene(){
        return this.scene;
    }
}

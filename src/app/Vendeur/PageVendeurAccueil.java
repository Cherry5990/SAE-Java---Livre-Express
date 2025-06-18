package app.Vendeur;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageVendeurAccueil {
    private Scene scene;

    public PageVendeurAccueil(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurAccueil.fxml"));
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

        Button gererStock = (Button) this.scene.lookup("#gererStock");
        gererStock.setOnMouseEntered(e -> {
                gererStock.setScaleX(1.1);
                gererStock.setScaleY(1.1);
            });
            gererStock.setOnMouseExited(e -> {
                gererStock.setScaleX(1.0);
                gererStock.setScaleY(1.0);
            });
        gererStock.setOnAction(e -> {
            try {
               app.scenePageVendeurGererStocks();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

        Button commande = (Button) this.scene.lookup("#commande");
        commande.setOnMouseEntered(e -> {
                commande.setScaleX(1.1);
                commande.setScaleY(1.1);
            });
            commande.setOnMouseExited(e -> {
                commande.setScaleX(1.0);
                commande.setScaleY(1.0);
            });
        //gererStock.setOnAction(e -> app.scenePageVendeurCommande());



    }

    public Scene getScene(){
        return this.scene;
    }

    
}
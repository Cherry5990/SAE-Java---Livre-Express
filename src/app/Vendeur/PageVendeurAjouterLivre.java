package app.Vendeur;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import modele.Vendeur;

public class PageVendeurAjouterLivre {
    private Scene scene;

    //A finir
    public PageVendeurAjouterLivre(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurAjouterLivre.fxml"));
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
            try {
                app.scenePageVendeurGererStocks();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

    

    }

    public Scene getScene(){
        return this.scene;
    }
}

package app.Admin;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageAdmin {
    private Scene scene;

    public PageAdmin(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminAccueil.fxml"));
        this.scene = new Scene(root);

        Button creerVendeur = (Button)scene.lookup("#creerVendeur");
        creerVendeur.setOnMouseEntered(e -> {
                creerVendeur.setScaleX(1.1);
                creerVendeur.setScaleY(1.1);
            });
            creerVendeur.setOnMouseExited(e -> {
                creerVendeur.setScaleX(1.0);
                creerVendeur.setScaleY(1.0);
            });

        creerVendeur.setOnAction(e -> {
            app.sceneAjouterVendeur();
        });

        Button ajouterLibrairie = (Button) this.scene.lookup("#ajouterLibrairie");
        ajouterLibrairie.setOnMouseEntered(e -> {
                ajouterLibrairie.setScaleX(1.1);
                ajouterLibrairie.setScaleY(1.1);
            });
            ajouterLibrairie.setOnMouseExited(e -> {
                ajouterLibrairie.setScaleX(1.0);
                ajouterLibrairie.setScaleY(1.0);
            });

        ajouterLibrairie.setOnAction(e -> {
            app.sceneAjouterMagasin();
        });

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
               app.scenePageAdminGererStock();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

        // Button statistiques = (Button) this.scene.lookup("#statistiques");
        // statistiques.setOnMouseEntered(e -> {
        //         statistiques.setScaleX(1.1);
        //         statistiques.setScaleY(1.1);
        //     });
        //     statistiques.setOnMouseExited(e -> {
        //         statistiques.setScaleX(1.0);
        //         statistiques.setScaleY(1.0);
        //     });

        // statistiques.setOnAction(e -> {
        //     try {
        //        app.scenePageAdminStatistiques();
        //     } catch (IOException ex) {
        //         System.out.println("Problème");
        //     }
        // });

    }

    public Scene getScene(){
        return this.scene;
    }
}
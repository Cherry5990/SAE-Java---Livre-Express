package app.Vendeur;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import modele.Vendeur;

public class PageVendeurGererStocks {
    private Scene scene;

    public PageVendeurGererStocks(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurGererStocks.fxml"));
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
                app.scenePageVendeurAccueil();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

        //Bouton Ajouter Livre
        Button addLivre = (Button) this.scene.lookup("#ajouterLivre");
        addLivre.setOnMouseEntered(e -> {
                addLivre.setScaleX(1.1);
                addLivre.setScaleY(1.1);
            });
            addLivre.setOnMouseExited(e -> {
                addLivre.setScaleX(1.0);
                addLivre.setScaleY(1.0);
            });
        addLivre.setOnAction(e -> {
            try {
                app.scenePageVendeurAjouterLivre();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

        //Bouton Mise à jour de la quantité de livres
        Button majQteLivre = (Button) this.scene.lookup("#majQteLivre");
        majQteLivre.setOnMouseEntered(e -> {
                majQteLivre.setScaleX(1.1);
                majQteLivre.setScaleY(1.1);
            });
            majQteLivre.setOnMouseExited(e -> {
                majQteLivre.setScaleX(1.0);
                majQteLivre.setScaleY(1.0);
            });
        majQteLivre.setOnAction(e -> {
            try {
                app.scenePageVendeurMajQte();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });

        //Bouton Voir le stock du magasin
        Button voirStock = (Button) this.scene.lookup("#voirStock");
        voirStock.setOnMouseEntered(e -> {
                voirStock.setScaleX(1.1);
                voirStock.setScaleY(1.1);
            });
            voirStock.setOnMouseExited(e -> {
                voirStock.setScaleX(1.0);
                voirStock.setScaleY(1.0);
            });
        voirStock.setOnAction(e -> {
            try {
                app.scenePageVendeurVoirStocks();
            } catch (IOException ex) {
                System.out.println("Problème avec PageVendeurVoirStocks");
            }
        });

        //Bouton transferer livre depuis un magasin
        Button transfererLivre = (Button) this.scene.lookup("#transfererLivre");
        transfererLivre.setOnMouseEntered(e -> {
                transfererLivre.setScaleX(1.1);
                transfererLivre.setScaleY(1.1);
            });
            transfererLivre.setOnMouseExited(e -> {
                transfererLivre.setScaleX(1.0);
                transfererLivre.setScaleY(1.0);
            });
        transfererLivre.setOnAction(e -> {
            try {
                app.scenePageVendeurTransfererLivre();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });


        



    }




    public Scene getScene(){
        return this.scene;
    }
}

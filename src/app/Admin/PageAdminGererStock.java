package app.Admin;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageAdminGererStock{
    private Scene scene;

    public PageAdminGererStock(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminGererStock.fxml"));
        this.scene = new Scene(root);

        Button retour = (Button) this.scene.lookup("#retour");
        retour.setOnAction(e -> {
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

        // Bouton Transférer un livre
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
            try{
                app.scenePageAdminTransfererLivre();
            }
            catch (IOException ex){
                System.out.println("Problème");
            }
        });

        // Bouton voir stock
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
            try{
                app.scenePageAdminVoirStock();
            }
            catch (IOException ex){
                System.out.println("Problème");
            }
        });
    }

    public Scene getScene(){
        return this.scene;
    }
}
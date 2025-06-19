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
        creerVendeur.setOnAction(e -> app.sceneAjouterVendeur());

        Button ajouterLibrairie = (Button)scene.lookup("#ajouterLibrairie");
        ajouterLibrairie.setOnAction(e -> app.sceneAjouterMagasin());

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

        Button statistiques = (Button)scene.lookup("#statistiques");
        statistiques.setOnAction(e -> app.scenePageAdminConsulterStat());
    }

    public Scene getScene(){
        return this.scene;
    }
}
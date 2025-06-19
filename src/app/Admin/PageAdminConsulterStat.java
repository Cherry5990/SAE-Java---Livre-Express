package app.Admin;

import java.io.IOException;
import java.util.List;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Magasin;

public class PageAdminConsulterStat {
    private Scene scene;
    private ComboBox<String> typeStat;
    private ComboBox<String> parMagasin;
    private ComboBox<Integer> parAnnee;

    public PageAdminConsulterStat(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminConsulterStat.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminConsulterStat");
        }

        this.typeStat = (ComboBox<String>) scene.lookup("#typeStat");
        this.parMagasin = (ComboBox<String>) scene.lookup("#parMagasin");
        this.parAnnee = (ComboBox<Integer>) scene.lookup("#parAnnee");

        ObservableList<String> lstTypeStat = FXCollections.observableArrayList("Chiffre d'affaire", "Livre les plus vendus", "Comparer ventes en ligne et ventes en magasin", "Valeur du stock par magasin");
        this.typeStat.setItems(lstTypeStat);
        this.typeStat.setValue(lstTypeStat.get(0));

        List<Magasin> magasins = App.magasinBD.getAllMagasins();
        ObservableList<String> lstParMagasin = FXCollections.observableArrayList();
        for (Magasin magasin : magasins){
            lstParMagasin.add(magasin.getNomMagasin());
        }
        this.parMagasin.setItems(lstParMagasin);
        this.parMagasin.setValue(lstParMagasin.get(0));

        ObservableList<Integer> lstParAnnees = FXCollections.observableArrayList(App.reseauBD.getAnnees());
        this.parAnnee.setItems(lstParAnnees);
        this.parAnnee.setValue(lstParAnnees.get(0));

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
                app.sceneAdmin();
            } catch (IOException ex) {
                System.out.println("Problème");
            }
        });
    }

    public Scene getScene(){
        return this.scene;
    }
    
}

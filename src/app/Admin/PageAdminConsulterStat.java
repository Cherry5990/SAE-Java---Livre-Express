package app.Admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Magasin;

public class PageAdminConsulterStat {
    private Scene scene;
    private ComboBox<String> typeStat;
    private ComboBox<String> filtres;
    private ComboBox<String> parMagasin;
    private ComboBox<String> parAnnee;
    private VBox espaceGraphiques;
    private BarChart<String, Number> graphiqueCA;

    public PageAdminConsulterStat(App app){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminConsulterStat.fxml"));
            this.scene = new Scene(root);
        } catch (IOException e){
            System.err.println("Problème d'ouverture du fichier PageAdminConsulterStat");
        }

        this.typeStat = (ComboBox<String>) scene.lookup("#typeStat");
        this.filtres = (ComboBox<String>) scene.lookup("#filtres");
        this.parMagasin = (ComboBox<String>) scene.lookup("#parMagasin");
        this.parAnnee = (ComboBox<String>) scene.lookup("#parAnnee");
        this.espaceGraphiques = (VBox) scene.lookup("#espaceGraphiques");

        ObservableList<String> lstTypeStat = FXCollections.observableArrayList("Chiffre d'affaire", "Livre les plus vendus", "Comparer ventes en ligne et ventes en magasin", "Valeur du stock par magasin");
        this.typeStat.setItems(lstTypeStat);
        this.typeStat.setValue(lstTypeStat.get(0));

        ObservableList<String> lstFiltres = FXCollections.observableArrayList("Par année", "Par magasin");
        this.filtres.setItems(lstFiltres);

        List<Magasin> magasins = App.magasinBD.getAllMagasins();
        ObservableList<String> lstParMagasin = FXCollections.observableArrayList();
        for (Magasin magasin : magasins){
            lstParMagasin.add(magasin.getNomMagasin());
        }
        lstParMagasin.add("Sur tous les magasins");
        this.parMagasin.setItems(lstParMagasin);
        this.parMagasin.setValue(lstParMagasin.get(lstParMagasin.size() - 1));

        List<Integer> annees = App.reseauBD.getAnnees();
        ObservableList<String> lstParAnnees = FXCollections.observableArrayList();
        for (Integer annee : annees){
            lstParAnnees.add(String.valueOf(annee));
        }
        lstParAnnees.add("Sur toutes les années");
        this.parAnnee.setItems(lstParAnnees);
        this.parAnnee.setValue(lstParAnnees.get(lstParAnnees.size() - 1));

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

        Button consulter = (Button) this.scene.lookup("#consulter");
        consulter.setOnAction(e -> this.appuiBouton());

        this.creerGraphiqueChiffreDAffaire();
    }

    public void creerGraphiqueChiffreDAffaire(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Année");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("CA (en euro)");

        // Création du graphique
        this.graphiqueCA = new BarChart<>(xAxis, yAxis);
        this.graphiqueCA.setTitle("Chiffre d'affaire par année");
    }

    public void setGraphiqueChiffreDAffaire(){
        this.espaceGraphiques.getChildren().clear();
        this.graphiqueCA.getData().clear();
        // Série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String,Integer> entree : App.adminBD.chiffreDAffaireTotalParAnsGraphique()){
            series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
        }

        // Ajout de la série au graphique
        this.graphiqueCA.getData().add(series);
        this.espaceGraphiques.getChildren().add(this.graphiqueCA);
    }

    public void appuiBouton(){
        String stat = this.typeStat.getValue();
        switch (stat) {
            case "Chiffre d'affaire":
                System.out.println(stat);
                this.setGraphiqueChiffreDAffaire();
                break;
            case "Livre les plus vendus":
                System.out.println(stat);
                break;
            case "Comparer ventes en ligne et ventes en magasin":
                System.out.println(stat);
                break;
            case "Valeur du stock par magasin":
                System.out.println(stat);
                break;
            default:
                System.err.println("Problème dans la sélection du ComboBox typeStat");
                break;
        }
    }

    public Scene getScene(){
        return this.scene;
    }
    
}

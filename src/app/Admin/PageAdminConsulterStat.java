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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private BarChart<String, Number> graphiqueCAAnnee;
    private BarChart<String, Number> graphiqueCAMagasin;
    private BarChart<String, Number> graphiqueVenteLivresAnnee;
    private BarChart<String, Number> graphiqueVenteLivresMagasin;
    private PieChart graphiqueVentesLigneMagasinAnnee;
    private PieChart graphiqueVentesLigneMagasinMagasin;
    private BarChart<String, Number> graphiqueStock;

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
        this.filtres.setValue(lstFiltres.get(0));

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

        this.creerGraphiqueChiffreDAffaireAnnee();
        this.creerGraphiqueChiffreDAffaireMagasin();
        this.creerGraphiqueVenteLivresAnnee();
        this.creerGraphiqueVenteLivresMagasin();
        this.creerGraphiqueVenteLigneMagasinAnnee();
        this.creerGraphiqueVenteLigneMagasinMagasin();
        this.creerGraphiqueStock();

        this.setGraphiqueChiffreDAffaire();
    }

    // ------------------- Graphique pour CA -------------------

    public void creerGraphiqueChiffreDAffaireAnnee(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Année");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("CA (en €)");

        // Création du graphique
        this.graphiqueCAAnnee = new BarChart<>(xAxis, yAxis);
        this.graphiqueCAAnnee.setTitle("Chiffre d'affaire par année");
    }

    public void creerGraphiqueChiffreDAffaireMagasin(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Magasin");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("CA (en €)");

        // Création du graphique
        this.graphiqueCAMagasin = new BarChart<>(xAxis, yAxis);
        this.graphiqueCAMagasin.setTitle("Chiffre d'affaire par magasin");
    }

    public void setGraphiqueChiffreDAffaire(){
        this.espaceGraphiques.getChildren().clear();
        this.graphiqueCAMagasin.getData().clear();
        this.graphiqueCAAnnee.getData().clear();

        String filtre = this.filtres.getValue();
        // Série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (filtre) {
            case "Par année":
                for (Map.Entry<String,Integer> entree : App.adminBD.chiffreDAffaireTotalParAnsGraphique()){
                    series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
                }
                this.graphiqueCAAnnee.getData().add(series);
                this.espaceGraphiques.getChildren().add(this.graphiqueCAAnnee);
                break;
            case "Par magasin":
                for (Map.Entry<String,Integer> entree : App.adminBD.chiffreDAffaireTotalParMagasinGraphique()){
                    series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
                }
                this.graphiqueCAMagasin.getData().add(series);
                this.espaceGraphiques.getChildren().add(this.graphiqueCAMagasin);
                break;
        
            default:
                break;
        }
    }

    // ------------------- Graphique pour livres les plus vendus -------------------

    public void creerGraphiqueVenteLivresAnnee(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Titre du livre");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Quantité vendue");

        // Création du graphique
        this.graphiqueVenteLivresAnnee = new BarChart<>(xAxis, yAxis);
        this.graphiqueVenteLivresAnnee.setTitle("Les 10 livres les plus vendus pour l'année...");
    }

    public void creerGraphiqueVenteLivresMagasin(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Titre du livre");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Quantité vendue");

        // Création du graphique
        this.graphiqueVenteLivresMagasin = new BarChart<>(xAxis, yAxis);
        this.graphiqueVenteLivresMagasin.setTitle("Les 10 livres les plus vendus pour le magasin...");
    }

    public void setGraphiqueVenteLivres(){
        this.espaceGraphiques.getChildren().clear();
        this.graphiqueVenteLivresAnnee.getData().clear();
        this.graphiqueVenteLivresMagasin.getData().clear();

        String filtre = this.filtres.getValue();
        // Série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (filtre) {
            case "Par année":
                try{
                    Integer annee = Integer.parseInt(this.parAnnee.getValue());
                    this.graphiqueVenteLivresAnnee.setTitle("Les 10 livres les plus vendus pour l'année " + annee);

                    // Série de données
                    for (Map.Entry<String,Integer> entree : App.adminBD.livresLesPlusVendusTotalParAnsGraphique(annee)){
                        series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
                    }
                } catch (NumberFormatException e){
                    System.out.println("Toutes les années choisies");
                }

                // Ajout de la série au graphique
                this.graphiqueVenteLivresAnnee.getData().add(series);
                this.espaceGraphiques.getChildren().add(this.graphiqueVenteLivresAnnee);
                break;

            case "Par magasin":
                String magasinChoisi = this.parMagasin.getValue();
                if (magasinChoisi != "Sur tous les magasins"){
                    this.graphiqueVenteLivresMagasin.setTitle("Les 10 livres les plus vendus pour le magasin " + magasinChoisi);

                    // Série de données
                    for (Map.Entry<String,Integer> entree : App.adminBD.livresLesPlusVendusTotalParMagasinGraphique(magasinChoisi)){
                        series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
                    }
                } else {
                    System.out.println("Tous les magasins choisis");
                }

                // Ajout de la série au graphique
                this.graphiqueVenteLivresMagasin.getData().add(series);
                this.espaceGraphiques.getChildren().add(this.graphiqueVenteLivresMagasin);
                break;

            default:
                break;
        }
    }

    // ------------------- Graphique pour ventes en ligne vs en magasin -------------------

    public void creerGraphiqueVenteLigneMagasinAnnee(){
        this.graphiqueVentesLigneMagasinAnnee = new PieChart();
        this.graphiqueVentesLigneMagasinAnnee.setTitle("CA ventes en ligne et en magasin");
    }

    public void creerGraphiqueVenteLigneMagasinMagasin(){
        this.graphiqueVentesLigneMagasinMagasin = new PieChart();
        this.graphiqueVentesLigneMagasinMagasin.setTitle("CA ventes en ligne et en magasin");
    }

    public void setGraphiqueVenteLigneMagasin(){
        this.espaceGraphiques.getChildren().clear();
        this.graphiqueVentesLigneMagasinAnnee.getData().clear();
        this.graphiqueVentesLigneMagasinMagasin.getData().clear();

        String filtre = this.filtres.getValue();

        switch (filtre) {
            case "Par année":
                try{
                    Integer annee = Integer.parseInt(this.parAnnee.getValue());
                    this.graphiqueVentesLigneMagasinAnnee.setTitle("CA ventes en ligne et en magasin " + annee);

                    // Série de données
                    for (Map.Entry<String,Integer> entree : App.adminBD.ventesLigneContreMagasinParMagasinParAnsGraphique(annee)){
                        this.graphiqueVentesLigneMagasinAnnee.getData().add(new PieChart.Data(entree.getKey(),entree.getValue()));
                    }
                } catch (NumberFormatException e){
                    System.out.println("Toutes les années choisies");
                }

                this.espaceGraphiques.getChildren().add(this.graphiqueVentesLigneMagasinAnnee);
                break;

            case "Par magasin":
                String magasinChoisi = this.parMagasin.getValue();
                if (magasinChoisi != "Sur tous les magasins"){
                    this.graphiqueVentesLigneMagasinMagasin.setTitle("CA ventes en ligne et en magasin pour le magasin " + magasinChoisi);
                    
                    // Série de données
                    for (Map.Entry<String,Integer> entree : App.adminBD.ventesLigneContreMagasinParMagasinParMagasinGraphique(magasinChoisi)){
                        this.graphiqueVentesLigneMagasinMagasin.getData().add(new PieChart.Data(entree.getKey(),entree.getValue()));
                    }
                } else {
                    System.out.println("Tous les magasins choisis");
                }

                // Ajout de la série au graphique
                this.espaceGraphiques.getChildren().add(this.graphiqueVentesLigneMagasinMagasin);
                break;

            default:
                break;
        }
    }

    // ------------------- Graphique pour valeur du stock -------------------

    public void creerGraphiqueStock(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Librairie");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valeur du stock (en €)");

        // Création du graphique
        this.graphiqueStock = new BarChart<>(xAxis, yAxis);
        this.graphiqueStock.setTitle("Valeur du stock par magasin");
    }

    public void setGraphiqueStock(){
        this.espaceGraphiques.getChildren().clear();
        this.graphiqueStock.getData().clear();
        // Série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String,Integer> entree : App.adminBD.valeurStockParMagasinGraphique()){
            series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
        }

        // Ajout de la série au graphique
        this.graphiqueStock.getData().add(series);
        this.espaceGraphiques.getChildren().add(this.graphiqueStock);
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
                this.setGraphiqueVenteLivres();
                break;
            case "Comparer ventes en ligne et ventes en magasin":
                System.out.println(stat);
                this.setGraphiqueVenteLigneMagasin();
                break;
            case "Valeur du stock par magasin":
                System.out.println(stat);
                this.setGraphiqueStock();
                break;
            default:
                System.err.println("Problème dans la sélection du ComboBox typeStat");
                break;
        }
    }

    private void popUpValeursNonValides(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Erreur : Veuillez sélectionner tous les filtres", ButtonType.OK);
        alert.setTitle("Valeurs non valides");
        alert.setHeaderText("Valeurs non valides");
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }
    
}

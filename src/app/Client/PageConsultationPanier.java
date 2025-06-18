package app.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ToolTipManager;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.DetailCommande;

public class PageConsultationPanier {
    private Scene scene;
    private App app;
    private GridPane tableau;
    private Label prixTotale;
    private RadioButton livraison;
    private RadioButton magasin;


    public PageConsultationPanier(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientPanier.fxml"));
        this.scene = new Scene(root);

        Button retour=(Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneMagasin();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

        this.livraison = (RadioButton)scene.lookup("#livraison");
        this.magasin = (RadioButton)scene.lookup("#magasin");
        ToggleGroup choix = new ToggleGroup();
        livraison.setToggleGroup(choix);
        magasin.setToggleGroup(choix);
        String baseStyle = "-fx-font-size: 13px; -fx-padding: 6 14 6 14; -fx-background-radius: 14; -fx-border-radius: 14; -fx-border-color: #0078D7; -fx-border-width: 1.5px;";
        String selectedStyle = baseStyle + " -fx-background-color: #E3F2FD;";
        String unselectedStyle = baseStyle + " -fx-background-color: transparent;";

        livraison.setStyle(selectedStyle);
        magasin.setStyle(unselectedStyle);
        livraison.setSelected(true);

        choix.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == livraison) {
            livraison.setStyle(selectedStyle);
            magasin.setStyle(unselectedStyle);
            } else if (newToggle == magasin) {
            magasin.setStyle(selectedStyle);
            livraison.setStyle(unselectedStyle);
            }
        });
        
        Button commander = (Button)scene.lookup("#commander");
        commander.setOnAction(new ControleurCommander(this,app));

        this.app=app;
        List<DetailCommande> dcs = App.commande.getDetailCommandes();
        this.tableau = (GridPane) scene.lookup("#tab");
        double somme = 0;
        for(DetailCommande dc:dcs){
            int ligne = dc.getNumlig();
            System.out.println(ligne);
            Label numLigne = new Label(dc.getNumlig()+"");
            Label titre = new Label(dc.getLivre().getTitre());
            Label qte = new Label(dc.getQte()+"");
            Label prix = new Label(dc.getLivre().getPrix()+"");
            Label sommeLab = new Label(dc.getPrixVente()+"");
            somme+=dc.getPrixVente();
            tableau.add(numLigne, 0, ligne);
            tableau.add(titre, 1, ligne);
            tableau.add(qte, 2, ligne);
            tableau.add(prix, 3, ligne);
            tableau.add(sommeLab, 4, ligne);
            Button supprimer = new Button();
            ImageView suprimmerImage = new ImageView(new Image("file:img/icônes/poubelle_rouge.png"));
            suprimmerImage.setFitHeight(30);
            suprimmerImage.setFitWidth(30);
            supprimer.setStyle("-fx-background-color: transparent;");
            supprimer.setGraphic(suprimmerImage);
            supprimer.setOnAction(new ControleurEnleverPanier(app, this,dc));
            tableau.add(supprimer, 5, ligne);
        }
        this.prixTotale = (Label) scene.lookup("#prix");
        somme = Math.round(somme * 100.0) / 100.0;
        prixTotale.setText("Prix totale : "+somme + "€");
    }

    public void maj(){
        tableau.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        List<DetailCommande> dcs = App.commande.getDetailCommandes();
        double somme = 0;
        Button supprimer = new Button();
        ImageView suprimmerImage = new ImageView(new Image("file:img/icônes/poubelle_rouge.png"));
        suprimmerImage.setFitHeight(30);
        suprimmerImage.setFitWidth(30);
        supprimer.setStyle("-fx-background-color: transparent;");
        supprimer.setGraphic(suprimmerImage);
        for(DetailCommande dc:dcs){
            int ligne = dc.getNumlig();
            System.out.println(ligne);
            Label numLigne = new Label(dc.getNumlig()+"");
            Label titre = new Label(dc.getLivre().getTitre());
            Label qte = new Label(dc.getQte()+"");
            Label prix = new Label(dc.getLivre().getPrix()+"");
            Label sommeLab = new Label(dc.getPrixVente()+"");
            somme+=dc.getPrixVente();
            tableau.add(numLigne, 0, ligne);
            tableau.add(titre, 1, ligne);
            tableau.add(qte, 2, ligne);
            tableau.add(prix, 3, ligne);
            tableau.add(sommeLab, 4, ligne);
            supprimer.setOnAction(new ControleurEnleverPanier(app, this,dc));
            tableau.add(supprimer, 5, ligne);
        }
        // Arrondir la somme à 2 chiffres après la virgule
        somme = Math.round(somme);
        prixTotale.setText("Prix totale : "+somme + "€");
    }

    public Scene getScene(){
        return this.scene;
    }

    public GridPane getGridPane(){
        return this.tableau;
    }

    public boolean getLivraison(){
        return this.livraison.isSelected();
    }

    public boolean getMagasin(){
        return this.magasin.isSelected();
    }
}

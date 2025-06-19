package app.Vendeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.App;
import app.Display.DetailCommandeDisplay;
import app.Display.LivreDisplayLigne;
import app.Display.LivreDisplayLigneVendeur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Client;
import modele.DetailCommande;
import modele.Livre;

public class PageVendeurCommande {
    private Scene scene;
    private List<DetailCommande> dcs;
    private VBox ligne;
    private Spinner<Integer> spinner;
    private Button ajouter;
    private TextField textLivre;
    private Button valider;
    private Label nbArticle;
    private Label prixTotal;

    public PageVendeurCommande(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurCommande.fxml"));
        this.scene = new Scene(root);
        this.dcs = App.commande.getDetailCommandes();
        ScrollPane scroll = (ScrollPane)scene.lookup("#scroll");
        scroll.setFitToWidth(true);
        this.nbArticle = (Label)scene.lookup("#article");
        this.nbArticle.setText("0 Article");
        this.prixTotal = (Label)scene.lookup("#total");
        this.prixTotal.setText("0€");
        this.ligne = (VBox)scroll.getContent();
        for(DetailCommande dc:dcs){
            ligne.getChildren().add(new DetailCommandeDisplay(this,dc));
        }
        this.valider = (Button)scene.lookup("#valider");
        this.valider.setDisable(true);
        this.textLivre = (TextField)scene.lookup("#livre");
        ComboBox<String> comboLivre = (ComboBox<String>)scene.lookup("#comboLivre");
        this.spinner = (Spinner<Integer>)scene.lookup("#qte");
        spinner.setDisable(true);
        this.ajouter =(Button)scene.lookup("#ajouter");
        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            for(DetailCommande dc:dcs){
                App.magasinBD.ajouteQteLivre(dc.getLivre().getIsbn(), App.magasin.getIdMagasin(), dc.getQte());
            }
            try {
                app.scenePageVendeurChoixClient();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });
        ajouter.setDisable(true);
        ajouter.setOnAction(e -> {
            if (App.livre != null) {
                App.magasinBD.enleveQteLivre(App.livre.getIsbn(), App.magasin.getIdMagasin(), spinner.getValue());
                App.commande.ajouteLivre(App.livre, spinner.getValue());
                maj();
                reset();
            } else {
                System.out.println("Error: No book selected");
            }
        });

        valider.setOnAction(e -> {
            try {
                App.commandeBD.insererCommande(App.commande);
                //faire alert
                app.scenePageVendeurChoixClient();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } catch (IOException e2) {
                System.out.println(e2.getMessage());
            }
        });

        List<Livre> livresInit = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), "");
        comboLivre.getItems().clear();
        for (Livre elt : livresInit) {
            comboLivre.getItems().add(elt.getTitre());
        }

        textLivre.textProperty().addListener((obs, oldVal, newVal) -> {
            List<Livre> livresCommande = new ArrayList<>();
            for(DetailCommande dc:dcs){
                livresCommande.add(dc.getLivre());
            } 
            List<Livre> livres = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), newVal,livresCommande);
            comboLivre.getItems().clear();
            for (Livre livre : livres) {
                comboLivre.getItems().add(livre.getTitre());
            }
            // Montre le menu déroulant
            if(!this.textLivre.getText().equals("")){
                comboLivre.show();
            }
            else{
                reset();
            }
        });

        comboLivre.setOnAction(e ->{ 
            try {
                String selectedTitre = comboLivre.getSelectionModel().getSelectedItem();
                // Add null check here
                if (selectedTitre != null && !selectedTitre.isEmpty()) {
                    System.out.println(selectedTitre);
                    App.livre = App.livreBD.getLivreParTitre(selectedTitre);
                    spinner.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, App.magasinBD.getQte(App.livre.getIsbn(),App.magasin.getIdMagasin()), 1));
                    spinner.setDisable(false);
                    ajouter.setDisable(false);

                } else {
                    // Reset the controls if no valid selection
                    spinner.setDisable(true);
                    ajouter.setDisable(true);
                    App.livre = null;
                }
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
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
    }

    public Scene getScene(){
        return this.scene;
    }

    public void reset(){
        this.spinner.setDisable(true);
        this.ajouter.setDisable(true);
        this.textLivre.setText("");
    }

    public void maj(){
        ligne.getChildren().clear();
        this.dcs = App.commande.getDetailCommandes();
        if(this.dcs.size()!=0){
            this.valider.setDisable(false);
        }
        try{
            for(DetailCommande dc:dcs){
                System.out.println(dc);
                ligne.getChildren().add(new DetailCommandeDisplay(this,dc));
            }
        this.nbArticle.setText(dcs.size()+" Article(s)");
        this.prixTotal.setText(App.commande.getPrix()+" €");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Alert alertDeleteDetailCommandeMoins(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Suppresion livre"); 
        alert.setContentText("êtes vous sur de supprimer ce livre de la commande?");     
        return alert;
    }

    public Alert alertDeleteDetailCommandePlus(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Impossible"); 
        alert.setContentText("Nous n'avons pas plus d'exemplaire de ce livre");     
        return alert;
    }
    
}
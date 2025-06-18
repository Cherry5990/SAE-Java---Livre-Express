package app.Vendeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import app.App;
import app.Display.DetailCommandeDisplay;
import app.Display.LivreDisplayLigne;
import app.Display.LivreDisplayLigneVendeur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private List<Client> clients;
    private VBox lignes;

    public PageVendeurCommande(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurCommande.fxml"));
        this.scene = new Scene(root);

        this.dcs = App.commande.getDetailCommandes();
        VBox ligne = (VBox)scene.lookup("#dc");
        for(DetailCommande dc:dcs){
            ligne.getChildren().add(new DetailCommandeDisplay(dc));
        }

        TextField textLivre = (TextField)scene.lookup("#livre");
        ComboBox<String> comboLivre = (ComboBox<String>)scene.lookup("#comboLivre");
        Spinner<Integer> spinner = (Spinner<Integer>)scene.lookup("#qte");
        Button ajouter =(Button)scene.lookup("#ajouter");
        ajouter.setOnAction(e ->{
            App.commande.ajouteLivre(App.livre, spinner.getValue());
            maj();
        });

        List<Livre> livresInit = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), "");
        comboLivre.getItems().clear();
        for (Livre elt : livresInit) {
            comboLivre.getItems().add(elt.getTitre());
        }

        textLivre.textProperty().addListener((obs, oldVal, newVal) -> {
            // Filtrage dynamique
            List<Livre> livres = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), newVal);
            comboLivre.getItems().clear();
            for (Livre livre : livres) {
                comboLivre.getItems().add(livre.getTitre());
            }
            // Montre le menu déroulant
            comboLivre.show();
        });

        comboLivre.setOnAction(e ->{ 
            try {
                App.livre = App.livreBD.getLivreParTitre(comboLivre.getValue());
                spinner.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, App.magasinBD.getQte(App.livre.getIsbn(),App.magasin.getIdMagasin()), 1));
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            });
    }

    public Scene getScene(){
        return this.scene;
    }

    public void maj(){
        
    }
}
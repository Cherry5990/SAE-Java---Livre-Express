package app.Client;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Client;
import modele.DetailCommande;
import modele.Livre;
import javafx.scene.control.ToggleGroup;

public class PageClientPanierv2 {
    private Scene scene;
    private List<DetailCommande> dcs;
    private VBox ligne;
    private Button valider;
    private Label nbArticle;
    private Label prixTotal;
    private RadioButton livraison;
    private RadioButton magasin;

    public PageClientPanierv2(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientPanierv2.fxml"));
        this.scene = new Scene(root);
        this.dcs = App.commande.getDetailCommandes();
        ScrollPane scroll = (ScrollPane)scene.lookup("#scroll");
        scroll.setFitToWidth(true);
        this.nbArticle = (Label)scene.lookup("#article");
        this.nbArticle.setText("0 Article");
        this.prixTotal = (Label)scene.lookup("#total");
        this.prixTotal.setText("0€");
        this.ligne = (VBox)scroll.getContent();
        this.valider = (Button)scene.lookup("#valider");
        this.valider.setDisable(true);
        for(DetailCommande dc:dcs){
            ligne.getChildren().add(new DetailCommandeDisplay(this,dc));
        }
        this.livraison = (RadioButton)scene.lookup("#livraison");
        this.livraison.setOnAction(e -> {
            if(this.dcs.size()!=0){
                this.valider.setDisable(false);
            }
        });
        this.magasin = (RadioButton)scene.lookup("#magasin");
        this.magasin.setOnAction(e -> {
            if(this.dcs.size()!=0){
                this.valider.setDisable(false);
            }
        });
        ToggleGroup group = new ToggleGroup();
        this.livraison.setToggleGroup(group);
        this.magasin.setToggleGroup(group);
        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneMagasin();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

        valider.setOnAction(e -> {
            try {
                App.commande.setEnLigne(this.livraison.isSelected());
                App.commandeBD.insererCommande(App.commande);
                //faire alert
                app.sceneClient();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } catch (IOException e2) {
                System.out.println(e2.getMessage());
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

    public void maj(){
        ligne.getChildren().clear();
        this.dcs = App.commande.getDetailCommandes();
        if(this.dcs.size()==0){
            this.valider.setDisable(true);
        }
        try{
            for(DetailCommande dc:dcs){
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
package app.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.App;
import app.Display.LivreDisplayLigne;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.DetailCommande;
import modele.Livre;

public class PageClientCatalogue {
    private Scene scene;
    private ScrollPane scroll;
    private List<Livre> livres;
    private VBox lignes;
    private int position;
    private App app;
    private String like;
    private Label page;

    public PageClientCatalogue(App app)throws IOException{
        this.app = app;
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientCatalogue.fxml"));
        this.scene = new Scene(root);
        this.position = 0;
        this.like = "";
        List<Livre> livresCommande = new ArrayList<>();
            for(DetailCommande dc:App.commande.getDetailCommandes()){
                livresCommande.add(dc.getLivre());
            }
        this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(),like,position,20,livresCommande);
        this.scroll = (ScrollPane) this.scene.lookup("#test");
        this.scroll.setStyle("-fx-background-color: transparent;");
        int i = position+1;
        this.lignes = (VBox) this.scroll.getContent();
        ControleurConsulterLivre controleur = new ControleurConsulterLivre(app);
        for(Livre livreMag:livres){
            this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag,i));
            i++;
        }
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

        TextField recherche = (TextField)scene.lookup("#recherche");
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            position = 0;
            this.like = newValue;
            maj();
        });
    
        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneClient();
                if(App.commande.getDetailCommandes().size()!=0){
                    App.memoiresCommandesClient.get(App.client).put(App.magasin,App.commande);
                }
            } 
            catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

        Button panier = (Button)scene.lookup("#panier");
        panier.setOnAction(e -> {
			try {
				app.sceneConsultationPanier();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		});

        Button prec = (Button)scene.lookup("#prec");
        prec.setOnAction(e -> {
            if(position-20>=0){
                position -=20;
            }
            maj();
        });

        Button suiv = (Button)scene.lookup("#suiv");
        suiv.setOnAction(e -> {
            if(position<App.magasinBD.nbLivreLike(App.magasin.getIdMagasin(), like)-20){
                position+=20;
            }
            maj();
        });

        this.page = (Label)scene.lookup("#page");
        int page1 = (position/20)+1;
        int page2 = (App.magasinBD.nbLivreLike(App.magasin.getIdMagasin(), like)/20)+1;
        this.page.setText(page1+"/"+page2);

    }

    public Scene getScene(){
        return this.scene;
    }

    public void maj(){
        List<Livre> livresCommande = new ArrayList<>();
            for(DetailCommande dc:App.commande.getDetailCommandes()){
                livresCommande.add(dc.getLivre());
            }
        int i = position+1;
        this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(),like,position,20,livresCommande);
        this.lignes.getChildren().clear();
        ControleurConsulterLivre controleur = new ControleurConsulterLivre(app);
        for(Livre livreMag:livres){
            this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag,i));
            i++;
        }

        int page1 = (position/20)+1;
        int page2 = (App.magasinBD.nbLivreLike(App.magasin.getIdMagasin(), like)/20)+1;
        this.page.setText(page1+"/"+page2);
    }
}

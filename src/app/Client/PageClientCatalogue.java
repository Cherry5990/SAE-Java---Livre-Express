package app.Client;

import java.io.IOException;
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
        this.position = 1;
        this.like = "";
        this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(),like,position,20);
        this.scroll = (ScrollPane) this.scene.lookup("#test");
        int i = position;
        this.lignes = (VBox) this.scroll.getContent();
        ControleurConsulterLivre controleur = new ControleurConsulterLivre(app);
        for(Livre livreMag:livres){
            this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag,i));
            i++;
        }

        TextField recherche = (TextField)scene.lookup("#recherche");
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            position = 1;
            this.like = newValue;
            maj();
        });
    
        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneClient();
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
        int i = position;
        this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(),like,position,20);
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

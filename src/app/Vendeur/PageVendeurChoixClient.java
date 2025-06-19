package app.Vendeur;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import app.App;
import app.Client.ControleurConsulterLivre;
import app.Display.ClientDisplay;
import app.Display.LivreDisplayLigne;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Client;
import modele.Commande;
import modele.Livre;

public class PageVendeurChoixClient {
    private Scene scene;
    private ScrollPane scroll;
    private String nomLike;
    private String prenomLike;
    private List<Client> clients;
    private int position;
    private VBox lignes;
    private Label page;
    private App app;

    public PageVendeurChoixClient(App app)throws IOException{
        this.app = app;
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurChoixClient.fxml"));
        this.scene = new Scene(root);
        this.position = 0;
        this.nomLike = "";
        this.prenomLike="";
        App.commande = new Commande(0, null, false, false, null, App.vendeur.getMagasin());

        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.scenePageVendeurAccueil();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
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

        TextField nom =(TextField)scene.lookup("#nom");
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            position = 0;
            this.nomLike = newValue;
            maj();
        });
        TextField prenom =(TextField)scene.lookup("#prenom");
        prenom.textProperty().addListener((observable, oldValue, newValue) -> {
            position = 0;
            this.prenomLike = newValue;
            maj();
        });
        this.scroll = (ScrollPane) this.scene.lookup("#test");
        this.clients = App.clientBD.rechercheClient(prenomLike, nomLike,20,position);
        this.lignes = (VBox) this.scroll.getContent();
        int i = position+1;
        ControleurChoixClient controleur = new ControleurChoixClient(app);
        for(Client cli:clients){
            this.lignes.getChildren().add(new ClientDisplay(controleur, cli,i));
            i++;
        }

        Button prec = (Button)scene.lookup("#prec");
        prec.setOnAction(e -> {
            if(position-20>=0){
                position -=20;
            }
            maj();
        });

        Button suiv = (Button)scene.lookup("#suiv");
        suiv.setOnAction(e -> {
            if(position<App.clientBD.getNbClientLike(this.prenomLike,this.nomLike)-20){
                position+=20;
            }
            maj();
        });

        this.page = (Label)scene.lookup("#page");
        int page1 = (position/20)+1;
        int page2 = (App.clientBD.getNbClientLike(this.prenomLike,this.nomLike)/20-1)+1;
        this.page.setText(page1+"/"+page2);
    }

    public Scene getScene(){
        return this.scene;
    }

    public void maj(){
        int i = position+1;
        this.clients = App.clientBD.rechercheClient(prenomLike, nomLike,20,position);
        this.lignes.getChildren().clear();
        ControleurChoixClient controleur = new ControleurChoixClient(app);
        for(Client cli:clients){
            this.lignes.getChildren().add(new ClientDisplay(controleur, cli,i));
            i++;
        }

        int page1 = (position/20)+1;
        int page2 = (App.clientBD.getNbClientLike(this.prenomLike,this.nomLike)/20-1)+1;
        this.page.setText(page1+"/"+page2);
    }
}

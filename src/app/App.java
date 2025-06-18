package app;
import BD.*;
import app.Admin.PageAdmin;
import app.Client.PageClient;
import app.Client.PageClientCatalogue;
import app.Client.PageConsultationCommande;
import app.Client.PageConsultationLivre;
import app.Client.PageConsultationPanier;
import app.Menu.PageAcceuil;
import app.Menu.PageConnexion;
import app.Vendeur.PageVendeurAccueil;
import app.Vendeur.PageVendeurAjouterLivre;
import app.Vendeur.PageVendeurGererStocks;
import app.Vendeur.PageVendeurMajQte;
import app.Vendeur.PageVendeurVoirStocks;
import app.Vendeur.PageVendeurCommande;
import modele.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{
    private Stage primaryStage;
    public static ClientBD clientBD;
    public static VendeurBD vendeurBD;
    public static MagasinBD magasinBD;
    public static CommandeBD commandeBD;
    public static LivreBD livreBD;
    public static Client client;
    public static Commande commande;
    public static Magasin magasin;
    public static Vendeur veudeur;
    public static Livre livre;
    public static Vendeur vendeur;
    private ConnexionMySQL con;

    @Override
    public void init(){
        Properties props = new Properties();
        try {
            // 1. Charger le fichier de configuration
            FileInputStream in = new FileInputStream("config.properties");
            props.load(in);
            in.close();

            // 2. Récupérer les paramètres
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            String host = props.getProperty("db.host");
            String database = props.getProperty("db.database");

            this.con = new ConnexionMySQL();
            this.con.connecter(host, database, user,password);
            App.clientBD = new ClientBD(this.con);
            App.vendeurBD = new VendeurBD(this.con);
            App.magasinBD = new MagasinBD(con);
            App.commandeBD = new CommandeBD(con);
            App.livreBD = new LivreBD(con);
            App.commande = null;
            App.client = null;
            App.magasin = null;
            App.livre = null;
            App.veudeur = null;
        }
        catch (Exception e) {
            System.out.println("Problème de connexion à la BD : "+e.getMessage());
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Librairie Livre Express");
        sceneAcceuil();
        primaryStage.show();
    }

    public void sceneAcceuil(){
        PageAcceuil acceuil = new PageAcceuil(this);
        primaryStage.setScene(acceuil.getScene());
    }

    public void sceneConnexion(String utilisateur)throws IOException{
        PageConnexion page = new PageConnexion(this,utilisateur);
        primaryStage.setScene(page.getScene());
    }

    public void sceneClient()throws IOException{
        PageClient page = new PageClient(this);
        primaryStage.setScene(page.getScene());
    }

    //Scenes de Vendeur
    public void scenePageVendeurAccueil()throws IOException{
        PageVendeurAccueil page = new PageVendeurAccueil(this);
        primaryStage.setScene(page.getScene());
    }
    public void scenePageVendeurGererStocks()throws IOException{
        PageVendeurGererStocks page = new PageVendeurGererStocks(this);
        primaryStage.setScene(page.getScene());
    }
    public void scenePageVendeurAjouterLivre()throws IOException{
        PageVendeurAjouterLivre page = new PageVendeurAjouterLivre(this);
        primaryStage.setScene(page.getScene());
    }
    public void scenePageVendeurMajQte()throws IOException{
        PageVendeurMajQte page = new PageVendeurMajQte(this);
        primaryStage.setScene(page.getScene());
    }
    public void scenePageVendeurVoirStocks()throws IOException{
        PageVendeurVoirStocks page = new PageVendeurVoirStocks(this);
        primaryStage.setScene(page.getScene());
    }

    
    public void sceneAdmin()throws IOException{
        PageAdmin page = new PageAdmin(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneMagasin()throws IOException{
        PageClientCatalogue page = new PageClientCatalogue(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneConsultationLivre()throws IOException{
        PageConsultationLivre page = new PageConsultationLivre(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneConsultationCommande()throws IOException{
        PageConsultationCommande page = new PageConsultationCommande(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneConsultationPanier()throws IOException{
        PageConsultationPanier page = new PageConsultationPanier(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageVendeurCommande()throws IOException{
        PageVendeurCommande page = new PageVendeurCommande(this);
        primaryStage.setScene(page.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

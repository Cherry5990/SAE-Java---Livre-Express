package app;
import BD.*;
import app.Admin.PageAdmin;
import app.Admin.PageAdminAjouterLibrairie;
import app.Admin.PageAdminGererStock;
import app.Admin.PageAdminTransfererLivre;
import app.Admin.PageAdminVoirStock;
import app.Admin.PageAdminAjouterVendeur;
import app.Admin.PageAdminConsulterStat;
import app.Client.PageClient;
import app.Client.PageClientCatalogue;
import app.Client.PageClientPanierv2;
import app.Client.PageClientRecommande;
import app.Client.PageConsultationCommande;
import app.Client.PageConsultationLivre;
import app.Client.PageConsultationPanier;
import app.Menu.PageClientCreerCompte;
import app.Menu.PageAcceuil;
import app.Menu.PageConnexion;
import app.Vendeur.PageVendeurAccueil;
import app.Vendeur.PageVendeurAjouterLivre;
import app.Vendeur.PageVendeurChoixClient;
import app.Vendeur.PageVendeurGererStocks;
import app.Vendeur.PageVendeurMajQte;
import app.Vendeur.PageVendeurTransfererLivre;
import app.Vendeur.PageVendeurVoirStocks;
import app.Vendeur.PageVendeurCommande;
import modele.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public static ReseauBD reseauBD;
    public static AdminBD adminBD;
    public static Client client;
    public static Commande commande;
    public static Magasin magasin;
    public static Livre livre;
    public static Vendeur vendeur;
    public static Reseau reseau;
    public static Map<Client,Map<Magasin,Commande>> memoiresCommandesClient;
    public static Map<Client,List<Livre>> recoClient;

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

            ConnexionMySQL con = new ConnexionMySQL();
            con.connecter(host, database, user,password);
            App.clientBD = new ClientBD(con);
            App.vendeurBD = new VendeurBD(con);
            App.magasinBD = new MagasinBD(con);
            App.commandeBD = new CommandeBD(con);
            App.livreBD = new LivreBD(con);
            App.reseauBD = new ReseauBD(con);
            App.adminBD = new AdminBD(con);
            App.commande = null;
            App.client = null;
            App.magasin = null;
            App.livre = null;
            App.vendeur = null;
            App.reseau = null;
            App.memoiresCommandesClient = new HashMap<>();
            App.recoClient = new HashMap<>();
        }
        catch (Exception e) {
            System.out.println("Problème de connexion à la BD : "+e.getMessage());
        }

    }
    public void popUpMessageDeconnexion(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Déconnextion"); 
        alert.setContentText("êtes vous sur de vous déconnecter?"); 
        Optional<ButtonType> reponse = alert.showAndWait();
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                    sceneAcceuil();
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

    public void sceneCreation()throws IOException{
        PageClientCreerCompte page = new PageClientCreerCompte(this);
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
    public void scenePageVendeurTransfererLivre()throws IOException{
        PageVendeurTransfererLivre page = new PageVendeurTransfererLivre(this);
        primaryStage.setScene(page.getScene());
    }
    public void scenePageVendeurChoixClient()throws IOException{
        PageVendeurChoixClient page = new PageVendeurChoixClient(this);
        primaryStage.setScene(page.getScene());
    }
    

    //Scenes d'Admin
    public void sceneAdmin()throws IOException{
        PageAdmin page = new PageAdmin(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageAdminGererStock() throws IOException {
        PageAdminGererStock page = new PageAdminGererStock(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageAdminTransfererLivre() throws IOException {
        PageAdminTransfererLivre page = new PageAdminTransfererLivre(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageAdminVoirStock() throws IOException {
        PageAdminVoirStock page = new PageAdminVoirStock(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneAjouterVendeur(){
        PageAdminAjouterVendeur page = new PageAdminAjouterVendeur(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageAdminConsulterStat(){
        PageAdminConsulterStat page = new PageAdminConsulterStat(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneAjouterMagasin(){
        PageAdminAjouterLibrairie page = new PageAdminAjouterLibrairie(this);
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
        PageClientPanierv2 page = new PageClientPanierv2(this);
        primaryStage.setScene(page.getScene());
    }

    public void scenePageVendeurCommande()throws IOException{
        PageVendeurCommande page = new PageVendeurCommande(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneConsultationRecommande()throws IOException{
        PageClientRecommande page = new PageClientRecommande(this);
        primaryStage.setScene(page.getScene());
    }
     
    


    public static void main(String[] args) {
        launch(args);
    }
}

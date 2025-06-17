package app;
import BD.*;
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
        }
        catch (Exception e) {
            System.out.println("Problème de connexion à la BD : "+e.getMessage());
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Librarie Livre Express");
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

    public void sceneClient(Client c)throws IOException{
        PageClient page = new PageClient(this,c);
        primaryStage.setScene(page.getScene());
    }

    public void sceneVendeur(Vendeur v)throws IOException{
        PageVendeur page = new PageVendeur(this,v);
        primaryStage.setScene(page.getScene());
    }

    public void sceneAdmin()throws IOException{
        PageAdmin page = new PageAdmin(this);
        primaryStage.setScene(page.getScene());
    }

    public void sceneMagasin(Client c,Magasin mag)throws IOException{
        PageClientCatalogue page = new PageClientCatalogue(this,c, mag);
        primaryStage.setScene(page.getScene());
    }

    public void sceneConsultationLivre(Livre livre)throws IOException{
        PageConsultationLivre page = new PageConsultationLivre(this, livre);
        primaryStage.setScene(page.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

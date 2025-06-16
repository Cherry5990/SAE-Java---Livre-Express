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
import javafx.stage.Stage;

public class App extends Application{
    private Stage primaryStage;
    public static ClientBD clientBD;
    public static VendeurBD vendeurBD;
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
            this.con.connecter(host, database, user,password );
        }
        catch (Exception e) {
            System.out.println("Problème de connexion à la BD : "+e.getMessage());
        }
        App.clientBD = new ClientBD(this.con);
        App.vendeurBD = new VendeurBD(this.con);

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Librarie Livre Express");
        sceneAcceuil();
        primaryStage.show();
    }

    public void sceneAcceuil(){
        try{
            PageAcceuil acceuil = new PageAcceuil(this);
            primaryStage.setScene(acceuil.getScene());
        }
        catch(IOException e){
            System.out.println("problème scenAceuil()");
        }
    }

    public void sceneConnexion(String utilisateur)throws IOException{
        PageConnexion page = new PageConnexion(this,utilisateur);
        primaryStage.setScene(page.getScene());
    }

    public void sceneClient(Client c){
        PageClient page = new PageClient(this,c);
        primaryStage.setScene(page.getScene());
    }

    public void sceneVendeur(Vendeur v){
        PageVendeur page = new PageVendeur(this,v);
        primaryStage.setScene(page.getScene());
    }

    public void sceneAdmin(){
        PageAdmin page = new PageAdmin(this);
        primaryStage.setScene(page.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

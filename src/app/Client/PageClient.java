package app.Client;

import app.App;
import modele.Client;
import modele.Commande;
import modele.Livre;
import modele.Magasin;
import app.Display.CommandeDisplay;
import app.Display.LivreDisplay;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class PageClient {
    private Scene scene;
    private ComboBox<String> magasin;
    private HBox livres;
    private int positionLivre; 
    private List<Commande> commandes;
    private HBox hBoxCommande;
    private int positionCommande;
    private App app;
    private String magasinChoix;

    public PageClient(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientAccueil.fxml"));
        this.scene = new Scene(root);
        this.app = app;
        this.magasinChoix = "";

        this.magasin = (ComboBox<String>) this.scene.lookup("#comboMag");

        List<Magasin> magasins = App.magasinBD.getAllMagasins();
        for(Magasin mag:magasins){
            this.magasin.getItems().add(mag.getNomMagasin());
        }
        this.magasin.setOnAction(event -> {
            String selectedMagasin = this.magasin.getValue();
            this.setMagasinChoix(selectedMagasin);
        });

        Button deco = (Button)scene.lookup("#deconnexion");
        deco.setOnAction(e -> {
            app.popUpMessageDeconnexion();
        });

        Button connecter = (Button) this.scene.lookup("#connexion");
        connecter.setOnAction(new ControleurConnexionMagasin(this,app));
        connecter.setOnMouseEntered(e -> connecter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        connecter.setOnMouseExited(e -> connecter.setStyle("-fx-background-color: a6897e;"));

        Button suivant = (Button) this.scene.lookup("#suivant");
        suivant.setOnAction(new ControleurSuivant(this));
        suivant.setOnMouseEntered(e -> suivant.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-scale-x: 1.08; -fx-scale-y: 1.08; -fx-effect: dropshadow(gaussian, #1976D2, 10, 0.5, 0, 2);"));
        suivant.setOnMouseExited(e -> suivant.setStyle("-fx-background-color: transparent;"));

        Button avant = (Button) this.scene.lookup("#avant");
        avant.setOnAction(new ControleurAvant(this));
        avant.setOnMouseEntered(e -> avant.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-scale-x: 1.08; -fx-scale-y: 1.08; -fx-effect: dropshadow(gaussian, #1976D2, 10, 0.5, 0, 2);"));
        avant.setOnMouseExited(e -> avant.setStyle("-fx-background-color: transparent;"));

        Button suivant1 = (Button) this.scene.lookup("#suivant1");
        suivant1.setOnAction(new ControleurSuivant1(this));
        suivant1.setOnMouseEntered(e -> suivant1.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-scale-x: 1.08; -fx-scale-y: 1.08; -fx-effect: dropshadow(gaussian, #1976D2, 10, 0.5, 0, 2);"));
        suivant1.setOnMouseExited(e -> suivant1.setStyle("-fx-background-color: transparent;"));

        Button avant1 = (Button) this.scene.lookup("#avant1");
        avant1.setOnAction(new ControleurAvant1(this));
        avant1.setOnMouseEntered(e -> avant1.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-scale-x: 1.08; -fx-scale-y: 1.08; -fx-effect: dropshadow(gaussian, #1976D2, 10, 0.5, 0, 2);"));
        avant1.setOnMouseExited(e -> avant1.setStyle("-fx-background-color: transparent;"));

        this.positionLivre=0;
        
        this.livres = (HBox) this.scene.lookup("#livres");
        int count = Math.min(7, App.recoClient.get(App.client).size());
        for (int i = 0; i < count; i++) {
            Livre livreData = App.recoClient.get(App.client).get(i);
            if (livreData != null) {
                LivreDisplay livre = new LivreDisplay(new ControleurLivreRecommande(app,livreData), livreData);
                this.livres.getChildren().add(livre);
            }
        }


        try{
            this.positionCommande=0;
            this.commandes= App.commandeBD.CommandeClient(App.client.getId());
            this.hBoxCommande = (HBox) this.scene.lookup("#commandes");
            int count2 = Math.min(7, commandes.size());
            for (int i = 0; i < count2; i++) {
                Commande commandedata = commandes.get(i);
                if (commandedata != null) {
                    CommandeDisplay com = new CommandeDisplay(new ControleurConsulterCommande(app,commandedata), commandedata);
                    this.hBoxCommande.getChildren().add(com);
                }
            }
        }
        catch(SQLException e){
            System.out.println("Pas de commande"+e.getMessage());
        }
    }

    public String getMagasinChoix(){
        return this.magasinChoix;
    }

    public void majRecommandation() {
        this.livres.getChildren().clear();
        int count = Math.min(7, App.recoClient.get(App.client).size() - positionLivre);
        for (int j = 0; j < count; j++) {
            Livre livreData = App.recoClient.get(App.client).get(positionLivre + j);
            if (livreData != null) {
                LivreDisplay livre = new LivreDisplay(new ControleurLivreRecommande(app,livreData), livreData);
                this.livres.getChildren().add(livre);
            }
        }
    }

    public void majCommande() {
        this.hBoxCommande.getChildren().clear();
        int count = Math.min(7, commandes.size() - positionCommande);
        for (int j = 0; j < count; j++) {
            Commande dataCommande = commandes.get(positionCommande + j);
            if (dataCommande!= null) {
                CommandeDisplay com = new CommandeDisplay(new ControleurConsulterCommande(app, dataCommande),dataCommande);
                this.hBoxCommande.getChildren().add(com);
            }
        }
    }

    public Alert popUpMessageDeconnexion(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Déconnection"); 
        alert.setContentText("êtes vous sur de vous déconnecter?");     
        return alert;
    }
  
    public void setMagasinChoix(String mag){
        this.magasinChoix = mag;
    }


    public void setPositionLivre(int pos){
        this.positionLivre=pos;
    }
    

    public Scene getScene(){
        return this.scene;
    }

    public Client getClient() {
        return App.client;
    }

    public Integer getPositionLivre(){
        return this.positionLivre;
    }

    public int getNbLivre(){
        return App.recoClient.get(App.client).size();
    }

    public void setPositionCommande(int pos){
        this.positionCommande = pos;
    }


    public Integer getPositionCommande(){
        return this.positionCommande;
    }

    public int getNbCommande(){
        return this.commandes.size();
    }

}
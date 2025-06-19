package app.Vendeur;

import java.io.IOException;
import java.util.List;

import app.App;
import app.Client.ControleurConsulterLivre;
import app.Display.LivreDisplayLigne;
import app.Display.LivreDisplayLigneVendeur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Commande;
import modele.Livre;

public class PageVendeurVoirStocks {
    private Scene scene;
    private ScrollPane scroll;
    private List<Livre> livres;
    private VBox lignes;

    public PageVendeurVoirStocks(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurVoirStocks.fxml"));
        this.scene = new Scene(root);
        App.commande = new Commande(0, null, false, false, App.client, App.magasin);
        this.livres = App.magasinBD.getAllLivre(App.vendeur.getMagasin().getIdMagasin());
        int i = 1;
        this.scroll = (ScrollPane) this.scene.lookup("#test");
        this.lignes = (VBox) this.scroll.getContent();
        ControleurConsulterLivreVendeur controleur = new ControleurConsulterLivreVendeur(app);
        for(Livre livreMag:livres){
            this.lignes.getChildren().add(new LivreDisplayLigneVendeur(controleur, livreMag,i));
            i++;
        }

        TextField recherche = (TextField)scene.lookup("#recherche");
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(), newValue);
            this.lignes.getChildren().clear();
            int j = 1;
            for(Livre livreMag:this.livres){
                this.lignes.getChildren().add(new LivreDisplayLigneVendeur(controleur, livreMag, j));
                j++;
            }
            System.out.println("Recherche : " + newValue);
        });
    
        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.scenePageVendeurGererStocks();
            } 
            catch (IOException e1) {
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
    }

    public Scene getScene(){
        return this.scene;
    }
}

package app.Admin;

import java.io.IOException;
import java.util.List;

import app.App;
import app.Display.LivreDisplayLigne;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Commande;
import modele.Livre;


public class PageAdminVoirStock {
    private Scene scene;
    private ScrollPane scroll;
    private List<Livre> livres;
    private VBox lignes;

    public PageAdminVoirStock(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminVoirStock.fxml"));
        this.scene = new Scene(root);
        App.commande = new Commande(0, null, false, false, App.client, App.magasin);
        this.livres = App.reseauBD.voirStockReseau();
        int i = 1;
        this.lignes = (VBox) this.scroll.getContent();
        ControleurConsulterLivreAdmin controleur = new ControleurConsulterLivreAdmin(app);
        for(Livre livreMag:livres){
            this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag,i));
            i++;
        }

        TextField recherche = (TextField)scene.lookup("#recherche");
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            this.livres = App.magasinBD.rechercheLivre(App.magasin.getIdMagasin(), newValue);
            this.lignes.getChildren().clear();
            int j = 1;
            for(Livre livreMag:this.livres){
                this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag, j));
                j++;
            }
            System.out.println("Recherche : " + newValue);
        });
        

        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.scenePageAdminGererStock();
            } 
            catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });
    }
    public Scene getScene(){
        return this.scene;
    }
}

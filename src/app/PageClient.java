package app;
import modele.Client;
import modele.Livre;
import modele.Magasin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PageClient {
    private Scene scene;
    private ComboBox<String> magasin;
    private Client client;
    private List<Livre> recomandation;
    private HBox livres;
    private int position; 

    public PageClient(App app,Client c)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageAccueilClient.fxml"));
        this.scene = new Scene(root);
        this.client = c;

        this.magasin = (ComboBox<String>) this.scene.lookup("#comboMag");
        List<Magasin> magasins = App.magasinBD.getAllMagasins();
        for(Magasin mag:magasins){
            this.magasin.getItems().add(mag.getNomMagasin());
        }

        Button connecter = (Button) this.scene.lookup("#connexion");
        connecter.setOnAction(new ControleurConnexionMagasin(this,app));

        try{
            this.position=0;
            this.recomandation = App.clientBD.getRecommandationClient(c.getId());
            this.livres = (HBox) this.scene.lookup("#livres");
            LivreDisplay livre1 = new LivreDisplay(recomandation.get(0));
            LivreDisplay livre2 = new LivreDisplay(recomandation.get(1));
            LivreDisplay livre3 = new LivreDisplay(recomandation.get(2));
            this.livres.getChildren().addAll(livre1,livre2,livre3);
        }
        catch(SQLException e){
            System.out.println("caca");
        }
    }

    public Magasin getMagasin() throws SQLException{
        return App.magasinBD.getMagasin(this.magasin.getValue());
    }

    public void majLivre(int debut){
        int index = 0;
        for(int i = debut;i<debut+3;i++){
            if (i<recomandation.size()){
                this.panes.get(index).getChildren().add(new LivreDisplay(recomandation.get(i)));
                index++;
            }
        }
    }

    

    public Scene getScene(){
        return this.scene;
    }

    public Client getClient() {
        return client;
    }
}
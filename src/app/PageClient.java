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
        Pane root = FXMLLoader.load(getClass().getResource("view/Client/PageClientAccueil.fxml"));
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
            int count = Math.min(3, recomandation.size());
            for (int i = 0; i < count; i++) {
                LivreDisplay livre = new LivreDisplay(recomandation.get(i));
                this.livres.getChildren().add(livre);
            }
        }
        catch(SQLException e){
            System.out.println("caca");
        }
    }

    public Magasin getMagasin() throws SQLException{
        return App.magasinBD.getMagasin(this.magasin.getValue());
    }

    public void majLivre(){
        this.livres = (HBox) this.scene.lookup("#livres");
        this.livres.getChildren().clear();
        for (int j = 0; j < 3; j++) {
            if (position + j < recomandation.size()) {
                LivreDisplay livre = new LivreDisplay(recomandation.get(position + j));
                this.livres.getChildren().add(livre);
            }
        }
        position+=3;
    }

    public void setPosition(int pos){
        this.position=pos;
    }
    

    public Scene getScene(){
        return this.scene;
    }

    public Client getClient() {
        return client;
    }
}
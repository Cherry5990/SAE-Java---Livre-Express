package app;
import modele.Client;
import modele.Magasin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class PageClient {
    private Scene scene;
    private ComboBox<String> magasin;
    private Client client;

    public PageClient(App app,Client c)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageClient.fxml"));
        this.scene = new Scene(root);
        this.client = c;

        this.magasin = (ComboBox<String>) this.scene.lookup("#choixMagasin");
        List<Magasin> magasins = App.magasinBD.getAllMagasins();
        for(Magasin mag:magasins){
            this.magasin.getItems().add(mag.getNomMagasin());
        }

        Button connecter = (Button) this.scene.lookup("#connecter");
        connecter.setOnAction(new ControleurConnexionMagasin(this,app));


        try{
            TilePane livreRecommader = (TilePane) this.scene.lookup("#recommander");
            livreRecommader = new TileLivre(App.clientBD.getRecommandationClient(c.getId()));
        }
        catch(SQLException e){
            System.out.println("jamais l'expection se lance");
        }
        
    }

    public Magasin getMagasin() throws SQLException{
        return App.magasinBD.getMagasin(this.magasin.getValue());
    }

    

    public Scene getScene(){
        return this.scene;
    }

    public Client getClient() {
        return client;
    }
}
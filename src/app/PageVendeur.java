package app;
import modele.Vendeur;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageVendeur {
    private Scene scene;

    public PageVendeur(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageVendeur.fxml"));
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
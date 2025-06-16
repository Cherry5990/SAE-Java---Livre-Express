package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import modele.Client;
import modele.Magasin;

public class PageMagasin {
    private Scene scene;

    public PageMagasin(App app,Client c,Magasin mag)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageMagasin.fxml"));
        this.scene = new Scene(root);

    }


    public Scene getScene(){
        return this.scene;
    }
}

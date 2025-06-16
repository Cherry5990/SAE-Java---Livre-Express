package app;
import modele.Client;

import java.io.IOError;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageAdmin {
    private Scene scene;

    public PageAdmin(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageClient.fxml"));
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
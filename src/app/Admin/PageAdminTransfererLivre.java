package app.Admin;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageAdminTransfererLivre {
    private Scene scene;

    public PageAdminTransfererLivre(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminTransfererLivre.fxml"));
        this.scene = new Scene(root);
        
    }

    public Scene getScene(){
        return this.scene;
    }
}
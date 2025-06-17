package app.Client;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageConsultationPanier {
    private Scene scene;

    public PageConsultationPanier(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PagePanierClient.fxml"));
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}

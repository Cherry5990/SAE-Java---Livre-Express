package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Client;
import modele.Magasin;
import modele.Livre;

public class PageConsultationLivre {
    private Scene scene;
    private Livre livre;

    public PageConsultationLivre(App app, Livre livre) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/Client/PageClientConsultationLivre.fxml"));
        this.scene = new Scene(root);
        this.livre = livre;
    }

    public Scene getScene(){
        return this.scene;
    }
}
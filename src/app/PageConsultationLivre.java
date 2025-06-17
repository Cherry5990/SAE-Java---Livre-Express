package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import modele.Client;
import modele.Magasin;
import modele.Livre;

public class PageConsultationLivre {
    private Scene scene;
    private Livre livre;

    public PageConsultationLivre(Application app, Livre livre) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageConsultationLivre.fxml"));
        this.scene = new Scene(root);
        this.livre = livre;
    }

    public Scene getScene(){
        return this.scene;
    }
}
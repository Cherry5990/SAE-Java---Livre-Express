package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import modele.Livre;

public class PageConsultationLivre {
    private Scene scene;

    public PageConsultationLivre(Application app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/Client/PageConsultationLivre.fxml"));
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
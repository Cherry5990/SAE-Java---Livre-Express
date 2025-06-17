package app.Client;

import java.io.IOException;

import app.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import modele.Auteur;
import modele.Livre;
import modele.Theme;

public class PageConsultationLivre {
    private Scene scene;

    public PageConsultationLivre(Application app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientConsultationLivre.fxml"));
        this.scene = new Scene(root);
        Text isbn = (Text) this.scene.lookup("#isbn");
        Text pages = (Text) this.scene.lookup("#pages");
        Text prix = (Text) this.scene.lookup("#prix");
        Text auteur = (Text) this.scene.lookup("#auteur");
        Text theme = (Text) this.scene.lookup("#theme");
        isbn.setText(App.livre.getIsbn());
        pages.setText(App.livre.getNbPages()+"");
        prix.setText(App.livre.getPrix()+"");
    }

    public Scene getScene(){
        return this.scene;
    }
}
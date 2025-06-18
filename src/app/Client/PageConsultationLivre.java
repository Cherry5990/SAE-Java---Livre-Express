package app.Client;

import java.io.IOException;

import app.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import modele.Auteur;
import modele.Livre;
import modele.Theme;

public class PageConsultationLivre {
    private Scene scene;
    private Spinner<Integer> qte;

    public PageConsultationLivre(App app) throws IOException{
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

        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(new ControleurAjouterPanier(app, this));

        Label titre = (Label)scene.lookup("#titre");
        titre.setText(App.livre.getTitre());

        this.qte = (Spinner<Integer>) scene.lookup("#quantite");
        this.qte.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, App.magasinBD.getQte(App.livre.getIsbn(),App.magasin.getIdMagasin()), 1));
    }

    public Scene getScene(){
        return this.scene;
    }

    public Integer getQte(){
        return this.qte.getValue();
    }
}
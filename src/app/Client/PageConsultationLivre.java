package app.Client;

import java.io.IOException;
import java.sql.SQLException;

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
import modele.Auteur;
import modele.Livre;
import modele.Theme;

public class PageConsultationLivre {
    private Scene scene;
    private Spinner<Integer> qte;

    public PageConsultationLivre(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientConsultationLivre.fxml"));
        this.scene = new Scene(root);
        Label isbn = (Label) this.scene.lookup("#isbn");
        Label pages = (Label) this.scene.lookup("#nbpage");
        Label prix = (Label) this.scene.lookup("#prix");
        Label auteur = (Label) this.scene.lookup("#auteur");
        Label theme = (Label) this.scene.lookup("#theme");
        Label editeur = (Label) this.scene.lookup("#editeur");
        isbn.setText(App.livre.getIsbn());
        pages.setText(App.livre.getNbPages()+"");
        prix.setText(App.livre.getPrix()+"");
        try{
            auteur.setText(App.livreBD.getAuteur(App.livre.getIsbn()));
            theme.setText(App.livreBD.getTheme(App.livre.getIsbn()));
            editeur.setText(App.livreBD.getEditeur(App.livre.getIsbn()));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        Button ajouter = (Button) scene.lookup("#ajouter");
        ajouter.setOnAction(new ControleurAjouterPanier(app, this));

        Label titre = (Label)scene.lookup("#titre");
        titre.setText(App.livre.getTitre());

        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e ->{
            try {
                app.sceneMagasin();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

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
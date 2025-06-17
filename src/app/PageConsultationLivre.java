package app;

import java.io.IOException;

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
        Pane root = FXMLLoader.load(getClass().getResource("view/Client/PageConsultationLivre.fxml"));
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }

    private void ajoutInfoLivre(){  
        Text isbn = (Text) this.scene.lookup("#isbn");
        //isbn.setText(livre.getIsbn());
        Text pages = (Text) this.scene.lookup("#pages");
        //pages.setText("" + livre.getNbPages());
        Text prix = (Text) this.scene.lookup("#prix");
        //prix.setText("" + livre.getPrix());
        Text auteur = (Text) this.scene.lookup("#auteur");
        Text theme = (Text) this.scene.lookup("#theme");
        String auteurs = "";
        String themes = "";
        //for (Auteur unAuteur : livre.getAuteurs()){
        //    auteurs += unAuteur.getNomAuteur() + " ";
        //}
        //for (Theme unTheme : livre.getThemes()){
        //    auteurs += unTheme.getNom() + " ";
        //}
        auteur.setText(auteurs);
        theme.setText(themes);
    }
}
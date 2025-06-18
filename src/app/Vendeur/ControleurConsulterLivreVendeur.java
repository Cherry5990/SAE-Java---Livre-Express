package app.Vendeur;

import java.io.IOException;

import app.App;
import app.Display.LivreDisplay;
import app.Display.LivreDisplayLigne;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import modele.Livre;

public class ControleurConsulterLivreVendeur implements EventHandler<ActionEvent> {
    private App app;

    public ControleurConsulterLivreVendeur(App app){
        this.app = app;
    }

    public void handle(ActionEvent event){
        LivreDisplay boutonLivre = (LivreDisplay) event.getSource();
        Livre l = boutonLivre.getLivre();
        App.livre = l;
        try{
            app.scenePageVendeurMajQte();
        }
        catch(IOException e){
            System.out.println("Problème de fxml");
        }
    }
}

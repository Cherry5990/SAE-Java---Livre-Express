package app.Client;

import java.io.IOException;

import app.App;
import app.Display.LivreDisplay;
import app.Display.LivreDisplayLigne;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Livre;

public class ControleurConsulterLivre implements EventHandler<ActionEvent> {
    private App app;

    public ControleurConsulterLivre(App app){
        this.app = app;
    }

    public void handle(ActionEvent event){
        LivreDisplayLigne boutonLivre = (LivreDisplayLigne) event.getSource();
        App.livre = boutonLivre.getLivre();
        try{
            app.sceneConsultationLivre();
        }
        catch(IOException e){
            System.out.println("Problème de fxml");
        }
    }
}

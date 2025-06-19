package app.Client;

import java.io.IOException;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Livre;

public class ControleurLivreRecommande implements EventHandler<ActionEvent> {
    private App app;
    private Livre livre;

    public ControleurLivreRecommande(App app,Livre livre){
        this.app = app;
        this.livre = livre;
    }

    public void handle(ActionEvent event){
        App.livre=livre;
        try{
            app.sceneConsultationRecommande();
        }
        catch(IOException e){
            System.out.println("Problème de fxml");
        }
    }
}
package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Commande;

public class ControleurConsulterCommande implements EventHandler<ActionEvent> {
    private PageClient vue;
    private App app;
    private Commande c;

    public ControleurConsulterCommande(App app,PageClient vue,Commande c){
        this.vue = vue;
        this.app = app;
        this.c = c;
    }

    public void handle(ActionEvent event){
        try{
            app.sceneConsultationCommande();
        }
        catch(IOException ex){
            System.out.println("problème fxml");
        }
    }
}

package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurConsulterLivre implements EventHandler<ActionEvent> {
    private App vue;

    public ControleurConsulterLivre(App vue){
        this.vue = vue;
    }

    public void handle(ActionEvent event){
        LivreDisplay boutonLivre = (LivreDisplay) event.getSource();
        try{
            vue.sceneConsultationLivre(boutonLivre.getLivre());
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}

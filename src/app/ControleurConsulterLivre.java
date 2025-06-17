package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurConsulterLivre implements EventHandler<ActionEvent> {
    private Application vue;

    public ControleurConsulterLivre(Application vue){
        this.vue = vue;
    }

    public void handle(ActionEvent event){
        LivreDisplay boutonLivre = (LivreDisplay) event.getSource();
        try{
            if (vue instanceof Test){
                Test vueTest = (Test) vue;
                vueTest.pageConsultationLivre(boutonLivre.getLivre());
            } else if (vue instanceof App){
                App vueTest = (App) vue;
                vueTest.sceneConsultationLivre();
            }
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}

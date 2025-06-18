package app.Admin;

import java.io.IOException;

import app.App;
import app.Display.LivreDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Livre;

public class ControleurConsulterLivreAdmin implements EventHandler<ActionEvent>{
    private App app;

    public ControleurConsulterLivreAdmin(App app){
        this.app = app;
    }

    public void handle(ActionEvent event){
        LivreDisplay boutonLivre = (LivreDisplay) event.getSource();
        Livre l = boutonLivre.getLivre();
        App.livre = l;
        try{
            app.scenePageAdminTransfererLivre();;
        }
        catch(IOException e){
            System.out.println("Problème de fxml");
        }
    }
}

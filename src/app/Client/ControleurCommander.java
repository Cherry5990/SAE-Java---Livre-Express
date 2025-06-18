package app.Client;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;

public class ControleurCommander implements EventHandler<ActionEvent>{
    private App app;
    private PageConsultationPanier vue;

    public ControleurCommander(PageConsultationPanier vue,App app){
        this.vue=vue;
        this.app = app;
    }

    @Override
    public void handle(ActionEvent e) {
        try{
            App.commande.setEnLigne(true);
            App.commande.setLivraison(vue.getLivraison());
            App.commandeBD.insererCommande(App.commande);
            app.sceneMagasin();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch(IOException ex2){
            System.out.println(ex2.getMessage());
        }

    }
}
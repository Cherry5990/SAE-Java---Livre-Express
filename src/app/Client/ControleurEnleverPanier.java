package app.Client;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import app.Display.LivreDisplay;
import app.Display.LivreDisplayLigne;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.DetailCommande;
import modele.Livre;

public class ControleurEnleverPanier implements EventHandler<ActionEvent> {
    private App app;
    private PageConsultationPanier vue;
    private DetailCommande dc;

    public ControleurEnleverPanier(App app,PageConsultationPanier vue,DetailCommande dc){
        this.app = app;
        this.vue = vue;
        this.dc = dc;
    }

    public void handle(ActionEvent event){
        try{
            App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), dc.getQte(), App.magasin.getIdMagasin());
            App.commande.enleveLivre(dc.getLivre());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        vue.maj();
    }
}
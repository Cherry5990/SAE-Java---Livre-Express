package app.Client;

import java.io.IOException;

import app.App;
import app.Display.LivreDisplay;
import app.Display.LivreDisplayLigne;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Livre;

public class ControleurAjouterPanier implements EventHandler<ActionEvent> {
    private App app;
    private PageConsultationLivre vue;

    public ControleurAjouterPanier(App app,PageConsultationLivre vue){
        this.app = app;
        this.vue = vue;
    }

    public void handle(ActionEvent event){
        Integer qte = vue.getQte();
        App.magasinBD.enleveQteLivre(App.livre.getIsbn(),App.magasin.getIdMagasin(),qte);
        App.commande.ajouteLivre(App.livre, qte);
        try{
            app.sceneMagasin();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
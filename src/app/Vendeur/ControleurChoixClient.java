package app.Vendeur;

import java.io.IOException;

import app.App;
import app.Display.ClientDisplay;
import app.Display.LivreDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Client;
import modele.Livre;

public class ControleurChoixClient implements EventHandler<ActionEvent> {
    private App app;

    public ControleurChoixClient(App app){
        this.app = app;
    }

    public void handle(ActionEvent event){
        ClientDisplay boutonClient = (ClientDisplay) event.getSource();
        Client cli = boutonClient.getClient();
        App.client = cli;
        App.commande.setClient(cli);
        try{
            app.scenePageVendeurCommande();
        }
        catch(IOException e){
            System.out.println("Problème de fxml");
        }
    }
}
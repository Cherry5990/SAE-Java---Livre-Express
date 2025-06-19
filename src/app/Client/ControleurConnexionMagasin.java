package app.Client;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import modele.Commande;
import modele.Magasin;

public class ControleurConnexionMagasin implements EventHandler<ActionEvent>{
    private App app;
    private PageClient vue;

    public ControleurConnexionMagasin(PageClient vue,App app){
        this.app =app;
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent e){
        try {
            String nommag = vue.getMagasinChoix();
            App.magasin = App.magasinBD.getMagasin(nommag);
            if (App.memoiresCommandesClient.get(App.client).containsKey(App.magasin)){
                App.commande = App.memoiresCommandesClient.get(App.client).get(App.magasin);
            }
            else{
                App.commande = new Commande(0, "", false, false, App.client, App.magasin);
            }
            app.sceneMagasin();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

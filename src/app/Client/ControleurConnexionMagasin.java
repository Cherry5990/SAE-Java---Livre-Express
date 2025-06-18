package app.Client;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
        System.out.println("appuyer");
        try {
            String nommag = vue.getMagasinChoix();
            System.out.println(nommag);
            App.magasin = App.magasinBD.getMagasin(nommag);
            App.commande.setMagasin(App.magasin);
            if (App.magasin!=null){
                app.sceneMagasin();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

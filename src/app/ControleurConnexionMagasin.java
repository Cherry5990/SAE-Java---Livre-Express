package app;

import java.io.IOException;
import java.sql.SQLException;

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
            Magasin mag = App.magasinBD.getMagasin(nommag);
            if (mag!=null){
                app.sceneMagasin(vue.getClient(), mag);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

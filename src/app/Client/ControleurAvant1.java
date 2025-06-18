package app.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurAvant1 implements EventHandler<ActionEvent>{
    private PageClient vue;

    public ControleurAvant1(PageClient vue){
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent e) {
        if(vue.getPositionCommande()-7<0){
            vue.setPositionCommande(0);
        }
        else{
            vue.setPositionCommande(vue.getPositionCommande()-7);
        }
        vue.majCommande();
    }
}
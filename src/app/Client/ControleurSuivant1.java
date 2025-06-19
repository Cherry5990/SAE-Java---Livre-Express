package app.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurSuivant1 implements EventHandler<ActionEvent>{
    private PageClient vue;

    public ControleurSuivant1(PageClient vue){
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent e) {
        if(vue.getPositionCommande()+7<vue.getNbCommande()){
            vue.setPositionCommande(vue.getPositionCommande()+7);
        }
        vue.majCommande();
    }
}
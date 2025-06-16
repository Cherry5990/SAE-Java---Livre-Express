package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurSuivant implements EventHandler<ActionEvent>{
    private PageClient vue;

    public ControleurSuivant(PageClient vue){
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent e) {
        if(vue.getPositionLivre()+4<vue.getNbLivre()){
            vue.setPositionLivre(vue.getPositionLivre()+4);
        }
        vue.majRecommandation();
    }
}

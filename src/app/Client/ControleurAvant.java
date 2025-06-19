package app.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurAvant implements EventHandler<ActionEvent>{
    private PageClient vue;

    public ControleurAvant(PageClient vue){
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent e) {
        if(vue.getPositionLivre()-7<0){
            vue.setPositionLivre(0);
        }
        else{
            vue.setPositionLivre(vue.getPositionLivre()-7);
        }
        vue.majRecommandation();
    }
}
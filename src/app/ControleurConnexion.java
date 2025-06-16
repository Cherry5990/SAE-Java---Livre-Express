package app;
import modele.Client;
import modele.Vendeur;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurConnexion implements EventHandler<ActionEvent>{
    private App app;
    private PageConnexion vue;

    public ControleurConnexion(App app,PageConnexion vue){
        this.app = app;
        this.vue=vue;
    }

    @Override
    public void handle(ActionEvent e) {
        String nom = vue.getUtilisateur();
        try{
            Integer id = Integer.parseInt(vue.getId());
            switch (nom) {
                case "client":
                    Client c = App.clientBD.getClient(id);
                    System.out.println(c.getPrenom());
                    app.sceneClient(c);
                    break;
                case "vendeur":
                    Vendeur v = App.vendeurBD.getVendeur(id);
                    app.sceneVendeur(v);
                case "admin":
                    app.sceneAdmin();
                    break;
            }
        }
        catch(SQLException ex){
            vue.idInvalide().showAndWait();
        }
    }
}

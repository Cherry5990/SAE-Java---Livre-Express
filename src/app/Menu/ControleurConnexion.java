package app.Menu;
import modele.Client;
import modele.Vendeur;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
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
                    App.client = App.clientBD.getClient(id);
                    System.out.println(App.client.getPrenom());
                    app.sceneClient();
                    break;
                case "vendeur":
                    App.vendeur = App.vendeurBD.getVendeur(id);
                    app.sceneVendeur();
                case "admin":
                    app.sceneAdmin();
                    break;
            }
        }
        catch(SQLException ex){
            vue.idInvalide().showAndWait();
        }
        catch(IOException ex2){
            System.out.println("Problème de fxml" + ex2.getMessage());
        }
    }
}

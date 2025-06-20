package app.Menu;
import modele.Client;
import modele.Livre;
import modele.Vendeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
        String typeutilisateur = vue.getUtilisateur();
        try{
            String nom = vue.getNom();
            String mdp = vue.getMdp();
            switch (typeutilisateur) {
                case "client":
                    App.client = App.clientBD.connexionClient(nom, mdp);
                    if(App.client!=null){
                        if(!App.recoClient.containsKey(App.client)){
                            if(App.commandeBD.CommandeClient(App.client.getId()).size()!=0){
                                App.recoClient.put(App.client,App.clientBD.getRecommandationClient(App.client.getId()));
                            }
                            else{
                                App.recoClient.put(App.client,App.adminBD.livresLesPlusVendusTotalToutTemps());
                            }
                        }
                        if(!App.memoiresCommandesClient.containsKey(App.client)){
                            App.memoiresCommandesClient.put(App.client,new HashMap<>());
                        }
                        app.sceneClient();
                    }
                    else{
                        vue.idInvalide().showAndWait();
                        vue.reset();
                    }
                    break;
                case "vendeur":
                    App.vendeur = App.vendeurBD.connexionVendeur(nom,mdp);
                    if(App.vendeur!=null){
                        App.magasin = App.vendeur.getMagasin();
                        app.scenePageVendeurAccueil();;
                    }
                    else{
                        vue.idInvalide().showAndWait();
                        vue.reset();
                    }
                    break;
                case "admin":
                    if (App.adminBD.connexionAdmin(nom, mdp)){
                        app.sceneAdmin();
                    }
                    else{
                        vue.idInvalide().showAndWait();
                    }
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

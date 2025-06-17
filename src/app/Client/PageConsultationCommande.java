package app.Client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

import app.App;
import modele.DetailCommande;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.Client;
import modele.Commande;

public class PageConsultationCommande {
    private Scene scene;

    public PageConsultationCommande(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageConsultationCommande.fxml"));
        this.scene = new Scene(root);

        Label num = (Label)scene.lookup("#numero");
        num.setText(num.getText()+App.commande.getNumCom());

        Button retour=(Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneClient();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

        try{
            List<DetailCommande> dcs = App.commandeBD.getDetailCommande(App.commande.getNumCom());
            GridPane tableau = (GridPane) scene.lookup("#tab");
            double somme = 0;
            System.out.println("test");
            for(DetailCommande dc:dcs){
                int ligne = dc.getNumlig();
                System.out.println(ligne);
                Label numLigne = new Label(dc.getNumlig()+"");
                Label titre = new Label(dc.getLivre().getTitre());
                Label qte = new Label(dc.getQte()+"");
                Label prix = new Label(dc.getLivre().getPrix()+"");
                Label sommeLab = new Label(dc.getPrixVente()+"");
                somme+=dc.getPrixVente();
                tableau.add(numLigne, 0, ligne);
                tableau.add(titre, 1, ligne);
                tableau.add(qte, 2, ligne);
                tableau.add(prix, 3, ligne);
                tableau.add(sommeLab, 4, ligne);
            }
            tableau.add(new Label(somme+""),4,dcs.size()+1);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Scene getScene(){
        return this.scene;
    }
}
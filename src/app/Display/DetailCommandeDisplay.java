package app.Display;
import app.App;
import app.Vendeur.PageVendeurCommande;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.DetailCommande;

public class DetailCommandeDisplay extends Pane{
    private Label prixTotale;
    private Label qte;
    public DetailCommandeDisplay(PageVendeurCommande vue,DetailCommande dc)throws IOException{
        Pane widget = FXMLLoader.load(getClass().getResource("../view/ViewDetailCommande.fxml"));
        this.getChildren().add(widget);
        Label prix = (Label)widget.lookup("#prix");
        prix.setText(dc.getLivre().getPrix()+" €");
        this.prixTotale= (Label)widget.lookup("#prixtotale");
        this.prixTotale.setText(dc.getPrixVente()+"€");
        this.qte = (Label)widget.lookup("#qte");
        this.qte.setText(dc.getQte()+"");
        Button delete = (Button)widget.lookup("#delete");
        delete.setOnAction(e -> {
            try {
                App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), dc.getQte(), App.magasin.getIdMagasin());
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
            App.commande.enleveLivre(dc.getLivre());
            vue.maj();
        });
        Label titre =(Label)widget.lookup("#titre");
        titre.setText(dc.getLivre().getTitre());

    }

}

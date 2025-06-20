package app.Display;
import app.App;
import app.Client.PageClientPanierv2;
import app.Vendeur.PageVendeurCommande;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.DetailCommande;

public class DetailCommandeDisplay extends VBox{
    private Label prixTotale;
    private Label qte;
    public DetailCommandeDisplay(PageVendeurCommande vue,DetailCommande dc)throws IOException{
        super(100);
        Pane widget = FXMLLoader.load(getClass().getResource("../view/ViewDetailCommande.fxml"));
        widget.setMinHeight(219);
        widget.setMinWidth(600);
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
        this.setHeight(219);
        this.setWidth(600);
        this.getChildren().add(widget);

        Button plus = (Button)widget.lookup("#plus");
        Button moins = (Button)widget.lookup("#moins");
        plus.setOnAction(e -> {
            if (App.magasinBD.getQte(dc.getLivre().getIsbn(), App.magasin.getIdMagasin())>0){
                dc.setQte(dc.getQte()+1);
                dc.setPrix(dc.getLivre().getPrix()*dc.getQte());
                App.magasinBD.enleveQteLivre(dc.getLivre().getIsbn(), App.magasin.getIdMagasin(), 1);
            }
            else{
                vue.alertDeleteDetailCommandePlus().showAndWait();
            }
            vue.maj();
        });

        moins.setOnAction(e -> {
            if (dc.getQte()==1 ){
                Optional<ButtonType> reponse = vue.alertDeleteDetailCommandeMoins().showAndWait();
                if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                    try {
                        App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), 1, App.magasin.getIdMagasin());
                    } catch (SQLException e1) {
                        System.out.println(e1.getMessage());
                    }
                    App.commande.enleveLivre(dc.getLivre());
                }
            }
            else{
                dc.setQte(dc.getQte()-1);
                dc.setPrix(dc.getLivre().getPrix()*dc.getQte());
                try {
                    App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), 1, App.magasin.getIdMagasin());
                } catch (SQLException e1) {
                    e1.getMessage();
                }
            }
            vue.maj();
        });
    }

    public DetailCommandeDisplay(PageClientPanierv2 vue,DetailCommande dc)throws IOException{
        super(100);
        Pane widget = FXMLLoader.load(getClass().getResource("../view/ViewDetailCommande.fxml"));
        widget.setMinHeight(219);
        widget.setMinWidth(600);
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
        this.setHeight(219);
        this.setWidth(600);
        this.getChildren().add(widget);

        Button plus = (Button)widget.lookup("#plus");
        Button moins = (Button)widget.lookup("#moins");
        plus.setOnAction(e -> {
            if (App.magasinBD.getQte(dc.getLivre().getIsbn(), App.magasin.getIdMagasin())>0){
                dc.setQte(dc.getQte()+1);
                dc.setPrix(dc.getLivre().getPrix()*dc.getQte());
                App.magasinBD.enleveQteLivre(dc.getLivre().getIsbn(), App.magasin.getIdMagasin(), 1);
            }
            else{
                vue.alertDeleteDetailCommandePlus().showAndWait();
            }
            vue.maj();
        });

        moins.setOnAction(e -> {
            if (dc.getQte()==1 ){
                Optional<ButtonType> reponse = vue.alertDeleteDetailCommandeMoins().showAndWait();
                if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                    try {
                        App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), 1, App.magasin.getIdMagasin());
                    } catch (SQLException e1) {
                        System.out.println(e1.getMessage());
                    }
                    App.commande.enleveLivre(dc.getLivre());
                }
            }
            else{
                dc.setQte(dc.getQte()-1);
                dc.setPrix(dc.getLivre().getPrix()*dc.getQte());
                try {
                    App.magasinBD.ajouterQte(dc.getLivre().getIsbn(), 1, App.magasin.getIdMagasin());
                } catch (SQLException e1) {
                    e1.getMessage();
                }
            }
            vue.maj();
        });
        
    }

}

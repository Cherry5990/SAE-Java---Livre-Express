package app.Display;

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

public class DetailCommandeDisplay extends GridPane{
    public DetailCommandeDisplay(DetailCommande dc){

        //image
        ImageView imageLivre = new ImageView(new Image("file:img/icônes/livre.png"));
        VBox image = new VBox();
        image.getChildren().addAll(imageLivre,new Label(dc.getLivre().getPrix()+"€"));
        this.add(image,0,0,1,3);

        //titre
        Label titre = new Label(dc.getLivre().getTitre());
        this.add(titre,1,0,2,1);

        //sous-total
        VBox sousTotal = new VBox();
        Label mot = new Label("Sous-total");
        Label prix = new Label(dc.getPrixVente()+"");
        sousTotal.getChildren().addAll(mot,prix);
        this.add(sousTotal,1,1);

        //quantité
        VBox boxQte = new VBox();
        Label q = new Label("Quantité");
        Label qte = new Label("...");
        Spinner spinner = new Spinner<>();
        boxQte.getChildren().addAll(q,qte,spinner);
        this.add(boxQte,1,2);

        //supprimer
        Button supprimer = new Button();
        supprimer.setGraphic(new ImageView(new Image("file:img/icône/poubelle_rouge.png")));
        this.add(supprimer,2,1,1,2);
    }
}

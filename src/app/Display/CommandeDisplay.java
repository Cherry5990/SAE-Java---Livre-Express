package app.Display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modele.Commande;

public class CommandeDisplay extends Button{
    private Text texte;
    private Commande commande;
    private String numcom;

    public CommandeDisplay(EventHandler<ActionEvent> controleur, Commande commande){
        super();
        this.commande = commande;
        ImageView imageLivre = new ImageView("file:img/commande.png");
        this.numcom = "Commande n°"+String.valueOf(commande.getNumCom());
        this.texte = new Text(numcom);
        imageLivre.setFitHeight(100);
        imageLivre.setFitWidth(100);
        VBox interieurBouton = new VBox(5);
        interieurBouton.setPadding(new Insets(5));
        interieurBouton.getChildren().addAll(imageLivre, texte);
        interieurBouton.setAlignment(Pos.CENTER);
        interieurBouton.setPrefHeight(120);
        interieurBouton.setPrefWidth(120);
        this.setGraphic(interieurBouton);
        this.setHeight(120);
        this.setWidth(120);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e8e4df"), new CornerRadii(25), new Insets(0))));
        this.setOnAction(controleur);
    }
}

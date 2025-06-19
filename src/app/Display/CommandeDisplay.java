package app.Display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modele.Commande;

public class CommandeDisplay extends Button{

    public CommandeDisplay(EventHandler<ActionEvent> controleur, Commande commande){
        super();
        ImageView imageLivre = new ImageView("file:img/commande.png");
        Label labelCommande = new Label("Commande");
        Label numero = new Label(String.valueOf(commande.getNumCom()));
        labelCommande.setStyle("-fx-font-size: 18px;");
        numero.setStyle("-fx-font-size: 23px;");
        imageLivre.setFitHeight(100);
        imageLivre.setFitWidth(100);
        VBox interieurBouton = new VBox();
        interieurBouton.setPadding(new Insets(5));
        interieurBouton.getChildren().addAll(imageLivre, labelCommande,numero);
        interieurBouton.setAlignment(Pos.CENTER);
        interieurBouton.setPrefHeight(120);
        interieurBouton.setPrefWidth(120);
        this.setGraphic(interieurBouton);
        this.setHeight(200);
        this.setWidth(200);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e8e4df"), new CornerRadii(25), new Insets(0))));
        this.setOnAction(controleur);
    }
}

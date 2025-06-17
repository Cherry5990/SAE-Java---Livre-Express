package app.Display;

import java.security.PrivateKey;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import modele.Livre;

public class LivreDisplayLigne extends Button {
    private Livre l;

    public LivreDisplayLigne(EventHandler<ActionEvent> controleur, Livre livre,int i){
        super();
        this.l=livre;
        HBox ligne = new HBox();
        ImageView imageLivre = new ImageView("file:img/livre.png");
        String prixString = ""+livre.getPrix();
        imageLivre.setFitHeight(25);
        imageLivre.setFitWidth(25);

        Label titreLivre = new Label(livre.getTitre());
        Label prix = new Label(prixString+"€");
        ligne.setPadding(new Insets(5));
        Label numero = new Label(i+". ");
        ligne.getChildren().addAll(numero,imageLivre, titreLivre,prix);
        ligne.setAlignment(Pos.CENTER);
        ligne.setPrefHeight(30);
        ligne.setPrefWidth(1150);
        this.setHeight(120);
        ligne.setAlignment(Pos.CENTER_LEFT);
        ligne.setSpacing(10);
        titreLivre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        prix.setStyle("-fx-font-size: 16px; -fx-text-fill: #4CAF50; -fx-font-weight: bold;");
        ligne.setStyle("-fx-background-color: transparent;");
        imageLivre.setStyle("-fx-effect: dropshadow(gaussian, #bbb, 4, 0.5, 0, 1);");

        // Spacer to push the price to the far right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Remove prix from previous addAll and add after spacer
        ligne.getChildren().clear();
        ligne.getChildren().addAll(numero, imageLivre, titreLivre, spacer, prix);
        HBox.setMargin(imageLivre, new Insets(0, 10, 0, 10));
        HBox.setMargin(titreLivre, new Insets(0, 20, 0, 0));
        this.setCursor(javafx.scene.Cursor.HAND);
        this.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-background-color: #e8e4df;");
        this.setWidth(1150);
        this.setGraphic(ligne);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e8e4df"), new CornerRadii(25), new Insets(0))));
        this.setOnAction(controleur);
    }

    public Livre getLivre(){
        return this.l;
    }
}
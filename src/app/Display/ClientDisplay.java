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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modele.Client;
import modele.Livre;

public class ClientDisplay extends Button{
    private Client client;
    public ClientDisplay(EventHandler<ActionEvent> controleur, Client client,int i){
        super();
        this.client = client;
        ImageView imageClient = new ImageView("file:img/icônes/client.png");
        HBox ligne = new HBox();
        Label nom = new Label(client.getNom());
        Label prenom = new Label(client.getPrenom());
        Label addresse = new Label(client.getAdresse());
        Label numero = new Label(i+".");
        imageClient.setFitHeight(30);
        imageClient.setFitWidth(30);
        ligne.getChildren().addAll(imageClient,numero,nom,prenom,addresse);
        ligne.setAlignment(Pos.CENTER);
        ligne.setPrefHeight(30);
        ligne.setPrefWidth(1150);
        this.setHeight(40);
        ligne.setAlignment(Pos.CENTER_LEFT);
        ligne.setSpacing(10);
        nom.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        prenom.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-weight: bold;");
        ligne.setStyle("-fx-background-color: transparent;");
        addresse.setStyle("-fx-effect: dropshadow(gaussian, #bbb, 4, 0.5, 0, 1);");
        this.getChildren().add(ligne);

        // Spacer to push the price to the far right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setCursor(javafx.scene.Cursor.HAND);
        this.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-background-color: #e8e4df;");
        this.setWidth(1150);
        this.setGraphic(ligne);
        this.setBackground(new Background(new BackgroundFill(Color.web("#e8e4df"), new CornerRadii(25), new Insets(0))));
        this.setOnAction(controleur);
    }

    public Client getClient(){
        return this.client;
    }
}

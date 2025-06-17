package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modele.Livre;

public class LivreDisplay extends Button {
    private Text texte;
    private Livre livre;
    private String titre;

    public LivreDisplay(Livre livre){
        super();
        this.livre = livre;
        ImageView imageLivre = new ImageView("file:img/icônes/livre.png");
        this.titre = livre.getTitre();
        this.texte = new Text(this.titre.substring(0, 15) + "...");
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
        this.setTooltip(new Tooltip(this.titre));
    }

    public LivreDisplay(Livre livre, Application vue){
        super();
        this.livre = livre;
        ImageView imageLivre = new ImageView("file:img/icônes/livre.png");
        this.titre = livre.getTitre();
        this.texte = new Text(this.titre.substring(0, 15) + "...");
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
        this.setTooltip(new Tooltip(this.titre));
        this.setOnAction(new ControleurConsulterLivre(vue));
    }

    public LivreDisplay(EventHandler<ActionEvent> controleur, Livre livre){
        super();
        this.livre = livre;
        ImageView imageLivre = new ImageView("file:img/icônes/livre.png");
        this.titre = livre.getTitre();
        this.texte = new Text(this.titre.substring(0, 15) + "...");
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
        this.setTooltip(new Tooltip(this.titre));
        this.setOnAction(controleur);
    }

    public Livre getLivre(){
        return this.livre;
    }

    public void setPrix(){
        this.texte.setText("" + livre.getPrix());
    }

    public void setTitre(){
        this.texte.setText(livre.getTitre());
    }
}

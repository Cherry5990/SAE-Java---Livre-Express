package app;

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
import modele.Livre;

public class LivreDisplay extends Button {
    private Text texte;
    private Livre livre;

    public LivreDisplay(Livre livre){
        super();
        this.livre = livre;
        ImageView imageLivre = new ImageView("livre.png");
        this.texte = new Text(livre.getTitre());
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
    }

    public void setPrix(){
        this.texte.setText("" + livre.getPrix());
    }

    public void setTitre(){
        this.texte.setText(livre.getTitre());
    }
}

package app;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import modele.Livre;

public class TileLivre extends TilePane{
    private List<Livre> livres;

    public TileLivre(List<Livre>livres){
        for(Livre livre:livres){
            Button livreButton = new Button(livre.getTitre());
            this.getChildren().add(livreButton);
        }
    }
}

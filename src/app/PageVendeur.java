package app;
import modele.Vendeur;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageVendeur {
    private Scene scene;

    public PageVendeur(App app,Vendeur c){
        Pane root = new Pane();
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
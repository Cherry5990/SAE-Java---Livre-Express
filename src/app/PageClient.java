package app;
import modele.Client;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageClient {
    private Scene scene;

    public PageClient(App app,Client c){
        Pane root = new Pane();
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
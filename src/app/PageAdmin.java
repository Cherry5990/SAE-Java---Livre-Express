package app;
import modele.Client;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageAdmin {
    private Scene scene;

    public PageAdmin(App app){
        Pane root = new Pane();
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}
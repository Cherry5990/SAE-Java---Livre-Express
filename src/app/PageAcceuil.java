package app;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class PageAcceuil {
    private Scene scene;

    public PageAcceuil(App app){
        Pane root = new Pane();
        this.scene = new Scene(root);
    }

    public Scene getScene(){
        return this.scene;
    }
}

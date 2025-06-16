package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
    private Stage primaryStage;
    private Pane centre;
    private Pane top;


    @Override
    public void init(){

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Librarie Livre Express");
        sceneAcceuil();
        primaryStage.show();
    }

    public void sceneAcceuil(){
        PageAcceuil acceuil = new PageAcceuil(this);
        primaryStage.setScene(acceuil.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

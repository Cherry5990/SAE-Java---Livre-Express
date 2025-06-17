package app.Menu;

import java.io.IOException;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurPageConnexion implements EventHandler<ActionEvent>{
    private App app;

    public ControleurPageConnexion(App app){
        this.app =app;
    }

    @Override
    public void handle(ActionEvent e){
        Button button = (Button) e.getSource();
        String nom = button.getId();
        try{
            app.sceneConnexion(nom);
        }
        catch(IOException ex){
            System.out.println("bug fxml");
        }
    }

}

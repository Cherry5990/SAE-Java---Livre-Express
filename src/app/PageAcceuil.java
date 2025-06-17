package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageAcceuil {
    private Scene scene;

    public PageAcceuil(App app){
        try{
            Pane root = FXMLLoader.load(getClass().getResource("view/PageMenuPrincipal.fxml"));
            this.scene = new Scene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        Button buttonClient = (Button) this.scene.lookup("#client");
        Button buttonVendeur = (Button) this.scene.lookup("#vendeur");
        Button buttonAdmin = (Button) this.scene.lookup("#admin");
        List<Button> buttons = Arrays.asList(buttonAdmin,buttonClient,buttonVendeur);
        ControleurPageConnexion controlleur = new ControleurPageConnexion(app);
        for (Button button : buttons) {
            button.setOnAction(controlleur);
            button.setOnMouseEntered(e -> {
                button.setScaleX(1.1);
                button.setScaleY(1.1);
            });
            button.setOnMouseExited(e -> {
                button.setScaleX(1.0);
                button.setScaleY(1.0);
            });
        }
    }

    public Scene getScene(){
        return this.scene;
    }
}

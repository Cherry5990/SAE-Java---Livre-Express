package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageAcceuil {
    private Scene scene;

    public PageAcceuil(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("view/PageChoixCompte.fxml"));
        this.scene = new Scene(root);
        Button buttonClient = (Button) this.scene.lookup("#client");
        Button buttonVendeur = (Button) this.scene.lookup("#vendeur");
        Button buttonAdmin = (Button) this.scene.lookup("#admin");
        ControleurPageConnexion controlleur = new ControleurPageConnexion(app);
        buttonClient.setOnAction(controlleur);
        buttonVendeur.setOnAction(controlleur);
        buttonAdmin.setOnAction(controlleur);
    }

    public Scene getScene(){
        return this.scene;
    }
}

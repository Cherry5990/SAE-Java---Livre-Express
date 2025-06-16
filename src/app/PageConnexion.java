package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PageConnexion {
    private Scene scene;
    private Label titre;
    private Button con;
    private String utilisateur;
    private TextField id;

    public PageConnexion(App app,String utilisateur)throws IOException{
        this.utilisateur = utilisateur;
        Pane root = FXMLLoader.load(getClass().getResource("view/P4Accueil.fxml"));
        this.scene = new Scene(root);
        Label titre = (Label) this.scene.lookup("#titre");
        titre.setText(titre.getText() + utilisateur);
        Button con = (Button) this.scene.lookup("#connexion");
        con.setOnAction(new ControleurConnexion(app,this));
    }

    public String getId(){
        return id.getText();
    }

    public String getUtilisateur(){
        return this.utilisateur;
    }


    public Alert idInvalide(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur connexion");
        alert.setHeaderText("L'identifiant est invalide"); 
        alert.setContentText("Veillez rentrez un identifiant valide");     
        return alert;
    }

    public Scene getScene(){
        return this.scene;
    }
}

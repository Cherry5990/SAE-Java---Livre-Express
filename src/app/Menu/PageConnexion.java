package app.Menu;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PageConnexion {
    private Scene scene;
    private Label titre;
    private Button con;
    private String utilisateur;
    private TextField nom;
    private PasswordField mdp;

    public PageConnexion(App app,String utilisateur)throws IOException{
        this.utilisateur = utilisateur;
        Pane root = FXMLLoader.load(getClass().getResource("../view/PageConnexion.fxml"));
        this.scene = new Scene(root);

        if (utilisateur.equals("client")){
            HBox creation = (HBox)scene.lookup("#creation");
            Button inscription = new Button("Inscription");
            inscription.setPrefWidth(220);
            inscription.setPrefHeight(50);
            creation.setSpacing(10);
            creation.setStyle("-fx-alignment: center;");
            inscription.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 50; -fx-padding: 8 20; -fx-font-size: 18px;");
            inscription.setOnAction(e -> {
                try {
                    app.sceneCreation();
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            });
            creation.getChildren().add(inscription);
        }

        this.titre = (Label) this.scene.lookup("#titre");
        titre.setText(titre.getText() + utilisateur);

        Button con = (Button) this.scene.lookup("#connexion");
        con.setOnAction(new ControleurConnexion(app,this));

        this.nom = (TextField) this.scene.lookup("#nom");

        this.mdp = (PasswordField) this.scene.lookup("#mdp");

        Button retour = (Button) this.scene.lookup("#retour");
        retour.setOnAction(e -> app.sceneAcceuil());
    }

    public String getNom(){
        return nom.getText();
    }

    public String getMdp(){
        return mdp.getText();
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

    public void reset(){
        this.nom.setText("");
        this.mdp.setText("");
    }
}

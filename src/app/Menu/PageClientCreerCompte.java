package app.Menu;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Client;

public class PageClientCreerCompte {
    private Scene scene;
    public PageClientCreerCompte(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientCreerCompte.fxml"));
        this.scene = new Scene(root);

        TextField prenom = (TextField) scene.lookup("#prenom");
        TextField nom = (TextField) scene.lookup("#nom");
        TextField adresse = (TextField) scene.lookup("#adresse");
        TextField codepostal = (TextField) scene.lookup("#codepostal");
        TextField ville = (TextField) scene.lookup("#ville");
        TextField nomcompte = (TextField) scene.lookup("#nomcompte");
        PasswordField mdp = (PasswordField) scene.lookup("#mdp");
        PasswordField mdpconfirmation = (PasswordField) scene.lookup("#mdpconfirmation");
        Button retour =(Button)scene.lookup("#retour");
        Button res = (Button)scene.lookup("#reset");
        Button creer = (Button)scene.lookup("#creer");

        retour.setOnAction(e -> app.sceneAcceuil());
        res.setOnAction(e ->{
            prenom.setText("");
            nom.setText("");
            adresse.setText("");
            codepostal.setText("");
            ville.setText("");
            nomcompte.setText("");
            mdp.setText("");
            mdpconfirmation.setText("");
        });

        creer.setOnAction(e -> {
            if (prenom.getText().isEmpty() || nom.getText().isEmpty() || adresse.getText().isEmpty() ||
                codepostal.getText().isEmpty() || ville.getText().isEmpty() ||
                nomcompte.getText().isEmpty() || mdp.getText().isEmpty() || mdpconfirmation.getText().isEmpty()) {
                alertCaseVide().showAndWait();
            }
            else{
                if (!App.clientBD.NomCompteClientExiste(nomcompte.getText())){
                    if(mdp.getText().equals(mdpconfirmation.getText())){
                        try {
                            App.clientBD.insererClient(prenom.getText(), nom.getText(), adresse.getText(), codepostal.getText(), ville.getText(), nomcompte.getText(), mdp.getText());
                            app.sceneConnexion("client");
                        } 
                        catch (IOException e1) {System.out.println(e1.getMessage());} 
                        catch(SQLException e2){System.out.println(e2.getMessage());}
                    }
                    else{
                        alertMDPIncorrecte().showAndWait();
                    }
                }
                else{
                    alertNomCompteInvalide().showAndWait();
                }
            }
        });


    }

    public Scene getScene(){
        return this.scene;
    }

    public Alert alertMDPIncorrecte(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Livre Express");
        alert.setHeaderText("Erreur création compte"); 
        alert.setContentText("Mot de passe ne sont pas identique");     
        return alert;
    }

    public Alert alertNomCompteInvalide(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Livre Express");
        alert.setHeaderText("Erreur création compte"); 
        alert.setContentText("Nom de compte déja utilisé");     
        return alert;
    }

    public Alert alertCaseVide(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Livre Express");
            alert.setHeaderText("Erreur création compte");
            alert.setContentText("Tous les champs doivent être remplis.");
            return alert;
    }


}

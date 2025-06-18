package app.Vendeur;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Vendeur;

public class PageVendeurAjouterLivre {
    private Scene scene;
    private TextField nom;
    private TextField newQteLivre;
    private TextField isbn;
    private TextField prix;
    private TextField nbPages;
    private TextField datePubli;
    private TextField qte;
    private boolean verif = true;

    //A finir
    public PageVendeurAjouterLivre(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurAjouterLivre.fxml"));
        this.scene = new Scene(root);

        Button deconnexion = (Button) this.scene.lookup("#deconnexion");
        deconnexion.setOnMouseEntered(e -> {
                deconnexion.setScaleX(1.1);
                deconnexion.setScaleY(1.1);
            });
            deconnexion.setOnMouseExited(e -> {
                deconnexion.setScaleX(1.0);
                deconnexion.setScaleY(1.0);
            });
        deconnexion.setOnAction(e -> app.sceneAcceuil());

        Button retour = (Button) this.scene.lookup("#retour");
        retour.setOnMouseEntered(e -> {
                retour.setScaleX(1.1);
                retour.setScaleY(1.1);
            });
            retour.setOnMouseExited(e -> {
                retour.setScaleX(1.0);
                retour.setScaleY(1.0);
            });
        retour.setOnAction(e -> {
            try {
                app.scenePageVendeurGererStocks();
            } catch (IOException ex) {
            }
        });

        //Bouton réinitialisé
        Button reset = (Button) this.scene.lookup("#reset");
        reset.setOnMouseEntered(e -> {
                reset.setScaleX(1.1);
                reset.setScaleY(1.1);
            });
            reset.setOnMouseExited(e -> {
                reset.setScaleX(1.0);
                reset.setScaleY(1.0);
            });
        reset.setOnAction(e -> {
            this.reset();
        });

        //Bouton ajoute
        Button ajouter = (Button) this.scene.lookup("#ajouter");
        ajouter.setOnMouseEntered(e -> {
                ajouter.setScaleX(1.1);
                ajouter.setScaleY(1.1);
            });
            ajouter.setOnMouseExited(e -> {
                ajouter.setScaleX(1.0);
                ajouter.setScaleY(1.0);
            });
        //ajouter.setOnAction(new ControleurVendeurAjoute(app,this));

        //Les TextFields
        this.nom = (TextField) this.scene.lookup("#nom");
        this.isbn = (TextField) this.scene.lookup("#isbn");
        this.prix = (TextField) this.scene.lookup("#prix");
        this.nbPages = (TextField) this.scene.lookup("#nbPages");
        this.datePubli = (TextField) this.scene.lookup("#datePubli");
        this.qte = (TextField) this.scene.lookup("#qte");

        //Bouton verif
        Button verifier = (Button) this.scene.lookup("#verif");
        verifier.setOnMouseEntered(e -> {
                verifier.setScaleX(1.1);
                verifier.setScaleY(1.1);
            });
            verifier.setOnMouseExited(e -> {
                verifier.setScaleX(1.0);
                verifier.setScaleY(1.0);
            });
        verifier.setOnAction(e -> {
            this.verifierLivreExiste();
        });

        //Initialisation des TextFields : desactiver les champs
        this.isbn.setDisable(true);
        this.prix.setDisable(true);
        this.nbPages.setDisable(true);
        this.datePubli.setDisable(true);
        this.qte.setDisable(true);


    }



    public Scene getScene(){
        return this.scene;
    }

    public void verifierLivreExiste(){
        System.out.println(this.nom.getText());
        this.verif = App.magasinBD.existeLivreTitre(this.nom.getText(), App.vendeur.getMagasin().getIdMagasin());
        if (this.verif){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Livre déjà existant");
            alert.setHeaderText("Le livre existe déjà dans le stock de votre magasin.");
            alert.setContentText("Veuillez aller sur la page de mise à jour de la quantité pour modifier le stock.\n " +
                                 "Si vous souhaitez ajouter un nouveau livre, veuillez changer le titre du livre.\n ");
            alert.showAndWait();
        }
        else{
            this.majTextFields();

        }
    }

    public void majTextFields(){
        this.isbn.setDisable(verif);
        this.prix.setDisable(verif);
        this.nbPages.setDisable(verif);
        this.datePubli.setDisable(verif);
        this.qte.setDisable(verif);
    }
    public void reset(){
        this.nom.clear();
        this.isbn.clear();
        this.prix.clear();
        this.nbPages.clear();
        this.datePubli.clear();
        this.qte.clear();
        this.verif = true;
        this.majTextFields();
    }

}

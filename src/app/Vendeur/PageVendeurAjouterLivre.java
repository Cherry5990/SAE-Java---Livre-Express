package app.Vendeur;

import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Livre;

public class PageVendeurAjouterLivre {
    private Scene scene;
    private TextField nom;
    private Label isbn;
    private TextField prix;
    private TextField nbPages;
    private TextField datePubli;
    private TextField qte;
    private boolean verif = true;
    private boolean livreExiste = false;
    
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
        ajouter.setOnAction(new ControleurVendeurAjoute(app,this));

        //Les TextFields
        this.nom = (TextField) this.scene.lookup("#nom");
        this.isbn = (Label) this.scene.lookup("#isbn");
        this.isbn.setDisable(true);
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
            this.prix.clear();
            this.nbPages.clear();
            this.datePubli.clear();
            this.qte.clear();
            this.verifierLivreExiste();
        });

        //Initialisation des TextFields : desactiver les champs        
        this.prix.setDisable(true);
        this.nbPages.setDisable(true);
        this.datePubli.setDisable(true);
        this.qte.setDisable(true);

    }



    public Scene getScene(){
        return this.scene;
    }

    //Vérification si le livre existe déjà dans le stock du magasin ou dans la base de données
    public void verifierLivreExiste(){
        if (this.nom.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Le titre du livre est vide.");
            alert.setContentText("Veuillez entrer un titre pour le livre.");
            alert.showAndWait();
            return;
        }
        this.verif = App.magasinBD.existeLivreTitre(this.nom.getText(), App.vendeur.getMagasin().getIdMagasin());
        if (this.verif){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Livre déjà existant");
            alert.setHeaderText("Le livre existe déjà dans le stock de votre magasin.");
            alert.setContentText("Veuillez aller sur la page de mise à jour de la quantité pour modifier le stock.\n " +
                                 "Si vous souhaitez ajouter un nouveau livre,\nveuillez changer le titre du livre rentré.\n ");
            alert.showAndWait();
        }
        else{
            try {
                String isbnLivre = null;
                isbnLivre = App.livreBD.regardeSiISBNExiste(this.nom.getText());
                if(isbnLivre != null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Livre déjà existant");
                    alert.setHeaderText("Le livre existe déjà dans la base de données.");
                    alert.setContentText("Vous n'avez donc pas besoin de donner ses information\n" +
                                 "Veuillez ainsi rentrer la quantité\n que vous souhaité ajouter à votre magasin");
                    alert.showAndWait();
                    Livre livre = App.livreBD.getLivre(isbnLivre);
                    this.isbn.setText(livre.getIsbn());
                    this.prix.setText(String.valueOf(livre.getPrix()));
                    this.nbPages.setText(String.valueOf(livre.getNbPages()));
                    this.datePubli.setText(String.valueOf(livre.getDatePubli()));
                    this.qte.setDisable(false);
                    this.livreExiste = true;
                }
                else{
                    this.majTextFields();
                    this.isbn.setText(App.livreBD.maxIsbnPlus1());
                 
                }
            } catch (SQLException e) {
                System.out.println("Problème lors de la vérification de l'ISBN : " + e.getMessage());
            }
        }
    }

    //Mise à jour des TextFields en fonction de la vérification
    public void majTextFields(){
        this.prix.setDisable(verif);
        this.nbPages.setDisable(verif);
        this.datePubli.setDisable(verif);
        this.qte.setDisable(verif);
    }

    //Réinitialisation des champs
    public void reset(){
        this.nom.clear();
        this.isbn.setText("...");
        this.prix.clear();
        this.nbPages.clear();
        this.datePubli.clear();
        this.qte.clear();
        this.verif = true;
        this.majTextFields();
    }

    public String getTitre() {
        return this.nom.getText();
    }
    public String getIsbn() {
        return this.isbn.getText();
    }
    public String getPrix() {
        return this.prix.getText();
    }
    public String getNbPages() {
        return this.nbPages.getText();
    }
    public String getDatePubli() {
        return this.datePubli.getText();
    }
    public String getQte() {
        return this.qte.getText();
    }
    public boolean livreExiste() {
        return this.livreExiste;
    }
}

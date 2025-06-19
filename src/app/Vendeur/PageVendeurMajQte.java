package app.Vendeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Livre;


public class PageVendeurMajQte {
    private Scene scene;
    private ComboBox<String> comboBox;
    private TextField nom;
    private TextField newQteLivre;
    private Label isbn;
    private Label prix;
    private Label nbPages;
    private Label datePubli;
    private Label qte;
    
    public PageVendeurMajQte(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Vendeur/PageVendeurMajQte.fxml"));
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
        deconnexion.setOnAction(e -> app.popUpMessageDeconnexion());

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
                System.out.println("Problème");
            }
        });


        //Nom du livre
        this.comboBox = (ComboBox<String>) this.scene.lookup("#comboBox");
        this.nom = (TextField) this.scene.lookup("#nom");

        // Initialisation de la liste complète
        List<Livre> livresInit = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), "");
        this.comboBox.getItems().clear();
        for (Livre elt : livresInit) {
            this.comboBox.getItems().add(elt.getTitre());
        }

        this.nom.textProperty().addListener((obs, oldVal, newVal) -> {
            // Filtrage dynamique
            List<Livre> livres = App.magasinBD.rechercheLivre(App.vendeur.getMagasin().getIdMagasin(), newVal);
            this.comboBox.getItems().clear();
            for (Livre livre : livres) {
                this.comboBox.getItems().add(livre.getTitre());
            }
            // Montre le menu déroulant
            this.comboBox.show();
        });

        this.comboBox.setOnAction(e -> {
            this.modifValeurs();

        });
        
        //Nouvelle quantité
        this.newQteLivre = (TextField) this.scene.lookup("#newQteLivre");

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
        ajouter.setOnAction(new ControleurVendeurMajQte(app,this));

        //Les labels
        this.isbn = (Label) this.scene.lookup("#isbn");
        this.prix = (Label) this.scene.lookup("#prix");
        this.nbPages = (Label) this.scene.lookup("#nbPages");
        this.datePubli = (Label) this.scene.lookup("#datePubli");
        this.qte = (Label) this.scene.lookup("#qte");

        if (App.livre != null) {
            this.comboBox.getSelectionModel().select(App.livre.getTitre());
            this.modifValeurs();
        } 
    }

    public Scene getScene(){
        return this.scene;
    }

    public void reset(){
        this.comboBox.getSelectionModel().clearSelection();
        this.comboBox.getItems().clear();
        this.comboBox.setPromptText(("Selectionner un livre"));
        this.nom.clear();
        this.nom.setPromptText("Rentrez le nom du livre");
        this.newQteLivre.setPromptText("...");
        this.isbn.setText("...");
        this.prix.setText("...");
        this.nbPages.setText("...");
        this.datePubli.setText("...");
        this.qte.setText("...");
    }

    public void modifValeurs(){
        try {
            Livre livre = App.livreBD.getLivreParTitre(comboBox.getValue());
            if (livre == null) {
                isbn.setText("...");
                prix.setText("...");
                nbPages.setText("...");
                datePubli.setText("...");
                qte.setText("...");
                return;
            }
            isbn.setText(livre.getIsbn());
            prix.setText(String.valueOf(livre.getPrix()));
            nbPages.setText(String.valueOf(livre.getNbPages()));
            datePubli.setText(String.valueOf(livre.getDatePubli()));
            qte.setText(String.valueOf(App.magasinBD.getQte(livre.getIsbn(), (Integer) App.vendeur.getMagasin().getIdMagasin())));
        } catch (SQLException e) {
        }

    }

    public void majAffichage() {
        try {
            Livre livre = App.livreBD.getLivreParTitre(comboBox.getValue());
            isbn.setText(livre.getIsbn());
            prix.setText(String.valueOf(livre.getPrix()));
            nbPages.setText(String.valueOf(livre.getNbPages()));
            datePubli.setText(String.valueOf(livre.getDatePubli()));
            qte.setText(String.valueOf(App.magasinBD.getQte(livre.getIsbn(), (Integer) App.vendeur.getMagasin().getIdMagasin())));
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'affichage : " + e.getMessage());
        }
    }

    public String getNomLivre() {
        return this.nom.getText();
    }
    public String getNewQteLivre() {
        return this.newQteLivre.getText();
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

    
}

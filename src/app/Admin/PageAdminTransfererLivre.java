package app.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import app.App;
import app.Vendeur.ControleurVendeurTransferer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import modele.Livre;
import modele.Magasin;

public class PageAdminTransfererLivre {
    private Scene scene;
    private ComboBox<String> comboLivre;
    private ComboBox<String> comboMag1;
    private ComboBox<String> comboMag2;
    private int idMag1;
    private int idMag2;
    private TextField nom;
    private TextField qteTransfere;
    private Label isbn;
    private Label prix;
    private Label nbPages;
    private Label datePubli;
    private Label qte;

    public PageAdminTransfererLivre(App app) throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminTransfererLivre.fxml"));
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
                System.out.println("Problème");
            }
        });

        //Choix magasin
        this.comboMag1 = (ComboBox<String>) this.scene.lookup("#comboMagD");
        this.comboMag2 = (ComboBox<String>) this.scene.lookup("#comboMagR");
        this.comboLivre = (ComboBox<String>) this.scene.lookup("#comboLivre");
        this.qteTransfere = (TextField) this.scene.lookup("#qteTransfere");

        for (Magasin mag : App.magasinBD.getAllMagasins()) {
            this.comboMag1.getItems().add(mag.getNomMagasin());
            this.comboMag2.getItems().add(mag.getNomMagasin());
        }

        this.nom = (TextField) this.scene.lookup("#nom");
        this.nom.setDisable(true);

        this.comboMag1.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.idMag1 = -1;
            this.idMag2 = -1;
            Magasin magSource = null;
            Magasin magDest = null;
            String nomMagSource = this.comboMag1.getValue();
            String nomMagDest = this.comboMag2.getValue();
            for (Magasin mag : App.magasinBD.getAllMagasins()) {
                if (nomMagSource != null && mag.getNomMagasin().equals(nomMagSource)) {
                    magSource = mag;
                    this.idMag1 = mag.getIdMagasin();
                }
                if (nomMagDest != null && mag.getNomMagasin().equals(nomMagDest)) {
                    magDest = mag;
                    this.idMag2 = mag.getIdMagasin();
                }
            }

            if (magSource == null || magDest == null) {
                this.comboLivre.setPromptText("Selectionnez un magasin");
                this.comboLivre.setDisable(true);
                this.nom.setDisable(true);
                this.comboLivre.getItems().clear();
            } else {
                this.comboLivre.setPromptText("Selectionnez un livre");
                this.comboLivre.setDisable(false);
                this.nom.setDisable(false);
                List<Livre> livresInit = App.reseauBD.rechercheLivre("", this.idMag1);
                this.comboLivre.getItems().clear();
                for (Livre elt : livresInit) {
                    this.comboLivre.getItems().add(elt.getTitre());
                }
                this.nom.textProperty().addListener((obsNom, oldNom, newNom) -> {
                    List<Livre> livres = app.magasinBD.rechercheLivre(this.idMag1, newNom);
                    this.comboLivre.getItems().clear();
                    for (Livre livre : livres) {
                        this.comboLivre.getItems().add(livre.getTitre());
                    }
                    this.comboLivre.show();
                });
                this.comboLivre.setOnAction(e -> {
                    this.modifValeurs();
                });
            }
        });
        if (this.comboMag1.getValue() == null || this.comboMag2.getValue() == null){
            this.comboLivre.setPromptText("Sélectionnez deux magasins");
            this.comboLivre.setDisable(true);
        }

        this.qteTransfere = (TextField) this.scene.lookup("#qteTransfere");

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

        Button transferer = (Button) this.scene.lookup("#transferer");
        transferer.setOnMouseEntered(e -> {
                transferer.setScaleX(1.1);
                transferer.setScaleY(1.1);
            });
            transferer.setOnMouseExited(e -> {
                transferer.setScaleX(1.0);
                transferer.setScaleY(1.0);
            });
        transferer.setOnAction(new ControleurAdminTransferer(app,this));

        //Les labels
        this.isbn = (Label) this.scene.lookup("#isbn");
        this.prix = (Label) this.scene.lookup("#prix");
        this.nbPages = (Label) this.scene.lookup("#nbPages");
        this.datePubli = (Label) this.scene.lookup("#datePubli");
        this.qte = (Label) this.scene.lookup("#qte");
            
                
    }

    public Scene getScene(){
        return this.scene;
    }

    public void reset(){
        this.nom.clear();
        this.nom.setPromptText("Rentrez le nom du livre");
        this.comboMag1.getSelectionModel().clearSelection();
        this.comboMag1.setPromptText(("Selectionner un magasin"));
        this.comboMag2.getSelectionModel().clearSelection();
        this.comboMag2.setPromptText(("Selectionner un magasin"));
        this.comboLivre.getSelectionModel().clearSelection();
        this.comboLivre.getItems().clear();
        this.comboLivre.setPromptText(("Selectionner un livre"));
        this.qteTransfere.setPromptText("...");
        this.isbn.setText("...");
        this.prix.setText("...");
        this.nbPages.setText("...");
        this.datePubli.setText("...");
        this.qte.setText("...");
        
    }

    public void modifValeurs(){
        try {
            Livre livre = App.livreBD.getLivreParTitre(comboLivre.getValue());
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
            qte.setText(String.valueOf(App.magasinBD.getQte(livre.getIsbn(), this.idMag1)));
        } catch (SQLException e) {}

    }

    public String getNomLivre() {
        return this.nom.getText();
    }
    public String getQteTransfere() {
        return this.qteTransfere.getText();
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
    public Integer getIdMag1() {
        return this.idMag1;
    }
    public Integer getIdMag2() {
        return this.idMag2;
    }
}
package app.Admin;

import java.io.IOException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Magasin;

public class PageAdminTransfererLivre {
    private Scene scene;
    private ComboBox<String> comboLivre;
    private ComboBox<String> comboMag1;
    private ComboBox<String> comboMag2;
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
        this.comboMag1 = (ComboBox<String>) this.scene.lookup("#magasinSource");
        this.comboMag2 = (ComboBox<String>) this.scene.lookup("#magasinDest");
        this.comboLivre = (ComboBox<String>) this.scene.lookup("#comboLivre");
        this.qteTransfere = (TextField) this.scene.lookup("#qteTransfere");

        for (Magasin mag : App.magasinBD.getAllMagasins()) {
            this.comboMag1.getItems().add(mag.getNomMagasin());
            this.comboMag2.getItems().add(mag.getNomMagasin());
        }

        Button boutonAjouter = (Button) this.scene.lookup("#ajouterLivre");
        boutonAjouter.setOnAction(e -> {
            String nomLivre = comboLivre.getValue();
            String nomMagSource = comboMag1.getValue();
            String nomMagDest = comboMag2.getValue();
            try {
                int qte = Integer.parseInt(qteTransfere.getText());
            }
            catch (NumberFormatException ex) {
                System.out.println("Quantité invalide");
                return;
            }

            if (nomLivre == null || nomMagSource == null || nomMagDest == null || nomMagSource.equals(nomMagDest)) {
                System.out.println("Veuillez sélectionner un livre et deux magasins différents.");
                return;
            }

            Magasin magSource = null, magDest = null;
            for (Magasin mag : App.magasinBD.getAllMagasins()) {
                if (mag.getNomMagasin().equals(nomMagSource)) magSource = mag;
                if (mag.getNomMagasin().equals(nomMagDest)) magDest = mag;
            }

            if (magSource == null || magDest == null) {
                System.out.println("Magasin introuvable.");
                return;
            }

            boolean success = App.magasinBD.transfererLivre(nomLivre, magSource.getIdMagasin(), magDest.getIdMagasin(), qte);
            if (success) {
            System.out.println("Transfert réussi !");
            } else {
            System.out.println("Échec du transfert.");
            }
        });
    }



    public Scene getScene(){
        return this.scene;
    }
}
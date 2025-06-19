package app.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.App;
import app.Client.ControleurConsulterLivre;
import app.Display.LivreDisplayLigne;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.Commande;
import modele.Livre;
import modele.Magasin;


public class PageAdminVoirStock {
    private App app;
    private Label page;
    private String like;
    private Scene scene;
    private ComboBox<String> comboMag;
    private int idMag;
    private ScrollPane scroll;
    private List<Livre> livres;
    private VBox lignes;
    private int position;

    public PageAdminVoirStock(App app) throws IOException{
        this.app = app;
        Pane root = FXMLLoader.load(getClass().getResource("../view/Admin/PageAdminVoirStocks.fxml"));
        this.scene = new Scene(root);
        this.like = "";

        this.comboMag = (ComboBox<String>) this.scene.lookup("#comboMag");
        for (Magasin mag : App.magasinBD.getAllMagasins()) {
            this.comboMag.getItems().add(mag.getNomMagasin());
        }

        this.livres = new ArrayList<>();
        this.scroll = (ScrollPane) this.scene.lookup("#test");
        this.scroll.setStyle("-fx-background-color: transparent;");
        this.lignes = (VBox) this.scroll.getContent();
        ControleurConsulterLivreAdmin controleur = new ControleurConsulterLivreAdmin(app);
        

        this.comboMag.valueProperty().addListener((obs, oldVal, newVal) -> {
            Magasin mag = null;
            String nomMag = this.comboMag.getValue();
            for (Magasin maga : App.magasinBD.getAllMagasins()) {
                if (nomMag != null){
                    mag = maga;
                    this.idMag = maga.getIdMagasin();
                }
            }
            this.livres = App.magasinBD.rechercheLivre(this.idMag,like,position,300);
            for(Livre livreMag:livres){
                this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag,1));
            }
        });

        





        TextField recherche = (TextField)scene.lookup("#recherche");
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            position = 0;
            this.like = newValue;
            this.livres = App.magasinBD.rechercheLivre(this.idMag, newValue);
            this.lignes.getChildren().clear();
            int j = 1;
            for(Livre livreMag:this.livres){
                this.lignes.getChildren().add(new LivreDisplayLigne(controleur, livreMag, j));
                j++;
            }
        });

        

        Button retour = (Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneAdmin();
            } 
            catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });

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
    }
    public Scene getScene(){
        return this.scene;
    }
}

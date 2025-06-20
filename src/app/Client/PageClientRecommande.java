package app.Client;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;

import app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PageClientRecommande {
    private Scene scene;
    public PageClientRecommande(App app)throws IOException{
        Pane root = FXMLLoader.load(getClass().getResource("../view/Client/PageClientAvoirLivre.java.fxml"));
        this.scene = new Scene(root);

        Button retour=(Button)scene.lookup("#retour");
        retour.setOnAction(e -> {
            try {
                app.sceneClient();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });
        
        GridPane tab = (GridPane)scene.lookup("#tab");
        Label titre = (Label)scene.lookup("#titre");
        titre.setText(App.livre.getTitre());
        for (int row = 0; row < 6; row++) {
            Label label = new Label();
            label.setMinWidth(0);
            label.setStyle("-fx-padding: 0 0 0 20;"); // 20px left margin
            tab.add(label, 1, row);
        }
        tab.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().isEmpty());
        Label l1=(Label)scene.lookup("#isbn");
        l1.setText(App.livre.getIsbn());
        Label l2=(Label)scene.lookup("#prix");
        l2.setText(App.livre.getPrix()+"€");
        Label l3=(Label)scene.lookup("#nbpage");
        l3.setText(App.livre.getNbPages()+"");
        try{
            Label l4=(Label)scene.lookup("#auteur");
            l4.setText(App.livreBD.getAuteur(App.livre.getIsbn()));
            Label l5=(Label)scene.lookup("#theme");
            l5.setText(App.livreBD.getTheme(App.livre.getIsbn()));
            Label l6=(Label)scene.lookup("#editeur");
            l6.setText(App.livreBD.getEditeur(App.livre.getIsbn()));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Scene getScene(){
        return this.scene;
    }
}

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
        Label l1 =new Label(App.livre.getIsbn());
        Label l2 =new Label(App.livre.getPrix()+"");
        Label l3 =new Label(App.livre.getNbPages()+"");
        try{
            Label l4 =new Label(App.livreBD.getAuteur(App.livre.getIsbn()));
            Label l5 =new Label(App.livreBD.getTheme(App.livre.getIsbn()));
            Label l6 = new Label(App.livreBD.getEditeur(App.livre.getIsbn()));
            tab.add(l1, 1, 0);
            tab.add(l2, 1, 1);
            tab.add(l3, 1, 2);
            tab.add(l4, 1, 3);
            tab.add(l5, 1, 4);
            tab.add(l6, 1, 5);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Scene getScene(){
        return this.scene;
    }
}

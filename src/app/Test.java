package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Livre;

public class Test extends Application {

    @Override
    public void start(Stage stage){
        VBox root = new VBox();
        Scene scene = new Scene(root);
        Livre livreTest = new Livre("978-2-02-130452-7 ", "Astrale - Les aventures fantastiques de Cristalle", 316, "2027", 29.99);
        LivreDisplay affichageLivre = new LivreDisplay(livreTest);
        LivreDisplay affichageLivre2 = new LivreDisplay(livreTest);
        affichageLivre2.setPrix();
        root.getChildren().addAll(affichageLivre, affichageLivre2);
        stage.setHeight(300);
        stage.setWidth(300);
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

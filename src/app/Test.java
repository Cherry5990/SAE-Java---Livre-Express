package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Livre;

public class Test extends Application {
    private Stage primaryStage;
    private Livre livreTest;

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;
        VBox root = new VBox();
        Scene scene = new Scene(root);
        this.livreTest = new Livre("978-2-02-130452-7 ", "Astrale - Les aventures fantastiques de Cristalle", 316, "2027", 29.99);
        LivreDisplay affichageLivre = new LivreDisplay(this.livreTest, this);
        LivreDisplay affichageLivre2 = new LivreDisplay(this.livreTest, this);
        affichageLivre2.setPrix();
        root.getChildren().addAll(affichageLivre, affichageLivre2);
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pageConsultationLivre(Livre livre){
        try{
            PageConsultationLivre consultationLivre = new PageConsultationLivre(this, livre);
            primaryStage.setScene(consultationLivre.getScene());
        } catch (IOException e){
            System.err.println(e.getMessage() + " erreur dans Test.java");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

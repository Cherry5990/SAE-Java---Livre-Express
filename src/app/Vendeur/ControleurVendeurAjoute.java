package app.Vendeur;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControleurVendeurAjoute implements EventHandler<ActionEvent> {

    private App app;
    private PageVendeurAjouterLivre vue;

    public ControleurVendeurAjoute(App app, PageVendeurAjouterLivre vue) {
        this.app = app;
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            boolean livreExiste = vue.livreExiste();
            String isbn = vue.getIsbn();
            String titre = vue.getTitre();
            Integer nbPages = Integer.parseInt(vue.getNbPages());
            Integer datePubli = Integer.parseInt(vue.getDatePubli());
            Double prix = Double.parseDouble(vue.getPrix());
            Integer qte = Integer.parseInt(vue.getQte());
            if(livreExiste){
                App.magasinBD.ajouterQte(isbn, qte, App.vendeur.getMagasin().getIdMagasin());
            }
            else{
                App.livreBD.insererLivre(isbn, titre, nbPages, datePubli, prix);
                App.magasinBD.ajouterQte(isbn, qte, App.vendeur.getMagasin().getIdMagasin());
            }            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            if(livreExiste){
                alert.setContentText("Le livre a été ajouté au magasin avec succès.");
            }
            else{
                alert.setContentText("Le livre a été ajouté à la base de donné\netau magasin avec succès.");
            }
            
            alert.showAndWait();
            vue.reset();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une quantité et un prix valides.\nLa quantité doit être un nombre entier et\nle prix un nombre décimal.\nMettez un point et non une virgule pour les décimales.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs correctement.");
            alert.showAndWait();
        }
    }
    
}

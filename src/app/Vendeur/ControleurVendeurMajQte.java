package app.Vendeur;


import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import app.App;
import javafx.event.ActionEvent;

public class ControleurVendeurMajQte implements EventHandler<ActionEvent>{
    
    private App app;
    private PageVendeurMajQte vue;

    public ControleurVendeurMajQte(App app, PageVendeurMajQte vue) {
        this.app = app;
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Integer newQteLivre = Integer.parseInt(vue.getNewQteLivre());
            String isbn = vue.getIsbn();
            Integer idMagasin = App.vendeur.getMagasin().getIdMagasin();
            App.magasinBD.miseAJourQuantite(isbn,newQteLivre, idMagasin);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La quantité du livre a été mise à jour avec succès.");
            alert.showAndWait();
            vue.majAffichage();


        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une quantité valide.\nUne quantité doit être un nombre entier.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un livre et entrer une quantité valide.");
            alert.showAndWait();
        }


}
}

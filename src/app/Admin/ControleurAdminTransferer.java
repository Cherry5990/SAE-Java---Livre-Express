package app.Admin;


import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import app.App;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;

public class ControleurAdminTransferer implements EventHandler<ActionEvent>{
    
    private App app;
    private PageAdminTransfererLivre vue;

    public ControleurAdminTransferer(App app, PageAdminTransfererLivre vue) {
        this.app = app;
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Integer qteTransfere = Integer.parseInt(vue.getQteTransfere());
            if(qteTransfere > Integer.parseInt(vue.getQte())){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Vous ne pouvez pas transférer plus de livres que la quantité disponible dans le magasin.");
                alert.showAndWait();
            }
            else{
                Integer qteDonneur = Integer.parseInt(vue.getQte());
                String isbn = vue.getIsbn();
                Integer qteReceveur = App.magasinBD.getQte(isbn, App.vendeur.getMagasin().getIdMagasin());

                if(qteReceveur == 0) {
                    App.magasinBD.ajouterQte(isbn, qteTransfere, vue.getIdMag2());
                }
                else {
                    App.magasinBD.miseAJourQuantite(isbn, qteTransfere + qteReceveur, vue.getIdMag2());
                }
                App.magasinBD.miseAJourQuantite(isbn, qteDonneur - qteTransfere, vue.getIdMag1());

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La quantité de livre a été transférée avec succès.");
                alert.showAndWait();
                vue.reset();
            }
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
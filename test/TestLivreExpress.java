import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;


public class TestLivreExpress {
    
    @Test
    public void testAssosciationLivre(){
        Livre livre1 = new Livre(1,"Titanic",350,"2000",30);
        Auteur auteur1 = new Auteur(1, "Joubert", 2000, 2020);
        Theme theme1 = new Theme(1, "horreur");
        Editeur editeur1 = new Editeur(1, "mario");
        livre1.ajouteAuteur(auteur1);
        livre1.ajouteEditeur(editeur1);
        livre1.ajouteTheme(theme1);
        assertEquals(Arrays.asList(auteur1),livre1.getAuteurs());
        assertEquals(Arrays.asList(editeur1),livre1.getEditeur());
        assertEquals(Arrays.asList(theme1),livre1.getThemes());
    }

    @Test
    public void testAssociationPosseder(){
        Magasin magasin = new Magasin(1, "la goutière", "Paris");
        Livre livre1 = new Livre(1,"Titanic",350,"2000",30);
        Auteur auteur1 = new Auteur(1, "Joubert", 2000, 2020);
        Theme theme1 = new Theme(1, "horreur");
        Editeur editeur1 = new Editeur(1, "mario");
        livre1.ajouteAuteur(auteur1);
        livre1.ajouteEditeur(editeur1);
        livre1.ajouteTheme(theme1);
        magasin.ajouteLivre(livre1, 2);

        assertEquals(true,magasin.livreEnStock(livre1));
        assertEquals(2, magasin.qteEnStock(livre1));
    }
}
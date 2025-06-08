package modele;

import java.util.List;
import java.util.ArrayList;

public class Livre {
    private String isbn;
    private String titre;
    private int nbPages;
    private String datePubli;
    private double prix;
    private List<Theme> themes;
    private List<Auteur> auteurs;
    private List<Editeur> editeurs;

    public Livre(String isbn, String titre, int nbPages, String datePubli, double prix) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
        this.themes = new ArrayList<>();
        this.auteurs = new ArrayList<>();
        this.editeurs = new ArrayList<>();
    }

    public String getIsbn(){return this.isbn;}

    public String getTitre(){return this.titre;}

    public int getNbPages(){return this.nbPages;}

    public String getDatePubli(){return this.datePubli;}

    public double getPrix(){return this.prix;}

    public void ajouteTheme(Theme theme){
        this.themes.add(theme);
    }

    public void ajouteAuteur(Auteur auteur){
        this.auteurs.add(auteur);
    }

    public void ajouteEditeur(Editeur editeur){
        this.editeurs.add(editeur);
    }

    public List<Auteur> getAuteurs(){
        return this.auteurs;
    }

    public List<Theme> getThemes(){
        return this.themes;
    }

    public List<Editeur> getEditeur(){
        return this.editeurs;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){return false;}
        if (o == this){return true;}
        if (!(o instanceof Livre)){return false;}
        Livre cast = (Livre) o;
        return this.isbn == cast.getIsbn();
    }
}

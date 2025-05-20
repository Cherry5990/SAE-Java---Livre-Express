package modele;

import java.util.List;

public class Theme {
    private int idDewey;
    private String nomTheme;
    private List<Livre> livres;

    public Theme(int id, String nom){
        this.idDewey = id;
        this.nomTheme = nom;
    }

    public int getId(){return this.idDewey;}
    
    public String getNom(){return this.nomTheme;}

    public List<Livre> getListeLivreAvecTheme(){return this.livres;}
}
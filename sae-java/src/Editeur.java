import java.util.List;

public class Editeur {
    private int idEditeur;
    private String nomEditeur;
    private List<Livre> livres;

    public Editeur(int idEditeur,String nomEditeur, List<Livre> livres){
        this.idEditeur = idEditeur;
        this.nomEditeur = nomEditeur;
        this.livres = livres;
    }
    
    public int idEditeur(){
        return idEditeur;
    }
    public String nomEditeur(){
        return nomEditeur;
    }
    public List<Livre> getLivres(){
        return livres;
    }
    
    
}

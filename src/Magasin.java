import java.util.ArrayList;
import java.util.List;

public class Magasin {
    private int idMagasin;
    private String nomMagasin;
    private String ville;
    private List<Posseder> stock;

    public Magasin(int id, String nom, String ville){
        this.idMagasin = id;
        this.nomMagasin = nom;
        this.ville = ville;
        this.stock = new ArrayList<>();
    }

    public int getIdMagasin(){
        return this.idMagasin;
    }

    public String getNomMagasin(){
        return this.nomMagasin;
    }
    
    public String getVille(){
        return this.ville;
    }
}
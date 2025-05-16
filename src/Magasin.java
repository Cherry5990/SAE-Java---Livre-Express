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

    public void ajouteLivre(Livre livre,int qte){
        this.stock.add(new Posseder(this, livre, qte));
    }

    public boolean livreEnStock(Livre livre,int qte){
        for(Posseder pos:this.stock){
            if (pos.getLivre().equals(livre)){
                if(pos.getQte()==qte){
                    return true;
                }
            }
        }
        return false;
    }

    public int qteEnStock(Livre livre){
        for(Posseder pos:this.stock){
            if (pos.getLivre().equals(livre)){
                return pos.getQte();
            }
        }
        return 0;
    }

    public boolean augmenteQte(Livre livre,int qte){
        for(Posseder pos:this.stock){
            if (pos.getLivre().equals(livre)){
                pos.setQte(pos.getQte()+qte);
                return true;
            }
        }
        return false;
    }

    public boolean dimimueQte(Livre livre,int qte){
        for(Posseder pos:this.stock){
            if (pos.getLivre().equals(livre) && pos.getQte()-qte>-1){
                if (pos.getQte()-qte==0){
                    this.stock.remove(pos);
                }
                else{
                    pos.setQte(pos.getQte()-qte);
                }
                return true;
            }
        }
        return false;
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
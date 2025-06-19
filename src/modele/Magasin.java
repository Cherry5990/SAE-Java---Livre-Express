package modele;

public class Magasin {
    private int idMagasin;
    private String nomMagasin;
    private String ville;

    public Magasin(int id, String nom, String ville){
        this.idMagasin = id;
        this.nomMagasin = nom;
        this.ville = ville;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Magasin magasin = (Magasin) obj;
        return idMagasin == magasin.idMagasin;
    }

    @Override
    public int hashCode() {
        return idMagasin;
    }
}
package modele;

import java.util.List;
import java.util.ArrayList;

public class Client extends Compte{
    
    private String adresse;
    private String codePostal;
    private String ville;
    private List<Commande> commandes;

    public Client(int id, String nom,String prenom, String adresse, String codePostal, String ville){
        super(id,nom,prenom);
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.commandes = new ArrayList<>();
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public String getAdresse(){return this.adresse;}
    public String getCodePostal(){return this.codePostal;}
    public String getVille(){return this.ville;}
    public List<Commande> getCommandes(){return this.commandes;}
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return this.getId() == client.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", adresse='" + adresse + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
    
}


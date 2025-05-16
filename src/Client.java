public class Client extends Compte{
    
    private String adresse;
    private String codePostal;
    private String ville;

    public Client(int id, String nom,String prenom,Reseau reseau, String adresse, String codePostal, String ville){
        super(id,nom,prenom,reseau);
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public Reseau getReseau(){return this.reseau;}
    public String getAdresse(){return this.adresse;}
    public String getCodePostal(){return this.codePostal;}
    public String getVille(){return this.ville;}
    

    
}


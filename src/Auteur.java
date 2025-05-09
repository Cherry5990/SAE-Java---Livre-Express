public class Auteur {
    private int idAuteur;
    private String nomAuteur;
    private int anneeNais;
    private int anneeDeces;

    public Auteur(int idAuteur, String nomAuteur, int anneeNais, int anneeDeces) {
        this.idAuteur = idAuteur;
        this.nomAuteur = nomAuteur;
        this.anneeNais = anneeNais;
        this.anneeDeces = anneeDeces;
    }

    public int getIdAuteur(){return idAuteur;}
    public String getNomAuteur(){return nomAuteur;}

    public int getAnneeNais(){return anneeNais;}

    public int getAnneeDeces(){return anneeDeces;}
}

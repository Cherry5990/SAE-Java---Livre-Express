public class Livre {
    private int isbn;
    private String titre;
    private int nbpages;
    private String datepubli;
    private double prix;

    public Livre(int isbn, String titre, int nbpages, String datepubli, double prix) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbpages = nbpages;
        this.datepubli = datepubli;
        this.prix = prix;
    }

    public int getIsbn(){return this.isbn;}

    public String getTitre(){return this.titre;}

    public int getNbPages(){return this.nbpages;}

    public String getDatePubli(){return this.datepubli;}

    public double getPrix(){return this.prix;}
}
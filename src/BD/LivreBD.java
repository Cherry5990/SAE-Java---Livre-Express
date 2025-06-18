package BD;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Livre;
import modele.Theme;

public class LivreBD {
    ConnexionMySQL laConnexion;
    
    public LivreBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;             
    }

    /**
     * Cette méthode permet d'insérer un livre dans la base de données
     * @param isbn l'isbn du livre
     * @param titre le titre du livre
     * @param nbPages le nombre de pages
     * @param datePubli la date de publication
     * @param Prix le prix du livre
     * @throws SQLException
     */
    public void insererLivre(String isbn,String titre,Integer nbPages, Integer datePubli, Double Prix) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into LIVRE values(?,?,?,?,?);")){
            ps.setString(1, isbn);
            ps.setString(2, titre);
            ps.setInt(3, nbPages);
            ps.setInt(4, datePubli);
            ps.setDouble(5, Prix);;
            ps.executeUpdate();
            ps.close();
        }
	}


    // Methodes pour Partie 1 de MenuVendeur
    /**
     * Cette méthode permet de récupérer le plus grand ISBN dans la base de données et d'y ajouter 1
     * @return String: l'isbn maximum + 1
     * @throws SQLException
     */
    public String maxIsbnPlus1() throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select max(isbn) from LIVRE;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            String isbnStr = rs.getString("max(isbn)");
            Long isbnInt= (Long.parseLong(isbnStr));
            isbnInt += 1;
            isbnStr = Long.toString(isbnInt);
            return isbnStr;
            

        }
    }
    /**
     * Cette méthode permet de vérifier si un livre existe déjà dans un magasin
     * @param id l'identifiant du magasin
     * @param entrer l'isbn du livre à rechercher
     * @return
     * @throws SQLException
     */
    public boolean verifLivreExisteDansMagasin(int id, String entrer) throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select isbn, titre From LIVRE natural join POSSEDER where titre = ? and idmag = ?;")){
            ps.setString(1, entrer);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        return false;
    }

    /**
     * Cette méthode permet de vérifier si un ISBN existe déjà dans la base de données pour un titre de livre donné
     * @param entrer le titre du livre à rechercher
     * @return l'isbn du livre si trouvé, sinon null
     * @throws SQLException
     */
    public String regardeSiISBNExiste(String entrer) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement(" select isbn from LIVRE where titre = ?;")){
            ps.setString(1, entrer);
            ResultSet rs = ps.executeQuery();
            String isbn  = null;
            if(rs.next()){
                isbn = rs.getString("isbn");
            }
            return isbn;
        }
    }

    //Les trois methodes suivante pourrait être compilé en une seule méthode
    /**
     * Cette méthode permet de rechercher le nombre de pages d'un livre dans la base de données
     * @param isbn
     * @return le nombre de pages du livre
     * @throws SQLException
     */
    public Integer rechercheNbPagesLivre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select nbpages from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer nbPages = rs.getInt("nbpages");
            return nbPages;
        }
    }

    /**
     * Cette méthode permet de rechercher le prix d'un livre dans la base de données
     * @param isbn
     * @return le prix du livre
     * @throws SQLException
     */
    public Double recherchePrixLivre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select prix from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Double prix = rs.getDouble("prix");
            return prix;
        }
    }

    /**
     * Cette méthode permet de rechercher la date de publication d'un livre dans la base de données
     * @param isbn
     * @return la date de publication du livre
     * @throws SQLException
     */
    public Integer rechercheDatePubli(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select datepubli from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer datePubli = rs.getInt("datepubli");
            return datePubli;
        }
    } 
    // Fin des methodes pour Partie 1 de MenuVendeur
    
    // Methode pour Partie 2 de MenuVendeur
    /**
     * Cette méthode permet de rechercher le titre d'un livre dans la base de données en fonction de son ISBN
     * @param isbn
     * @return
     * @throws SQLException
     */
    public String rechercheTitre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select titre from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String titre = rs.getString("titre");
            return titre;
        }
    } 

    public Livre getLivre(String isbn)throws SQLException{
        Livre livre = null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,nbpages,datepubli,prix from LIVRE where isbn=?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                livre = new Livre(isbn, titre,nbPages,datePubli,prix);
            }
        }
        return livre;
    }

    public Livre getLivreParTitre(String titre)throws SQLException{
        Livre livre = null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,nbpages,datepubli,prix from LIVRE where titre=?;")){
            ps.setString(1, titre);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String isbn = rs.getString("isbn");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                livre = new Livre(isbn, titre,nbPages,datePubli,prix);
            }
        }
        return livre;
    }
    
}

package BD;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreBD {
    ConnexionMySQL laConnexion;
    
    public LivreBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;             
    }


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
    public Integer rechercheNbPagesLivre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select nbpages from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer nbPages = rs.getInt("nbpages");
            return nbPages;
        }
    }

    public Double recherchePrixLivre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select prix from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Double prix = rs.getDouble("prix");
            return prix;
        }
    }

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
    public String rechercheTitre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select titre from LIVRE where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String titre = rs.getString("titre");
            return titre;
        }
    } 

    
    
}

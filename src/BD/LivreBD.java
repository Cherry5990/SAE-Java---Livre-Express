package BD;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreBD {
    ConnexionMySQL laConnexion;
	Statement st;
    private Long maxIsbn;
    public LivreBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;
        this.maxIsbn = 9792000000000L;             
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
    public String maxIsbn(){
        String res = Long.toString(this.maxIsbn);
        this.maxIsbn += 1;
        return res;
    }
    public boolean verifLivreExisteDansMagasin(int id, String entrer) throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select isbn, titre From MAGASIN natural join POSSEDER natural join Livre where titre = ?;")){
            ps.setString(1, entrer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        return false;
    }

    public String regardeSiISBNExiste(String entrer) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement(" select isbn from Livre where titre = ?;")){
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
        try(PreparedStatement ps = laConnexion.prepareStatement("select nbpages from Livre where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer nbPages = rs.getInt("nbpages");
            return nbPages;
        }
    }

    public Double recherchePrixLivre(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select prix from Livre where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Double prix = rs.getDouble("prix");
            return prix;
        }
    }

    public Integer rechercheDatePubli(String isbn) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select datepubli from Livre where isbn = ?;")){
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer datePubli = rs.getInt("datepubli");
            return datePubli;
        }
    } 
    // Fin des methodes pour Partie 1 de MenuVendeur
    
}

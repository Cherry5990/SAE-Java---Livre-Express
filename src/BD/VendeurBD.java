package BD;
import java.sql.*;

import modele.Magasin;
import modele.Vendeur;


public class VendeurBD {
	ConnexionMySQL laConnexion;
    public VendeurBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;          
    }
    
    /**
     * Retourne l'id maximum de vendeurs dans la base de données
     * @return l'id maximum'
     * @throws SQLException
     */
    public int maxIdVendeur() throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select MAX(idVendeur) from VENDEUR;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            int max = rs.getInt("MAX(idVendeur)");
            rs.close();
            ps.close();
            return max;
        }
    }

    /**
     * Insère un vendeur dans la base de données
     * @param prenom le prénom du vendeur
     * @param nom le nom du vendeur
     * @param mag l'identifiant du magasin auquel le vendeur est associé
     * @throws SQLException
     */
	public void insererVendeur(String prenom,String nom,int mag,String nomcompte,String mdp) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into VENDEUR values(?,?,?,?,?,?);")){
            ps.setInt(1, maxIdVendeur()+1);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setInt(4,mag);
            ps.setString(5, nomcompte);
            ps.setString(6, mdp);
            ps.executeUpdate();
            ps.close();
        }
	}

    /**
     * Supprime un vendeur de la base de données
     * @param idVendeur l'id du vendeur à supprimer
     * @throws SQLException
     */
    public void deleteVendeur(int idVendeur)throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("delete from VENDEUR where idVendeur=?;")){
            ps.setInt(1, idVendeur);
            ps.executeUpdate();
            ps.close();
        }
    }

    public void deleteVendeur(String nom, String prenom, int idMagasin)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("delete from VENDEUR where nomVendeur = ? and prenomVendeur = ? and idmag = ?")){
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, idMagasin);
            ps.executeUpdate();
            ps.close();
        }
    }

    /**
     * Retourne les informations du vendeur correspondant à l'identifiant donné
     * @param id l'identifiant du vendeur
     * @return les informations du vendeur
     * @throws SQLException
     */
    public Vendeur getVendeur(int id)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select * from VENDEUR natural join MAGASIN group by idVendeur having idVendeur=?;")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Vendeur(rs.getInt("idVendeur"),
                            rs.getString("nomVendeur"),
                            rs.getString("prenomVendeur"),
                            new Magasin(rs.getInt("idmag"), rs.getString("nommag"), "villemag"));
        }
    }

    public boolean vendeurPresent(String nom, String prenom,String nomcompte,String mdp, int idMagasin)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select * from VENDEUR where nomVendeur = ? and prenomVendeur = ? and idmag = ? and nomcompte=? and mdpcompte=?;")){
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, idMagasin);
            ps.setString(4, nomcompte);
            ps.setString(5, mdp);
            ResultSet rs = ps.executeQuery();
            boolean dedans = rs.next();
            rs.close();
            return dedans;
        }
    }

    public Vendeur connexionVendeur(String nom,String mdp){
        try(PreparedStatement ps =laConnexion.prepareStatement("select idVendeur,nomcompte,mdpcompte from VENDEUR where nomcompte=? and mdpcompte=?;")){
            ps.setString(1, nom);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return getVendeur(rs.getInt("idVendeur"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean NomCompteVendeurExiste(String nomCompte){
        try(PreparedStatement ps =laConnexion.prepareStatement("select nomcompte from VENDEUR where nomcompte=?")){
            ps.setString(1, nomCompte);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
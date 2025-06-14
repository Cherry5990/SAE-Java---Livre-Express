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
	public void insererVendeur(String prenom,String nom,int mag) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into VENDEUR values(?,?,?,?);")){
            ps.setInt(1, maxIdVendeur()+1);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setInt(4,mag);
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


    
}
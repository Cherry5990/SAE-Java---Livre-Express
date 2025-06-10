package BD;
import java.sql.*;

import modele.Magasin;
import modele.Vendeur;


public class VendeurBD {
	ConnexionMySQL laConnexion;
	Statement st;
    public VendeurBD(ConnexionMySQL laConnexion){
        this.laConnexion=laConnexion;          
    }
    

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

    public void deleteVendeur(int idVendeur)throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("delete from VENDEUR where idVendeur=?;")){
            ps.setInt(1, idVendeur);
            ps.executeUpdate();
            ps.close();
        }
    }

    public Vendeur getVendeur(int id)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select * from VENDEUR natural join MAGASIN group by idVendeur having idVendeur=?;")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Vendeur(rs.getInt("idVendeur"),
                            rs.getString("nomVendeur"),
                            rs.getString("prenomVendeur"),
                            new Magasin(id, rs.getString("nommag"), "villemag"));
        }
    }


    
}
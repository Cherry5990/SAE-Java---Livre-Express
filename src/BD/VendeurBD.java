package BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Magasin;


public class VendeurBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public VendeurBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public int maxIdClient() throws SQLException{
        List<Magasin> magasins = new ArrayList<>();
        try(PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            }
            int max = rs.getInt("MAX(idcli)");
            rs.close();
            ps.close();
            return max;
        }
    }

	public void insererClient(String prenom,String nom,String adresse,int codePostal,String ville) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into CLIENT values(?,?,?,?,?,?);")){
            ps.setInt(1, this.maxIdClient()+1);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setString(4, adresse);
            ps.setInt(5, codePostal);
            ps.setString(6, ville);
            ps.executeUpdate();
            ps.close();
        }
	}

    public void deleteClient(int id)throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("delete from CLIENT where idcli=?;")){
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }
    }
}
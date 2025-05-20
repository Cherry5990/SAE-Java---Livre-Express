package BD;
import java.sql.*;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public int maxIdClient() throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select MAX(idcli) from CLIENT;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            int max = rs.getInt("MAX(idcli)");
            rs.close();
            ps.close();
            return max;
        }
    }

	public void insererClient(String prenom,String nom,String adresse,int codePostal,String ville) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into CLIENT values(?,?,?,?,?,?);")){
            ps.setInt(1, maxIdClient()+1);
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
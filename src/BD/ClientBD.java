package BD;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import modele.Client;
import modele.Livre;

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

    public Client getClient(int id)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select idcli,nomcli,prenomcli,adressecli,codepostal,villecli from CLIENT where idcli=?;")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Client(rs.getInt("idcli"),
                            rs.getString("nomcli"),
                            rs.getString("prenomcli"),
                            rs.getString("adressecli"),
                            rs.getString("codepostal"),
                            rs.getString("villecli"));
        }
    }

    public List<Livre> getRecommandationClient(int id)throws SQLException{
        List<Livre> recommandation = new ArrayList<>();
        List<Livre> livreClient = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement(
            "SELECT l.isbn, l.titre, l.nbpages, l.datepubli, l.prix " +
            "FROM LIVRE l " +
            "JOIN DETAILCOMMANDE dc ON l.isbn = dc.isbn " +
            "JOIN COMMANDE c ON dc.numcom = c.numcom " +
            "WHERE c.idcli = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            livreClient.add(new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getInt("nbpages"),
                rs.getString("datepubli"),
                rs.getDouble("prix")
            ));
            }
        }
        List<Livre> max = new ArrayList<>();
        Integer maxCommun = null;
        try(PreparedStatement ps =laConnexion.prepareStatement("select idcli from CLIENT;")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getInt("idcli") == id) {
                    continue;
                }
                List<Livre> listClient = new ArrayList<>();
                PreparedStatement ps2 =laConnexion.prepareStatement("select numcom from COMMANDE where idcli=?;");
                ps2.setInt(1, rs.getInt("idcli"));
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()){
                    PreparedStatement ps3 =laConnexion.prepareStatement("select isbn,titre,nbpages,datepubli,prix from DETAILCOMMANDE natural join LIVRE where numcom=?;");
                    ps3.setInt(1, rs2.getInt("numcom"));
                    ResultSet rs3 = ps3.executeQuery();
                    while (rs3.next()) {
                        listClient.add(new Livre(
                            rs3.getString("isbn"),
                            rs3.getString("titre"),
                            rs3.getInt("nbpages"),
                            rs3.getString("datepubli"),
                            rs3.getDouble("prix")
                        ));
                    }
                    rs3.close();
                    ps3.close();
                }
                int commun = 0;
                for (Livre livre : livreClient) {
                    if (listClient.contains(livre)) {
                        commun++;
                    }
                }
                if (maxCommun == null || commun > maxCommun) {
                    maxCommun = commun;
                    max.clear();
                    max.addAll(listClient);
                }
            }
            for (Livre livre : max) {
                if (!livreClient.contains(livre)) {
                    recommandation.add(livre);
                }
            }
        }
        catch(SQLException e){
            System.out.println("problème de recommandation : "+e.getMessage());
        }
        return recommandation;
    }

    public String rechercheClient(String prenom,String nom)throws SQLException{
        StringBuilder sb = new StringBuilder(nom);
        try(PreparedStatement ps =laConnexion.prepareStatement("select idcli,nomcli,prenomcli from CLIENT where nomcli LIKE ? and prenomcli LIKE ?;")){
            ps.setString(1, "%" + nom + "%");
            ps.setString(2, "%" + prenom + "%");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            while(rs.next()){
                sb.append("\n" + rs.getInt("idcli") + ", " + rs.getString("nomcli") + ", " + rs.getString("prenomcli"));
            }
        }
        catch(SQLException e){
            System.out.println("problème de nom et prenom : " +e.getMessage());
        }
        return sb.toString();
    }
}
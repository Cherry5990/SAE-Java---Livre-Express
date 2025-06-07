package BD;
import java.sql.*;


public class MagasinBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public String avoirNom(int mag){
        String nom= null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select nommag from MAGASIN;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            nom = rs.getString("nommag");
            rs.close();
            ps.close();
        }
        catch(SQLException e){
            System.out.println("Le magasin "+mag+"n'éxiste pas :"+e.getMessage());
        }
        return nom;
    }

    public int maxIdMagasin(){
        Integer max= null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select MAX(idmag) from MAGASIN;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            max = rs.getInt("MAX(idmag)");
            rs.close();
            ps.close();
        }
        catch(SQLException e){
            System.out.println("Aucun magasin existe");
        }
        return max;
    }

	public void insererMagasin(String nom,String ville) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into Magasin values(?,?,?);")){
            ps.setInt(1, maxIdMagasin()+1);
            ps.setString(2, nom);
            ps.setString(3, ville);
            ps.executeUpdate();
            ps.close();
        }
	}

    public String voirStock(int mag) throws SQLException{
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,prix,qte from MAGASIN natural join POSSEDER natural join LIVRE where idmag=?;")){
            ps.setInt(1, mag);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                sb.append(rs.getString("isbn") +" "+rs.getString("titre")+" "+rs.getDouble("prix")+" "+rs.getString("qte")+"\n");
            }
        }
        return sb.toString();
    }

    public String voirStock(int mag,int debut,int fin){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("select titre,prix,qte from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? LIMIT ? OFFSET ?;")){
            int limit = fin-debut;
            ps.setInt(1, mag);
            ps.setInt(2, limit);
            ps.setInt(3, debut);
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-40s %-8s %-5s\n", "titre", "prix", "qte"));
            while(rs.next()){
                String titre = "";
                if (rs.getString("titre").length()>35){
                    titre = rs.getString("titre").substring(0, 35)+"...";
                }
                else{
                    titre = rs.getString("titre");
                }
                sb.append(String.format("%-40s %-8s %-5s\n",titre,rs.getDouble("prix")+"€",rs.getString("qte")));
            }
        }
        catch(SQLException e){
            System.out.println("bug au niveau de voirStock");
        }
        return sb.toString();
    }

    public String afficheMagasins(){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, villemag FROM MAGASIN")) {
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-8s %-30s %-20s\n", "numéro", "nom", "ville"));
            while (rs.next()) {
                sb.append(String.format("%-8d %-30s %-20s\n",
                        rs.getInt("idmag"),
                        rs.getString("nommag"),
                        rs.getString("villemag")));
            }
        } catch (SQLException e) {
            sb.append("Erreur lors de l'affichage des magasins : ").append(e.getMessage());
        }
        return sb.toString();
    }
}
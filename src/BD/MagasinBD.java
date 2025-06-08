package BD;
import java.sql.*;

import modele.Client;
import modele.Magasin;


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
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,prix,qte from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? LIMIT ? OFFSET ?;")){
            int limit = fin-debut;
            ps.setInt(1, mag);
            ps.setInt(2, limit);
            ps.setInt(3, debut);
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-40s %-8s %-5s\n", "isbn", "titre", "prix", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-8s %-5s\n",
                        rs.getString("isbn"),
                        titre,
                        rs.getDouble("prix") + "€",
                        rs.getString("qte")));
            }
        }
        catch(SQLException e){
            System.out.println("bug au niveau de voirStock");
        }
        return sb.toString();
    }

    public int maxPossederLivre(int mag){
        Integer maxLivre = null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select count(*) nbLivre from MAGASIN natural join POSSEDER where idmag=?;")){
            ps.setInt(1, mag);
            ResultSet rs = ps.executeQuery();
            rs.next();
            maxLivre = rs.getInt("nbLivre");
        }
        catch(SQLException e){
            System.out.println("Le magsin n'existe pas");
        }
        return maxLivre;
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

    public String rechercheLivre(int mag,String like){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn,titre,prix,qte from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? and titre LIKE ?")) {
            ps.setInt(1, mag);
            ps.setString(2, "%" + like + "%");
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-40s %-8s %-5s\n", "isbn", "titre", "prix", "qte"));
            while (rs.next()) {
                String titre = rs.getString("titre");
                if (titre.length() > 35) {
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-8s %-5s\n",
                        rs.getString("isbn"),
                        titre,
                        rs.getDouble("prix") + "€",
                        rs.getString("qte")));
            }
        } 
        catch (SQLException e) {
            sb.append("Erreur lors de l'affichage des magasins : ").append(e.getMessage());
        }
        return sb.toString();
    }

    public Magasin getMagasin(int mag)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select idmag,nommag,villemag from MAGASIN where idmag=?;")){
            ps.setInt(1, mag);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Magasin(rs.getInt("idmag"),
                            rs.getString("nommag"),
                            rs.getString("villemag"));
        }
    }
}
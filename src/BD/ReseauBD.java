package BD;
import java.nio.file.attribute.PosixFileAttributeView;
import java.sql.*;

import modele.Livre;
import modele.Magasin;
import modele.Posseder;
import modele.Reseau;
import modele.Vendeur;

public class ReseauBD {
    private ConnexionMySQL laConnexion;
	private Statement st;
	public ReseauBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public void ajouterMagasins(Reseau reseau){
        try(PreparedStatement ps = laConnexion.prepareStatement("select * From MAGASIN natural join POSSEDER natural join LIVRE;")){
            ResultSet rs = ps.executeQuery();
            int idCourant = 1;
            Magasin magCourant = null;
            while (rs.next()) {
                int id = rs.getInt("idmag");
                if (id == idCourant){
                    if(magCourant == null){
                        magCourant = new Magasin(id, rs.getString("nommag"), rs.getString("villemag"));

                        
                    }
                    magCourant.ajouteLivre(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getString("datepubli"), rs.getDouble("prix")), rs.getInt("qte"));
                }
                else{
                    
                    idCourant = id;
                    
                    reseau.ajouteMagasinExistant(magCourant);
                    

                    magCourant = new Magasin(id, rs.getString("nommag"), rs.getString("villemag"));
                    magCourant.ajouteLivre(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getString("datepubli"), rs.getDouble("prix")), rs.getInt("qte"));
                }
            }
            reseau.ajouteMagasinExistant(magCourant);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String magasinsAyantLivre(Integer idmag, String isbn){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT * FROM LIVRE NATURAL JOIN POSSEDER NATURAL JOIN MAGASIN WHERE idmag <> ? and isbn = ?;")) {
            ps.setInt(1, idmag);
            ps.setString(2, isbn);
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-40s %-20s %-5s\n", "idmag", "nommag", "villemag", "qte"));
            while (rs.next()) {
                String nommag = rs.getString("nommag");
                if (nommag.length() > 35) {
                    nommag = nommag.substring(0, 35) + "...";
                }
                sb.append(String.format("%-5s %-40s %-20s %-5s\n",
                        rs.getInt("idmag"),
                        nommag,
                        rs.getString("villemag"),
                        rs.getInt("qte") + " exemplaires"));
            
        }
        }catch(SQLException e){
        System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    public String rechercheLivre(String like){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, sum(qte) qte FROM LIVRE NATURAL JOIN POSSEDER group by isbn having titre LIKE '%hu%';")) {
            ps.setString(1, "%" + like + "%");
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-40s %-5s\n", "isbn", "titre", "qte totale"));
            while (rs.next()) {
                String titre = rs.getString("titre");
                if (titre.length() > 35) {
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-5s\n",
                        rs.getString("isbn"),
                        titre,
                        rs.getString("qte")));
            }
        } 
        catch (SQLException e) {
            sb.append("Erreur lors de l'affichage des magasins : ").append(e.getMessage());
        }
        return sb.toString();
    }


}

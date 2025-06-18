package BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modele.Livre;


public class ReseauBD {
    private ConnexionMySQL laConnexion;
	public ReseauBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    /**
     * Affiche les magasins qui possèdent le livre donné en paramètre
     * @param idmag l'identifiant du magasin
     * @param isbn l'ISBN du livre
     * @return les magasins qui possèdent le livre
     */
    public String magasinsAyantLivre(Integer idmag, String isbn){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT * FROM LIVRE NATURAL JOIN POSSEDER NATURAL JOIN MAGASIN WHERE idmag != ? and isbn = ?;")) {
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

    /**
     * Recherche les livres dont le titre contient la chaîne de caractères donnée en paramètre
     * @param like la chaîne de caractères à rechercher dans le titre des livres
     * @param idmag l'identifiant du magasin à exclure de la recherche
     * @return les livres dont le titre contient la chaîne de caractères donnée
     */
    public String rechercheLivre(String like,int idmag){
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, sum(qte) qte FROM LIVRE NATURAL JOIN POSSEDER where idmag!= ? group by isbn having titre LIKE ?;")) {
            ps.setString(2, "%" + like + "%");
            ps.setInt(1, idmag);
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


    /**
     * Affiche le stock de livres du réseau avec une limite et un offset
     * @param debut l'index de début
     * @param fin l'index de fin
     * @return le stock de livres du réseau
     */
    public String voirStockReseau(int debut, int fin){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,prix,qte from LIVRE natural join POSSEDER LIMIT ? OFFSET ?;")){
            int limit = fin-debut;
            ps.setInt(1, limit);
            ps.setInt(2, debut);
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-40s %-13s %-5s\n", "isbn", "titre", "prix", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-13s %-5s\n",
                        rs.getString("isbn"),
                        titre,
                        rs.getDouble("prix") + " euros",
                        rs.getString("qte")));
            }
        }
        catch(SQLException e){
            System.out.println("bug au niveau de voirStock");
        }
        return sb.toString();
    }

    /**
     * Affiche le stock de livres du réseau
     * @return le stock de livres du réseau
     */
    public List<Livre> voirStockReseau(){
        List<Livre> res = new ArrayList<>();
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,prix,nbpages,datepubli from LIVRE natural join POSSEDER;")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String titre =rs.getString("titre");
                String isbn = rs.getString("isbn");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                res.add(new Livre(isbn,titre,nbPages,datePubli,prix));
            }
        }
        catch(SQLException e){
            System.out.println("bug au niveau de voirStock");
        }
        return res;
    }


    /**
     * Retourne le nombre maximum de livres du réseau
     * @return le nombre maximum de livres
     */
    public Integer maxPossederLivreReseau(){
        Integer maxLivre = null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select count(*) nbLivre from LIVRE;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            maxLivre = rs.getInt("nbLivre");
        }
        catch(SQLException e){
            System.out.println("Le magasin n'existe pas");
        }
        return maxLivre;
    }


}

package BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modele.Livre;
import modele.Magasin;


public class MagasinBD {
	ConnexionMySQL laConnexion;
	public MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    /**
     * Cette méthode permet de récupérer le nom d'un magasin à partir de son identifiant
     * @param mag l'identifiant du magasin
     * @return le nom du magasin ou null si le magasin n'existe pas
     */
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

    /**
     * Cette méthode permet de récupérer l'identifiant maximum d'un magasin dans la base de données
     * @return l'identifiant maximum d'un magasin
     */
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

    /**
     * Cette méthode permet d'insérer un nouveau magasin dans la base de données
     * @param nom le nom du magasin
     * @param ville la ville du magasin
     * @throws SQLException
     */
	public void insererMagasin(String nom,String ville) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into MAGASIN values(?,?,?);")){
            ps.setInt(1, maxIdMagasin()+1);
            ps.setString(2, nom);
            ps.setString(3, ville);
            ps.executeUpdate();
            ps.close();
        }
	}

    /**
     * Cette méthode permet de voir le stock d'un magasin
     * @param mag l'id du magasin
     * @return une chaîne de caractères contenant les informations sur les livres en stock
     * @throws SQLException
     */
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

    /**
     * Cette méthode permet de voir le stock d'un magasin avec une limite et un offset 
     * @param mag l'id du magasin
     * @param debut l'index de début
     * @param fin l'index de fin
     * @return une chaîne de caractères contenant les informations sur les livres en stock dans la plage spécifiée
     */
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

    /**
     * Cette méthode permet de récupérer le nombre maximum de livres possédés par un magasin
     * @param mag l'identifiant du magasin
     * @return le nombre maximum de livres possédés par le magasin
     */
    public int maxPossederLivre(int mag){
        Integer maxLivre = null;
        try(PreparedStatement ps = laConnexion.prepareStatement("select count(*) nbLivre from MAGASIN natural join POSSEDER where idmag=?;")){
            ps.setInt(1, mag);
            ResultSet rs = ps.executeQuery();
            rs.next();
            maxLivre = rs.getInt("nbLivre");
        }
        catch(SQLException e){
            System.out.println("Le magasin n'existe pas");
        }
        return maxLivre;
    }

    /**
     * Cette méthode permet d'afficher toutes les informations de tous les magasins de la base de données
     * @return une chaîne de caractères contenant les informations sur les magasins
     */
    public List<Magasin> getAllMagasins(){
        List<Magasin> magasins = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, villemag FROM MAGASIN")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                magasins.add(new Magasin(rs.getInt("idmag"), rs.getString("nommag"), rs.getString("villemag")));
            }
        }catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des magasins : "+e.getMessage());
        }
        return magasins;
    }

    /**
     * Cette méthode permet de rechercher des livres dans un magasin en fonction d'un mot-clé du titre
     * @param mag l'identifiant du magasin
     * @param like le mot-clé à rechercher dans les titres des livres
     * @return une chaîne de caractères contenant les informations sur les livres trouvés
     */
    public List<Livre> rechercheLivre(int mag,String like){
        List<Livre> livres = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn,titre,prix,nbpages,datepubli from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? and titre LIKE ?")) {
            ps.setInt(1, mag);
            ps.setString(2, "%" + like + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String titre = rs.getString("titre");
                String isbn = rs.getString("isbn");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                livres.add(new Livre(isbn, titre,nbPages,datePubli,prix));
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return livres;
    }

    public List<Livre> rechercheLivre(int mag,String like,int debut,int nb){
        List<Livre> livres = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn,titre,prix,nbpages,datepubli from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? and titre LIKE ? LIMIT ? OFFSET ?")) {
            ps.setInt(1, mag);
            ps.setString(2, "%" + like + "%");
            ps.setInt(3, nb);
            ps.setInt(4, debut);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String titre = rs.getString("titre");
                String isbn = rs.getString("isbn");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                livres.add(new Livre(isbn, titre,nbPages,datePubli,prix));
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return livres;
    }

    /**
     * Cette méthode permet de récupérer un objet Magasin à partir de son identifiant
     * @param mag l'identifiant du magasin
     * @return un objet Magasin contenant les informations du magasin
     * @throws SQLException
     */
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

    public Magasin getMagasin(String nomMag)throws SQLException{
        try(PreparedStatement ps =laConnexion.prepareStatement("select idmag,nommag,villemag from MAGASIN where nommag=?;")){
            ps.setString(1, nomMag);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Magasin(rs.getInt("idmag"),
                            rs.getString("nommag"),
                            rs.getString("villemag"));
        }
    }

    /**
     * Cette méthode permet de mettre à jour la quantité d'un livre dans un magasin
     * @param isbn l'ISBN du livre
     * @param qte la nouvelle quantité du livre
     * @param idmag l'identifiant du magasin
     */
    public void miseAJourQuantite(String isbn, Integer qte, Integer idmag){
        try{
            if (qte == 0) {
                PreparedStatement ps = laConnexion.prepareStatement("DELETE FROM POSSEDER WHERE isbn=? and idmag=?;");
                ps.setString(1, isbn);
                ps.setInt(2, idmag);
                ps.executeUpdate();
            }
            else{
                PreparedStatement ps = laConnexion.prepareStatement("update POSSEDER set qte = ? where isbn = ? and idmag = ?;");
                ps.setInt(1, qte);
                ps.setString(2, isbn);
                ps.setInt(3, idmag);
                ps.executeUpdate();
                ps.close();
            }
        }
        catch(SQLException e){
            System.out.println("Changement impossible de la quantité du livre : "+e.getMessage());
        }
    }

    /**
     * Cette méthode permet d'ajouter une quantité d'un livre dans un magasin
     * Si le livre existe déjà dans le magasin, la quantité est mise à jour, sinon le livre est ajouté avec la quantité spécifiée
     * @param isbn l'ISBN du livre
     * @param qte la quantité à ajouter
     * @param mag l'identifiant du magasin
     * @throws SQLException
     */
    public void ajouterQte(String isbn,Integer qte,Integer mag)throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select qte from MAGASIN natural join POSSEDER where isbn=? and idmag=?")){
            ps.setString(1, isbn);
            ps.setInt(2, mag);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer ancienneQte = rs.getInt("qte");
                PreparedStatement ps2 = laConnexion.prepareStatement("update POSSEDER set qte=? where isbn=? and idmag=?");
                ps2.setInt(1, qte+ancienneQte);
                ps2.setString(2, isbn);
                ps2.setInt(3, mag);
                ps2.executeUpdate();
                ps2.close();
            } else {
                PreparedStatement ps2 = laConnexion.prepareStatement("INSERT INTO POSSEDER (isbn, idmag, qte) VALUES (?, ?, ?)");
                ps2.setString(1, isbn);
                ps2.setInt(2, mag);
                ps2.setInt(3, qte);
                ps2.executeUpdate();
                ps2.close();
            }
        }
        catch(SQLException e){
            System.out.println("Modification impossible : "+e.getMessage());
        }
    }

    /**
     * Cette méthode permet de récupérer la quantité d'un livre dans un magasin
     * @param isbn l'ISBN du livre
     * @param idmag l'identifiant du magasin
     * @return la quantité du livre dans le magasin
     */
    public Integer getQte(String isbn, Integer idmag){
        try(PreparedStatement ps = laConnexion.prepareStatement("select qte from POSSEDER where isbn = ? and idmag = ?;" )){
            ps.setString(1, isbn);
            ps.setInt(2, idmag);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer qte = rs.getInt("qte");
            return qte;


        }catch(SQLException e){
            return 0;
        }
        
    }

    /**
     * Cette méthode permet de vérifier si un livre existe dans un magasin
     * @param isbn l'ISBN du livre
     * @param idmag l'identifiant du magasin
     * @return true si le livre existe dans le magasin, false sinon
     */
    public boolean existeLivre(String isbn, int idmag){
        try(PreparedStatement ps = laConnexion.prepareStatement("select * from POSSEDER where isbn = ? and idmag = ?;")){
            ps.setString(1, isbn);
            ps.setInt(2, idmag);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.out.println("Erreur lors de la vérification de l'existence du livre : " + e.getMessage());
            return false;
        }
    }

    public List<Livre> getAllLivre(int idmag){
        List<Livre> livres = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn,titre,prix,nbpages,datepubli from MAGASIN natural join POSSEDER natural join LIVRE where idmag=?;")) {
            ps.setInt(1, idmag);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String titre = rs.getString("titre");
                String isbn = rs.getString("isbn");
                int nbPages = rs.getInt("nbpages");
                double prix = rs.getDouble("prix");
                String datePubli = rs.getString("datepubli");
                livres.add(new Livre(isbn, titre,nbPages,datePubli,prix));
            }
        } 
        catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des magasins : "+e.getMessage());
        }
        return livres;
    }

   public int nbLivreLike(int mag,String like){
        List<Livre> livres = new ArrayList<>();
        try (PreparedStatement ps = laConnexion.prepareStatement("SELECT count(*) nb from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? and titre LIKE ?")) {
            ps.setInt(1, mag);
            ps.setString(2, "%" + like + "%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("nb");
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void enleveQteLivre(String isbn,int idmag,int qte){
        try(PreparedStatement ps = laConnexion.prepareStatement("select qte from MAGASIN natural join POSSEDER where isbn=? and idmag=?")){
            ps.setString(1, isbn);
            ps.setInt(2, idmag);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Integer nouvelleQte = rs.getInt("qte")-qte;
            if(nouvelleQte==0){
                PreparedStatement ps2 = laConnexion.prepareStatement("DELETE FROM POSSEDER WHERE isbn=? and idmag=?;");
                ps2.setString(1, isbn);
                ps2.setInt(2, idmag);
                ps2.executeUpdate();
                ps2.close();
            }
            else {
                PreparedStatement ps2 = laConnexion.prepareStatement("update POSSEDER set qte=? where isbn=? and idmag=?");
                ps2.setInt(1, nouvelleQte);
                ps2.setString(2, isbn);
                ps2.setInt(3, idmag);
                ps2.executeUpdate();
                ps2.close();
            }
        }
        catch(SQLException e){
            System.out.println("Modification impossible : "+e.getMessage());
        }
    }

    public void ajouteQteLivre(String isbn,int idmag,int qte){
        try(PreparedStatement ps = laConnexion.prepareStatement("select qte from LIVRE natural left join POSSEDER where isbn=? and idmag=?")){
            ps.setString(1, isbn);
            ps.setInt(2, idmag);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Integer nouvelleQte = rs.getInt("qte")+qte;
                PreparedStatement ps2 = laConnexion.prepareStatement("UPDATE POSSEDER set qte=? WHERE isbn=? and idmag=?;");
                ps2.setInt(1, nouvelleQte);
                ps2.setString(2, isbn);
                ps2.setInt(3, idmag);
                ps2.executeUpdate();
                ps2.close();
            }
            else {
                Integer nouvelleQte = qte;
                PreparedStatement ps2 = laConnexion.prepareStatement("insert into POSSEDER values(?,?,?);");
                ps2.setInt(3, nouvelleQte);
                ps2.setString(2, isbn);
                ps2.setInt(1, idmag);
                ps2.executeUpdate();
                ps2.close();
            }
        }
        catch(SQLException e){
            System.out.println("Modification impossible : "+e.getMessage());
        }
    }

    
}
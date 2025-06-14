package BD;
import java.sql.*;
import java.util.List;

import modele.Commande;
import modele.DetailCommande;
import modele.Livre;

public class CommandeBD {
	ConnexionMySQL laConnexion;
	public CommandeBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    /**
     * Cette méthode permettra de récupérer le dernier id de commande inséré dans la base de donnée
     * @return
     * @throws SQLException
     */
    public int maxIdCommande() throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select MAX(numcom) from COMMANDE;")){
            ResultSet rs = ps.executeQuery();
            rs.next();
            int max = rs.getInt("MAX(numcom)");
            rs.close();
            ps.close();
            return max;
        }
    }

    /**
     * Insére une commande dans la base de donnée
     * @param c
     * @throws SQLException
     */
	public void insererCommande(Commande c) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into COMMANDE values(?,CURDATE(),?,?,?,?);")){
            int numCom = maxIdCommande()+1;
            ps.setInt(1,numCom);
            if (c.isEnLigne())ps.setString(2,"O");
            else ps.setString(2,"N");
            if (c.isLivraison())ps.setString(3, "C");
            else ps.setString(3, "M");
            ps.setInt(4, c.getClient().getId());
            ps.setInt(5, c.getMagasin().getIdMagasin());
            List<DetailCommande> detailCommandes = c.getDetailCommandes();
            for(DetailCommande dc:detailCommandes){
                try(PreparedStatement ps2 = laConnexion.prepareStatement("insert into DETAILCOMMANDE values(?,?,?,?,?);")){
                    ps2.setInt(1, numCom);
                    ps2.setInt(2, dc.getNumlig());
                    ps2.setString(3, dc.getLivre().getIsbn());
                    ps2.setInt(4, dc.getQte());
                    ps2.setDouble(5, dc.getPrixVente());
                }
            }
        }
	}

    /**
     * Cette méthode permet de vérifier si un livre existe dans le magasin grâce à son titre
     * @param entrer le title du livre à vérifier
     * @param mag l'id du magasin dans lequel on vérifie l'existence du livre
     * @return le livre s'il existe, sinon null
     * @throws SQLException
     */
    public Livre verifLivreExiste(String entrer,int mag) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select isbn,titre,nbPages,datepubli,prix from MAGASIN natural join POSSEDER natural join LIVRE where idmag=? and titre=?")){
            ps.setInt(1, mag);
            ps.setString(2, entrer);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String isbn = rs.getString("isbn");
                String titre = rs.getString("titre");
                int nbPages = rs.getInt("nbPages");
                String datePubli = rs.getString("datepubli");
                double prix = rs.getDouble("prix");
                Livre livre = new Livre(isbn, titre, nbPages, datePubli, prix);
                return livre;
            }
        }
        return null;
            
    }

    /**
     * Cette méthode retourne le stock d'un livre dans un magasin
     * @param livre
     * @param mag
     * @return le stock du livre dans le magasin, retourne 0 si le livre n'existe pas dans le magasin
     * @throws SQLException
     */
    public int avoirStockLivre(Livre livre,int mag) throws SQLException{
        int stock = 0;
        try(PreparedStatement ps = laConnexion.prepareStatement("select qte from MAGASIN natural join POSSEDER where idmag=? and isbn=?")){
            ps.setInt(1, mag);
            ps.setString(2, livre.getIsbn());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                stock = rs.getInt("qte");
            }
        }
        return stock;
            
    }

}
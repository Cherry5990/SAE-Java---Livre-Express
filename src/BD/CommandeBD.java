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
            ps.executeUpdate();
            List<DetailCommande> detailCommandes = c.getDetailCommandes();
            for(DetailCommande dc:detailCommandes){
                try(PreparedStatement ps2 = laConnexion.prepareStatement("insert into DETAILCOMMANDE values(?,?,?,?,?);")){
                    ps2.setInt(1, numCom);
                    ps2.setInt(2, dc.getNumlig());
                    ps2.setString(5, dc.getLivre().getIsbn());
                    ps2.setInt(3, dc.getQte());
                    ps2.setDouble(4, dc.getPrixVente());
                    ps2.executeUpdate();
                }
            }
        }
	}

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

    public String voirCommandeClient(int id) throws SQLException{
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT numcom,datecom,enligne,livraison,nommag from COMMANDE natural join MAGASIN where idcli = ?";
        try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int numcom = rs.getInt("numcom");
                Date datecom = rs.getDate("datecom");
                String enligne = rs.getString("enligne");
                String livraison = rs.getString("livraison");
                String nommag = rs.getString("nommag");
                sb.append("Commande n°").append(numcom)
                  .append(" | Date: ").append(datecom)
                  .append(" | En ligne: ").append(enligne)
                  .append(" | Livraison: ").append(livraison)
                  .append(" | Magasin: ").append(nommag)
                  .append("\n");
            }
        }
        return sb.toString();
    }

    public String getCommande(int numcom,int idclient) throws SQLException {
        boolean found = false;
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement ps = laConnexion.prepareStatement("select numcom,numlig,qte,prixvente,titre from COMMANDE natural join DETAILCOMMANDE natural join LIVRE where numcom=? and idcli=?")) {
            ps.setInt(1, numcom);
            ps.setInt(2, idclient);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                found = true;
                int numCom = rs.getInt("numcom");
                int numLig = rs.getInt("numlig");
                int qte = rs.getInt("qte");
                double prixVent = rs.getDouble("prixvente");
                String titre = rs.getString("titre");
                sb.append("Commande n°").append(numCom)
                  .append(" | Ligne: ").append(numLig)
                  .append(" | Titre: ").append(titre)
                  .append(" | Quantité: ").append(qte)
                  .append(" | Prix de vente: ").append(prixVent)
                  .append("\n");
            }
        }
        catch(SQLException e){
            System.out.println("commande inconnu"+e.getMessage());
        }
        if(!found){
            return null;
        }
        return sb.toString();
    }
}
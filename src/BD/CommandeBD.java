package BD;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

import modele.Commande;
import modele.DetailCommande;
import modele.Livre;

public class CommandeBD {
	ConnexionMySQL laConnexion;
	Statement st;
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
        try(PreparedStatement ps = laConnexion.prepareStatement("insert into COMMANDE values(?,?,?,?,?,?);")){
            int numCom = maxIdCommande()+1;
            ps.setInt(1,numCom);
            ps.setString(2, c.getDateCom());
            if (c.isEnLigne())ps.setString(3,"O");
            else ps.setString(3,"N");
            if (c.isLivraison())ps.setString(4, "C");
            else ps.setString(4, "M");
            ps.setInt(5, c.getClient().getId());
            ps.setInt(6, c.getMagasin().getIdMagasin());
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

    public Livre verifLivreExiste(String entrer) throws SQLException{
        try(PreparedStatement ps = laConnexion.prepareStatement("select * from LIVRE")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String nom = rs.getString("titre");

                if (nom.equals(entrer)){
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
    }

}
package BD;
import java.sql.*;
import java.util.List;

import modele.Commande;
import modele.DetailCommande;

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
            List<DetailCommande> detailCommandes = c.getDetailComanndes();
            for(DetailCommande dc:detailCommandes){
                try(PreparedStatement ps2 = laConnexion.prepareStatement("insert into DETAILCOMMANDE values(?,?,?,?,?);")){
                    ps2.setInt(1, numCom);
                    ps2.setInt(2, dc.getNumlig());
                    ps2.setInt(3, dc.getLivre().getIsbn());
                    ps2.setInt(4, dc.getQte());
                    ps2.setDouble(5, dc.getPrixVente());
                }
            }
        }
	}
}
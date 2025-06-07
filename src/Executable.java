import java.sql.SQLException;
import BD.ConnexionMySQL;
import BD.*;


public class Executable{
    public static void main(String[] args) throws ClassNotFoundException {
        try{
            ConnexionMySQL con = new ConnexionMySQL();

            con.connecter("localhost", "java");
            ClientBD clientBD = new ClientBD(con);
            VendeurBD vendeurBD = new VendeurBD(con);
            CommandeBD commandeBD = new CommandeBD(con);
            MagasinBD magasinBD = new MagasinBD(con);
            
            
            //clientBD.insererClient("Désiré", "Doué", "Tour Eiffel", 93000, "Paris");


            //vendeurBD.insererVendeur("Nathan", "Joubert", 7);
            //vendeurBD.deleteVendeur(vendeurBD.maxIdVendeur());
            //System.out.println(magasinBD.voirStock(1));
            //System.out.println(magasinBD.voirStock(1,10,20));
            System.out.println(magasinBD.afficheMagasins());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException ex){
            System.out.println("Driver MySQL non trouvé!!!");
            System.exit(1);
        }
    }
}
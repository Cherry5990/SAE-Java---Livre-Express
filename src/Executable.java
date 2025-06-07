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
            
            System.out.println(clientBD.maxIdClient());
            clientBD.insererClient("Nathan", "Joubert", "69 rue des pomiers", 45000, "Orléans");
            clientBD.deleteClient(clientBD.maxIdClient());



            System.out.println(vendeurBD.maxIdVendeur());
            vendeurBD.insererVendeur("Nathan", "Joubert", 7);
            vendeurBD.deleteVendeur(vendeurBD.maxIdVendeur());
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
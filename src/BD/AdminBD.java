package BD;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.App;
import modele.Client;
import modele.Livre;

public class AdminBD {
    ConnexionMySQL laConnexion;
	public AdminBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}


    //Chiffre d'affaire - début
    /**
     * Calcul le chiffre d'affaire total de tous les temps
     * @return le chiffre d'affaire total
     */
    public String chiffreDAffaireTotalToutTemps(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement(" SELECT SUM(qte * prixvente) AS CA FROM DETAILCOMMANDE ;")){
            ResultSet rs = ps.executeQuery();
            sb.append("Les chiffre d'affaire total est de ");
            rs.next();
            String res = rs.getString("CA");
            sb.append(res + " euros");
        return sb.toString();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de chiffre d'affaire";
        }

    }

    /**
     * Calcul le chiffre d'affaire total de tous les temps par magasin
     * @return le chiffre d'affaire total par magasin
     */
    public String chiffreDAffaireMagasinToutTemps(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, SUM(qte * prixvente) CA FROM DETAILCOMMANDE natural join COMMANDE natural join MAGASIN GROUP BY idmag, nommag ORDER BY idmag;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-25s %-10s\n", "idmag", "nommag", "CA"));
            while(rs.next()){
                sb.append(String.format("%-5s %-25s %-10s\n",
                        rs.getInt("idmag"),
                        
                        rs.getString("nommag") ,
                        rs.getDouble("CA")+ " euros"));
            }
        return sb.toString();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de chiffre d'affaire";
        }
    }

    /**
     * Calcul le chiffre d'affaire total par an
     * @return le chiffre d'affaire total par an
     */
    public String chiffreDAffaireTotalParAns(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT YEAR(datecom) annee, SUM(qte * prixvente) CA FROM DETAILCOMMANDE natural join COMMANDE GROUP BY annee ORDER BY annee;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-10s\n", "annee", "CA"));
            while(rs.next()){
                sb.append(String.format("%-5s %-10s\n",
                    rs.getInt("annee"),
                    rs.getInt("CA")+ " euros" ));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de chiffre d'affaire";
        }
    }

    /**
     * Calcul le chiffre d'affaire total pour un magasin à une année donnée
     * @param idmag l'identifiant du magasin
     * @return le chiffre d'affaire total du magasin à l'année donnée
     */
    public String chiffreDAffaireMagasinParAns(Integer anne){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, YEAR(datecom) annee, SUM(qte * prixvente) CA FROM DETAILCOMMANDE  natural join COMMANDE natural join MAGASIN WHERE YEAR(datecom) = ? GROUP BY idmag, nommag, annee ORDER BY idmag  ;")){
            ps.setInt(1, anne);
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-25s %-10s\n", "idmag", "nommag","CA"));
            while(rs.next()){
                sb.append(String.format("%-5s %-25s %-10s\n",
                        rs.getInt("idmag"),
                        rs.getString("nommag") ,
                        rs.getDouble("CA")+ " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de chiffre d'affaire";
        }
    }

    //Chiffre d'affaire - fin

    /**
     * Affiche les 10 livres les plus vendus de tous les temps
     * @return une chaîne de caractères contenant les 10 livres les plus vendus
     */
    public List<Livre> livresLesPlusVendusTotalToutTemps(){
        List<Livre> recos = new ArrayList<>();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE GROUP BY isbn, titre ORDER BY qte DESC LIMIT 10;")){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String titre =rs.getString("titre");
                recos.add(App.livreBD.getLivre(rs.getString("isbn")));
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return recos;
    }

    /**
     * Affiche les 10 livres les plus vendus par année
     * @return une chaîne de caractères contenant les 10 livres les plus vendus par année
     */
    public String livresLesPlusVendusTotalParAns(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE GROUP BY isbn, titre ORDER BY qte DESC LIMIT 10;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-40s %-5s\n", "isbn", "titre", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-5s\n",
                        rs.getString("isbn"),
                        
                        titre ,
                        rs.getInt("qte")+ " exemplaires"));
            }
        return sb.toString();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de livres vendus";
        }
    }

    /**
     * Affiche les 10 livres les plus vendus pour une année donnée
     * @param annee 
     * @return une chaîne de caractères contenant les 10 livres les plus vendus pour l'année donnée
     */
    public String livresLesPlusVendusTotalParAns(Integer annee){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, YEAR(datecom) annee, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE WHERE YEAR(datecom) = ? GROUP BY annee, isbn, titre ORDER BY annee, qte DESC LIMIT 10;")){
            ps.setInt(1, annee);
            ResultSet rs = ps.executeQuery();
            sb.append("Les 10 livres les plus vendu pour l'annee " + annee +"\n");
            sb.append(String.format("%-15s %-40s %-5s\n", "isbn", "titre", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-5s\n",
                        rs.getString("isbn"),
                        
                        titre ,
                        rs.getInt("qte")+ " exemplaires"));
            }
        return sb.toString();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de livres vendus";
        }
    }

    /**
     * Affiche les 10 livres les plus vendus pour un magasin donné de tous les temps
     * @param idmag
     * @return une chaîne de caractères contenant les 10 livres les plus vendus pour le magasin donné
     */
    public String livresLesPlusVendusParMagasinToutTemps(Integer idmag){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, idmag, nommag, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE NATURAL JOIN MAGASIN WHERE idmag = ? GROUP BY idmag, nommag, isbn, titre ORDER BY qte DESC LIMIT 10;")){
            ps.setInt(1, idmag);
            ResultSet rs = ps.executeQuery();
            sb.append("Les 10 livres les plus vendu pour le magasin " + idmag+"\n");
            sb.append(String.format("%-15s %-40s %-5s\n", "isbn", "titre", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-5s\n",
                        rs.getString("isbn"),
                        titre ,
                        rs.getInt("qte")+ " exemplaires"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de livres vendus";
        }
    }

    /**
     * Affiche les 10 livres les plus vendus pour un magasin donné pour une année donnée
     * @param idmag l'identifiant du magasin
     * @param annee l'année
     * @return une chaîne de caractères contenant les 10 livres les plus vendus pour le magasin et l'année donnée
     */
    public String livresLesPlusVendusParMagasinParAns(Integer idmag, Integer annee){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, idmag, nommag, YEAR(datecom) annee, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE NATURAL JOIN MAGASIN WHERE idmag = ? and YEAR(datecom) = ? GROUP BY annee, idmag, nommag, isbn, titre ORDER BY annee, qte DESC LIMIT 10;")){
            ps.setInt(1, idmag);
            ps.setInt(2, annee);
            ResultSet rs = ps.executeQuery();
            sb.append("Les 10 livres les plus vendu pour le magasin " + idmag + " pour l'annee " + annee+"\n");
            sb.append(String.format("%-15s %-40s %-5s\n", "isbn", "titre", "qte"));
            while(rs.next()){
                String titre =rs.getString("titre");
                if (titre.length() > 35){
                    titre = titre.substring(0, 35) + "...";
                }
                sb.append(String.format("%-15s %-40s %-5s\n",
                        rs.getString("isbn"),
                        titre ,
                        rs.getInt("qte")+ " exemplaires"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de livres vendus";
        }
    }

    //10 livres les plus vendus - fin 

    //Ventes en ligne contre en magasin - début
    /**
     * Affiche les ventes en ligne contre les ventes en magasin de tous les temps
     * @return une chaîne de caractères contenant les ventes en ligne contre en magasin
     */
    public String ventesLigneContreMagasinToutTemps(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT enligne, SUM(qte * prixvente) CA FROM DETAILCOMMANDE NATURAL JOIN COMMANDE GROUP BY enligne")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-10s %-10s\n", "enligne", "CA"));
            while(rs.next()){
                sb.append(String.format("%-10s %-10s\n",
                        rs.getString("enligne"),
                        rs.getDouble("CA") + " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de ventes";
        }
    }

    /**
     * Affiche les ventes en ligne contre les ventes en magasin par année
     * @param annee l'année pour laquelle on veut les ventes
     * @return une chaîne de caractères contenant les ventes en ligne contre en magasin par an
     */
    public String ventesLigneContreMagasinParAns(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT enligne, YEAR(datecom) annee, SUM(qte * prixvente) CA FROM DETAILCOMMANDE NATURAL JOIN COMMANDE GROUP BY enligne, annee ORDER BY annee, enligne;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-10s %-10s %-10s\n", "enligne", "annee", "CA"));
            while(rs.next()){
                sb.append(String.format("%-10s %-10s %-10s\n",
                        rs.getString("enligne"),
                        rs.getInt("annee"),
                        rs.getDouble("CA") + " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de ventes";
        }
    }

    /**
     * Affiche les ventes en ligne contre les ventes en magasin par magasin de tous les temps
     * @return une chaîne de caractères contenant les ventes en ligne contre en magasin par magasin
     */
    public String ventesLigneContreMagasinParMagasinTousTemps(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, enligne, SUM(qte * prixvente) CA FROM DETAILCOMMANDE NATURAL JOIN COMMANDE NATURAL JOIN MAGASIN GROUP BY idmag, nommag, enligne ORDER BY idmag, enligne;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-25s %-10s %-10s\n", "idmag", "nommag", "enligne", "CA"));
            while(rs.next()){
                sb.append(String.format("%-5s %-25s %-10s %-10s\n",
                        rs.getInt("idmag"),
                        rs.getString("nommag"),
                        rs.getString("enligne"),
                        rs.getDouble("CA") + " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de ventes";
        }
    }    
    
    /**
     * Affiche les ventes en ligne contre les ventes en magasin par magasin pour une année donnée
     * @param annee l'année pour laquelle on veut les ventes
     * @return une chaîne de caractères contenant les ventes en ligne contre en magasin par magasin pour l'année donnée
     */
    public String ventesLigneContreMagasinParMagasinParAns(Integer annee){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, YEAR(datecom) annee, enligne, SUM(qte * prixvente) CA FROM DETAILCOMMANDE NATURAL JOIN COMMANDE NATURAL JOIN MAGASIN WHERE YEAR(datecom) = ? GROUP BY idmag, nommag, annee, enligne ORDER BY annee, idmag, enligne; ")){
            ps.setInt(1, annee); 
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-25s %-10s %-10s\n", "idmag", "nommag", "enligne", "CA"));
            while(rs.next()){
                sb.append(String.format("%-5s %-25s %-10s %-10s\n",
                        rs.getInt("idmag"),
                        rs.getString("nommag"),
                        rs.getString("enligne"),
                        rs.getDouble("CA") + " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de ventes";
        }
    }
  
    //Ventes en ligne contre en magasin - fin

    //Valeur du stock - début

    /**
     * Calcule la valeur totale du stock de tous les livres présents dans une librairie quelconque
     * @return une chaîne de caractères contenant la valeur totale du stock
     */
    public String valeurStockTotal(){
        try{
            PreparedStatement ps = laConnexion.prepareStatement("SELECT SUM(qte * prix) stock FROM POSSEDER NATURAL JOIN LIVRE;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return "La valeur totale du stock est de " + rs.getDouble("stock") + " euros";
            }else{
                return "Pas de stock";
            }
    }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de stock";
        }
    }

    /**
     * Calcule la valeur du stock de chaque magasin
     * @return une chaîne de caractères contenant la valeur du stock par magasin
     */
    public String valeurStockParMagasin(){
        StringBuilder sb = new StringBuilder();
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT idmag, nommag, SUM(qte * prix) stock FROM POSSEDER NATURAL JOIN MAGASIN NATURAL JOIN LIVRE GROUP BY idmag, nommag ORDER BY idmag;")){
            ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-5s %-25s %-10s\n", "idmag", "nommag", "stock"));
            while(rs.next()){
                sb.append(String.format("%-5s %-25s %-10s\n",
                        rs.getInt("idmag"),
                        rs.getString("nommag"),
                        rs.getDouble("stock") + " euros"));
            }
        return sb.toString();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "Pas de stock";
        }
    }

    public boolean connexionAdmin(String nom,String mdp){
        try(PreparedStatement ps =laConnexion.prepareStatement("select idadmin,nomcompte,mdpcompte from ADMIN where nomcompte=? and mdpcompte=?")){
            ps.setString(1, nom);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    //------------------ POUR LES GRAPHIQUES ------------------
    
    /**
     * Calcul le chiffre d'affaire total par an
     * @return le chiffre d'affaire total par an
     */
    public ArrayList<Map.Entry<String,Integer>> chiffreDAffaireTotalParAnsGraphique(){
        ArrayList<Map.Entry<String, Integer>> lstResult = new ArrayList<>();
        String annee;
        Integer CA;
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT YEAR(datecom) annee, SUM(qte * prixvente) CA FROM DETAILCOMMANDE natural join COMMANDE GROUP BY annee ORDER BY annee;")){
            ResultSet result = ps.executeQuery();
            while(result.next()){
                annee = "" + result.getInt(1);
                CA = result.getInt(2);
                lstResult.add(new AbstractMap.SimpleEntry<>(annee, CA));
            }
            return lstResult;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Calcul le chiffre d'affaire total par an
     * @return le chiffre d'affaire total par an
     */
    public ArrayList<Map.Entry<String,Integer>> chiffreDAffaireTotalParMagasinGraphique(){
        ArrayList<Map.Entry<String, Integer>> lstResult = new ArrayList<>();
        String magasin;
        Integer CA;
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT nommag, SUM(qte * prixvente) CA FROM DETAILCOMMANDE natural join COMMANDE natural join MAGASIN GROUP BY nommag ORDER BY nommag")){
            ResultSet result = ps.executeQuery();
            while(result.next()){
                magasin = result.getString(1);
                CA = result.getInt(2);
                lstResult.add(new AbstractMap.SimpleEntry<>(magasin, CA));
            }
            return lstResult;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Affiche les 10 livres les plus vendus pour une année donnée
     * @param annee 
     * @return une chaîne de caractères contenant les 10 livres les plus vendus pour l'année donnée
     */
    public ArrayList<Map.Entry<String,Integer>> livresLesPlusVendusTotalParAnsGraphique(Integer annee){
        ArrayList<Map.Entry<String, Integer>> lstResult = new ArrayList<>();
        String livre;
        Integer qte;
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT isbn, titre, SUM(qte) qte FROM LIVRE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN COMMANDE WHERE YEAR(datecom) = ? GROUP BY isbn, titre ORDER BY qte DESC LIMIT 10;")){
            ps.setInt(1, annee);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                String titre = result.getString(2);
                if (titre.length() > 21){
                    livre = /*result.getString(1) + " - " + */titre.substring(0, 20);
                } else {
                    livre = /*result.getString(1) + " - " +*/ titre;
                }
                qte = result.getInt(3);
                lstResult.add(new AbstractMap.SimpleEntry<>(livre, qte));
            }
            return lstResult;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Affiche les ventes en ligne contre les ventes en magasin par magasin pour une année donnée
     * @param annee l'année pour laquelle on veut les ventes
     * @return une chaîne de caractères contenant les ventes en ligne contre en magasin par magasin pour l'année donnée
     */
    public ArrayList<Map.Entry<String,Integer>> ventesLigneContreMagasinParMagasinParAnsGraphique(Integer annee){
        ArrayList<Map.Entry<String, Integer>> lstResult = new ArrayList<>();
        String modeachat = "";
        Integer CA;
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT enligne, SUM(qte * prixvente) CA FROM DETAILCOMMANDE NATURAL JOIN COMMANDE NATURAL JOIN MAGASIN WHERE YEAR(datecom) = ? GROUP BY enligne")){
            ps.setInt(1, annee); 
            ResultSet result = ps.executeQuery();
            while(result.next()){
                CA = result.getInt(2);
                switch (result.getString(1)) {
                    case "O":
                        modeachat = "En ligne (" + CA + " € de CA)";
                        break;
                    case "N":
                        modeachat = "En magasin (" + CA + " € de CA)";
                        break;
                    default:
                        break;
                }
                lstResult.add(new AbstractMap.SimpleEntry<>(modeachat, CA));
            }
            return lstResult;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Calcule la valeur du stock de chaque magasin
     * @return une chaîne de caractères contenant la valeur du stock par magasin
     */
    public ArrayList<Map.Entry<String,Integer>> valeurStockParMagasinGraphique(){
        ArrayList<Map.Entry<String, Integer>> lstResult = new ArrayList<>();
        String nommag;
        Integer valeur;
        try(PreparedStatement ps = laConnexion.prepareStatement("SELECT nommag, SUM(qte * prix) stock FROM POSSEDER NATURAL JOIN MAGASIN NATURAL JOIN LIVRE GROUP BY nommag ORDER BY nommag")){
            ResultSet result = ps.executeQuery();
            while(result.next()){
                nommag = result.getString(1);
                valeur = result.getInt(2);
                lstResult.add(new AbstractMap.SimpleEntry<>(nommag, valeur));
            }
            return lstResult;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}


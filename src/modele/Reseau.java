package modele;

import java.util.ArrayList;
import java.util.List;

public class Reseau {
    private List<Magasin> magasins;
    private List<Vendeur> vendeurs;
    private List<Admin> admins;
    private List<Client> clients;
    private int idMag;
    private int idCompte;

    public Reseau(){
        this.magasins = new ArrayList<>();
        this.vendeurs = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.idMag = 1;
        this.idCompte = 1;
    }

    public List<Magasin> getMagasins(){return this.magasins;}
    public List<Vendeur> getVendeurs(){return this.vendeurs;}
    public List<Admin> getAdmins(){return this.admins; }
    public List<Client> getClients(){return this.clients;}
    public int getIdMag(){return this.idMag;}
    public int getIdCompte(){return this.idCompte;}

}


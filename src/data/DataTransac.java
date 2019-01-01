/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Constantes.Constantes;

/**
 *
 * @author Fares
 */
public class DataTransac implements ActionsBD {

    private Connection dbConn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ArrayList<ProgrammeurBean> listeProgrammeurs;
    private ProgrammeurBean prog;

    /**
     * Le constructeur permet d'initialiser la connexion
     *
     */
    public DataTransac() {
        try {
            dbConn = DriverManager.getConnection(Constantes.URL, Constantes.USER, Constantes.MDP);
        } catch (SQLException sqle) {
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }

    }

    /**
     * Lance la récupère passée en paramètre et retourne le ResultSet
     * correspondant à cette requête
     *
     * @param req La requête SQL que l'on souhaite exécuter
     * @return rs Une variable de type ResultSet
     */
    @Override
    public ResultSet getResultSet(String req) {
        try {
            stmt = dbConn.createStatement();
            rs = stmt.executeQuery(req);
        } catch (SQLException sqle) {
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rs;
    }

    /**
     * Cette méthode récupère toutes les infos d'un programmeur et retourne une
     * liste de l'ensemble des programmeurs
     *
     * @return listeProgrammeurs Une variable de type ArrayList
     */
    @Override
    public ArrayList getProgrammeurs() {
        rs = this.getResultSet(Constantes.REQUETE_TOUS);
        listeProgrammeurs = new ArrayList<>();

        try {
            while (rs.next()) {
                prog = new ProgrammeurBean();
                prog.setMatricule(rs.getInt("MATRICULE"));
                prog.setPrenom(rs.getString("PRENOM"));
                prog.setNom(rs.getString("NOM"));
                prog.setAdresse(rs.getString("ADRESSE"));
                prog.setPseudo(rs.getString("PSEUDO"));
                prog.setHobby(rs.getString("HOBBY"));
                prog.setResponsable(rs.getString("RESPONSABLE"));
                prog.setAnNaissance(rs.getString("DATE_NAISS"));
                prog.setAnEmbauche(rs.getString("DATE_EMB"));
                listeProgrammeurs.add(prog);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        
        return listeProgrammeurs;
    }

    /**
     * Cette méthode récupère toutes les infos d'un programmeur et retourne ce
     * programmeur sous la forme d'un Java Bean Cette méthode est utilisée pour
     * rechercher un progammeur via son matricule
     *
     * @param nom Le nom saisi par l'utilisateur pour lancer la recherche
     * @return prog Une variable de type ProgrammeurBean
     *
     */
    @Override
    public ProgrammeurBean getProgrammeur(String nom) {
        try {
            pstmt = dbConn.prepareStatement(Constantes.REQUETE_UNIQUE);
            pstmt.setString(1, nom);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                prog = new ProgrammeurBean();
                  prog = new ProgrammeurBean();
                 prog.setMatricule(rs.getInt("MATRICULE"));
                prog.setPrenom(rs.getString("PRENOM"));
                prog.setNom(rs.getString("NOM"));
                prog.setAnNaissance(rs.getString("DATE_NAISS"));
                prog.setAnEmbauche(rs.getString("DATE_EMB"));
                prog.setHobby(rs.getString("HOBBY"));
                prog.setResponsable(rs.getString("RESPONSABLE"));
                prog.setPseudo(rs.getString("PSEUDO"));
                listeProgrammeurs.add(prog);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return prog;
    }

    /**
     * Cette méthode permet de construire la chaîne de caractères qui sera
     * affichée lorsqu'on choisit Programmeur - Afficher - Tous
     *
     * @return listeProg Une variable de type String
     *
     */
    @Override
    public String afficherProgrammeurs() {
        String listeProg = "";

        listeProgrammeurs = this.getProgrammeurs();
        for (ProgrammeurBean progr : listeProgrammeurs) {
            listeProg = listeProg + progr;
            listeProg=listeProg+"\n";
        }

        return listeProg;
    }

    /**
     * Cette méthode permet de libérer les ressources liées à la base de données
     * *
     */
    @Override
    public void fermerRessources() {
        if (dbConn != null) {
            try {
                dbConn.close();
                if (stmt != null) {
                    stmt.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                dbConn = null;
            } catch (SQLException ex) {
                Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @Override
    public int modifierProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String nouvelleAdresse ) {
        
        
        ArrayList<String> result= new ArrayList<>();
        result=rechercherProgrammeurs(matricule);
        
        if(result.isEmpty())
        {
            return 0;
        }
        
        else
        {
        listeProgrammeurs = this.getProgrammeurs();
        matricule="'"+matricule+"'";
        nouveauNom="'"+nouveauNom+"'";
        nouveauPrénom="'"+nouveauPrénom+"'";
        nouveauHobby="'"+nouveauHobby+"'";
        nouveauRespo="'"+nouveauRespo+"'";
        nouvelleDateNaiss="'"+nouvelleDateNaiss+"'";
        nouvelleDateEmb="'"+nouvelleDateEmb+"'";
        nouvelleAdresse="'"+nouvelleAdresse+"'";
        nouveauPseudo="'"+nouveauPseudo+"'";
        
        
        String requete="UPDATE PROGRAMMEUR "
                + "SET NOM = " + nouveauNom + ", "
                + "PRENOM = " + nouveauPrénom + ", "
                + "HOBBY = " + nouveauHobby + ", "
                + "RESPONSABLE = " + nouveauRespo + ", "
                + "PSEUDO= " + nouveauPseudo +", "
                + "DATE_NAISS= " + nouvelleDateNaiss +", "
                + "DATE_EMB = " + nouvelleDateEmb + ", "
                + "ADRESSE = " + nouvelleAdresse + " "
                + "WHERE MATRICULE=" + matricule  ; 
        try {
            pstmt = dbConn.prepareStatement(requete);
            pstmt.execute();
        }catch(SQLException sqle){
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        
        
        return 1;
        
        }
        
        
    }
    
    @Override
    public int ajouterProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String adresse) {
        
         ArrayList<String> result= new ArrayList<>();
        result=rechercherProgrammeurs(matricule);
        if(matricule.length()==0)
        {
            return -1;
        }
        if(!result.isEmpty())
        {
            return 0;
        }
        
        
  
  else
        {
        listeProgrammeurs = this.getProgrammeurs();
        matricule="'"+matricule+"'";
        nouveauNom="'"+nouveauNom+"'";
        nouveauPrénom="'"+nouveauPrénom+"'";
        nouveauHobby="'"+nouveauHobby+"'";
        nouveauRespo="'"+nouveauRespo+"'";
        nouvelleDateNaiss="'"+nouvelleDateNaiss+"'";
        nouvelleDateEmb="'"+nouvelleDateEmb+"'";
        adresse="'"+adresse+"'";
        nouveauPseudo="'"+nouveauPseudo+"'";
        
        
        
        String requete = Constantes.REQUETE_INSERT
                + matricule + ","
                + nouveauNom + ","
                + nouveauPrénom + ","
                + adresse + ","
                + nouveauPseudo + ","
                + nouveauRespo + ","
                + nouveauHobby + ","
                + nouvelleDateNaiss + ","
                + nouvelleDateEmb + ")"  ;
        try {
            pstmt = dbConn.prepareStatement(requete);
            pstmt.execute();
        }catch(SQLException sqle){
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return 1;
        
        }
    
    }
    
    @Override
    public int supprimerProgrammeurs(String matricule){
        
        ArrayList<String> result= new ArrayList<>();
        result=rechercherProgrammeurs(matricule);
        System.out.println("Result size : " + result.size());
        if(matricule.length()==0)
        {
            return -2;
        }
        
        if(result.isEmpty())
        {
            return 0;
        }
        
        else
        {
        matricule="'"+matricule+"'";
        String requete  = Constantes.REQUETE_DELETE +matricule;
       
        
        try{
            pstmt=dbConn.prepareStatement(requete);
            pstmt.execute();
        }catch(SQLException sqle){
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        
            return 1;
        }
        
        
        
    }
    
    @Override
    public ArrayList <String> rechercherProgrammeurs(String matricule)
    {
        matricule="'"+matricule+"'";  
      String requete  = Constantes.REQUETE_UNIQUE + matricule;
      String date_naiss;
      String date_emb;
      String nouveauPrénom;
      String nouveauNom;
      String nouveauHobby;
      String nouveauRespo;
      String nouveauPseudo,adresse,ann_emb,jour_emb,ann_naiss,jour_naiss,mois_naiss,mois_emb;
      ArrayList <String> result = new ArrayList <String> ();
      
    
      try{
            pstmt=dbConn.prepareStatement(requete);
            rs=pstmt.executeQuery();
            
                
            
            while(rs.next()==true)
            {
               nouveauPrénom = rs.getString("PRENOM");
               nouveauNom= rs.getString("NOM");
               date_naiss=rs.getString("DATE_NAISS");
               date_emb=rs.getString("DATE_EMB");
               nouveauHobby=rs.getString("HOBBY");
               nouveauRespo=rs.getString("RESPONSABLE");
               nouveauPseudo=rs.getString("PSEUDO");
               adresse=rs.getString("ADRESSE");

               ann_emb=date_emb.substring(0,4);
               jour_emb=date_emb.substring(8);
               ann_naiss=date_naiss.substring(0, 4);
               jour_naiss=date_naiss.substring(8);
               mois_naiss=date_naiss.substring(6,7);
               mois_emb=date_emb.substring(6,7); 
               
               
               result.add(nouveauNom); //O
               result.add(nouveauPrénom); //1
               result.add(adresse); //2
               result.add(nouveauPseudo); //3
               result.add(nouveauRespo); //4
               result.add(nouveauHobby); //5
               result.add(jour_naiss); //6
               result.add(mois_naiss); //7
               result.add(ann_naiss);//8
               result.add(jour_emb);//9
               result.add(mois_emb);//10
               result.add(ann_emb);//11
               
            }
     
        }catch(SQLException sqle){
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return result;
    }     
      

}

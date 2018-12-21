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
import java.text.SimpleDateFormat;

/**
 *
 * @author Fares
 */
public class DataTransac {

    private Connection dbConn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ArrayList<ProgrammeurBean> listeProgrammeurs;
    private ProgrammeurBean prog;
   String pattern = "yyyy-MM-dd";
   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

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
     * @return listeProgrammeurs Une variable de type ArryList
     */
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
        System.out.println("commit");
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
    
    
    public void modifierProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String nouvelleAdresse ) {
        
        
        
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
        System.out.println("Modifier :" + requete + "\n" );
            
        
        
        
        
    }
    
    public void ajouterProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String adresse) {
        
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
        
        
        
        String requete = "INSERT INTO PROGRAMMEUR (MATRICULE,NOM,PRENOM,ADRESSE,PSEUDO,RESPONSABLE,HOBBY,DATE_NAISS,DATE_EMB) VALUES ("
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
        System.out.println("Ajouter :" + requete + "\n" );
    
    }
    
    public void supprimerProgrammeurs(String matricule){
        
        matricule="'"+matricule+"'";
        String requete  = "DELETE FROM PROGRAMMEUR WHERE MATRICULE = " +matricule;
        
        try{
            pstmt=dbConn.prepareStatement(requete);
            pstmt.execute();
        }catch(SQLException sqle){
            Logger.getLogger(DataTransac.class.getName()).log(Level.SEVERE, null, sqle);
        }
        
    }

}

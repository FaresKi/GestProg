/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author fkissoum
 */
public interface ActionsBD {
    
    ResultSet getResultSet(String req);
    ArrayList getProgrammeurs();
    String afficherProgrammeurs() ;
    void fermerRessources();
    public int modifierProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String nouvelleAdresse );
    public void ajouterProgrammeurs(String matricule, String nouveauNom, String nouveauPrénom, String nouveauHobby, String nouveauRespo, String nouveauPseudo, String nouvelleDateNaiss, String nouvelleDateEmb, String adresse);
    public int supprimerProgrammeurs(String matricule);
    public ArrayList <String> rechercherProgrammeurs(String matricule);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author fkissoum
 */
public class ProgrammeurBean {
    private String nom;
    private String prenom;
    private String adresse;
    private String responsable;
    private String hobby;
    private String pseudo;
    private String anNaissance;
    private String anEmbauche;
    private int matricule;

    public ProgrammeurBean() {
    }

    @Override
    public String toString() {
        String affichage =   this.getPrenom() + " "
                            + this.getNom() + " "
                            + this.getAnNaissance() + " "
                            + this.getAdresse() + " "
                            + this.getResponsable() + " "
                            + this.getHobby() + " "
                            + this.getAnEmbauche() + " "
                            + this.getPseudo() + " "
                            + this.getMatricule()+ " ";
                            
        return affichage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAnNaissance() {
        return anNaissance;
    }

    public void setAnNaissance(String anNaissance) {
        this.anNaissance = anNaissance;
    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAnEmbauche() {
        return anEmbauche;
    }

    public void setAnEmbauche(String anEmbauche) {
        this.anEmbauche = anEmbauche;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    
}

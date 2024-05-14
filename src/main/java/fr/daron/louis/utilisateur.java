package fr.daron.louis;


public class utilisateur {
    
    // Initialisation des attributs de la classe utilisateur

    private static String matricule;

    public static void setMatricule(String matricule) {
        utilisateur.matricule = matricule;
    }

    public static String getMatricule() {
        return matricule;
    }

    private static String nom;

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        utilisateur.nom = nom;
    }

    private static String identfiant;

    public static void setIdentfiant(String identfiant) {
        utilisateur.identfiant = identfiant;
    }

    public static String getIdentfiant() {
        return identfiant;
    }

    // Constructeur permettant la création de l'objet de la classe

    utilisateur(){
    }
}

package fr.daron.louis;

//Importation des librairies nécessaires au bon fonctionnement du code 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import org.mariadb.*;

public class Sqldb {

    // Initialisation des attributs de la classe Sqldb
    private static String url = credentialsBdd.getIpBdd();
    

    private static String user = credentialsBdd.getLogBdd();

    private static String mdp = credentialsBdd.getPwdBdd();

    // Constructeur permettant la création de l'objet de la classe
    Sqldb() {
    }

    /* Méthode executionRequete
        Nom : executionRequete
        Résultat : ResultSet (format de résultat de requete SQL en Java) nommé res
        Paramètres : String sql, qui contient une requete SQL
        Objet de la méthode : Permet d'executer la requete donnée et renvoie le résultat de la requete
     */
    static ResultSet executionRequete(String sql) throws SQLException {
        Connection c = DriverManager.getConnection(url, user, mdp);
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        return res;
    }

    /* Méthode executionUpdate
        Nom : executionUpdate
        Résultat : String qui indique si la requete à bien été exécutée
        Paramètres : String sql, qui contient une requete SQL
        Objet de la méthode : Permet d'executer la requete de mise a jour donnée et renvoie un code de validation
     */
    static String executionUpdate(String sql) throws SQLException {
        Connection c = DriverManager.getConnection(url, user, mdp);
        PreparedStatement statement = c.prepareStatement(sql);
        statement.executeUpdate();
        return "Requete effectué avec succès !";
    }
}

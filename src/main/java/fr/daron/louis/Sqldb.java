package fr.daron.louis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.mariadb.*;

public class Sqldb {
    private static String url = credentialsBdd.getIpBdd();
    // private String url = "jdbc:mariadb://88.127.124.40:12456/gsb_etudiants";

    private static String user = credentialsBdd.getLogBdd();

    private static String mdp = credentialsBdd.getPwdBdd();

    Sqldb() {
    }

    static ResultSet executionRequete(String sql) throws SQLException {
        Connection c = DriverManager.getConnection(url, user, mdp);
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        return res;
    }

    static String executionUpdate(String sql) throws SQLException {
        Connection c = DriverManager.getConnection(url, user, mdp);
        PreparedStatement statement = c.prepareStatement(sql);
        statement.executeUpdate();
        return "Requete effectué avec succès !";
    }
}

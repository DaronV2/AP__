package fr.daron.louis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.mariadb.*;



public class Sqldb {
    static String url = "jdbc:mysql://localhost/gsb_etudiants";
    //private String url = "jdbc:mariadb://88.127.124.40:12456/gsb_etudiants";

    static String user = "requeteur";

    static String mdp = "J'adorelesrequetes.";

    Sqldb(){
    }

    @SuppressWarnings("exports")
    static Connection connexionDb() throws SQLException{
        Connection c = DriverManager.getConnection(url,user,mdp);
        return c;
    }

    @SuppressWarnings("exports")
    static ResultSet exeRequete(Statement stmnt, String requete) throws SQLException{
        ResultSet res = stmnt.executeQuery(requete);
        return res;
    }

    static ResultSet executionRequete(String sql) throws SQLException{
        Connection c = DriverManager.getConnection(url,user,mdp);
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        return res;
    }
}

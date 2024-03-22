package fr.daron.louis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.mariadb.*;



public class Sqldb {
    private String url = "jdbc:mysql://localhost/gsb_etudiants";
    //private String url = "jdbc:mariadb://88.127.124.40:12456/gsb_etudiants";

    private String user = "requeteur";

    private String mdp = "J'adorelesrequetes.";

    Sqldb(){
    }

    @SuppressWarnings("exports")
    public Connection connexionDb() throws SQLException{
        Connection c = DriverManager.getConnection(this.url,this.user,this.mdp);
        return c;
    }

    @SuppressWarnings("exports")
    public ResultSet exeRequete(Statement stmnt, String requete) throws SQLException{
        ResultSet res = stmnt.executeQuery(requete);
        return res;
    }
}

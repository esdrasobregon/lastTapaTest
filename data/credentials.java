/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import entity.usuario;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author esdra
 */
public class credentials {

    public static boolean testConnector(usuario currentUsser) {
        //String contrasenya = "esnija103";
        String db = "mydb";
        //String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/";
        try {
            java.sql.Connection con = DriverManager.getConnection(
                    url + db,
                    currentUsser.getMsqlUsser(),
                    currentUsser.getContrasenya());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static java.sql.Connection getConnector(usuario currentUsser) {
        //String contrasenya = "esnija103";
        String db = "mydb";
        //String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/";
        try {
            java.sql.Connection con = DriverManager.getConnection(
                    url + db,
                    currentUsser.getMsqlUsser(),
                    currentUsser.getContrasenya());
            return con;
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

}

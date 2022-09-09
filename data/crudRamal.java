/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import entity.ramal;
import entity.tipoUnidades;
import entity.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author esdra
 */
public class crudRamal {
    public static ArrayList<ramal> getAllRamales(usuario currentUsser) {
        ArrayList<ramal> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credentials.getConnector(currentUsser);
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "idramal,"
                            + " nombre,"
                            + " estado from ramal"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ramal un = new ramal();
                un.setIdramal(rs.getInt(1));
                un.setNombre(rs.getString(2));
                un.setEstado(rs.getBoolean(3));
                list.add(un);
            }
            con.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

}

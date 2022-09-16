/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import entity.ramal;
import entity.tarifa;
import entity.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author esdra
 */
public class crudTarifa {

    public static tarifa getMaxTarifa(usuario currentUsser, ramal r) {
        tarifa list = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credentials.getConnector(currentUsser);
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "max(idtarifa),"
                            + " fechaInicio,"
                            + " fechaFin,"
                            + " valor,"
                            + " ramal_idramal from tarifa"
                            + " where fechafin is null and"
                            + " ramal_idramal = ?"
                    );
            ps.setInt(1, r.getIdramal());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new tarifa();
                list.setIdtarifa(rs.getInt(1));
                list.setFechaInicio(rs.getDate(2));
                list.setFechaFin(rs.getDate(3));
                list.setValor(rs.getDouble(4));
                list.setRamal_idramal(rs.getInt(5));
            }
            con.close();
            if (list.getRamal_idramal() == 0) {
                return null;
            } else {
                return list;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static ArrayList<tarifa> getAllTarifas(usuario currentUsser) {
        ArrayList<tarifa> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credentials.getConnector(currentUsser);
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "idtarifa,"
                            + " fechaInicio,"
                            + " fechaFin,"
                            + " valor,"
                            + " ramal_idramal from tarifa"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tarifa t = new tarifa();
                t.setIdtarifa(rs.getInt(1));
                t.setFechaInicio(rs.getDate(2));
                if (rs.getDate(3) != null) {
                    t.setFechaFin(rs.getDate(3));
                }
                t.setValor(rs.getDouble(4));
                t.setRamal_idramal(rs.getInt(5));
                list.add(t);
            }
            con.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static boolean add(usuario currentUsser, tarifa un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credentials.getConnector(currentUsser);
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO tarifa "
                            + "(fechaInicio,"
                            + " valor,"
                            + "ramal_idramal) "
                            + "VALUES(?, ?, ?);");
            ps.setDate(1, new Date(un.getFechaInicio().getTime()));
            ps.setDouble(2, un.getValor());
            ps.setInt(3, un.getRamal_idramal());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public static boolean update(usuario currentUsser, tarifa nuevaTarifa, tarifa viejaTarifa) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credentials.getConnector(currentUsser);
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("UPDATE tarifa "
                            + "SET fechaFin = subdate(?, 1) "
                            + "WHERE ramal_idramal = ? and idtarifa =?;");
            ps.setDate(1, new Date(nuevaTarifa.getFechaInicio().getTime()));
            ps.setDouble(2, viejaTarifa.getRamal_idramal());
            ps.setInt(3, viejaTarifa.getIdtarifa());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

}

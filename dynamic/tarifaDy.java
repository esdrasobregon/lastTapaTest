/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dynamic;

import entity.ramal;
import entity.tarifa;
import entity.usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author esdra
 */
public class tarifaDy {

    public static tarifa getMaxTarifa(usuario currentUsser, ramal r) {
        return data.crudTarifa.getMaxTarifa(currentUsser, r);
    }
    public static ArrayList<tarifa> getAllTarifas(usuario currentUsser) {
        return data.crudTarifa.getAllTarifas(currentUsser);
    }

    public static boolean add(usuario currentUsser, tarifa nuevaTarifa, tarifa viejaTarifa) {
        if (viejaTarifa == null) {
            return data.crudTarifa.add(currentUsser, nuevaTarifa);
        } else {
            boolean result = data.crudTarifa.update(currentUsser, nuevaTarifa, viejaTarifa);
            if (result) {
                return data.crudTarifa.add(currentUsser, nuevaTarifa);
            } else {
                return false;
            }

        }
    }
}

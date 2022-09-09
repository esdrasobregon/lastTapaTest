/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.formMantenimiento;

import entity.Unidades;
import entity.ramal;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author esdra
 */
public class manejotablaUnidades {

    public static void addRowToTheTableUnidades(JTable table, Unidades un) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{
            un.getIdbus(),
            un.getFecha_ingreso(),
            un.getMarca(),
            un.getModelo(),
            un.getPlaca(),
            un.getActivo(),
            un.getTipo()
        });
    }
    public static void addRowToTheTableRamales(JTable table, ramal un) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{
            
            un.getNombre(),
            un.getEstado()
        });
    }
}

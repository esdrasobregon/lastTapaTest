/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.formMantenimiento;

import entity.Unidades;
import entity.ramal;
import entity.usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author esdra
 */
public class manejadorLlistaUnidades {

    public ArrayList<Unidades> lista;

    public manejadorLlistaUnidades() {
    }

    public ArrayList<Unidades> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Unidades> lista) {
        this.lista = lista;
    }

    public void getAllUnidades(usuario currentUsser, JTable jtable1, JPanel pnlloading) {

        Thread newThread = new Thread(() -> {
            try {
                this.lista = dynamic.unidadesDy.getAllUnidades(currentUsser);
                this.lista
                        .forEach(e -> {
                            manejotablaUnidades.addRowToTheTableUnidades(jtable1, e);
                        });
                pnlloading.setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        newThread.start();
    }
    public static void loadTableRamales(usuario currentUsser, JTable jtable1, JPanel pnlloading, ArrayList<ramal> lista) {

        Thread newThread = new Thread(() -> {
            try {
                
                lista
                        .forEach(e -> {
                            manejotablaUnidades.addRowToTheTableRamales(jtable1, e);
                        });
                pnlloading.setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        newThread.start();
    }

}

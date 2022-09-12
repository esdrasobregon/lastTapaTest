/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dynamic.ramalDy;
import dynamic.tarifaDy;
import entity.ramal;
import entity.tarifa;
import entity.usuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import view.formMantenimiento.manejadorLlistaUnidades;

/**
 *
 * @author esdra
 */
public class mantenimientoTarifas extends javax.swing.JFrame {

    private usuario currentUsser;
    private ArrayList<ramal> listaRamales;
    private ArrayList<ramal> ramalesParaAgregarTarifa;
    private ArrayList<tarifa> listaTarifas;
    private ArrayList<tarifa> allTarifas;
    /**
     * Creates new form mantenimientoTarifas
     */
    manejadorLlistaUnidades manejoListaUn;

    public mantenimientoTarifas() {
        initComponents();
    }

    mantenimientoTarifas(usuario currentUsser) {
        initComponents();
        this.currentUsser = currentUsser;
        this.setTitle("Bienvenido: " + this.currentUsser.getMsqlUsser());
        initVariables();

    }

    private void initVariables() {
        this.ramalesParaAgregarTarifa = new ArrayList<>();
        this.manejoListaUn = new manejadorLlistaUnidades();
        this.listaRamales = ramalDy.getAllRamales(currentUsser);
        this.allTarifas = tarifaDy.getAllTarifas(currentUsser);
        this.lbTotalTarifas.setText("TOTAL DE TARIFAS: " + this.allTarifas.size());
        LoopRamalesToAddRow();
        //testTbRamales();
        getCurrentTarifas();
        addListenerToTheTableRamales();
        this.pnlloading.setVisible(false);
        justNumbers(txtValor, 5);
    }

    private void showHistorial() {
        //System.out.println("view.mantenimientoTarifas.showHistorial()");
        int list[] = tbRamales.getSelectedRows();
        clearTbHistorial();
        if (list.length > 1) {
            loadTbHitorial(list);
        } else {
            loadTbHitorial(list);
        }
    }

    private void loadTbHitorial(int list[]) {
        for (int i = 0; i < list.length; i++) {

            ramal r = this.listaRamales.get(list[i]);
            addRowToTheTableHistorialNombreRamal(r.getNombre());
            this.allTarifas.forEach(e -> {

                if (e.getRamal_idramal() == r.getIdramal()) {
                    addRowToHistorial(e);
                }

            });
            addEmptyRowToTheTableHistorial();
        }
    }

    private void getCurrentTarifas() {
        this.listaTarifas = new ArrayList<>();
        this.listaRamales.forEach(e -> {
            tarifa t = tarifaDy.getMaxTarifa(currentUsser, e);
            if (t != null) {
                System.out.println("tarifa ramal: " + t.getIdtarifa());
                this.listaTarifas.add(t);
            } else {
                System.out.println("nulo");
            }
        });
    }

    private void LoopRamalesToAddRow() {
        this.listaRamales.forEach(e -> {
            addRowToTheTableRamales(e);
        });
    }

    private void addRowToHistorial(tarifa e) {

        addRowToTheTableHistorial(e);
    }

    private void addListenerToTheTableRamales() {
        this.tbRamales.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                getTableSelectedIndex();
            }
        });
        this.tbRamales.getSelectionModel().addListSelectionListener((e) -> showHistorial());
    }

    private void getTableSelectedIndex() {

        int rowIndex = tbRamales.getSelectedRow();
        int colIndex = tbRamales.getSelectedColumn();
        if (rowIndex < 0) {
            rowIndex = 0;
        }
        System.out.println("fila: " + rowIndex + " columna: " + colIndex);
        String value = this.tbRamales.getModel().getValueAt(rowIndex, 0).toString();

        System.out.println("nombre: " + value);
        boolean existe = existeRamalEnListaPara(value);
        if (existe) {
            int index = getIndexRamalEnListaParaAgregarTa(value);
            if (index > -1) {
                System.out.println("se remueve " + value);
                removeFromListaToAddTarifa(index);
                this.lbSelectRamales.setText("Ramales seleccionados: " + this.ramalesParaAgregarTarifa.size());
            }

        } else {
            ramal r = getRamalDeLaLista(value);
            this.ramalesParaAgregarTarifa.add(r);
            System.out.println("se agrega " + value);
            this.lbSelectRamales.setText("Ramales seleccionados: " + this.ramalesParaAgregarTarifa.size());
        }

    }

    private void unSelectTable() {
        DefaultTableModel model = (DefaultTableModel) this.tbRamales.getModel();
        for (int i = 0; i < this.tbRamales.getRowCount(); i++) {
            model.setValueAt(false, i, 1);
        }
    }

    private void addCheckBox(int column) {
        TableColumn tc = this.tbRamales.getColumnModel().getColumn(column);
        tc.setCellEditor(this.tbRamales.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(this.tbRamales.getDefaultRenderer(Boolean.class));
    }

    private void addRowToTheTableRamales(ramal un) {

        DefaultTableModel model = (DefaultTableModel) this.tbRamales.getModel();
        model.addRow(new Object[]{
            un.getNombre()});
    }

    private void addRowToTheTableHistorial(tarifa un) {

        DefaultTableModel model = (DefaultTableModel) this.tbHistorial.getModel();
        model.addRow(new Object[]{
            un.getFechaInicio(),
            un.getFechaFin(),
            un.getValor()
        });
    }

    private void addRowToTheTableHistorialNombreRamal(String nombreRamal) {

        DefaultTableModel model = (DefaultTableModel) this.tbHistorial.getModel();

        model.addRow(new Object[]{
            nombreRamal
        });
    }

    private void addEmptyRowToTheTableHistorial() {

        DefaultTableModel model = (DefaultTableModel) this.tbHistorial.getModel();
        model.addRow(new Object[]{
            ""
        });
    }

    private ramal getRamalDeLaLista(String nombreRamal) {
        ramal r = null;
        int count = 0;
        boolean found = false;
        while (!found && count < this.listaRamales.size()) {
            if (this.listaRamales.get(count).getNombre() == nombreRamal) {
                found = true;
                r = this.listaRamales.get(count);
            }
            count++;
        }
        return r;
    }

    private void removeFromListaToAddTarifa(int index) {
        if (index >= 0) {
            this.ramalesParaAgregarTarifa.remove(index);
        }
    }

    private boolean existeRamalEnListaPara(String nombreRamal) {
        int count = 0;
        boolean found = false;
        while (!found && count < this.ramalesParaAgregarTarifa.size()) {
            if (this.ramalesParaAgregarTarifa.get(count).getNombre() == nombreRamal) {
                found = true;
            }
            count++;
        }
        return found;
    }

    private int getIndexRamalEnListaParaAgregarTa(String nombreRamal) {
        int count = 0;
        int index = -1;
        boolean found = false;
        while (!found && count < this.ramalesParaAgregarTarifa.size()) {
            if (this.ramalesParaAgregarTarifa.get(count).getNombre() == nombreRamal) {
                found = true;
                index = count;
            }
            count++;
        }
        return index;
    }

    private boolean isSelected(int row, int column) {
        return this.tbRamales.getValueAt(row, column) != null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        center = new javax.swing.JPanel();
        centerTable = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lbRanalSeleccionado = new javax.swing.JLabel();
        lbTotalTarifas = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbHistorial = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        pnlloading = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        top = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtValor = new javax.swing.JTextField();
        dtInicio = new com.toedter.calendar.JDateChooser();
        dtFin = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        botton = new javax.swing.JPanel();
        rigth = new javax.swing.JPanel();
        left = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbRamales = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        lbSelectRamales = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        center.setBackground(new java.awt.Color(255, 255, 255));
        center.setLayout(new java.awt.BorderLayout());

        centerTable.setBackground(new java.awt.Color(255, 255, 255));
        centerTable.setBorder(javax.swing.BorderFactory.createTitledBorder("HISTORIAL TARIFAS"));
        centerTable.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel10.setPreferredSize(new java.awt.Dimension(600, 50));

        lbRanalSeleccionado.setText("RAMAL: ");

        lbTotalTarifas.setText("TOTAL DE TARIFAS");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lbRanalSeleccionado)
                .addGap(213, 213, 213)
                .addComponent(lbTotalTarifas)
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRanalSeleccionado)
                    .addComponent(lbTotalTarifas))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        centerTable.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(600, 300));

        tbHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA INICIO", "FECHA FIN", "VALOR"
            }
        ));
        jScrollPane3.setViewportView(tbHistorial);

        centerTable.add(jScrollPane3, java.awt.BorderLayout.PAGE_END);

        center.add(centerTable, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(431, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        center.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pnlloading.setBackground(new java.awt.Color(255, 255, 255));
        pnlloading.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-spinner.gif"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        pnlloading.add(jButton1);

        center.add(pnlloading, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(20, 143));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        center.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(20, 143));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        center.add(jPanel5, java.awt.BorderLayout.LINE_START);

        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        top.setBackground(new java.awt.Color(255, 255, 255));
        top.setBorder(javax.swing.BorderFactory.createTitledBorder("FORMULARIO TARIFAS"));
        top.setPreferredSize(new java.awt.Dimension(661, 100));
        top.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 4, 10, 5));

        txtValor.setBorder(javax.swing.BorderFactory.createTitledBorder("VALOR"));
        jPanel1.add(txtValor);

        dtInicio.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA INICIO"));
        jPanel1.add(dtInicio);

        dtFin.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA FINAL"));
        jPanel1.add(dtFin);

        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregar);

        top.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(651, 5));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        top.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(651, 5));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        top.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(10, 100));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        top.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(10, 100));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        top.add(jPanel8, java.awt.BorderLayout.LINE_START);

        getContentPane().add(top, java.awt.BorderLayout.PAGE_START);

        botton.setBackground(new java.awt.Color(255, 255, 255));
        botton.setPreferredSize(new java.awt.Dimension(400, 50));

        javax.swing.GroupLayout bottonLayout = new javax.swing.GroupLayout(botton);
        botton.setLayout(bottonLayout);
        bottonLayout.setHorizontalGroup(
            bottonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 845, Short.MAX_VALUE)
        );
        bottonLayout.setVerticalGroup(
            bottonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(botton, java.awt.BorderLayout.PAGE_END);

        rigth.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout rigthLayout = new javax.swing.GroupLayout(rigth);
        rigth.setLayout(rigthLayout);
        rigthLayout.setHorizontalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        rigthLayout.setVerticalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        getContentPane().add(rigth, java.awt.BorderLayout.LINE_END);

        left.setBackground(new java.awt.Color(255, 255, 255));
        left.setPreferredSize(new java.awt.Dimension(200, 439));
        left.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA RAMALES"));

        tbRamales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ramal", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbRamales.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbRamales);
        if (tbRamales.getColumnModel().getColumnCount() > 0) {
            tbRamales.getColumnModel().getColumn(0).setResizable(false);
            tbRamales.getColumnModel().getColumn(1).setResizable(false);
            tbRamales.getColumnModel().getColumn(1).setPreferredWidth(30);
        }

        left.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(200, 50));

        lbSelectRamales.setText("Rmales seleccionados: 0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbSelectRamales)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSelectRamales)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        left.add(jPanel9, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(left, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearTbRamales() {
        DefaultTableModel dtm = (DefaultTableModel) this.tbRamales.getModel();
        dtm.setRowCount(0);
    }

    private void clearTbHistorial() {
        DefaultTableModel dtm = (DefaultTableModel) this.tbHistorial.getModel();
        dtm.setRowCount(0);
    }

    private void testTbRamales() {
        DefaultTableModel dtm = (DefaultTableModel) this.tbRamales.getModel();
        dtm.setRowCount(this.listaRamales.size());
        this.listaRamales.forEach(e -> {
            JCheckBox ch = new JCheckBox(e.getNombre());

        });
    }

    private void loadRamalesWithNewTarifa() {
        for (int i = 0; i < this.tbRamales.getRowCount(); i++) {
            if (isSelected(i, 1)) {
                JOptionPane.showMessageDialog(null, "selected at: " + i);

            }
        }
        clearTbRamales();
        LoopRamalesToAddRow();
    }

    private void getCellValue(int row) {
        String value = this.tbRamales.getModel().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(null, value);
    }

    private void justNumbers(JTextField txt, int maxLength) {
        txt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                }
                if (txt.getText().length() >= maxLength) // limit to 3 characters
                {
                    e.consume();
                }
            }
        });

    }
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if (formTarifasOK()) {
            if (this.ramalesParaAgregarTarifa.size() > 0) {
                this.ramalesParaAgregarTarifa.forEach(e -> {
                    tarifa viejaTarifa = getTarifaActual(e);
                    tarifa nuevaTarifa = getTarifaFromTheForm();
                    nuevaTarifa.setRamal_idramal(e.getIdramal());
                    addTarifa(nuevaTarifa, viejaTarifa);

                });
                // clearTbRamales();
                unSelectTable();
                this.ramalesParaAgregarTarifa = new ArrayList<>();
                this.lbSelectRamales.setText("Ramales seleccionados: " + this.ramalesParaAgregarTarifa.size());

                getCurrentTarifas();
            } else {
                JOptionPane.showMessageDialog(null, "No hay ramales seleccionados ");
            }
        } else {
            JOptionPane.showMessageDialog(null, "informacion incorrecta");
        }

    }//GEN-LAST:event_btnAgregarActionPerformed
    private boolean formTarifasOK() {
        boolean ok = false;
        if (this.txtValor.getText() != "" && this.txtValor.getText().length() > 0 && this.dtInicio.getDate() != null) {
            ok = true;
        }
        return ok;
    }

    private void addTarifa(tarifa nuevaTarifa, tarifa viejaTarifa) {;
        int res = -1;
        if (viejaTarifa != null) {
            res = compareIniFinDates(viejaTarifa.getFechaInicio(), nuevaTarifa.getFechaInicio());
        }
        if (res < 0) {
            boolean result = dynamic.tarifaDy.add(currentUsser, nuevaTarifa, viejaTarifa);
            if (result) {

                JOptionPane.showMessageDialog(null, "hecho");

            }

        } else if (res == 0) {
            JOptionPane.showMessageDialog(null, "fechas inicio y fin iguales");
        } else {
            JOptionPane.showMessageDialog(null, "inicio nenor que fecha anterior");
        }

    }

    private void compareDates(Date ini, Date fin) {
        if (ini.before(fin)) {
            JOptionPane.showMessageDialog(null, ini + " es menor a " + fin);
        } else if (ini.after(fin)) {
            JOptionPane.showMessageDialog(null, ini + " es mayor a " + fin);
        } else {
            JOptionPane.showMessageDialog(null, ini + " es igual a " + fin);
        }
    }

    private int compareIniFinDates(Date ini, Date fin) {
        int result = 0;
        if (ini.before(fin)) {
            result = -1;
        } else if (ini.after(fin)) {
            result = 1;
        }
        return result;
    }

    private tarifa getTarifaFromTheForm() {

        tarifa t = new tarifa();
        t.setValor(Integer.parseInt(this.txtValor.getText()));
        t.setFechaInicio(getFechaInicio());
        return t;

    }

    private Date getFechaInicio() {
        Date d = new Date(this.dtInicio.getDate().getYear(), this.dtInicio.getDate().getMonth(), this.dtInicio.getDate().getDate());
        return d;
    }

    private tarifa getTarifaActual(ramal r) {
        tarifa t = null;
        int count = 0;
        boolean found = false;
        while (!found & count < this.listaTarifas.size()) {
            if (r.getIdramal() == this.listaTarifas.get(count).getRamal_idramal()) {
                found = true;
                t = this.listaTarifas.get(count);
            }
            count++;
        }
        return t;
    }

    private boolean existeTarifaActual(ramal r) {
        int count = 0;
        boolean found = false;
        while (!found & count < this.listaTarifas.size()) {
            if (r.getIdramal() == this.listaTarifas.get(count).getRamal_idramal()) {
                found = true;

            }
            count++;
        }
        return found;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mantenimientoTarifas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mantenimientoTarifas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mantenimientoTarifas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mantenimientoTarifas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mantenimientoTarifas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botton;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JPanel center;
    private javax.swing.JPanel centerTable;
    private com.toedter.calendar.JDateChooser dtFin;
    private com.toedter.calendar.JDateChooser dtInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbRanalSeleccionado;
    private javax.swing.JLabel lbSelectRamales;
    private javax.swing.JLabel lbTotalTarifas;
    private javax.swing.JPanel left;
    private javax.swing.JPanel pnlloading;
    private javax.swing.JPanel rigth;
    private javax.swing.JTable tbHistorial;
    private javax.swing.JTable tbRamales;
    private javax.swing.JPanel top;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}

package com.accesodatos.xestion;

import java.awt.Dimension;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hak
 */
public class StoreManageItemsDialog extends javax.swing.JDialog {

    DefaultListModel listModel;

    Connection con = null;
    String db = "xestionAppDB.db";

    int nItems = 0;

    int idSelectedStore = 0;
    /**
     * Creates new form CustomersDialog
     *
     * @param parent
     */
    public StoreManageItemsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
//        this.setLocationRelativeTo(parent);
//        listModel = new DefaultListModel();
//        listStores.setModel(listModel);
//
//        reloadList();
    }

    public StoreManageItemsDialog(java.awt.Frame parent, boolean modal, String nameSelectedStore, int idSelectedStore) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        listModel = new DefaultListModel();
        listItemsStore.setModel(listModel);

        lbSelectedStore.setText(nameSelectedStore);
        this.idSelectedStore = idSelectedStore;
        reloadList();
        if(listModel.isEmpty()){
            lbError.setText("No hay ningún producto");
        }
    }

    public void reloadList() {
        removeElements();

        con = DB.connectDatabase(db);

        addElements(DB.getItemsStore(con, idSelectedStore));

        DB.desconnetDatabase(con);
    }

    private void addElements(ArrayList<String> items) {
        nItems = items.size();
        for (String item : items) {
            System.out.println(item);
            listModel.addElement(item);
        }
    }

    private void removeElements() {
        int listLength = nItems;
        System.out.println("tamaño: " + listLength);
        if (listLength != 0) {
            for (int i = listLength - 1; i >= 0; i--) {
                listModel.remove(i);
            }
        }
    }

    private void removeSelectedElement() {
        String selectedItem = listItemsStore.getSelectedValue();
        if (selectedItem == null) {
            lbError.setText("Selecciona el producto a eliminar.");
        } else {
            String[] dataSplit = selectedItem.split(" - ");
            System.out.println("Vamos a eliminar el producto " + selectedItem);

            int respuesta = JOptionPane.showConfirmDialog(null, "Quiere eliminar el producto \""
                    + dataSplit[1] + "\" de esta tienda?");
            if (respuesta == 0) {

                int idSelectedItem = Integer.valueOf(dataSplit[0]);

                con = DB.connectDatabase(db);
                DB.deleteItemStore(con, idSelectedStore, idSelectedItem);
                DB.desconnetDatabase(con);
            }
        }
    }

    private void updateStock() {
        String selectedItem = listItemsStore.getSelectedValue();
        if (selectedItem == null) {
            lbError.setText("Selecciona un producto.");
        } else {
            String[] dataSplit = selectedItem.split(" - ");

            String nameSelectedItem = dataSplit[1];
            int idSelectedItem = Integer.valueOf(dataSplit[0]);

            StoreStockUpdateDialog ssud = new StoreStockUpdateDialog(null, true,idSelectedStore,idSelectedItem, nameSelectedItem);
            ssud.setVisible(true);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbSelectedStore = new javax.swing.JLabel();
        btnAddItem = new javax.swing.JButton();
        btnDeleteItem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listItemsStore = new javax.swing.JList<>();
        lbError = new javax.swing.JLabel();
        btnUpdateStock = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lbSelectedStore.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        lbSelectedStore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSelectedStore.setText("Tienda seleccionada");

        btnAddItem.setText("Añadir producto");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnDeleteItem.setText("Eliminar producto");
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(listItemsStore);

        lbError.setForeground(new java.awt.Color(254, 95, 95));
        lbError.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        btnUpdateStock.setText("Actualizar stock");
        btnUpdateStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbSelectedStore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUpdateStock, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteItem, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                    .addComponent(lbError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSelectedStore, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateStock, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:
        lbError.setText("");
        StoreItemCreateDialog sicd = new StoreItemCreateDialog(null, true, idSelectedStore);
        sicd.setVisible(true);
        reloadList();
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        // TODO add your handling code here:
        lbError.setText("");
        removeSelectedElement();
        reloadList();
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void btnUpdateStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStockActionPerformed
        // TODO add your handling code here:
        lbError.setText("");
        updateStock();
        reloadList();
    }//GEN-LAST:event_btnUpdateStockActionPerformed

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
            java.util.logging.Logger.getLogger(StoreManageItemsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StoreManageItemsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StoreManageItemsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StoreManageItemsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StoreManageItemsDialog dialog = new StoreManageItemsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnUpdateStock;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbError;
    private javax.swing.JLabel lbSelectedStore;
    private javax.swing.JList<String> listItemsStore;
    // End of variables declaration//GEN-END:variables
}

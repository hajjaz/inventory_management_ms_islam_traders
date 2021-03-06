/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Cor2Tect
 */
public class Add_CardPayment extends javax.swing.JFrame {

    public DBGateway dbg;
    Connection connection = null;
    double TP;

    /**
     * Creates new form Search_Bill
     */
    public Add_CardPayment() {
        this.dbg = new DBGateway();

        initComponents();
        connection = dbg.connectionTest();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database Connection Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        TP = 0.0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        tvReceiptNo = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnAddCardBill = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Card Paid Bill By Receipt No");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Receipt#");

        tvReceiptNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jTable1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Qty", "Name", "UnitPrice", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 0));
        jLabel3.setText("Enter Receipt No & Search Bill then Add Card Paid Bill");

        btnAddCardBill.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddCardBill.setText("Add Card Bill");
        btnAddCardBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCardBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tvReceiptNo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addGap(86, 86, 86)
                        .addComponent(btnAddCardBill, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tvReceiptNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddCardBill, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        TP = 0.0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            for (int j = 0; j < jTable1.getColumnCount(); j++) {
                jTable1.setValueAt("", i, j);
            }
        }

        int RowNo = 0;
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy ");
        String date = formatter.format(time);
        String from = "'" + date + "'";

        String billNo = "'" + tvReceiptNo.getText() + "'";

        int quantity = 0;
        double unitPrice = 0.0, sellsPrice = 0.0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_transaction where bill_no = " + billNo + " and to_date(tran_date) between to_date("+from+") "
                    + "and " + from + "");
            // print the results
            while (rs.next()) {
                quantity = Integer.parseInt(rs.getString(3));
                jTable1.setValueAt(quantity, RowNo, 0);
                jTable1.setValueAt(rs.getString(2), RowNo, 1);
                sellsPrice = Double.parseDouble(rs.getString(4));
                jTable1.setValueAt(sellsPrice, RowNo, 3);

                unitPrice = sellsPrice / quantity;
                jTable1.setValueAt(unitPrice, RowNo, 2);
                RowNo++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        double p = 0.0, _vat = 0.0, _discount = 0.0;
        for (int j = 0; j < RowNo; j++) {
            String s = String.valueOf(jTable1.getValueAt(j, 3));
            //JOptionPane.showMessageDialog(rootPane, s);
            if (s.equals("null")) {
                p = 0.0;
            } else {
                p = Double.parseDouble(s);
            }
            //System.out.println(p);
            TP += p;
        }
        jTable1.setValueAt("  Total  =", RowNo, 2);
        jTable1.setValueAt(TP, RowNo, 3);
        RowNo++;

        double vatTk = 0.0;
        java.sql.Statement stm2 = null;
        ResultSet rs2 = null;
        try {
            stm2 = connection.createStatement();
            rs2 = stm2.executeQuery("SELECT * FROM inv_vat where bill_no = " + billNo + " and to_date(vat_date) between to_date("+from+")" 
                    + "and " + from + "");
            // print the results
            while (rs2.next()) {
                vatTk = Double.parseDouble(rs2.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs2);
            DatabaseUtils.close(stm2);
        }

        double discountTk = 0.0;
        java.sql.Statement stm3 = null;
        ResultSet rs3 = null;
        try {
            stm3 = connection.createStatement();
            rs3 = stm3.executeQuery("SELECT * FROM inv_discount where bill_no = " + billNo + " and to_date(disc_date) between to_date("+from+") "
                    + "and " + from + "");
            // print the results
            while (rs3.next()) {
                discountTk = Double.parseDouble(rs3.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs2);
            DatabaseUtils.close(stm2);
        }

        if (vatTk > 0) {
            jTable1.setValueAt("    Vat    =", RowNo, 2);
            jTable1.setValueAt(vatTk, RowNo, 3);
            RowNo++;
            TP = TP + vatTk;
        }

        if (discountTk > 0) {
            jTable1.setValueAt("Less(-)=", RowNo, 2);
            jTable1.setValueAt(discountTk, RowNo, 3);
            RowNo++;
            TP = TP - discountTk;
        }

        jTable1.setValueAt("  Total  =", RowNo, 2);
        jTable1.setValueAt(TP, RowNo, 3);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error In Closing DBConnection. Please Ignore it & Click OK.");
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnAddCardBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCardBillActionPerformed
        // TODO add your handling code here:
        int flag = 0;
        if (TP == 0) {
            JOptionPane.showMessageDialog(this, "Search Bill First Then Add To Card Bill.");
        } else {
            Date time = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy ");
            String date = formatter.format(time);
            String from = "'" + date + "'";
            Calendar calendar = Calendar.getInstance();
            java.sql.Date sqlDateObject = new java.sql.Date(calendar.getTime().getTime());
            java.sql.Statement stm6 = null;
            ResultSet rs6 = null;
            try {
                stm6 = connection.createStatement();
                rs6 = stm6.executeQuery("SELECT serial from bill_card where "
                        + " bill_date between " + from + " " + "and " + from + "");
                // print the results
                while (rs6.next()) {
                    if (tvReceiptNo.getText().equals(rs6.getString(1))) {
                        flag = 1;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error4",1);
            } finally {
                DatabaseUtils.close(rs6);
                DatabaseUtils.close(stm6);
            }

            if (flag == 1) {
                JOptionPane.showMessageDialog(this, "This Bill Is Already In Card Bill.");
            } else {

                JTextField amountField = new JTextField(7);
                JTextField nameField = new JTextField(15);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Amount:"));
                myPanel.add(amountField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                //myPanel.add(new JLabel("Name:"));
                //myPanel.add(nameField);

                amountField.setText(String.valueOf(TP));
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Enter The Person's Name & Amount(if Required)", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    String amount = amountField.getText(), name = nameField.getText();
                    //inserting data into transaction table
                    PreparedStatement statement = null;
                    try {
                        statement = connection.prepareStatement("INSERT INTO bill_card (amount, serial, bill_date)"
                                + "VALUES (?, ?, ?)");
                        statement.setString(1, amount);
                        statement.setString(2, tvReceiptNo.getText());
                        statement.setDate(3, sqlDateObject);

                        statement.execute();
                        JOptionPane.showMessageDialog(this, "Successfully Added To Card Bill.");
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        System.out.println("Error while inserting data!");
                        JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
                    } finally {
                        DatabaseUtils.close(statement);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAddCardBillActionPerformed

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
            java.util.logging.Logger.getLogger(Add_CardPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_CardPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_CardPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_CardPayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add_CardPayment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCardBill;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tvReceiptNo;
    // End of variables declaration//GEN-END:variables
}

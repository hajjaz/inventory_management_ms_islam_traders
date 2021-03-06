/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurantpos;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Cor2Tect
 */
public class Search_Bill extends javax.swing.JFrame {

    public DBGateway dbg;
    Connection connection = null;
    int Height=0;
    int RowNo;
    /**
     * Creates new form Search_Bill
     */
    public Search_Bill() {
        this.dbg = new DBGateway();
        
            initComponents();
            connection = dbg.connectionTest();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        tvReceiptNo = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Bill By Receipt No");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Date    :");

        jDateChooser1.setDateFormatString("dd-MMM-yyyy");

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

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 51));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tvReceiptNo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tvReceiptNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        for (int i = 0; i < jTable1.getRowCount(); i++)
        {
            for(int j = 0; j < jTable1.getColumnCount(); j++) 
            {
                jTable1.setValueAt("", i, j);
            }
        }
        
        RowNo = 0;
        Date time = jDateChooser1.getDate();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy ");
           String date = formatter.format(time);
            String from = "'"+date+"'";
        
            String billNo = "'"+tvReceiptNo.getText()+"'";
        
        int quantity = 0;
        double unitPrice = 0.0, sellsPrice = 0.0;
        java.sql.Statement stm1 = null;
            ResultSet rs = null;
            try {
                stm1 = connection.createStatement();
                rs = stm1.executeQuery("SELECT * FROM inv_transaction where bill_no = "+billNo+" and to_date(tran_date) between to_date("+from+") "
                        + "and to_date("+from+")");
                // print the results
                while (rs.next()) {
                    quantity = Integer.parseInt(rs.getString(3));
                    jTable1.setValueAt(quantity, RowNo, 0);
                    jTable1.setValueAt(rs.getString(2), RowNo, 1);
                    sellsPrice = Double.parseDouble(rs.getString(4));
                    jTable1.setValueAt(sellsPrice, RowNo, 3);
                    
                    unitPrice = sellsPrice/quantity;
                    jTable1.setValueAt(unitPrice, RowNo, 2);
                    RowNo++;
                }
            } catch (Exception e) {
                //System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
                System.out.println("Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_transaction! Error :"+e.toString());
                JOptionPane.showMessageDialog(rootPane, e.toString(),"Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_transaction! Error: ",1);
                
            }finally
                {
                    DatabaseUtils.close(rs);
                    DatabaseUtils.close(stm1);
                }
            
        double TP = 0.0, p = 0.0,_vat = 0.0, _discount = 0.0;
        for(int j=0;j<RowNo;j++){
            String s = String.valueOf(jTable1.getValueAt(j, 3));
            //JOptionPane.showMessageDialog(rootPane, s);
            if(s.equals("null"))
            {
                p = 0.0;
            }
            else
            {
                p = Double.parseDouble(s);
            }
            //System.out.println(p);
            TP += p;
        }  
        jTable1.setValueAt("Total =", RowNo, 2);
        jTable1.setValueAt(TP, RowNo, 3);
        RowNo++;
        
        double vatTk = 0.0;
        java.sql.Statement stm2 = null;
            ResultSet rs2 = null;
            try {
                stm2 = connection.createStatement();
                rs2 = stm2.executeQuery("SELECT * FROM inv_vat where bill_no = "+billNo+" and to_date(vat_date) between to_date("+from+") "
                        + "and to_date("+from+")");
                // print the results
                while (rs2.next()) {
                    vatTk = Double.parseDouble(rs2.getString(1));  
                }
            } catch (Exception e) {
                //System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
                System.out.println("Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_vat! Error :"+e.toString());
                JOptionPane.showMessageDialog(rootPane, e.toString(),"Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_vat! Error: ",1);
                
            }finally
                {
                    DatabaseUtils.close(rs2);
                    DatabaseUtils.close(stm2);
                } 
        
        double discountTk = 0.0;
        java.sql.Statement stm3 = null;
            ResultSet rs3 = null;
            try {
                stm3 = connection.createStatement();
                rs3 = stm3.executeQuery("SELECT * FROM inv_discount where bill_no = "+billNo+" and to_date(disc_date) between to_date("+from+") "
                        + "and to_date("+from+")");
                // print the results
                while (rs3.next()) {
                    discountTk = Double.parseDouble(rs3.getString(1));  
                }
            } catch (Exception e) {
                //System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
                System.out.println("Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_vat! Error :"+e.toString());
                JOptionPane.showMessageDialog(rootPane, e.toString(),"Function: btnSearchActionPerformed(). Message: Error while Selecting data from inv_vat! Error: ",1);
            }finally
                {
                    DatabaseUtils.close(rs2);
                    DatabaseUtils.close(stm2);
                }    
            
        if(vatTk > 0)
        {
            jTable1.setValueAt("Vat   =", RowNo, 2); 
            jTable1.setValueAt(vatTk, RowNo, 3);    
            RowNo++;  
            TP = TP + vatTk;
        }
        
        if(discountTk > 0)
        {
            jTable1.setValueAt("Less  =", RowNo, 2); 
            jTable1.setValueAt(discountTk, RowNo, 3);    
            RowNo++;  
            TP = TP - discountTk;
        }
        
        if(vatTk > 0 || discountTk > 0){
            jTable1.setValueAt("Total =", RowNo, 2);
            jTable1.setValueAt(TP, RowNo, 3);
        }
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
                connection.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error In Closing DBConnection. Please Ignore it & Click OK.");
            }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(InventoryPOS.printPageSizeBig){
            printCard();
        }else{
            printCard_Small();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void printCard() {

        PrinterJob printjob = PrinterJob.getPrinterJob();
        printjob.setJobName("Label");
        
        Printable printable;
        printable = new Printable() {
            
            @Override
            public int print(Graphics pg, PageFormat pf, int pageNum) throws PrinterException {
                 //To change body of generated methods, choose Tools | Templates.
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                
                //Dimension size = jLayeredPane2.getSize();
                //BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                
                //jLayeredPane2.print(bufferedImage.getGraphics());
                
                Graphics2D g = (Graphics2D) pg;
                g.translate(pf.getImageableX(), pf.getImageableY());
                Date date = jDateChooser1.getDate();
         DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy");
        String Time = "Date: "+formatter.format(date);
        System.out.println("RowNo ="+RowNo);
        
        String RNo = "Receipt# "+tvReceiptNo.getText();
        String line = "----------------------------------";
        String S[][] = new String[50][5];
        S[0][0] = "Qty";
        S[0][1] = "Description";
        S[0][2] = "UnitP";
        S[0][3] = "Price";
        String s;
        int size = RowNo;
        for(int i=0;i<=size;i++){
            
            if((s=String.valueOf(jTable1.getValueAt(i, 0)))!=null){
                S[i+1][0] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 1)))!=null){
                S[i+1][1] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 2)))!=null){
                S[i+1][2] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 3)))!=null){
                S[i+1][3] =s;
            }
            System.out.println("S["+i+"] = "+S[i][0]);
        }
        int h=1 ,height=0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString(InventoryPOS.companyName, 35, 15);
        g.setFont(new Font("Courier New", Font.BOLD, 8));
        g.drawString(InventoryPOS.strBillCompanyAddress1, 20, 35);
        g.drawString(InventoryPOS.strBillCompanyAddress2 ,20, 45);
        //g.drawString(InventoryPOS.strVatRegNo+"  "+RNo, 20, 60);
        g.drawString(RNo, 20, 60);
        g.drawString(Time, 20, 70);
        for(int j=0;j<=size+1;j++){
            if(j==0){
                h =1;
                g.drawString(line, 15, 80);
                g.drawString(S[j][0], 20, 90*h);
                g.drawString(S[j][1], 40, 90*h);
                g.drawString(S[j][2], 115, 90*h);
                g.drawString(S[j][3], 150, 90*h);
                g.drawString(line, 15, 100);
                height =100;
            }
            else if(j>=1){
                h=j+1;
            g.drawString(S[j][0], 20, height+10);
            g.drawString(S[j][1], 40, height+10);
            g.drawString(S[j][2], 115, height+10);
            g.drawString(S[j][3], 150, height+10);
            height+=10;
            }
        }
        g.drawString(InventoryPOS.strBillMessage1, 20, height+15);
        g.drawString(InventoryPOS.strContactInfo, 20, height+25);
        g.drawString(InventoryPOS.strElseCode, 20, height+35);
        g.dispose();
        Height += height+45;
//        _printJob.end();
        
        
                
                return Printable.PAGE_EXISTS;
            }
            
        };

        
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 253, Height);
        //paper.setSize(243, 154);

        PageFormat format = new PageFormat();
        format.setPaper(paper);
        //format.setOrientation(PageFormat.LANDSCAPE);

        printjob.setPrintable(printable, format);
//        if (printjob.printDialog() == false)
//                return;

        try {
                printjob.print();
        } catch (PrinterException ex) {
                System.out.println("NO PAGE FOUND." + ex);

        }
    }
    
    private void printCard_Small() {

        PrinterJob printjob = PrinterJob.getPrinterJob();
        printjob.setJobName("Label");
        
        Printable printable;
        printable = new Printable() {
            
            @Override
            public int print(Graphics pg, PageFormat pf, int pageNum) throws PrinterException {
                 //To change body of generated methods, choose Tools | Templates.
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                
                //Dimension size = jLayeredPane2.getSize();
                //BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                
                //jLayeredPane2.print(bufferedImage.getGraphics());
                
                Graphics2D g = (Graphics2D) pg;
                g.translate(pf.getImageableX(), pf.getImageableY());
                Date date = jDateChooser1.getDate();
         DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy");
        String Time = "Date: "+formatter.format(date);
        
        String RNo = "Bill# "+tvReceiptNo.getText(),Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                HD="Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.";
        String line = "----------------------------------";
        String S[][] = new String[50][5];
        S[0][0] = "Qty";
        S[0][1] = "Description";
        S[0][2] = "UnitP";
        S[0][3] = "Price";
        String s;
        int size = RowNo;
        for(int i=0;i<=size;i++){
            
            if((s=String.valueOf(jTable1.getValueAt(i, 0)))!=null){
                S[i+1][0] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 1)))!=null){
                S[i+1][1] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 2)))!=null){
                S[i+1][2] =s;
            }
            if((s=String.valueOf(jTable1.getValueAt(i, 3)))!=null){
                S[i+1][3] =s;
            }
            System.out.println("S["+i+"] = "+S[i][0]);
        }
        int h=1 ,height=0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString(InventoryPOS.companyName, 0, 15);
        g.setFont(new Font("Courier New", Font.BOLD, 7));
        g.drawString(InventoryPOS.strBillCompanyAddress1, 0, 35);
        g.drawString(InventoryPOS.strBillCompanyAddress2, 0, 45);
        g.drawString(InventoryPOS.strVatRegNo+" "+RNo, 0, 60);
        g.drawString(Time, 0, 70);
        for(int j=0;j<=size+1;j++){
            if(j==0){
                h =1;
                g.drawString(line, 0, 80);
                g.drawString(S[j][0], 0, 90*h);
                g.drawString(S[j][1], 15, 90*h);
                g.drawString(S[j][2], 80, 90*h);
                g.drawString(S[j][3], 110, 90*h);
                g.drawString(line, 0, 100);
                height =100;
            }
            else if(j>=1){
                h=j+1;
            g.drawString(S[j][0], 0, height+10);
            g.drawString(S[j][1], 15, height+10);
            g.drawString(S[j][2], 80, height+10);
            g.drawString(S[j][3], 110, height+10);
            height+=10;
            }
        }
        g.drawString(InventoryPOS.strBillMessage1, 0, height+15);
        g.drawString(InventoryPOS.strContactInfo, 0, height+25);
        g.drawString(InventoryPOS.strElseCode, 0, height+35);
        g.dispose();
        Height += height+45;
//        _printJob.end();
        
        
                
                return Printable.PAGE_EXISTS;
            }
            
        };

        
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 253, Height);
        //paper.setSize(243, 154);

        PageFormat format = new PageFormat();
        format.setPaper(paper);
        //format.setOrientation(PageFormat.LANDSCAPE);

        printjob.setPrintable(printable, format);
//        if (printjob.printDialog() == false)
//                return;

        try {
                printjob.print();
        } catch (PrinterException ex) {
                System.out.println("NO PAGE FOUND." + ex);

        }
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
            java.util.logging.Logger.getLogger(Search_Bill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Search_Bill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Search_Bill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Search_Bill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Search_Bill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tvReceiptNo;
    // End of variables declaration//GEN-END:variables
}

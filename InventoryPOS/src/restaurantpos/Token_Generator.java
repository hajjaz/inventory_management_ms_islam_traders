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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class Token_Generator extends javax.swing.JFrame {

    String tokenSerial = "", tokenMessage1 = "", tokenMessage2 = "";
    Date tokenValidFor;
    /**
     * Creates new form Token_Generator
     */
    public Token_Generator() {
        initComponents();
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
        jLabel2 = new javax.swing.JLabel();
        tvMessage1 = new javax.swing.JTextField();
        tvMessage2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tvTokenCount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Token Generator");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Message 1:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Message 2:");

        tvMessage1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tvMessage2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Total Token :");

        tvTokenCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Valid for:");

        jDateChooser1.setDateFormatString("dd-MMM-yyyy");

        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tvMessage1)
                        .addComponent(tvMessage2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
                    .addComponent(tvTokenCount, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
                .addContainerGap(251, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tvMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tvMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tvTokenCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(107, 107, 107)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        DecimalFormat dFormater = new DecimalFormat("000");
        String fromatedString = "";
        for(int i = 1; i<=Integer.parseInt(tvTokenCount.getText().toString()); i++){
            fromatedString = dFormater.format(i);
            printToken(fromatedString, tvMessage1.getText().toString(), tvMessage2.getText().toString(), jDateChooser1.getDate());
        }
        
    }//GEN-LAST:event_btnPrintActionPerformed

    private void printToken(String serialToken, String message1, String message2, Date validity) {

        tokenSerial = serialToken;
        tokenMessage1 = message1;
        tokenMessage2 = message2;
        tokenValidFor = validity;
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
                
         DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy");
        String Time = "Valid for : "+formatter.format(tokenValidFor);
        String CName = "New Shad Restora", CAddress1 = "Plot# 35, Sonargaon Janapath Road", 
        CAddress2 = "Sector# 12, Uttara, Dhaka 1230", CAddress3 ="Phone No: 01778110011 ";
        
        int h=1 ,height=0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
        //Graphics g = _printJob.getGraphics();
        g.setFont(new Font("Courier New", Font.BOLD, 16));
        g.drawString(tokenMessage1, 70, 15);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString(tokenMessage2, 20, 30);
        
        g.setFont(new Font("Courier New", Font.BOLD, 16));
        g.drawString("Token# "+tokenSerial, 35, 60);
        
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString(CName, 35, 90);
        
        g.setFont(new Font("Courier New", Font.BOLD, 8));
        g.drawString(CAddress1, 20, 110);
        g.drawString(CAddress2, 20, 120);
        g.drawString(CAddress3, 20, 130);
        g.drawString(Time, 20, 140);
        g.dispose();
//        _printJob.end();
        
                return Printable.PAGE_EXISTS;
            }
            
        };

        
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 253, 200);
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
            java.util.logging.Logger.getLogger(Token_Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Token_Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Token_Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Token_Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Token_Generator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tvMessage1;
    private javax.swing.JTextField tvMessage2;
    private javax.swing.JTextField tvTokenCount;
    // End of variables declaration//GEN-END:variables
}
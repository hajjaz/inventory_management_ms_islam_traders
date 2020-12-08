/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buy;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.SimpleDoc;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import restaurantpos.DBGateway;
import restaurantpos.DatabaseUtils;
import restaurantpos.ItemDB;

/**
 *
 * @author HajjazPC
 */

public class BuyFrame_StockOut extends javax.swing.JFrame {

    static boolean jobRunning = true;
    
    String discountTK = "";
    private boolean discount = false; 
    int _receiptNo = 0, receiptNo = 0;
    int transaction_id = 0;
    public BuyFrame_StockOut Sframe;
    public Dimension screenSize;
    public DBGateway dbg;;
    Connection connection = null;
	ResultSet RS;
        private int RowNo;
    private boolean cash_paid = false, addVAT = false;
    private double TotalPrice = 0.0;
    
    private final static int button_width   =   95;        // button width
    private final static int button_height  =   60;        // button height
    private final static int horizontalGap  =   5;         // horizontal gap in button
    private final static int verticalGap    =   5;         // verticle gap in button
    private final static int numberOfColumns=   7;          // number of colums in the button panel
    private final static int fontSize       =   11;         // font size of button name
    private final static int fontType       =   Font.BOLD;  // font type
    private final static String fontName    =   "Thoma";    // font name
    private final static Color  fontColor   =   new Color(0, 51, 255);  // font colot
    
    public static String UserName = "";
    Date date;
    
    ArrayList UcatID = new ArrayList();
    private Double vatTK = 0.0;
    private int update_quantity = 0;
    /**
     * Creates new form SellsFrame
     */
    @SuppressWarnings("empty-statement")
    public BuyFrame_StockOut() {
        this.RowNo = 0;
        date = Calendar.getInstance().getTime();
         DateFormat formatter = new SimpleDateFormat(" dd/MMM/yyyy");
        String Time = "Date: "+formatter.format(date);
    
        this.dbg = new DBGateway();
        initComponents();
        
        connection = dbg.connectionTest();
        jLabel6.setText(Time);
        jLabel10.setText(UserName);
        
        Notification();
    }
    
    public void Notification()
    {
        int count = 0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_fooditem where item_quantity < 5");
            // print the results
            
            while (rs.next()) {
                //jTable1.setValueAt(rs.getString(3), count, 0);
                //JOptionPane.showMessageDialog(this, rs.getString(3));
                count++;
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //WriteError.Write("SellsFrame->Notification() "+e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
        {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        if(count>0)
        {
            menuNotification.setForeground(Color.RED);
            menuNotification.setText("Notification "+count);
        }
    }
    
    public int GetTransaction_Id() {
        int id = 0;
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_transaction");
            // print the results
            while (rs.next()) {
                //inv_cat_id++;
                id++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //Utilities.setErrorLog(SellsFrame_Modified.class.getSimpleName()+" GetTransaction_Id()", "Exception", "SELECT * FROM inv_transaction Query Execution Error", e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return id;
    }
    
    //writes data to the printing table
    public void WriteToTable(Double price, String itemName, int cat_id)
    {
        Double unitPrice= price, TP = 0.0, vatTK = 0.0, grossTP = 0.0;
        int qty =1,flag=0;
        String name = itemName;
        if(RowNo==0){
            jTable2.setValueAt(cat_id, RowNo, 0);
            jTable1.setValueAt(RowNo, RowNo, 0);
            jTable1.setValueAt(qty, RowNo, 1);
            jTable1.setValueAt(name, RowNo, 2);
            jTable1.setValueAt(unitPrice, RowNo, 3);
            jTable1.setValueAt(qty*unitPrice, RowNo, 4);
            RowNo +=1;
        }
        else{
            int k=jTable1.getRowCount(),j;
            System.out.println(k);
            for(j=0; j<k;j++ )
            {
                if(name.equals(jTable1.getValueAt(j, 2))){
                    qty = (int) jTable1.getValueAt(j, 1);
                    qty +=1;
                    flag=1;
                    break;
                }
                else
                    continue;
            }
            if(flag==1){
                jTable2.setValueAt(cat_id, j, 0);
                jTable1.setValueAt(j, j, 0);
                jTable1.setValueAt(qty, j, 1);
                jTable1.setValueAt(name, j, 2);
                jTable1.setValueAt(unitPrice, j, 3);
                jTable1.setValueAt(qty*unitPrice, j, 4);
                //RowNo =j+1;
            }
            else{
                jTable2.setValueAt(cat_id, RowNo, 0);
                jTable1.setValueAt(RowNo, RowNo, 0);
                jTable1.setValueAt(qty, RowNo, 1);
                jTable1.setValueAt(name, RowNo, 2);
                jTable1.setValueAt(unitPrice, RowNo, 3);
                jTable1.setValueAt(qty*unitPrice, RowNo, 4);
                RowNo +=1;
            }
        }
        int k= jTable1.getRowCount(), j, n=RowNo;
        for(j=0;j<RowNo;j++){
            String s = String.valueOf(jTable1.getValueAt(j, 4));
            double p = Double.parseDouble(s);
            //System.out.println(p);
            TP += p;
        }    
        jTable1.setValueAt(" ", n, 0);
        jTable1.setValueAt(" ", n, 1);
        jTable1.setValueAt(" ", n, 2);
        jTable1.setValueAt("Total =", n, 3);
        jTable1.setValueAt(TP, n, 4);
        
        /*//for vat 15%
        jTable1.setValueAt(" ", n+1, 0);
        jTable1.setValueAt(" ", n+1, 1);
        jTable1.setValueAt(" ", n+1, 2);
        jTable1.setValueAt("Vat   =", n+1, 3);
        jTable1.setValueAt("15%", n+1, 4);
        
        //gross total
        vatTK = (15 * TP)/100;
        grossTP = TP + vatTK;
        jTable1.setValueAt(" ", n+2, 0);
        jTable1.setValueAt(" ", n+2, 1);
        jTable1.setValueAt(" ", n+2, 2);
        jTable1.setValueAt("Total =", n+2, 3);
        jTable1.setValueAt(grossTP, n+2, 4);*/
        
        
        txtTotalPrice.setText(String.valueOf(TP));
        
        
        if(cash_paid){
            double PA = Double.parseDouble(txtCashPaid.getText());
            double CA = PA - grossTP;
            jTable1.setValueAt("Paid =", RowNo+3, 3);   jTable1.setValueAt(String.valueOf(PA), RowNo+3, 4);
            jTable1.setValueAt("Change =", RowNo+4, 3);   jTable1.setValueAt(String.valueOf(CA), RowNo+4, 4);
            txtChange.setText(String.valueOf(CA));
        }
        //////////////////////////////////////////
    }

    private int getSerial(String d)
    {
        int serial=0;
        String date = "'"+d+"'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_bill_serial where bill_date between "
                    + "to_date("+date+") "+ "and to_date("+date+")");
            // print the results
            while (rs.next()) {
                serial = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
        {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        
        serial++;
        return serial;
    }
    private void setSerial(int serial, java.sql.Date d) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO buy_bill_serial (serial,bill_date)VALUES (?, ?)");
            statement.setString(1, String.valueOf(serial));
            statement.setDate(2, d);

            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error while inserting data!");
            JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
        } finally {
            DatabaseUtils.close(statement);
        }
    }
    
    private Double getQuantity(String name, int cat) {
        Double quantity = 0.0;
        int cat_id = cat;
        String Name = "'" + name + "'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_fooditem where item_name = " + Name + " and cat_id = " + cat_id + "");
            // print the results
            while (rs.next()) {
                quantity = Double.parseDouble(rs.getString(6));

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
            JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return quantity;
    }
    
    private int getCategory_ID(String categoryName)
    {
        int id=0;
        String catName = "'"+categoryName+"'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_category where cat_name = "+catName +"");
            // print the results
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
        {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return id;
    }
    
    private int getCategoryID_fromFoodItem(String itemname)
    {
        int catId=0;
        String itemName = "'"+itemname+"'";
        java.sql.Statement stm1= null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_fooditem where item_name = "+itemName +"");
            // print the results
            while (rs.next()) {
                catId = Integer.parseInt(rs.getString(2));
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
        {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return catId;
    }
    
    private String getCategory_Name(int catid)
    {
        int id= catid;
        String catName = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_category where cat_id = "+id +"");
            // print the results
            while (rs.next()) {
                catName = rs.getString(2);                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
        {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return catName;
    }
    
    
    private void addMainMenue() {

        pnl_button.removeAll();
        repaint();

        Image img, sub;
        ImageIcon icon;
        String imagePath,imag = "/com/images/";

        //ArrayList menue = new ArrayList();
        ArrayList catName = new ArrayList();
        ArrayList catImage = new ArrayList();
        
        //get the category list in ArrayList catName
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_category ");
            // print the results
            while (rs.next()) {
                String n = rs.getString(2);
                String i = rs.getString(3);
                catName.add(n);
                catImage.add(i);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }

        /*for (int size = 0 ; size<ItemDB.mainMenuCodes.length; size++) {
            menue.add(ItemDB.mainMenuCodes[size]);
            itemName.add(ItemDB.mainMenuDesc[size]);
        }*/
        
        JButton[] buttons = new JButton[catName.size()];

        for (int i = 0; i < buttons.length; i++) {
            
            imagePath = catImage.get(i).toString();
            
            if(imagePath!=null){
                img         = Toolkit.getDefaultToolkit().getImage(imagePath);
                sub         = img.getScaledInstance(button_width - 8, button_height - 30, Image.SCALE_FAST);
                icon        = new ImageIcon(sub);
            }
            else
               icon        = new ImageIcon();

            buttons[i]  = new JButton(catName.get(i).toString(), icon);
            buttons[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            buttons[i].setHorizontalTextPosition(AbstractButton.CENTER);

            buttons[i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
            buttons[i].setFont(new java.awt.Font("Tahoma", 1, 13));
            buttons[i].setForeground(new java.awt.Color(0, 51, 255));

            buttons[i].setActionCommand(catName.get(i).toString());
            buttons[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String choice = e.getActionCommand();
                    int cat = getCategory_ID(choice);
                    addSubmenue(choice,cat);
                }
            });
        }

        int b       =   0;
        int vGap    =   verticalGap;
        int hGap    =   horizontalGap;
        int bLength =   buttons.length;
        int bRows   =   bLength/numberOfColumns +1;

        L1:    for (int j = 0; j <bRows; j++) {
                vGap = 10;
                for (int k = 0; k < numberOfColumns; k++) {

                    pnl_button.add(buttons[b], new org.netbeans.lib.awtextra.AbsoluteConstraints(vGap, hGap, button_width, button_height));
                    repaint();
                    vGap +=button_width+verticalGap;
                    b++;
                    if(b>=bLength){
                        break L1;
                    }
                }
                hGap +=button_height+horizontalGap;
        }
        pack();
    }

    private void addSubmenue(String choice, int cat) {
        pnl_button.removeAll();
        repaint();

        Image img, sub;
        ImageIcon icon;
        String imagePath,imag = "/com/images/";

        ArrayList menue = new ArrayList();
        ArrayList itemName = new ArrayList();
        ArrayList itemImage = new ArrayList();

        ArrayList   list  =   ItemDB.getSubMenu(choice);
        
        final int cat_id = cat;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
            try {
                stm1 = connection.createStatement();
                //String name = "'"+choice+"'";
                rs = stm1.executeQuery("SELECT * FROM buy_fooditem where cat_id = "+cat_id +"");
                // print the results
                while (rs.next()) {
                    itemName.add(rs.getString(3));
                    itemImage.add(rs.getString(5));
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }

        JButton[] buttons = new JButton[itemName.size()];

        for (int i = 0; i < buttons.length; i++) {

            imagePath = itemImage.get(i).toString();

            if(imagePath!=null){
                img         = Toolkit.getDefaultToolkit().getImage(imagePath);
                sub         = img.getScaledInstance(button_width - 8, button_height - 30, Image.SCALE_FAST);
                icon        = new ImageIcon(sub);
            }
            else
               icon        = new ImageIcon();



            buttons[i]  = new JButton(itemName.get(i).toString(), icon);
            buttons[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            buttons[i].setHorizontalTextPosition(AbstractButton.CENTER);

            buttons[i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
            buttons[i].setFont(new java.awt.Font("Tahoma", 1, 13));
            buttons[i].setForeground(new java.awt.Color(0, 51, 255));


            buttons[i].setActionCommand(itemName.get(i).toString());
            buttons[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String choice = e.getActionCommand();
                    addItems(choice,cat_id);
                }
            });
        }
        int b       =   0;
        int vGap    =   verticalGap;
        int hGap    =   horizontalGap;
        int bLength =   buttons.length;
        int bRows   =   bLength/numberOfColumns +1;

        L1:    for (int j = 0; j <bRows; j++) {
                vGap = 10;
                for (int k = 0; k < numberOfColumns; k++) {

                    pnl_button.add(buttons[b], new org.netbeans.lib.awtextra.AbsoluteConstraints(vGap, hGap, button_width, button_height));
                    repaint();
                    vGap +=button_width+verticalGap;
                    b++;
                    if(b>=bLength){
                        break L1;
                    }
                }
                hGap +=button_height+horizontalGap;
        }
        pack();
    }

    private void addItems(String choice,int cat_id) {
        
        Double Price = 0.0;
        int quantity = 0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
            try {
                stm1 = connection.createStatement();
                String name = "'"+choice+"'";
                rs = stm1.executeQuery("SELECT * FROM buy_fooditem where item_name = "+name +" and cat_id = "+cat_id +"");
                // print the results
                while (rs.next()) {
                    Price = Double.parseDouble(rs.getString(4));
                    quantity = Integer.parseInt(rs.getString(6));
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }
            if(quantity == 0)
            {
                JOptionPane.showMessageDialog(this, choice+" is Not Available");
            }
            else
            {
                DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
                Price = new Double(df2.format(Price)).doubleValue();
                WriteToTable(Price,choice,cat_id);
                int q = quantity -1;
                DeleteItem_Quantity(choice,cat_id,1.0);
            }
    }
    
    private Double DeleteItem_Quantity(String choice, int cat_id, Double quantity) {
        //JOptionPane.showMessageDialog(this, "from DeleteItem() ->quantity="+quantity);
        Double flag = -1.0;
        Double dbQuan = getQuantity(choice, cat_id);
        Double quan = dbQuan - quantity;
        //JOptionPane.showMessageDialog(this, "from DeleteItem() ->dbQ="+dbQuan+" Q="+quan);
        if (quan < 0) {
            flag = dbQuan;
            JOptionPane.showMessageDialog(this, "Not available quantity. Available = " + dbQuan);
        } else {
            flag = -1.0;
            String itemName = "'" + choice + "'";
            String editQuantity = "'" + quan + "'";
            PreparedStatement statement = null;
            try {

                statement = connection.prepareStatement("UPDATE buy_fooditem set item_quantity =" + editQuantity + " where item_name = " + itemName + " and cat_id = " + cat_id + "");
                statement.execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.toString(), "Error", 1);
                //System.out.println("Error while inserting data!");
            } finally {
                DatabaseUtils.close(statement);
            }
        }
        return flag;
    }
    private void AddItem_Quantity(String itemname, Double quantity, int catid) {

        Double q = 0.0;
        int cat_id = catid;
        q = getQuantity(itemname, cat_id);

        Double quan = quantity + q;
        String itemName = "'" + itemname + "'";
        String editQuantity = "'" + quan + "'";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("UPDATE buy_fooditem set item_quantity =" + editQuantity + " where item_name = " + itemName + " and cat_id = " + cat_id + "");
            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.toString(), "Error", 1);
            //System.out.println("Error while inserting data!");
        } finally {
            DatabaseUtils.close(statement);
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCashPaid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnDeleteItem = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtChange = new javax.swing.JTextField();
        pnl_main = new javax.swing.JPanel();
        btn_exit = new javax.swing.JButton();
        jsc_item = new javax.swing.JScrollPane();
        pnl_button = new javax.swing.JPanel();
        btn_back = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnDiscount = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnIn1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuRefresh = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuDateWise = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        menuNotification = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem3.setText("jMenuItem3");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Buy Frame");
        setMinimumSize(new java.awt.Dimension(1024, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Wide Latin", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 51));
        jLabel1.setText("New Shad Restora");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Plot# 35, Sonargaon Janapoth Road, Sector# 12, Uttara, Dhaka 1230");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jTable1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Serial", "Qty", "Name", "UnitPrice", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("List Of Items In Sale");
        jTable1.setGridColor(new java.awt.Color(204, 255, 255));
        jTable1.setSelectionBackground(new java.awt.Color(153, 204, 255));
        jTable1.setSelectionForeground(new java.awt.Color(204, 0, 51));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setBackground(new java.awt.Color(153, 153, 153));
        txtTotalPrice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotalPrice.setForeground(new java.awt.Color(0, 102, 102));
        txtTotalPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPrice.setText("0.00");
        txtTotalPrice.setToolTipText("Total Price");
        txtTotalPrice.setAlignmentX(1.0F);
        txtTotalPrice.setAlignmentY(1.0F);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Cash Paid:");

        txtCashPaid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCashPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCashPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCashPaidActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Total: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Date");

        btnDeleteItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteItem.setText("Delete Item");
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Change: ");

        txtChange.setEditable(false);
        txtChange.setBackground(new java.awt.Color(0, 0, 0));
        txtChange.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtChange.setForeground(new java.awt.Color(0, 204, 0));
        txtChange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChange.setText("0.00");
        txtChange.setToolTipText("Total Price");
        txtChange.setAlignmentX(1.0F);
        txtChange.setAlignmentY(1.0F);

        pnl_main.setBackground(new java.awt.Color(140, 205, 250));
        pnl_main.setForeground(new java.awt.Color(140, 205, 250));
        pnl_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_exit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_exit.setText("Exit");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });
        pnl_main.add(btn_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 620, 120, 50));

        jsc_item.setBackground(new java.awt.Color(140, 205, 250));
        jsc_item.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsc_item.setAutoscrolls(true);

        pnl_button.setBackground(new java.awt.Color(102, 153, 255));
        pnl_button.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 0, 51))); // NOI18N
        pnl_button.setForeground(new java.awt.Color(140, 205, 250));
        pnl_button.setAutoscrolls(true);
        pnl_button.setEnabled(false);
        pnl_button.setOpaque(false);
        pnl_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jsc_item.setViewportView(pnl_button);

        pnl_main.add(jsc_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 740, 630));

        btn_back.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_back.setText("Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        pnl_main.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 620, 120, 50));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Developed By ElseCode ");
        pnl_main.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, -1, -1));

        btnDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiscount.setText("Discount");
        btnDiscount.setEnabled(false);
        btnDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscountActionPerformed(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("UserName");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "CatId"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("User:");

        btnIn1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnIn1.setText("Stock OUT");
        btnIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIn1ActionPerformed(evt);
            }
        });

        jMenu2.setText("File");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuRefresh.setText("Refresh");
        menuRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRefreshActionPerformed(evt);
            }
        });
        jMenu2.add(menuRefresh);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("  Category");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem4.setText("Add Category");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem5.setText("Update Category");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem5);

        jMenuBar1.add(jMenu5);

        jMenu1.setText("   Item");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem9.setText("Add Item");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem10.setText("Update Item");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem11.setText("Delete Item");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuBar1.add(jMenu1);

        jMenu4.setBackground(new java.awt.Color(153, 153, 255));
        jMenu4.setBorder(null);
        jMenu4.setText("    DashBoard");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuDateWise.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuDateWise.setText("Date Wise");
        menuDateWise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDateWiseActionPerformed(evt);
            }
        });
        jMenu4.add(menuDateWise);

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem6.setText("Item Wise");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem8.setText("Category Wise");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        menuNotification.setText("  Notification");
        menuNotification.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem12.setText("Show All");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        menuNotification.add(jMenuItem12);

        jMenuBar1.add(menuNotification);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(pnl_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCashPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDiscount)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteItem)
                                .addGap(47, 47, 47)
                                .addComponent(btnIn1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(115, 115, 115)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(551, 551, 551)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_main, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtCashPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnIn1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 136, Short.MAX_VALUE)
                        .addComponent(lblUser)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        // TODO add your handling code here:
        Double vatTK = 0.0, grossTP = 0.0, quantity = 0.0;
        
        int Selectedrow ,j, cat = 0;
       String data[][] = new String[20][20];
       String data1[][] = new String[20][20];
       Selectedrow = jTable1.getSelectedRow();
       String name;
       
       if(RowNo == 0 || Selectedrow == -1 || (RowNo-1) < Selectedrow)
       {
           JOptionPane.showMessageDialog(this, "No data Selected");
       }
       
       else
       {
           cat = Integer.parseInt(jTable2.getValueAt(Selectedrow, 0).toString());
           quantity = Double.parseDouble(jTable1.getValueAt(Selectedrow, 1).toString());
           name = String.valueOf(jTable1.getValueAt(Selectedrow, 2));
            for(int i=0;i<=RowNo;i++ ){
                     data[i][0]=String.valueOf(jTable1.getValueAt(i, 0));
                     data[i][1]=String.valueOf(jTable1.getValueAt(i, 1));
                     data[i][2]=String.valueOf(jTable1.getValueAt(i, 2));
                     data[i][3]=String.valueOf(jTable1.getValueAt(i, 3));
                     data[i][4]=String.valueOf(jTable1.getValueAt(i, 4));
             }

             for(int i=0;i<RowNo;i++){
                 if(i>=Selectedrow){
                     data1[i][0]=data[i+1][0];
                     data1[i][1]=data[i+1][1];
                     data1[i][2]=data[i+1][2];
                     data1[i][3]=data[i+1][3];
                     data1[i][4]=data[i+1][4];
                 }
                 else{
                     data1[i][0]=data[i][0];  data1[i][1]=data[i][1];  data1[i][2]=data[i][2];
                     data1[i][3]=data[i][3];  data1[i][4]=data[i][4];
                 }
             }

             for(int k=0;k<jTable1.getRowCount();k++){
                 jTable1.setValueAt("", k, 0);   jTable1.setValueAt("", k, 1);
                 jTable1.setValueAt("", k, 2);   jTable1.setValueAt("", k, 3);
                 jTable1.setValueAt("", k, 4);
             }
             double p,Tprice=0.0;
             for(j=0;j<RowNo-1;j++){
                 jTable1.setValueAt(j, j, 0);
                 jTable1.setValueAt( Integer.parseInt(data1[j][1]), j, 1);
                 jTable1.setValueAt(data1[j][2], j, 2);
                 jTable1.setValueAt(data1[j][3], j, 3);
                 jTable1.setValueAt(data1[j][4], j, 4);
                 p = Double.parseDouble(data1[j][4]);
                 Tprice +=p;
                 //System.out.println(data1[j][0]+","+data1[j][1]+","+data1[j][2]+","+data1[j][3]+","+data1[j][4]+"\n");
             }
             RowNo -=1;
             jTable1.setValueAt(" ", RowNo, 0);
             jTable1.setValueAt(" ", RowNo, 1);
             jTable1.setValueAt(" ", RowNo, 2);
             jTable1.setValueAt("Total =", RowNo, 3);
             jTable1.setValueAt(String.valueOf(Tprice), RowNo, 4);
             
            AddItem_Quantity(name,quantity,cat); 
            /*//for vat 15%
            jTable1.setValueAt(" ", RowNo+1, 0);
            jTable1.setValueAt(" ", RowNo+1, 1);
            jTable1.setValueAt(" ", RowNo+1, 2);
            jTable1.setValueAt("Vat   =", RowNo+1, 3);
            jTable1.setValueAt("15%", RowNo+1, 4);

            //gross total
            vatTK = (15 * Tprice)/100;
            grossTP = Tprice + vatTK;
            jTable1.setValueAt(" ", RowNo+2, 0);
            jTable1.setValueAt(" ", RowNo+2, 1);
            jTable1.setValueAt(" ", RowNo+2, 2);
            jTable1.setValueAt("Total =", RowNo+2, 3);
            jTable1.setValueAt(grossTP, RowNo+2, 4);*/
             
            txtTotalPrice.setText(String.valueOf(Tprice));
            
            //for discount
            if(discount)
            {
                jTable1.setValueAt(" ", RowNo+1, 0);
                jTable1.setValueAt(" ", RowNo+1, 1);
                jTable1.setValueAt(" ", RowNo+1, 2);
                jTable1.setValueAt("Discount=", RowNo+1, 3);
                jTable1.setValueAt(discountTK, RowNo+1, 4);

                //gross total
                double discountT = Double.parseDouble(discountTK);
                grossTP = Tprice - discountT;
                jTable1.setValueAt(" ", RowNo+2, 0);
                jTable1.setValueAt(" ", RowNo+2, 1);
                jTable1.setValueAt(" ", RowNo+2, 2);
                jTable1.setValueAt("Total =", RowNo+2, 3);
                jTable1.setValueAt(grossTP, RowNo+2, 4); 
                
                txtTotalPrice.setText(String.valueOf(grossTP));
            }
            
             txtChange.setText("0.00");
             
             cash_paid = false;
             if(RowNo == 0)
             {
                 for(int k=0;k<jTable1.getRowCount();k++){
                 jTable1.setValueAt("", k, 0);   jTable1.setValueAt("", k, 1);
                 jTable1.setValueAt("", k, 2);   jTable1.setValueAt("", k, 3);
                 jTable1.setValueAt("", k, 4);
             }
             }
       }
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void txtCashPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCashPaidActionPerformed
        // TODO add your handling code here:
        if(txtCashPaid.getText().equals("0") || txtCashPaid.getText().isEmpty())
        {
            cash_paid = false;
        }
        else
        {
            int row = RowNo;
            cash_paid = true;
            if(addVAT)
            {
                row = RowNo+2;
            }
            else if(discount)
            {
                row = RowNo+2; 
            }
            if(addVAT && discount)
            {
                row = RowNo+4; 
            }
            String s = String.valueOf(jTable1.getValueAt(row, 4));
                double TP = Double.parseDouble(s);
                double PA = Double.parseDouble(txtCashPaid.getText());
                double CA = PA - TP;
                jTable1.setValueAt(" ", row+1, 0);
                jTable1.setValueAt(" ", row+1, 1);
                jTable1.setValueAt(" ", row+1, 2);
                jTable1.setValueAt("Paid  =", row+1, 3);   jTable1.setValueAt(String.valueOf(PA), row+1, 4);
                jTable1.setValueAt(" ", row+2, 0);
                jTable1.setValueAt(" ", row+2, 1);
                jTable1.setValueAt(" ", row+2, 2);
                jTable1.setValueAt("Change=", row+2, 3);   jTable1.setValueAt(String.valueOf(CA), row+2, 4);
                txtChange.setText(String.valueOf(CA));
        }
    }//GEN-LAST:event_txtCashPaidActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        Double vatTK = 0.0, grossTP = 0.0;
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            int r = jTable1.getSelectedRow(),n=RowNo, row = 0, serial = 0, cat_id = 0;
            Double qt = 0.0, quantity = 0.0;
            //if(cash_paid)
            //{
                //n=RowNo - 2; 
            if(r<n)
            {
                row = RowNo;
                String name = String.valueOf(jTable1.getValueAt(r, 2));
                String s = String.valueOf(jTable1.getValueAt(r, 3)),sq=String.valueOf(jTable1.getValueAt(r, 1));
               qt = Double.parseDouble(sq);
                cat_id = Integer.parseInt(String.valueOf(jTable2.getValueAt(r, 0)));
                quantity = qt-update_quantity;
                //JOptionPane.showMessageDialog(this, "cat ="+cat_id+" q="+quantity);
                
                Double rtnValue = DeleteItem_Quantity(name,cat_id,quantity);
                if(rtnValue==-1.0)
                {
                   double up = Double.parseDouble(s),TP=0.0;
                
                jTable1.setValueAt(up*qt, r, 4);
                
                

                int k= jTable1.getRowCount(), j;
                for(j=0;j<n;j++){
                    String s1 = String.valueOf(jTable1.getValueAt(j, 4));
                    double p = Double.parseDouble(s1);
                    //System.out.println(p);
                    TP += p;
                }  
                jTable1.setValueAt(" ", n, 0);
                jTable1.setValueAt(" ", n, 1);
                jTable1.setValueAt(" ", n, 2);
                jTable1.setValueAt("Total =", n, 3);
                jTable1.setValueAt(TP, n, 4);


                /*//for vat 15%
                jTable1.setValueAt(" ", n+1, 0);
                jTable1.setValueAt(" ", n+1, 1);
                jTable1.setValueAt(" ", n+1, 2);
                jTable1.setValueAt("Vat   =", n+1, 3);
                jTable1.setValueAt("15%", n+1, 4);

                //gross total
                vatTK = (15 * TP)/100;
                grossTP = TP + vatTK;
                jTable1.setValueAt(" ", n+2, 0);
                jTable1.setValueAt(" ", n+2, 1);
                jTable1.setValueAt(" ", n+2, 2);
                jTable1.setValueAt("Total =", n+2, 3);
                jTable1.setValueAt(grossTP, n+2, 4);*/
                
                //for VAT
                if(addVAT)
                {
                    //gross total
                    vatTK = (7.5 * TP)/100;
                    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
                    double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                    grossTP = TP + dd2dec;
                    //for vat 7.5%
                        jTable1.setValueAt(" ", RowNo+1, 0);
                        jTable1.setValueAt(" ", RowNo+1, 1);
                        jTable1.setValueAt(" ", RowNo+1, 2);
                        jTable1.setValueAt("Vat   =", RowNo+1, 3);
                        jTable1.setValueAt(dd2dec, RowNo+1, 4);


                        jTable1.setValueAt(" ", RowNo+2, 0);
                        jTable1.setValueAt(" ", RowNo+2, 1);
                        jTable1.setValueAt(" ", RowNo+2, 2);
                        jTable1.setValueAt("Total =", RowNo+2, 3);
                        jTable1.setValueAt(grossTP, RowNo+2, 4);

                    txtTotalPrice.setText(String.valueOf(grossTP));
                }
                //for discount
                else if(discount)
                {
                    //if discount is enable
                    jTable1.setValueAt(" ", n+1, 0);
                    jTable1.setValueAt(" ", n+1, 1);
                    jTable1.setValueAt(" ", n+1, 2);
                    jTable1.setValueAt("Less(-)=", n+1, 3);
                    jTable1.setValueAt(discountTK, n+1, 4);

                    //gross total
                    double discountT = Double.parseDouble(discountTK);
                    grossTP = TP - discountT;
                    jTable1.setValueAt(" ", n+2, 0);
                    jTable1.setValueAt(" ", n+2, 1);
                    jTable1.setValueAt(" ", n+2, 2);
                    jTable1.setValueAt("Total =", n+2, 3);
                    jTable1.setValueAt(grossTP, n+2, 4);
                    txtTotalPrice.setText(String.valueOf(grossTP));

                    
                }
                else if(cash_paid)
                {
                    String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                    double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                    double CA = PA - grossTP;
                    jTable1.setValueAt(" ", n+1, 0);
                    jTable1.setValueAt(" ", n+1, 1);
                    jTable1.setValueAt(" ", n+1, 2);
                    jTable1.setValueAt("Paid =", n+1, 3);   jTable1.setValueAt(String.valueOf(PA), n+1, 4);
                    jTable1.setValueAt(" ", n+2, 0);
                    jTable1.setValueAt(" ", n+2, 1);
                    jTable1.setValueAt(" ", n+2, 2);
                    jTable1.setValueAt("Change =", n+2, 3);   jTable1.setValueAt(String.valueOf(CA), n+2, 4);
                    txtChange.setText(String.valueOf(CA));
                }
                    
                else if(addVAT && cash_paid)
                {
                    //for vat
                    vatTK = (7.5 * TP)/100;
                    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
                    double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                    grossTP = TP + dd2dec;
                    //for vat 7.5%
                        jTable1.setValueAt(" ", RowNo+1, 0);
                        jTable1.setValueAt(" ", RowNo+1, 1);
                        jTable1.setValueAt(" ", RowNo+1, 2);
                        jTable1.setValueAt("Vat   =", RowNo+1, 3);
                        jTable1.setValueAt(dd2dec, RowNo+1, 4);


                        jTable1.setValueAt(" ", RowNo+2, 0);
                        jTable1.setValueAt(" ", RowNo+2, 1);
                        jTable1.setValueAt(" ", RowNo+2, 2);
                        jTable1.setValueAt("Total =", RowNo+2, 3);
                        jTable1.setValueAt(grossTP, RowNo+2, 4);

                    txtTotalPrice.setText(String.valueOf(grossTP));
                    
                    String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                    double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                    double CA = PA - grossTP;
                    jTable1.setValueAt(" ", n+3, 0);
                    jTable1.setValueAt(" ", n+3, 1);
                    jTable1.setValueAt(" ", n+3, 2);
                    jTable1.setValueAt("Paid =", n+3, 3);   jTable1.setValueAt(String.valueOf(PA), n+3, 4);
                    jTable1.setValueAt(" ", n+4, 0);
                    jTable1.setValueAt(" ", n+4, 1);
                    jTable1.setValueAt(" ", n+4, 2);
                    jTable1.setValueAt("Change =", n+4, 3);   jTable1.setValueAt(String.valueOf(CA), n+4, 4);
                    txtChange.setText(String.valueOf(CA));
                }
                else if(addVAT && discount)
                {
                    //for vat
                    vatTK = (7.5 * TP)/100;
                    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
                    double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                    grossTP = TP + dd2dec;
                    //for vat 7.5%
                        jTable1.setValueAt(" ", RowNo+1, 0);
                        jTable1.setValueAt(" ", RowNo+1, 1);
                        jTable1.setValueAt(" ", RowNo+1, 2);
                        jTable1.setValueAt("Vat   =", RowNo+1, 3);
                        jTable1.setValueAt(dd2dec, RowNo+1, 4);


                        jTable1.setValueAt(" ", RowNo+2, 0);
                        jTable1.setValueAt(" ", RowNo+2, 1);
                        jTable1.setValueAt(" ", RowNo+2, 2);
                        jTable1.setValueAt("Total =", RowNo+2, 3);
                        jTable1.setValueAt(grossTP, RowNo+2, 4);
                        
                        //if discount is enable
                    jTable1.setValueAt(" ", n+3, 0);
                    jTable1.setValueAt(" ", n+3, 1);
                    jTable1.setValueAt(" ", n+3, 2);
                    jTable1.setValueAt("Less(-)=", n+3, 3);
                    jTable1.setValueAt(discountTK, n+3, 4);

                    //gross total
                    double discountT = Double.parseDouble(discountTK);
                    grossTP = TP - discountT;
                    jTable1.setValueAt(" ", n+4, 0);
                    jTable1.setValueAt(" ", n+4, 1);
                    jTable1.setValueAt(" ", n+4, 2);
                    jTable1.setValueAt("Total =", n+4, 3);
                    jTable1.setValueAt(grossTP, n+4, 4);
                    txtTotalPrice.setText(String.valueOf(grossTP));
                    
                    String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                    double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                    double CA = PA - grossTP;
                    jTable1.setValueAt(" ", n+5, 0);
                    jTable1.setValueAt(" ", n+5, 1);
                    jTable1.setValueAt(" ", n+5, 2);
                    jTable1.setValueAt("Paid =", n+5, 3);   jTable1.setValueAt(String.valueOf(PA), n+5, 4);
                    jTable1.setValueAt(" ", n+6, 0);
                    jTable1.setValueAt(" ", n+6, 1);
                    jTable1.setValueAt(" ", n+6, 2);
                    jTable1.setValueAt("Change =", n+6, 3);   jTable1.setValueAt(String.valueOf(CA), n+6, 4);
                    txtChange.setText(String.valueOf(CA));
                } 
                }
                else
                {
                    jTable1.setValueAt(update_quantity, r, 1);
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No data to change.");
            }
            
        }
        //////////////////////////////////////////
    }//GEN-LAST:event_jTable1KeyPressed

    private void PrintBill(int In1Out0) {

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
        String Time = "Date:" + formatter.format(date);
        System.out.println("RowNo =" + RowNo);
        
//        //for receipt number
//        Date time1 = Calendar.getInstance().getTime();
//        DateFormat formatter1 = new SimpleDateFormat(" dd-MMM-yyyy ");
//        String date1 = formatter1.format(time1);
//        receiptNo = getSerial(date1);

        String RNo = "Receipt# " + receiptNo, Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.", blank = " ";
        String line = "---------------------------------------",
                CName = "New Shad Restora", CAddress1 = "Plot# 35, Sonargaon Janapath Road",
                CAddress2 = "Sector# 12, Uttara, Dhaka 1230", CAddress3 = "";
        String S[][] = new String[50][5];
        S[0][0] = "Qty";
        S[0][1] = "Description";
        S[0][2] = "UnitP";
        S[0][3] = "Price";
        String s;
        int size = RowNo;
        if (addVAT) {
            size = RowNo + 2;
        } else if (discount) {
            size = RowNo + 2;
        } else if (cash_paid) {
            size = RowNo + 2;
        }
        if (addVAT && discount) {
            size = RowNo + 4;
        }
        if (addVAT && cash_paid) {
            size = RowNo + 4;
        }
        if (discount && cash_paid) {
            size = RowNo + 4;
        }
        for (int i = 0; i <= size; i++) {

            if ((s = String.valueOf(jTable1.getValueAt(i, 1))) != null) {
                S[i + 1][0] = s;
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 2))) != null) {
                S[i + 1][1] = s;
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 3))) != null) {
                S[i + 1][2] = s;
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 4))) != null) {
                S[i + 1][3] = s;
            }

        }

        Calendar calendar = Calendar.getInstance();
        java.sql.Date sqlDateObject = new java.sql.Date(calendar.getTime().getTime());
        System.out.println("Receipt no " + receiptNo);
        _receiptNo = receiptNo;

        try{
            FileWriter filewriter = new FileWriter("E:/POS/bill.txt");
            PrintWriter out = new PrintWriter(filewriter);
            out.println("           "+CName);
            out.println("  ");
            //out.println("   "+CAddress1);
            //out.println("   "+CAddress2);
            //out.println("  "+CAddress3);
            //out.println("   "+vatReg+" Receipt# "+receiptNo);
            //out.println("   "+"Receipt# "+receiptNo);
            out.println("   User: "+UserName);
            out.println("   "+Time);
            //out.println("  "+"   ");
            out.println(line);
            out.println(S[0][0]+" "+S[0][1]+"         "+S[0][2]+"   "+S[0][3]);
            out.println(line);
            for(int j=1;j<=size+1;j++){
                out.println(S[j][0]+AddSpace(4,S[j][0].length())+S[j][1]+AddSpace(20,S[j][1].length())+S[j][2]+
                        AddSpace(8,S[j][2].length())+S[j][3]);
            }
            
            
            out.println("  "+"   ");
            //out.println("  "+"   ");
            //out.println("  "+"   ");
            //out.println("       "+Thank);
            //out.println("  "+HD);
            //out.println("       "+elseCode);
            out.println(" "+blank);
            out.println(" "+" ");
            out.println(" "+" ");
            out.println(" "+" ");
            out.println();
            
            out.close();
        }catch(IOException ex){
            
        }
        //print the bill file
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.print(new File("E:/POS/bill.txt"));
        } catch (IOException ex) {
            Logger.getLogger(BuyFrame_StockOut.class.getName()).log(Level.SEVERE, null, ex);
        }

        int catID = 0, rowno = RowNo;

        String tran = "", category = "";
        PreparedStatement statement = null, statement1 = null;
        try {
            //statement1 = connection.prepareStatement("INSERT INTO inv_vat_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name)VALUES (?, ?, ?, ?, ?, ?)");
            statement = connection.prepareStatement("INSERT INTO buy_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name, bill_no)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                        
            for (int t = 1; t <= rowno; t++) {
                catID = Integer.parseInt(jTable2.getValueAt(t - 1, 0).toString());
                category = getCategory_Name(catID);
                //
                tran += S[t][0] + ", " + S[t][1] + ", " + S[t][3] + "\n";
//                if(In1Out0 == 1){
//                    AddItem_Quantity(S[t][1],Double.parseDouble(S[t][0]),catID);
//                }
//                else{
//                    DeleteItem_Quantity(S[t][1],catID,Double.parseDouble(S[t][0]));
//                }
                
            //JOptionPane.showMessageDialog(rootPane, tran);
                //AddTransactionData(S[t][0], S[t][1], S[t][3], category, receiptNo);

            //******************************Batch Insert*************************
                //String q, String n, String p, String cat, int bill
//            String name = n, quantity = q, price = p, category = cat;
//            int tran_id = 0, tran_vat_id = 0, billNo = bill;
                transaction_id++;
            //inserting data into transaction table

                statement.setString(1, String.valueOf(transaction_id));
                statement.setString(2, S[t][1]);
                statement.setString(3, S[t][0]);
                statement.setString(4, S[t][3]);
                statement.setDate(5, sqlDateObject);
                statement.setString(6, category);
                statement.setString(7, String.valueOf(In1Out0));

                statement.addBatch();

                
            }
            statement.executeBatch();
//            if (addVAT) {
//                statement1.executeBatch();
//            }
        } catch (Exception e) {
            // TODO: handle exception
            //Utilities.setErrorLog(SellsFrame_Modified.class.getSimpleName()+" PrintBill()", "Exception", "Batch INSERT INTO inv_transaction Query Execution Error", e.toString());
            e.printStackTrace();
            System.out.println("Error while inserting data!");
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(statement);
            //DatabaseUtils.close(statement1);
        }

            //******************************End of Batch Insert******************************
        if (addVAT) {
            AddVAT(vatTK, receiptNo);
            vatTK = 0.0;
        }

        if (discount) {
            double dtk = Double.parseDouble(discountTK);
            //insert into discount table
            AddDiscount(dtk, receiptNo);
        }
        //JOptionPane.showMessageDialog(rootPane, tran);
        JOptionPane.showMessageDialog(rootPane, "Transaction Data Added Successfully");

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            for (int j = 0; j < jTable1.getColumnCount(); j++) {
                jTable1.setValueAt("", i, j);
            }
        }
        txtTotalPrice.setText("0.00");
        txtChange.setText("0.00");
        txtCashPaid.setText("");
        RowNo = 0;
        cash_paid = false;
        discount = false;
        addVAT = false;

        setSerial(receiptNo, sqlDateObject);
        receiptNo += 1;
    }
    
    private void AddVAT(Double amount, int bill)
    {
        int billNo = bill;
        //getting system date
        Date time = Calendar.getInstance().getTime();
         DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);
        
        //inserting data into inv_vat table
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement
        ("INSERT INTO inv_vat (vat_amount, vat_date, bill_no)VALUES (?, ?, ?)");
                statement.setString(1, String.valueOf(amount));
                statement.setString(2, date);
                statement.setString(3, String.valueOf(billNo));

                statement.execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("Error while inserting data!");
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(statement);
            }
    }
    
    private String AddSpace(int flength, int length)
    {
        String space = "";
        if((flength-length) == 1)
        {
            space=" ";
        }
        else if((flength-length) == 2)
        {
            space="  ";
        }
        else if((flength-length) == 3)
        {
            space="   ";
        }
        else if((flength-length) == 4)
        {
            space="    ";
        }
        else if((flength-length) == 5)
        {
            space="     ";
        }
        else if((flength-length) == 6)
        {
            space="      ";
        }
        else if((flength-length) == 7)
        {
            space="       ";
        }
        else if((flength-length) == 8)
        {
            space="        ";
        }
        else if((flength-length) == 9)
        {
            space="         ";
        }
        else if((flength-length) == 10)
        {
            space="          ";
        }
        else if((flength-length) == 11)
        {
            space="           ";
        }
        else if((flength-length) == 12)
        {
            space="            ";
        }
        else if((flength-length) == 13)
        {
            space="             ";
        }
        else if((flength-length) == 14)
        {
            space="              ";
        }
        else if((flength-length) == 15)
        {
            space="               ";
        }
        else if((flength-length) == 16)
        {
            space="                ";
        }
        else if((flength-length) == 17)
        {
            space="                 ";
        }
        else if((flength-length) == 18)
        {
            space="                  ";
        }
        else if((flength-length) == 19)
        {
            space="                   ";
        }
        return space;
    }
    
    private void AddTransactionData(String q, String n, String p, String cat, int bill)
    {
        String name = n, quantity = q, price = p, category = cat;
        int tran_id = 0, tran_vat_id = 0, billNo = bill;
        
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM buy_transaction");
            // print the results
            while (rs.next()) {
                //inv_cat_id++;
                tran_id++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }
        tran_id += 1;
        
        
        
        //getting system date
        Date time = Calendar.getInstance().getTime();
         DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);
        
        //inserting data into transaction table
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement
        ("INSERT INTO buy_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name, bill_no)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, String.valueOf(tran_id));
                statement.setString(2, name);
                statement.setString(3, quantity);
                statement.setString(4, price);
                statement.setString(5, date);
                statement.setString(6, category);
                statement.setString(7, String.valueOf(billNo));

                statement.execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                //System.out.println("Error while inserting data!");
                JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(statement);
            }
        
        if(addVAT)
        {
            //inserting data into inv_vat_transaction table
            //getting transaction_vat id
            java.sql.Statement stm2 = null;
            ResultSet rs2 = null; 
            try {
                stm2 = connection.createStatement();
                rs2 = stm2.executeQuery("SELECT * FROM inv_vat_transaction");
                // print the results
                while (rs2.next()) {
                    //inv_cat_id++;
                    tran_vat_id++;
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(rs2);
                DatabaseUtils.close(stm2);
            }
            tran_vat_id += 1;
            
            PreparedStatement statement1 = null;
            try {
                    statement1 = connection.prepareStatement
            ("INSERT INTO inv_vat_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name)VALUES (?, ?, ?, ?, ?, ?)");
                    statement1.setString(1, String.valueOf(tran_id));
                    statement1.setString(2, name);
                    statement1.setString(3, quantity);
                    statement1.setString(4, price);
                    statement1.setString(5, date);
                    statement1.setString(6, category);

                    statement1.execute();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    System.out.println("Error while inserting data!");
                    //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
                }finally
                {
                    DatabaseUtils.close(statement1);
                }
        }
    }
    
    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        if(RowNo>0)
        {
            JOptionPane.showMessageDialog(this, "Delete All Items First, Then Exit.");
        }
        else
        {
            try {
                connection.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error In Closing DBConnection. Please Ignore it & Click OK.");
            }
        this.dispose();
        }
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        addMainMenue();
    }//GEN-LAST:event_btn_backActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        addMainMenue();
    }//GEN-LAST:event_formWindowOpened

    private void menuDateWiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDateWiseActionPerformed
        // TODO add your handling code here:
        new Buy_DashBoard().setVisible(true);
    }//GEN-LAST:event_menuDateWiseActionPerformed

    private void AddDiscount(Double amount, int bill)
    {
        int billNo = bill;
        //getting system date
        Date time = Calendar.getInstance().getTime();
         DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);
        
        //inserting data into inv_vat table
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement
        ("INSERT INTO inv_discount (disc_amount, disc_date, bill_no)VALUES (?, ?, ?)");
                statement.setString(1, String.valueOf(amount));
                statement.setString(2, date);
                statement.setString(3, String.valueOf(billNo));

                statement.execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("Error while inserting data!");
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(statement);
            }
    }
    
    private void btnDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountActionPerformed
        // TODO add your handling code here:
        Double TP = 0.0, grossTP = 0.0, dTk;
        discountTK = "";
        
        while(discountTK.equals("")){
            discountTK = JOptionPane.showInputDialog(this,"Enter Amount:","Discount Amount",
            JOptionPane.PLAIN_MESSAGE);
        }
        discount = true;
        //if discount is enable
        dTk = Double.parseDouble(discountTK);
        
        
        int row = RowNo;
        if(addVAT)
        {
            String s1 = String.valueOf(jTable1.getValueAt(RowNo+2, 4));
            TP = Double.parseDouble(s1);
            row +=2;
        }
        else
        {
            String s1 = String.valueOf(jTable1.getValueAt(RowNo, 4));
            TP = Double.parseDouble(s1);
            row = RowNo;
        }
         
                    jTable1.setValueAt(" ", row+1, 0);
                    jTable1.setValueAt(" ", row+1, 1);
                    jTable1.setValueAt(" ", row+1, 2);
                    jTable1.setValueAt("Less(-)=", row+1, 3);
                    jTable1.setValueAt(dTk, row+1, 4);

                    //gross total
                    //double discountT = Double.parseDouble(discountTK);
                    grossTP = TP - dTk;
                    jTable1.setValueAt(" ", row+2, 0);
                    jTable1.setValueAt(" ", row+2, 1);
                    jTable1.setValueAt(" ", row+2, 2);
                    jTable1.setValueAt("Total =", row+2, 3);
                    jTable1.setValueAt(grossTP, row+2, 4);
                    txtTotalPrice.setText(String.valueOf(grossTP));
        
    }//GEN-LAST:event_btnDiscountActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        update_quantity = Integer.parseInt(String.valueOf(jTable1.getValueAt(row, 1)));
        //JOptionPane.showMessageDialog(this, "Quantity = "+ update_quantity);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        new Buy_DashBoard_Item().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        new Buy_Add_Category().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new Buy_Update_Category().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        new Buy_DashBoard_Category().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        new Buy_Add_FoodItem().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new Buy_Update_FoodItem().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        new Buy_Delete_FoodItem().setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void menuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRefreshActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new BuyFrame_StockOut().setVisible(true);
    }//GEN-LAST:event_menuRefreshActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        new Buy_Notification().setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void btnIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIn1ActionPerformed
        // TODO add your handling code here:
        PrintBill(0);
    }//GEN-LAST:event_btnIn1ActionPerformed

    private static class JobCompleteMonitor extends PrintJobAdapter {
		@Override
		public void printJobCompleted(PrintJobEvent jobEvent) {
			System.out.println("Job completed");
			jobRunning = false;
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
            java.util.logging.Logger.getLogger(BuyFrame_StockOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuyFrame_StockOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuyFrame_StockOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuyFrame_StockOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuyFrame_StockOut().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnDiscount;
    private javax.swing.JButton btnIn1;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JScrollPane jsc_item;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenuItem menuDateWise;
    private javax.swing.JMenu menuNotification;
    private javax.swing.JMenuItem menuRefresh;
    private javax.swing.JPanel pnl_button;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JTextField txtCashPaid;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reseller;

import restaurantpos.*;
import employee.Employee_Advance_Salary;
import buy.BuyFrame_StockIn;
import dashboard.DashBoard_Category_For_All;
import dashboard.DashBoard_Today;
import DB.CustomerDBHelper;
import Model.InvCustomer;
import Model.InvItem;
import Utilities.AutoCompletion;
import Utilities.DB_Utilities;
import Utilities.EmailUtill;
import Utilities.HTMLGenerator;
import Utilities.SMSSender;
import Utilities.Utilities;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
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
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
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
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author HajjazPC
 */
public class Retailer_SellsFrame extends javax.swing.JFrame {

    static boolean jobRunning = true;

    String discountTK = "";
    private boolean discount = false, boolDue = false;
    int _receiptNo = 0;
    public Retailer_SellsFrame Sframe;
    public Dimension screenSize;
    public DBGateway dbg;
    ;
    Connection connection = null;
    ResultSet RS;
    private int RowNo;
    private boolean cash_paid = false, addVAT = false;
    private double TotalPrice = 0.0;

    int button_width = 95;        // button width
    int button_height = 95;        // button height
    int horizontalGap = 5;         // horizontal gap in button
    int verticalGap = 5;         // verticle gap in button
    int numberOfColumns = 7;          // number of colums in the button panel
    int fontSize = 13;         // font size of button name
    int fontType = Font.BOLD;  // font type
    String fontName = "Tahoma";    // font name
    String ss = "0, 51, 255";
    Color fontColor = new Color(0, 51, 255);  // font colot
    int fontColorR;
    int fontColorG;
    int fontColorB;
//    private final static int button_width   =   95;        // button width
//    private final static int button_height  =   105;        // button height
//    private final static int horizontalGap  =   5;         // horizontal gap in button
//    private final static int verticalGap    =   5;         // verticle gap in button
//    private final static int numberOfColumns=   7;          // number of colums in the button panel
//    private final static int fontSize       =   11;         // font size of button name
//    private final static int fontType       =   Font.BOLD;  // font type
//    private final static String fontName    =   "Thoma";    // font name
//    private final static Color  fontColor   =   new Color(0, 51, 255);  // font colot

    public static String UserName = "";
    Date date;
    int transaction_id = 0;
    ArrayList UcatID = new ArrayList();
    private Double vatTK = 0.0;
    private int update_quantity = 0;
    int Height = 0;
    Double billDueAmount = 0.0;
    int billDueCustomerId;
    String billDueCustomerName = "";

    /**
     * Creates new form SellsFrame
     */
    @SuppressWarnings("empty-statement")
    public Retailer_SellsFrame() {
        this.RowNo = 0;
        date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd/MMM/yyyy");
        String Time = "Date: " + formatter.format(date);

        this.dbg = new DBGateway();
        initComponents();
        initFields();

        connection = dbg.connectionTest();

        Notification();
        transaction_id = GetTransaction_Id();

        Notification_ZeroBill();

        List<InvItem> invItemList = DB_Utilities.getListOfItems("Select * from inv_item", connection);
        Vector comboBoxItems = new Vector();
        for (InvItem item : invItemList) {
            System.out.println("comboBoxItems add item Id = " + item.getItemId() + ", ItemName = " + item.getItemName());
            comboBoxItems.addElement(new Item(item.getCatId(), item.getItemName()));
        }
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

        cmbItems.setModel(model);
        AutoCompleteDecorator.decorate(cmbItems);

        //Initializing Customer Combobox
//        List<InvCustomer> itemList = CustomerDBHelper.getListOfCustomers("Select * from inv_customer", connection);
//        Vector comboBoxItems1 = new Vector();
//        for (InvCustomer item : itemList) {
//            System.out.println("Customer= " + item.getCustomerId() + ", " + item.getCustomerName());
//            comboBoxItems.addElement(new Item(item.getCustomerId(), item.getCustomerName()));
//        }
//        final DefaultComboBoxModel model1 = new DefaultComboBoxModel(comboBoxItems1);
//        cmbCustomerName.setModel(model1);
//        AutoCompleteDecorator.decorate(cmbCustomerName);
//        Calendar calendar = Calendar.getInstance();
//        java.sql.Date sqlDateObject = new java.sql.Date(calendar.getTime().getTime());
//        System.out.println(" Time = "+ sqlDateObject);
//        Window[] w = Window.getWindows();
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(w);
    }

    private void initFields() {
        button_width = InventoryPOS.buttonWidth;        // button width
        button_height = InventoryPOS.buttonHeight;        // button height
        horizontalGap = InventoryPOS.horizontalGap;         // horizontal gap in button
        verticalGap = InventoryPOS.verticalGap;         // verticle gap in button
        numberOfColumns = InventoryPOS.numberOfColumns;          // number of colums in the button panel
        fontSize = InventoryPOS.fontSize;         // font size of button name
        fontType = InventoryPOS.fontType;  // font type
        fontName = InventoryPOS.fontName;    // font name
        fontColorR = InventoryPOS.fontColorR;
        fontColorG = InventoryPOS.fontColorG;
        fontColorB = InventoryPOS.fontColorB;

        btnTokenPrint.setVisible(false);

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        String Time = "Date: " + formatter.format(date);

        lblDate.setText(Time);
        lblUserName.setText(Utilities.UserName);

    }

    public int GetTransaction_Id() {
        int id = 0;
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_transaction");
            // print the results
            while (rs.next()) {
                //inv_cat_id++;
                id++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        id++;
        return id;
    }

    public void Notification() {
        int count = 0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_item where item_quantity < 5");
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
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        if (count > 0) {
            menuNotification.setForeground(Color.RED);
            menuNotification.setText("Notification " + count);
            menuNotification.setEnabled(true);
        } else {
            menuNotification.setEnabled(false);
        }
    }

    public void Notification_ZeroBill() {
        int count = 0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("select * from inv_transaction where trunc(tran_date) = trunc(sysdate) and sell_price = 0");
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
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        if (count > 0) {
            menuZeroBill.setForeground(Color.RED);
            menuZeroBill.setText("Zero Bill " + count);
            menuZeroBill.setEnabled(true);
        } else {
            menuZeroBill.setEnabled(false);
        }
    }

    private int getSerial(String d) {
        int serial = 0;
        String date = "'" + d + "'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM bill_serial where to_date(bill_date) between "
                    + "to_date(" + date + ") " + "and to_date(" + date + ")");
            // print the results
            while (rs.next()) {
                serial = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        serial++;
        return serial;
    }

    private void setSerial(int serial, String date) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO bill_serial (serial,bill_date)VALUES (?, ?)");
            statement.setString(1, String.valueOf(serial));
            statement.setString(2, date);

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

    private int getQuantity(String name, int cat) {
        int quantity = 0, cat_id = cat;
        String Name = "'" + name + "'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_item where item_name = " + Name + " and cat_id = " + cat_id + "");
            // print the results
            while (rs.next()) {
                quantity = Integer.parseInt(rs.getString(7));

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

    private int getCategory_ID(String categoryName) {
        int id = 0;
        String catName = "'" + categoryName + "'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_category where cat_name = " + catName + "");
            // print the results
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return id;
    }

    private int getCategoryID_fromFoodItem(String itemname) {
        int catId = 0;
        String itemName = "'" + itemname + "'";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_item where item_name = " + itemName + "");
            // print the results
            while (rs.next()) {
                catId = Integer.parseInt(rs.getString(2));

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return catId;
    }

    private String getCategory_Name(int catid) {
        int id = catid;
        String catName = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_category where cat_id = " + id + "");
            // print the results
            while (rs.next()) {
                catName = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
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
        String imagePath, imag = "/com/images/";

        //ArrayList menue = new ArrayList();
        ArrayList<String> catName = new ArrayList();
        ArrayList<String> catImage = new ArrayList();

        //get the category list in ArrayList catName
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_category ");
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
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        /*for (int size = 0 ; size<ItemDB.mainMenuCodes.length; size++) {
            menue.add(ItemDB.mainMenuCodes[size]);
            itemName.add(ItemDB.mainMenuDesc[size]);
        }*/
        JButton[] buttons = new JButton[catName.size()];

        for (int i = 0; i < buttons.length; i++) {

            if (catImage.get(i) != null) {
                imagePath = catImage.get(i);
                img = Toolkit.getDefaultToolkit().getImage(imagePath);
                sub = img.getScaledInstance(button_width - 8, button_height - 30, Image.SCALE_FAST);
                icon = new ImageIcon(sub);
            } else {
                icon = new ImageIcon();
            }

            buttons[i] = new JButton(catName.get(i).toString(), icon);
            buttons[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            buttons[i].setHorizontalTextPosition(AbstractButton.CENTER);

            buttons[i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
            buttons[i].setFont(new java.awt.Font(fontName, fontType, fontSize));
            buttons[i].setForeground(new java.awt.Color(fontColorR, fontColorG, fontColorB));

            buttons[i].setActionCommand(catName.get(i).toString());
            buttons[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String choice = e.getActionCommand();
                    int cat = getCategory_ID(choice);
                    addSubmenue(choice, cat);
                }
            });
        }

        int b = 0;
        int vGap = verticalGap;
        int hGap = horizontalGap;
        int bLength = buttons.length;
        int bRows = bLength / numberOfColumns + 1;

        L1:
        for (int j = 0; j < bRows; j++) {
            vGap = 10;
            for (int k = 0; k < numberOfColumns; k++) {

                pnl_button.add(buttons[b], new org.netbeans.lib.awtextra.AbsoluteConstraints(vGap, hGap, button_width, button_height));
                repaint();
                vGap += button_width + verticalGap;
                b++;
                if (b >= bLength) {
                    break L1;
                }
            }
            hGap += button_height + horizontalGap;
        }
        pack();
    }

    private void addSubmenue(String choice, int cat) {
        pnl_button.removeAll();
        repaint();

        Image img, sub;
        ImageIcon icon;
        String imagePath, imag = "/com/images/";

        ArrayList menue = new ArrayList();
        ArrayList<String> itemName = new ArrayList();
        ArrayList<String> itemImage = new ArrayList();

        ArrayList list = ItemDB.getSubMenu(choice);

        final int cat_id = cat;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            //String name = "'"+choice+"'";
            rs = stm1.executeQuery("SELECT * FROM inv_item where cat_id = " + cat_id + "");
            // print the results
            while (rs.next()) {
                itemName.add(rs.getString(3));
                itemImage.add(rs.getString(6));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }

        JButton[] buttons = new JButton[itemName.size()];

        for (int i = 0; i < buttons.length; i++) {

            if (itemImage.get(i) != null) {
                imagePath = itemImage.get(i);
                img = Toolkit.getDefaultToolkit().getImage(imagePath);
                sub = img.getScaledInstance(button_width - 8, button_height - 30, Image.SCALE_FAST);
                icon = new ImageIcon(sub);
            } else {
                icon = new ImageIcon();
            }

            buttons[i] = new JButton(itemName.get(i).toString(), icon);
            buttons[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            buttons[i].setHorizontalTextPosition(AbstractButton.CENTER);

            buttons[i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
            buttons[i].setFont(new java.awt.Font(fontName, fontType, fontSize));
            buttons[i].setForeground(new java.awt.Color(fontColorR, fontColorG, fontColorB));

            buttons[i].setActionCommand(itemName.get(i).toString());
            buttons[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String choice = e.getActionCommand();
                    addItems(choice, cat_id, 1);
                }
            });
        }
        int b = 0;
        int vGap = verticalGap;
        int hGap = horizontalGap;
        int bLength = buttons.length;
        int bRows = bLength / numberOfColumns + 1;

        L1:
        for (int j = 0; j < bRows; j++) {
            vGap = 10;
            for (int k = 0; k < numberOfColumns; k++) {

                pnl_button.add(buttons[b], new org.netbeans.lib.awtextra.AbsoluteConstraints(vGap, hGap, button_width, button_height));
                repaint();
                vGap += button_width + verticalGap;
                b++;
                if (b >= bLength) {
                    break L1;
                }
            }
            hGap += button_height + horizontalGap;
        }
        pack();
    }

    private void addItems(String choice, int cat_id, int itemQuantity) {

        Double Price = 0.0;
        int quantity = 0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            String name = "'" + choice + "'";
            rs = stm1.executeQuery("SELECT * FROM inv_item where item_name = " + name + " and cat_id = " + cat_id + "");
            // print the results
            while (rs.next()) {
                Price = Double.parseDouble(rs.getString("item_retailer_price"));
                quantity = Integer.parseInt(rs.getString(7));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, choice + " is Not Available");
        } else {
            WriteToTable(Price, choice, cat_id, itemQuantity);
            DeleteItem_Quantity(choice, cat_id, itemQuantity);
        }
    }

    //writes data to the printing table
    public void WriteToTable(Double price, String itemName, int cat_id, int itemQuantity) {
        Double unitPrice = price, TP = 0.0, vatTK = 0.0, grossTP = 0.0;
        int qty = itemQuantity, flag = 0;
        String name = itemName;
        if (RowNo == 0) {
            jTable2.setValueAt(cat_id, RowNo, 0);
            jTable1.setValueAt(RowNo, RowNo, 0);
            jTable1.setValueAt(qty, RowNo, 1);
            jTable1.setValueAt(name, RowNo, 2);
            jTable1.setValueAt(unitPrice, RowNo, 3);
            jTable1.setValueAt(qty * unitPrice, RowNo, 4);
            RowNo += 1;
        } else {
            int k = jTable1.getRowCount(), j;
            System.out.println(k);
            for (j = 0; j < k; j++) {
                if (name.equals(jTable1.getValueAt(j, 2))) {
                    qty = (int) jTable1.getValueAt(j, 1);
                    qty += 1;
                    flag = 1;
                    break;
                } else {
                    continue;
                }
            }
            if (flag == 1) {
                jTable2.setValueAt(cat_id, j, 0);
                jTable1.setValueAt(j, j, 0);
                jTable1.setValueAt(qty, j, 1);
                jTable1.setValueAt(name, j, 2);
                jTable1.setValueAt(unitPrice, j, 3);
                jTable1.setValueAt(qty * unitPrice, j, 4);
                //RowNo =j+1;
            } else {
                jTable2.setValueAt(cat_id, RowNo, 0);
                jTable1.setValueAt(RowNo, RowNo, 0);
                jTable1.setValueAt(qty, RowNo, 1);
                jTable1.setValueAt(name, RowNo, 2);
                jTable1.setValueAt(unitPrice, RowNo, 3);
                jTable1.setValueAt(qty * unitPrice, RowNo, 4);
                RowNo += 1;
            }
        }
        int k = jTable1.getRowCount(), j, n = RowNo;
        for (j = 0; j < RowNo; j++) {
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

        if (cash_paid) {
            double PA = Double.parseDouble(txtCashPaid.getText());
            double CA = PA - grossTP;
            jTable1.setValueAt("Paid =", RowNo + 3, 3);
            jTable1.setValueAt(String.valueOf(PA), RowNo + 3, 4);
            jTable1.setValueAt("Change =", RowNo + 4, 3);
            jTable1.setValueAt(String.valueOf(CA), RowNo + 4, 4);
            txtChange.setText(String.valueOf(CA));
        }
        //////////////////////////////////////////
    }

    private int DeleteItem_Quantity(String choice, int cat_id, int quantity) {
        //JOptionPane.showMessageDialog(this, "from DeleteItem() ->quantity="+quantity);
        int flag = -1;
        int dbQuan = getQuantity(choice, cat_id);
        int quan = dbQuan - quantity;
        //JOptionPane.showMessageDialog(this, "from DeleteItem() ->dbQ="+dbQuan+" Q="+quan);
        if (quan < 0) {
            //flag = dbQuan;
            JOptionPane.showMessageDialog(this, "Not available quantity. Available = " + dbQuan);
        }

        flag = -1;
        String itemName = "'" + choice + "'";
        String editQuantity = "'" + quan + "'";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("UPDATE inv_item set item_quantity =" + editQuantity + " where item_name = " + itemName + " and cat_id = " + cat_id + "");
            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.toString(), "Error", 1);
            //System.out.println("Error while inserting data!");
        } finally {
            DatabaseUtils.close(statement);
        }

        return flag;
    }

    private void AddItem_Quantity(String choice, int quantity, int catid) {

        int q = 0, cat_id = catid;
        q = getQuantity(choice, cat_id);

        int quan = quantity + q;
        String itemName = "'" + choice + "'";
        String editQuantity = "'" + quan + "'";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("UPDATE inv_item set item_quantity =" + editQuantity + " where item_name = " + itemName + " and cat_id = " + cat_id + "");
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
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTotalPrice = new javax.swing.JTextField();
        txtCashPaid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        btnDeleteItem = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnAddTax = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtChange = new javax.swing.JTextField();
        pnl_main = new javax.swing.JPanel();
        btn_exit = new javax.swing.JButton();
        jsc_item = new javax.swing.JScrollPane();
        pnl_button = new javax.swing.JPanel();
        btn_back = new javax.swing.JButton();
        lblElseCode = new javax.swing.JLabel();
        lblElseCode1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnDiscount = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnDueBill = new javax.swing.JButton();
        btnTokenPrint = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cmbItems = new javax.swing.JComboBox<>();
        btnMinus = new javax.swing.JButton();
        tvCounter = new javax.swing.JTextField();
        btnPlus = new javax.swing.JButton();
        btnAddItem = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuRefresh = new javax.swing.JMenuItem();
        menuLogoutExit = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuSellsFrame = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        menuZeroBill = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuDateWise = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuNotification = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuGiveAdvance = new javax.swing.JMenuItem();
        menuDueBill = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        menuAddCardBill = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sells Frame");
        setExtendedState(6);
        setFocusable(false);
        setPreferredSize(new java.awt.Dimension(1442, 750));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(1366, 673, 271, 0);

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
                false, true, false, false, false
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

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(740, 150, 530, 410);

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setBackground(new java.awt.Color(153, 153, 153));
        txtTotalPrice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotalPrice.setForeground(new java.awt.Color(0, 102, 102));
        txtTotalPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPrice.setText("0.00");
        txtTotalPrice.setToolTipText("Total Price");
        txtTotalPrice.setAlignmentX(1.0F);
        txtTotalPrice.setAlignmentY(1.0F);
        getContentPane().add(txtTotalPrice);
        txtTotalPrice.setBounds(980, 120, 82, 23);

        txtCashPaid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCashPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCashPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCashPaidActionPerformed(evt);
            }
        });
        getContentPane().add(txtCashPaid);
        txtCashPaid.setBounds(850, 120, 66, 23);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Total: ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(920, 120, 59, 22);

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(0, 153, 153));
        lblDate.setText("Date");
        getContentPane().add(lblDate);
        lblDate.setBounds(760, 10, 217, 17);

        btnDeleteItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteItem.setText("Delete Item");
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeleteItem);
        btnDeleteItem.setBounds(890, 570, 130, 43);

        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        getContentPane().add(btnPrint);
        btnPrint.setBounds(1160, 570, 110, 43);

        btnAddTax.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAddTax.setText("Add VAT");
        btnAddTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTaxActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddTax);
        btnAddTax.setBounds(1040, 570, 110, 43);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Change: ");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(1070, 120, 81, 22);

        txtChange.setEditable(false);
        txtChange.setBackground(new java.awt.Color(0, 0, 0));
        txtChange.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtChange.setForeground(new java.awt.Color(0, 204, 0));
        txtChange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChange.setText("0.00");
        txtChange.setToolTipText("Total Price");
        txtChange.setAlignmentX(1.0F);
        txtChange.setAlignmentY(1.0F);
        getContentPane().add(txtChange);
        txtChange.setBounds(1150, 120, 77, 23);

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
        pnl_main.add(btn_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 620, 120, 50));

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

        pnl_main.add(jsc_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 740, 620));

        btn_back.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_back.setText("Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        pnl_main.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 620, 120, 50));

        lblElseCode.setText("<html>\n\t<p style=\"text-align:center;font-size:15px; color:black;background-color:Black;color:White\"> \n\t\t<span style=\"color:White; font-size:20px;background-color:black;font-weight:bold\">&nbsp  E L S E </span>\n\t\t<span style=\"color:Green; font-size:20px;background-color:black;font-weight:bold\"> C O D E  </span>\n\t\t<span style=\"color:White; font-size:12px;background-color:black;font-weight:normal\"><br/>  info@elsecode.com </span>\n\t\t<span style=\"color:White; font-size:10px;background-color:black;font-weight:normal\"><br/>Mobile: +8801674829764 </span>\n\t</p>\n<html>");
        pnl_main.add(lblElseCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblElseCode1.setText("<html>\n\t<p style=\"text-align:center;font-size:15px; color:black;background-color:Black;color:White\"> \n\t\t<span style=\"color:White; font-size:20px;background-color:black;font-weight:bold\">&nbsp  E L S E </span>\n\t\t<span style=\"color:Green; font-size:20px;background-color:black;font-weight:bold\"> C O D E  </span>\n\t\t<span style=\"color:White; font-size:12px;background-color:black;font-weight:normal\"><br/>  info@elsecode.com </span>\n\t\t<span style=\"color:White; font-size:10px;background-color:black;font-weight:normal\"><br/>Mobile: +8801674829764 </span>\n\t</p>\n<html>");
        pnl_main.add(lblElseCode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setText("<html>\n\t<p style=\"text-align:center;font-size:15px; color:black;background-color:Black;color:White\"> \n\t\t<span style=\"color:White; font-size:20px;background-color:black;font-weight:bold\">&nbsp  E L S E </span>\n\t\t<span style=\"color:Green; font-size:20px;background-color:black;font-weight:bold\"> C O D E  </span>\n\t\t<span style=\"color:White; font-size:12px;background-color:black;font-weight:normal\"><br/>  info@elsecode.com </span>\n\t\t<span style=\"color:White; font-size:10px;background-color:black;font-weight:normal\"></span>\n\t</p>\n<html>");
        pnl_main.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        getContentPane().add(pnl_main);
        pnl_main.setBounds(8, 11, 730, 730);

        btnDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiscount.setText("Discount");
        btnDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscountActionPerformed(evt);
            }
        });
        getContentPane().add(btnDiscount);
        btnDiscount.setBounds(760, 570, 120, 43);

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(lblUser);
        lblUser.setBounds(1265, 624, 228, 0);

        lblUserName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(0, 153, 153));
        lblUserName.setText("UserName");
        getContentPane().add(lblUserName);
        lblUserName.setBounds(1070, 10, 174, 17);

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

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(1270, 180, 10, 364);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("User:");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(1030, 10, 36, 17);

        btnDueBill.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDueBill.setText("Due Bill");
        btnDueBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDueBillActionPerformed(evt);
            }
        });
        getContentPane().add(btnDueBill);
        btnDueBill.setBounds(760, 620, 110, 41);

        btnTokenPrint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTokenPrint.setText("Print + Token");
        btnTokenPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTokenPrintActionPerformed(evt);
            }
        });
        getContentPane().add(btnTokenPrint);
        btnTokenPrint.setBounds(1110, 620, 160, 41);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Cash Paid:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(750, 120, 95, 28);

        cmbItems.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cmbItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemsActionPerformed(evt);
            }
        });
        getContentPane().add(cmbItems);
        cmbItems.setBounds(750, 60, 230, 30);

        btnMinus.setBackground(new java.awt.Color(204, 0, 0));
        btnMinus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMinus.setForeground(new java.awt.Color(255, 255, 255));
        btnMinus.setText("-");
        btnMinus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnMinus.setPreferredSize(new java.awt.Dimension(40, 31));
        btnMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusActionPerformed(evt);
            }
        });
        getContentPane().add(btnMinus);
        btnMinus.setBounds(990, 60, 50, 31);

        tvCounter.setEditable(false);
        tvCounter.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tvCounter.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tvCounter.setText("0");
        tvCounter.setToolTipText("");
        tvCounter.setPreferredSize(new java.awt.Dimension(20, 30));
        getContentPane().add(tvCounter);
        tvCounter.setBounds(1040, 60, 40, 31);

        btnPlus.setBackground(new java.awt.Color(0, 153, 0));
        btnPlus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPlus.setForeground(new java.awt.Color(255, 255, 255));
        btnPlus.setText("+");
        btnPlus.setToolTipText("");
        btnPlus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPlus.setPreferredSize(new java.awt.Dimension(40, 31));
        btnPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusActionPerformed(evt);
            }
        });
        getContentPane().add(btnPlus);
        btnPlus.setBounds(1080, 60, 50, 31);

        btnAddItem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddItem.setText("Add");
        btnAddItem.setActionCommand("btnAddItem");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddItem);
        btnAddItem.setBounds(1170, 60, 80, 31);

        menuFile.setBorder(null);
        menuFile.setText("     File   ");
        menuFile.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuRefresh.setForeground(new java.awt.Color(0, 153, 102));
        menuRefresh.setText("Refresh");
        menuRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRefreshActionPerformed(evt);
            }
        });
        menuFile.add(menuRefresh);

        menuLogoutExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuLogoutExit.setForeground(new java.awt.Color(153, 0, 0));
        menuLogoutExit.setText("Log Out / Exit");
        menuLogoutExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutExitActionPerformed(evt);
            }
        });
        menuFile.add(menuLogoutExit);

        jMenuBar1.add(menuFile);

        jMenu3.setBackground(new java.awt.Color(153, 153, 255));
        jMenu3.setBorder(null);
        jMenu3.setText("   Sells Frame");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuSellsFrame.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuSellsFrame.setText("New Sells Frame");
        menuSellsFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSellsFrameActionPerformed(evt);
            }
        });
        jMenu3.add(menuSellsFrame);

        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem7.setText("Search Bill");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        menuZeroBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuZeroBill.setText("Zero Bill");
        menuZeroBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZeroBillActionPerformed(evt);
            }
        });
        jMenu3.add(menuZeroBill);

        jMenuBar1.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(153, 153, 255));
        jMenu4.setBorder(null);
        jMenu4.setText("   DashBoard");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuDateWise.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuDateWise.setText("Dashboard Today");
        menuDateWise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDateWiseActionPerformed(evt);
            }
        });
        jMenu4.add(menuDateWise);

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem6.setText("Category Wise Items");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        menuNotification.setText("    Notification");
        menuNotification.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem2.setText("Show All");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuNotification.add(jMenuItem2);

        jMenuBar1.add(menuNotification);

        jMenu2.setText("    Salary   ");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuGiveAdvance.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuGiveAdvance.setText("Give Advance");
        menuGiveAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGiveAdvanceActionPerformed(evt);
            }
        });
        jMenu2.add(menuGiveAdvance);

        jMenuBar1.add(jMenu2);

        menuDueBill.setText("   Due Bill  ");
        menuDueBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem10.setText("Add Due Bill");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        menuDueBill.add(jMenuItem10);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem9.setText("Search/Delete Due Bill");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        menuDueBill.add(jMenuItem9);

        jMenuBar1.add(menuDueBill);

        jMenu6.setText("   Card Payment");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuAddCardBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuAddCardBill.setText("Add Bill");
        menuAddCardBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddCardBillActionPerformed(evt);
            }
        });
        jMenu6.add(menuAddCardBill);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        // TODO add your handling code here:
        Double vatTK = 0.0, grossTP = 0.0;

        int Selectedrow, j, quantity = 0, cat = 0;
        String data[][] = new String[20][20];
        String data1[][] = new String[20][20];
        Selectedrow = jTable1.getSelectedRow();
        String name;

        if (RowNo == 0 || Selectedrow == -1 || (RowNo - 1) < Selectedrow) {
            JOptionPane.showMessageDialog(this, "No data Selected");
        } else {
            cat = Integer.parseInt(jTable2.getValueAt(Selectedrow, 0).toString());
            quantity = Integer.parseInt(jTable1.getValueAt(Selectedrow, 1).toString());
            name = String.valueOf(jTable1.getValueAt(Selectedrow, 2));
            for (int i = 0; i <= RowNo; i++) {
                data[i][0] = String.valueOf(jTable1.getValueAt(i, 0));
                data[i][1] = String.valueOf(jTable1.getValueAt(i, 1));
                data[i][2] = String.valueOf(jTable1.getValueAt(i, 2));
                data[i][3] = String.valueOf(jTable1.getValueAt(i, 3));
                data[i][4] = String.valueOf(jTable1.getValueAt(i, 4));
            }

            for (int i = 0; i < RowNo; i++) {
                if (i >= Selectedrow) {
                    data1[i][0] = data[i + 1][0];
                    data1[i][1] = data[i + 1][1];
                    data1[i][2] = data[i + 1][2];
                    data1[i][3] = data[i + 1][3];
                    data1[i][4] = data[i + 1][4];
                } else {
                    data1[i][0] = data[i][0];
                    data1[i][1] = data[i][1];
                    data1[i][2] = data[i][2];
                    data1[i][3] = data[i][3];
                    data1[i][4] = data[i][4];
                }
            }

            for (int k = 0; k < jTable1.getRowCount(); k++) {
                jTable1.setValueAt("", k, 0);
                jTable1.setValueAt("", k, 1);
                jTable1.setValueAt("", k, 2);
                jTable1.setValueAt("", k, 3);
                jTable1.setValueAt("", k, 4);
            }
            double p, Tprice = 0.0;
            for (j = 0; j < RowNo - 1; j++) {
                jTable1.setValueAt(j, j, 0);
                jTable1.setValueAt(Integer.parseInt(data1[j][1]), j, 1);
                jTable1.setValueAt(data1[j][2], j, 2);
                jTable1.setValueAt(data1[j][3], j, 3);
                jTable1.setValueAt(data1[j][4], j, 4);
                p = Double.parseDouble(data1[j][4]);
                Tprice += p;
                //System.out.println(data1[j][0]+","+data1[j][1]+","+data1[j][2]+","+data1[j][3]+","+data1[j][4]+"\n");
            }
            RowNo -= 1;
            jTable1.setValueAt(" ", RowNo, 0);
            jTable1.setValueAt(" ", RowNo, 1);
            jTable1.setValueAt(" ", RowNo, 2);
            jTable1.setValueAt("Total =", RowNo, 3);
            jTable1.setValueAt(String.valueOf(Tprice), RowNo, 4);

            AddItem_Quantity(name, quantity, cat);
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
            if (discount) {
                jTable1.setValueAt(" ", RowNo + 1, 0);
                jTable1.setValueAt(" ", RowNo + 1, 1);
                jTable1.setValueAt(" ", RowNo + 1, 2);
                jTable1.setValueAt("Discount=", RowNo + 1, 3);
                jTable1.setValueAt(discountTK, RowNo + 1, 4);

                //gross total
                double discountT = Double.parseDouble(discountTK);
                grossTP = Tprice - discountT;
                jTable1.setValueAt(" ", RowNo + 2, 0);
                jTable1.setValueAt(" ", RowNo + 2, 1);
                jTable1.setValueAt(" ", RowNo + 2, 2);
                jTable1.setValueAt("Total =", RowNo + 2, 3);
                jTable1.setValueAt(grossTP, RowNo + 2, 4);

                txtTotalPrice.setText(String.valueOf(grossTP));
            }

            txtChange.setText("0.00");

            cash_paid = false;
            if (RowNo == 0) {
                for (int k = 0; k < jTable1.getRowCount(); k++) {
                    jTable1.setValueAt("", k, 0);
                    jTable1.setValueAt("", k, 1);
                    jTable1.setValueAt("", k, 2);
                    jTable1.setValueAt("", k, 3);
                    jTable1.setValueAt("", k, 4);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void txtCashPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCashPaidActionPerformed
        // TODO add your handling code here:
        if (txtCashPaid.getText().equals("0") || txtCashPaid.getText().isEmpty()) {
            cash_paid = false;
        } else {
            int row = RowNo;
            cash_paid = true;
            if (addVAT) {
                row = RowNo + 2;
            } else if (discount) {
                row = RowNo + 2;
            }
            if (addVAT && discount) {
                row = RowNo + 4;
            }
            String s = String.valueOf(jTable1.getValueAt(row, 4));
            double TP = Double.parseDouble(s);
            double PA = Double.parseDouble(txtCashPaid.getText());
            double CA = PA - TP;
            jTable1.setValueAt(" ", row + 1, 0);
            jTable1.setValueAt(" ", row + 1, 1);
            jTable1.setValueAt(" ", row + 1, 2);
            jTable1.setValueAt("Paid  =", row + 1, 3);
            jTable1.setValueAt(String.valueOf(PA), row + 1, 4);
            jTable1.setValueAt(" ", row + 2, 0);
            jTable1.setValueAt(" ", row + 2, 1);
            jTable1.setValueAt(" ", row + 2, 2);
            jTable1.setValueAt("Change=", row + 2, 3);
            jTable1.setValueAt(String.valueOf(CA), row + 2, 4);
            txtChange.setText(String.valueOf(CA));
        }
    }//GEN-LAST:event_txtCashPaidActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        Double vatTK = 0.0, grossTP = 0.0;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int r = jTable1.getSelectedRow(), qt, n = RowNo, row = 0, serial = 0, cat_id = 0, quantity = 0;
            //if(cash_paid)
            //{
            //n=RowNo - 2; 
            if (r < n) {
                row = RowNo;
                String name = String.valueOf(jTable1.getValueAt(r, 2));
                String s = String.valueOf(jTable1.getValueAt(r, 3)), sq = String.valueOf(jTable1.getValueAt(r, 1));
                qt = Integer.parseInt(sq);
                cat_id = Integer.parseInt(String.valueOf(jTable2.getValueAt(r, 0)));
                quantity = qt - update_quantity;
                //JOptionPane.showMessageDialog(this, "cat ="+cat_id+" q="+quantity);

                int rtnValue = DeleteItem_Quantity(name, cat_id, quantity);
                if (rtnValue == -1) {
                    double up = Double.parseDouble(s), TP = 0.0;

                    jTable1.setValueAt(up * qt, r, 4);

                    int k = jTable1.getRowCount(), j;
                    for (j = 0; j < n; j++) {
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
                    txtTotalPrice.setText(String.valueOf(TP));

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
                    if (addVAT) {
                        //gross total
                        vatTK = (InventoryPOS.vat * TP) / 100;
                        DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
                        double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                        grossTP = TP + dd2dec;
                        jTable1.setValueAt(" ", RowNo + 1, 0);
                        jTable1.setValueAt(" ", RowNo + 1, 1);
                        jTable1.setValueAt(" ", RowNo + 1, 2);
                        jTable1.setValueAt("Vat   =", RowNo + 1, 3);
                        jTable1.setValueAt(dd2dec, RowNo + 1, 4);

                        jTable1.setValueAt(" ", RowNo + 2, 0);
                        jTable1.setValueAt(" ", RowNo + 2, 1);
                        jTable1.setValueAt(" ", RowNo + 2, 2);
                        jTable1.setValueAt("Total =", RowNo + 2, 3);
                        jTable1.setValueAt(grossTP, RowNo + 2, 4);

                        txtTotalPrice.setText(String.valueOf(grossTP));
                    } //for discount
                    else if (discount) {
                        //if discount is enable
                        jTable1.setValueAt(" ", n + 1, 0);
                        jTable1.setValueAt(" ", n + 1, 1);
                        jTable1.setValueAt(" ", n + 1, 2);
                        jTable1.setValueAt("Less(-)=", n + 1, 3);
                        jTable1.setValueAt(discountTK, n + 1, 4);

                        //gross total
                        double discountT = Double.parseDouble(discountTK);
                        grossTP = TP - discountT;
                        jTable1.setValueAt(" ", n + 2, 0);
                        jTable1.setValueAt(" ", n + 2, 1);
                        jTable1.setValueAt(" ", n + 2, 2);
                        jTable1.setValueAt("Total =", n + 2, 3);
                        jTable1.setValueAt(grossTP, n + 2, 4);
                        txtTotalPrice.setText(String.valueOf(grossTP));

                    } else if (cash_paid) {
                        String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                        double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                        double CA = PA - grossTP;
                        jTable1.setValueAt(" ", n + 1, 0);
                        jTable1.setValueAt(" ", n + 1, 1);
                        jTable1.setValueAt(" ", n + 1, 2);
                        jTable1.setValueAt("Paid =", n + 1, 3);
                        jTable1.setValueAt(String.valueOf(PA), n + 1, 4);
                        jTable1.setValueAt(" ", n + 2, 0);
                        jTable1.setValueAt(" ", n + 2, 1);
                        jTable1.setValueAt(" ", n + 2, 2);
                        jTable1.setValueAt("Change =", n + 2, 3);
                        jTable1.setValueAt(String.valueOf(CA), n + 2, 4);
                        txtChange.setText(String.valueOf(CA));
                    } else if (addVAT && cash_paid) {
                        //for vat
                        vatTK = (InventoryPOS.vat * TP) / 100;
                        DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
                        double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                        grossTP = TP + dd2dec;
                        //for vat 7.5%
                        jTable1.setValueAt(" ", RowNo + 1, 0);
                        jTable1.setValueAt(" ", RowNo + 1, 1);
                        jTable1.setValueAt(" ", RowNo + 1, 2);
                        jTable1.setValueAt("Vat   =", RowNo + 1, 3);
                        jTable1.setValueAt(dd2dec, RowNo + 1, 4);

                        jTable1.setValueAt(" ", RowNo + 2, 0);
                        jTable1.setValueAt(" ", RowNo + 2, 1);
                        jTable1.setValueAt(" ", RowNo + 2, 2);
                        jTable1.setValueAt("Total =", RowNo + 2, 3);
                        jTable1.setValueAt(grossTP, RowNo + 2, 4);

                        txtTotalPrice.setText(String.valueOf(grossTP));

                        String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                        double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                        double CA = PA - grossTP;
                        jTable1.setValueAt(" ", n + 3, 0);
                        jTable1.setValueAt(" ", n + 3, 1);
                        jTable1.setValueAt(" ", n + 3, 2);
                        jTable1.setValueAt("Paid =", n + 3, 3);
                        jTable1.setValueAt(String.valueOf(PA), n + 3, 4);
                        jTable1.setValueAt(" ", n + 4, 0);
                        jTable1.setValueAt(" ", n + 4, 1);
                        jTable1.setValueAt(" ", n + 4, 2);
                        jTable1.setValueAt("Change =", n + 4, 3);
                        jTable1.setValueAt(String.valueOf(CA), n + 4, 4);
                        txtChange.setText(String.valueOf(CA));
                    } else if (addVAT && discount) {
                        //for vat
                        vatTK = (InventoryPOS.vat * TP) / 100;
                        DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
                        double dd2dec = new Double(df2.format(vatTK)).doubleValue();

                        grossTP = TP + dd2dec;
                        //for vat 7.5%
                        jTable1.setValueAt(" ", RowNo + 1, 0);
                        jTable1.setValueAt(" ", RowNo + 1, 1);
                        jTable1.setValueAt(" ", RowNo + 1, 2);
                        jTable1.setValueAt("Vat   =", RowNo + 1, 3);
                        jTable1.setValueAt(dd2dec, RowNo + 1, 4);

                        jTable1.setValueAt(" ", RowNo + 2, 0);
                        jTable1.setValueAt(" ", RowNo + 2, 1);
                        jTable1.setValueAt(" ", RowNo + 2, 2);
                        jTable1.setValueAt("Total =", RowNo + 2, 3);
                        jTable1.setValueAt(grossTP, RowNo + 2, 4);

                        //if discount is enable
                        jTable1.setValueAt(" ", n + 3, 0);
                        jTable1.setValueAt(" ", n + 3, 1);
                        jTable1.setValueAt(" ", n + 3, 2);
                        jTable1.setValueAt("Less(-)=", n + 3, 3);
                        jTable1.setValueAt(discountTK, n + 3, 4);

                        //gross total
                        double discountT = Double.parseDouble(discountTK);
                        grossTP = TP - discountT;
                        jTable1.setValueAt(" ", n + 4, 0);
                        jTable1.setValueAt(" ", n + 4, 1);
                        jTable1.setValueAt(" ", n + 4, 2);
                        jTable1.setValueAt("Total =", n + 4, 3);
                        jTable1.setValueAt(grossTP, n + 4, 4);
                        txtTotalPrice.setText(String.valueOf(grossTP));

                        String s1 = String.valueOf(jTable1.getValueAt(n, 4));
                        double TP1 = Double.parseDouble(s1), PA = Double.parseDouble(txtCashPaid.getText());
                        double CA = PA - grossTP;
                        jTable1.setValueAt(" ", n + 5, 0);
                        jTable1.setValueAt(" ", n + 5, 1);
                        jTable1.setValueAt(" ", n + 5, 2);
                        jTable1.setValueAt("Paid =", n + 5, 3);
                        jTable1.setValueAt(String.valueOf(PA), n + 5, 4);
                        jTable1.setValueAt(" ", n + 6, 0);
                        jTable1.setValueAt(" ", n + 6, 1);
                        jTable1.setValueAt(" ", n + 6, 2);
                        jTable1.setValueAt("Change =", n + 6, 3);
                        jTable1.setValueAt(String.valueOf(CA), n + 6, 4);
                        txtChange.setText(String.valueOf(CA));
                    }
                } else {
                    jTable1.setValueAt(update_quantity, r, 1);
                }

            } else {
                JOptionPane.showMessageDialog(this, "No data to change.");
            }

        }
        //////////////////////////////////////////
    }//GEN-LAST:event_jTable1KeyPressed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:

        if (jTable1.getValueAt(0, 0) != null && RowNo != 0) {
            //System.out.println("RowNo = "+RowNo);
            if (!boolDue) {
                JTextField amountField = new JTextField(7);
                JTextField nameField = new JTextField(15);
                JButton btnNewCustomer = new JButton();

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Amount:"));
                amountField.setEditable(false);
                myPanel.add(amountField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer

//      myPanel.add(new JLabel("Name:"));
//      myPanel.add(nameField);
//      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                //List<String> listCustomerName = DB_Utilities.getListOfColumnValues("Select * from inv_item",connection, 3);
                List<InvCustomer> invItemList = null;
                invItemList = CustomerDBHelper.getListOfCustomers(connection);
                Vector comboBoxItems = new Vector();
                for (InvCustomer item : invItemList) {
                    System.out.println("Customer= " + item.getCustomerId() + ", " + item.getCustomerName());
                    comboBoxItems.addElement(new Item(item.getCustomerId(), item.getCustomerName()));
                }
                final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
                JComboBox comboBox = new JComboBox(model);
                AutoCompleteDecorator.decorate(comboBox);
                myPanel.add(comboBox);

                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                btnNewCustomer.setText("New Customer");
                myPanel.add(btnNewCustomer);
                btnNewCustomer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new Add_Customer().setVisible(true);
                    }
                });

                amountField.setText(txtTotalPrice.getText());
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Select The Person's Name Otherwise Register New Customer", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    //PrintBill(0);
                    Item item = (Item) comboBox.getSelectedItem();

                    //billDueAmount = Double.parseDouble(amountField.getText().toString());
                    billDueCustomerId = item.getId();
                    billDueCustomerName = item.getDescription();

                    PrintBill(0);
                }
            } else {
//                billDueCustomerId = -1;
//                billDueCustomerName = "";
                PrintBill(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Item To Print!", "Warning", 2);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void PrintBill(int Print0Token1) {

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
        String Time = "Date:" + formatter.format(date);
        System.out.println("RowNo =" + RowNo);

        String smsText = "", htmlString = "";

        String Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
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
            size = size + 2;
        }
        if (discount) {
            size = size + 2;
        }
        if (boolDue) {
            size = size + 4;
        }

        if (Print0Token1 == 0) {
            GetReceiptNo();
        }
        int receiptNo = _receiptNo;

        smsText += "Bill# " + receiptNo + ", ";
//        if (addVAT) {
//            size = RowNo + 2;
//        } else if (discount) {
//            size = RowNo + 2;
//        } else if (cash_paid) {
//            size = RowNo + 2;
//        }
//        if (addVAT && discount) {
//            size = RowNo + 4;
//        }
//        if (addVAT && cash_paid) {
//            size = RowNo + 4;
//        }
//        if (discount && cash_paid) {
//            size = RowNo + 4;
//        }
        System.out.println("Hajjaz Table size before getting data: " + size);
        System.out.println("Hajjaz RowNo before getting data: " + RowNo);
        for (int i = 0; i <= size; i++) {

            if ((s = String.valueOf(jTable1.getValueAt(i, 1))) != null) {
                S[i + 1][0] = s;
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 2))) != null) {
                S[i + 1][1] = s;
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 3))) != null) {
                S[i + 1][2] = s;
                if (i >= RowNo) {
                    if (i == RowNo) {
                        smsText += "Total Purchase = " ;
                    } else {
                        smsText += s + " ";
                    }
                }
            }
            if ((s = String.valueOf(jTable1.getValueAt(i, 4))) != null) {
                S[i + 1][3] = s;
                if (i >= RowNo) {
                    smsText += s + ", ";
                }
            }
        }

        smsText = smsText.substring(0, (smsText.length() - 2));
        System.out.println("Hajjaz smsText= " + smsText);

        //for receipt number
        Date time1 = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat(" dd-MMM-yyyy ");
        String date1 = formatter1.format(time1);

//        _receiptNo = receiptNo;
        DateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        String Time1 = formatter2.format(date);

        try {
            FileWriter filewriter = new FileWriter(InventoryPOS.printFileDirectory + "\\" + "SellBill" + Time1 + InventoryPOS.printBillFileName);
            PrintWriter out = new PrintWriter(filewriter);

            out.println("           " + InventoryPOS.companyName);
            out.println("  ");
            out.println("   " + InventoryPOS.strBillCompanyAddress1);
            out.println("   " + InventoryPOS.strBillCompanyAddress2);
            //out.println("  "+CAddress3);
            out.println("   " + InventoryPOS.strVatRegNo + " Receipt# " + receiptNo);
            //out.println("   "+"Receipt# "+receiptNo);
            out.println("   " + Time);
            //out.println("  "+"   ");
            out.println(line);
            out.println(S[0][0] + " " + S[0][1] + "         " + S[0][2] + "   " + S[0][3]);
            out.println(line);
            for (int j = 1; j <= size + 1; j++) {
                out.println(S[j][0] + AddSpace(4, S[j][0].length()) + S[j][1] + AddSpace(20, S[j][1].length()) + S[j][2]
                        + AddSpace(8, S[j][2].length()) + S[j][3]);
            }

            out.println("  " + "   ");
            //out.println("  "+"   ");
            //out.println("  "+"   ");
            out.println("       " + InventoryPOS.strBillMessage1);
            out.println("  " + InventoryPOS.strContactInfo);
            out.println("       " + InventoryPOS.strElseCode);
            out.println(" " + blank);
            out.println(" " + " ");
            out.println(" " + " ");
            out.println(" " + " ");
            out.println();

            out.close();
        } catch (IOException ex) {

        }
        //print the bill file
//        Desktop desktop = Desktop.getDesktop();
//        try {
//            desktop.print(new File("E:/POS/bill.txt"));
//        } catch (IOException ex) {
//            Logger.getLogger(SellsFrame_Modified.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (InventoryPOS.printPageSizeBig) {
            printCard();
        } else if (InventoryPOS.printPageSizeSmall) {
            printCard_Small();
        } else if (InventoryPOS.printPageSizeInvoice) {
            htmlString = HTMLGenerator.GenerateHTMLFile(receiptNo, S, size, RowNo, billDueCustomerName, CustomerDBHelper.getCustomerAddress(billDueCustomerId, connection));
        }

        int catID = 0, rowno = RowNo;

        String tran = "", category = "";
        for (int t = 1; t <= rowno; t++) {
            catID = Integer.parseInt(jTable2.getValueAt(t - 1, 0).toString());
            category = getCategory_Name(catID);
            //
            tran += S[t][0] + ", " + S[t][1] + ", " + S[t][3] + "\n";
            //JOptionPane.showMessageDialog(rootPane, tran);
            AddTransactionData(S[t][0], S[t][1], S[t][3], category, receiptNo);
        }

        if (addVAT) {
            AddVAT(vatTK, receiptNo);
            vatTK = 0.0;
        }

        if (discount) {
            double dtk = Double.parseDouble(discountTK);
            //insert into discount table
            AddDiscount(dtk, receiptNo, billDueCustomerId);
        }

        if (boolDue) {
            Add_BillDue(billDueCustomerName, String.valueOf(billDueAmount), _receiptNo, date1, billDueCustomerId);
            Add_Due_Bill_For_Dashboard(billDueCustomerName, String.valueOf(billDueAmount), _receiptNo, date1, billDueCustomerId);
            addDueAmountToCustomerTable(billDueCustomerId, billDueAmount);
        }
        //JOptionPane.showMessageDialog(rootPane, tran);

        setSerial(receiptNo, date1);

        //Send SMS
        if (InventoryPOS.sendSMS) {
            String phone = CustomerDBHelper.getCustomerPhone(billDueCustomerId, connection);
            SMSSender.sendSMS(phone, smsText);
        }

        //Send Email
        if (InventoryPOS.sendEmail) {
            if (!htmlString.equals("")) {
                String email = CustomerDBHelper.getCustomerEmail(billDueCustomerId, connection);
                EmailUtill.sendMail(email, "Invoice Created #" + receiptNo, htmlString);
            }
        }

        //write activity log
        CustomerDBHelper.setCustomerActivity(billDueCustomerId, String.valueOf(receiptNo), txtTotalPrice.getText(), String.valueOf(billDueAmount), discountTK, "", connection);
        DatabaseUtils.WriteActivityLog(connection, Utilities.UserName, "Print Bill. bill_date = " + date1 + " amount = " + txtTotalPrice.getText() + ", billNo = " + receiptNo + "", Retailer_SellsFrame.class.getSimpleName() + ", btnPrintActionPerformed()");

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
        boolDue = false;
        billDueAmount = 0.0;
        discountTK = "0";

    }

    private void GetReceiptNo() {
        //for receipt number
        Date time1 = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat(" dd-MMM-yyyy ");
        String date1 = formatter1.format(time1);
        int receiptNo = getSerial(date1);
        _receiptNo = receiptNo;

    }

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
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
                String Time = "Date: " + formatter.format(date);
                System.out.println("RowNo =" + RowNo);

                String RNo = "Receipt# " + _receiptNo, Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                        HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.";
                String line = "----------------------------------",
                        CName = "New Shad Restora", CAddress1 = "Plot# 35,Sonargaon Janapath Road,", CAddress2 = "Sector# 12, Uttara, Dhaka 1230";
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
                    System.out.println("S[" + i + "] = " + S[i][0]);
                }
                int h = 1, height = 0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(InventoryPOS.companyName, 35, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 8));
                g.drawString(InventoryPOS.strBillCompanyAddress1, 20, 35);
                g.drawString(InventoryPOS.strBillCompanyAddress2, 20, 45);
                g.drawString(InventoryPOS.strVatRegNo + "  " + RNo, 20, 60);
                g.drawString(Time, 20, 70);
                for (int j = 0; j <= size + 1; j++) {
                    if (j == 0) {
                        h = 1;
                        g.drawString(line, 15, 80);
                        g.drawString(S[j][0], 20, 90 * h);
                        g.drawString(S[j][1], 40, 90 * h);
                        g.drawString(S[j][2], 115, 90 * h);
                        g.drawString(S[j][3], 150, 90 * h);
                        g.drawString(line, 15, 100);
                        height = 100;
                    } else if (j >= 1) {
                        h = j + 1;
                        g.drawString(S[j][0], 20, height + 10);
                        g.drawString(S[j][1], 40, height + 10);
                        g.drawString(S[j][2], 115, height + 10);
                        g.drawString(S[j][3], 150, height + 10);
                        height += 10;
                    }
                }
                g.drawString(InventoryPOS.strBillMessage1, 20, height + 15);
                g.drawString(InventoryPOS.strContactInfo, 20, height + 25);
                g.drawString(InventoryPOS.strElseCode, 20, height + 35);
                g.dispose();
                Height += height + 45;
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
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
                String Time = "Date: " + formatter.format(date);
                System.out.println("RowNo =" + RowNo);

                String RNo = "Bill# " + _receiptNo, Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                        HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.";
                String line = "----------------------------------",
                        CName = "New Shad Restora", CAddress1 = "Plot# 35,Sonargaon Janapath Road,", CAddress2 = "Sector# 12, Uttara, Dhaka 1230";
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
                    System.out.println("S[" + i + "] = " + S[i][0]);
                }
                int h = 1, height = 0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(InventoryPOS.companyName, 0, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 7));
                g.drawString(InventoryPOS.strBillCompanyAddress1, 0, 35);
                g.drawString(InventoryPOS.strBillCompanyAddress2, 0, 45);
                g.drawString(InventoryPOS.strVatRegNo + " " + RNo, 0, 60);
                g.drawString(Time, 0, 70);
                for (int j = 0; j <= size + 1; j++) {
                    if (j == 0) {
                        h = 1;
                        g.drawString(line, 0, 80);
                        g.drawString(S[j][0], 0, 90 * h);
                        g.drawString(S[j][1], 15, 90 * h);
                        g.drawString(S[j][2], 80, 90 * h);
                        g.drawString(S[j][3], 110, 90 * h);
                        g.drawString(line, 0, 100);
                        height = 100;
                    } else if (j >= 1) {
                        h = j + 1;
                        g.drawString(S[j][0], 0, height + 10);
                        g.drawString(S[j][1], 15, height + 10);
                        g.drawString(S[j][2], 80, height + 10);
                        g.drawString(S[j][3], 110, height + 10);
                        height += 10;
                    }
                }
                g.drawString(InventoryPOS.strBillMessage1, 0, height + 15);
                g.drawString(InventoryPOS.strContactInfo, 0, height + 25);
                g.drawString(InventoryPOS.strElseCode, 0, height + 35);
                g.dispose();
                Height += height + 45;
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

    private void AddVAT(Double amount, int bill) {
        int billNo = bill;
        //getting system date
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);

        //inserting data into inv_vat table
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO inv_vat (vat_amount, vat_date, bill_no)VALUES (?, ?, ?)");
            statement.setString(1, String.valueOf(amount));
            statement.setString(2, date);
            statement.setString(3, String.valueOf(billNo));

            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error while inserting data!");
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(statement);
        }
    }

    private String AddSpace(int flength, int length) {
        String space = "";
        if ((flength - length) == 1) {
            space = " ";
        } else if ((flength - length) == 2) {
            space = "  ";
        } else if ((flength - length) == 3) {
            space = "   ";
        } else if ((flength - length) == 4) {
            space = "    ";
        } else if ((flength - length) == 5) {
            space = "     ";
        } else if ((flength - length) == 6) {
            space = "      ";
        } else if ((flength - length) == 7) {
            space = "       ";
        } else if ((flength - length) == 8) {
            space = "        ";
        } else if ((flength - length) == 9) {
            space = "         ";
        } else if ((flength - length) == 10) {
            space = "          ";
        } else if ((flength - length) == 11) {
            space = "           ";
        } else if ((flength - length) == 12) {
            space = "            ";
        } else if ((flength - length) == 13) {
            space = "             ";
        } else if ((flength - length) == 14) {
            space = "              ";
        } else if ((flength - length) == 15) {
            space = "               ";
        } else if ((flength - length) == 16) {
            space = "                ";
        } else if ((flength - length) == 17) {
            space = "                 ";
        } else if ((flength - length) == 18) {
            space = "                  ";
        } else if ((flength - length) == 19) {
            space = "                   ";
        }
        return space;
    }

    private void AddTransactionData(String q, String n, String p, String cat, int bill) {
        String name = n, quantity = q, price = p, category = cat;
        int tran_id = 0, tran_vat_id = 0, billNo = bill;

        transaction_id++;

        //getting system date
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp sqlDateObject = new java.sql.Timestamp(calendar.getTime().getTime());
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy HH:mm:ss");
        String date = formatter.format(time);

        //inserting data into transaction table
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO inv_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name, bill_no, customer_id)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, String.valueOf(transaction_id));
            statement.setString(2, name);
            statement.setString(3, quantity);
            statement.setString(4, price);
            statement.setTimestamp(5, sqlDateObject);
            statement.setString(6, category);
            statement.setString(7, String.valueOf(billNo));
            statement.setString(8, String.valueOf(billDueCustomerId));

            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //System.out.println("Error while inserting data!");
            JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
        } finally {
            DatabaseUtils.close(statement);
        }

        if (addVAT) {
            //inserting data into inv_vat_transaction table

            PreparedStatement statement1 = null;
            try {
                statement1 = connection.prepareStatement("INSERT INTO inv_vat_transaction (tran_id, item_name, item_quantity, sell_price, tran_date, cat_name, customer_id)VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement1.setString(1, String.valueOf(transaction_id));
                statement1.setString(2, name);
                statement1.setString(3, quantity);
                statement1.setString(4, price);
                //statement1.setString(5, date);
                statement1.setTimestamp(5, sqlDateObject);
                statement1.setString(6, category);
                statement1.setString(7, String.valueOf(billDueCustomerId));

                statement1.execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("Error while inserting data!");
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            } finally {
                DatabaseUtils.close(statement1);
            }
        }
    }

    private void btnAddTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTaxActionPerformed
        // TODO add your handling code here:
        Double TP = 0.0, grossTP = 0.0;

        String s1 = String.valueOf(jTable1.getValueAt(RowNo, 4));
        TP = Double.parseDouble(s1);

        addVAT = true;
        double vat = InventoryPOS.vat;
        //gross total
        vatTK = (vat * TP) / 100;
        //vatTK = Math.ceil(vatTK);
        DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
        vatTK = new Double(df2.format(vatTK)).doubleValue();

        grossTP = TP + vatTK;

        //for vat 7.5%
        jTable1.setValueAt(" ", RowNo + 1, 0);
        jTable1.setValueAt(" ", RowNo + 1, 1);
        jTable1.setValueAt(" ", RowNo + 1, 2);
        jTable1.setValueAt("Vat   =", RowNo + 1, 3);
        jTable1.setValueAt(vatTK, RowNo + 1, 4);

        jTable1.setValueAt(" ", RowNo + 2, 0);
        jTable1.setValueAt(" ", RowNo + 2, 1);
        jTable1.setValueAt(" ", RowNo + 2, 2);
        jTable1.setValueAt("Total =", RowNo + 2, 3);
        jTable1.setValueAt(grossTP, RowNo + 2, 4);

        txtTotalPrice.setText(String.valueOf(grossTP));
    }//GEN-LAST:event_btnAddTaxActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        if (RowNo > 0) {
            JOptionPane.showMessageDialog(this, "Delete All Items First, Then Exit.");
        } else {
            try {
                connection.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error In Closing Database. Please Ignore it & Click OK.");
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
        new DashBoard_Today().setVisible(true);
    }//GEN-LAST:event_menuDateWiseActionPerformed

    private void menuSellsFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSellsFrameActionPerformed
        // TODO add your handling code here:
        InventoryPOS.ReadSystemProperties();
        new Retailer_SellsFrame().setVisible(true);
    }//GEN-LAST:event_menuSellsFrameActionPerformed

    private void AddDiscount(Double amount, int bill, int customer_id) {
        int billNo = bill;
        //getting system date
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);

        //inserting data into inv_vat table
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO inv_discount (disc_amount, disc_date, bill_no, customer_id)VALUES (?, ?, ?, ?)");
            statement.setString(1, String.valueOf(amount));
            statement.setString(2, date);
            statement.setString(3, String.valueOf(billNo));
            statement.setString(4, String.valueOf(customer_id));

            statement.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error while inserting data!");
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(statement);
        }
    }

    private void btnDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountActionPerformed
        // TODO add your handling code here:
        Double TP = 0.0, grossTP = 0.0, dTk;
        discountTK = "";

        while (discountTK.equals("")) {
            discountTK = JOptionPane.showInputDialog(this, "Enter Amount:", "Discount Amount",
                    JOptionPane.PLAIN_MESSAGE);
        }
        discount = true;
        //if discount is enable
        dTk = Double.parseDouble(discountTK);

        int row = RowNo;
        if (addVAT) {
            row += 2;
        } else {
            row = RowNo;
        }

        String s1 = txtTotalPrice.getText();
        TP = Double.parseDouble(s1);

        jTable1.setValueAt(" ", row + 1, 0);
        jTable1.setValueAt(" ", row + 1, 1);
        jTable1.setValueAt(" ", row + 1, 2);
        jTable1.setValueAt("Discount =", row + 1, 3);
        jTable1.setValueAt(dTk, row + 1, 4);

        //gross total
        //double discountT = Double.parseDouble(discountTK);
        grossTP = TP - dTk;
        jTable1.setValueAt(" ", row + 2, 0);
        jTable1.setValueAt(" ", row + 2, 1);
        jTable1.setValueAt(" ", row + 2, 2);
        jTable1.setValueAt("Total =", row + 2, 3);
        jTable1.setValueAt(grossTP, row + 2, 4);
        txtTotalPrice.setText(String.valueOf(grossTP));

        //write activity log
        DatabaseUtils.WriteActivityLog(connection, Utilities.UserName, "Add Discount. Aount = " + dTk + ", billNo = " + _receiptNo + "", Retailer_SellsFrame.class.getSimpleName() + ", btnDiscountActionPerformed()");

    }//GEN-LAST:event_btnDiscountActionPerformed

    private void menuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRefreshActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        InventoryPOS.ReadSystemProperties();
        new Retailer_SellsFrame().setVisible(true);

    }//GEN-LAST:event_menuRefreshActionPerformed

    private void menuLogoutExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutExitActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Runtime.getRuntime().exit(1);
    }//GEN-LAST:event_menuLogoutExitActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (RowNo > 0) {
            int row = jTable1.getSelectedRow();
            update_quantity = Integer.parseInt(String.valueOf(jTable1.getValueAt(row, 1)));
        }
        //JOptionPane.showMessageDialog(this, "Quantity = "+ update_quantity);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new Notification().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuGiveAdvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGiveAdvanceActionPerformed
        // TODO add your handling code here:
        new Employee_Advance_Salary().setVisible(true);
    }//GEN-LAST:event_menuGiveAdvanceActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        new DashBoard_Category_For_All().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        new Search_Bill().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowClosing

    private void btnDueBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDueBillActionPerformed
        // TODO add your handling code here:
        JTextField amountField = new JTextField(7);
        JTextField nameField = new JTextField(15);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Due Amount:"));
        myPanel.add(amountField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer

//      myPanel.add(new JLabel("Name:"));
//      myPanel.add(nameField);
//      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        //List<String> listCustomerName = DB_Utilities.getListOfColumnValues("Select * from inv_item",connection, 3);
        List<InvCustomer> invItemList = CustomerDBHelper.getListOfCustomers(connection);
        Vector comboBoxItems = new Vector();
        for (InvCustomer item : invItemList) {
            System.out.println("Customer= " + item.getCustomerId() + ", " + item.getCustomerName());
            comboBoxItems.addElement(new Item(item.getCustomerId(), item.getCustomerName()));
        }
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
        JComboBox comboBox = new JComboBox(model);
        AutoCompleteDecorator.decorate(comboBox);
        myPanel.add(comboBox);

        amountField.setText(txtTotalPrice.getText());
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter The Person's Name & Amount(if Required)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            //PrintBill(0);
            Item item = (Item) comboBox.getSelectedItem();

            billDueAmount = Double.parseDouble(amountField.getText().toString());
            billDueCustomerId = item.getId();
            billDueCustomerName = item.getDescription();

            Double TP = 0.0, grossTP = 0.0, dTk;

            boolDue = true;
            //if discount is enable
            dTk = billDueAmount;

            int row = RowNo;
            System.out.println("DiscountBill>> RowNo: " + RowNo);
            if (addVAT) {
                row += 2;
                System.out.println("addVAT row = " + row);
            }
            if (discount) {
                row += 2;
                System.out.println("discount row = " + row);
            }
            if (!addVAT && !discount) {
                row = RowNo;
                System.out.println("row = " + row);
            }

            String s1 = txtTotalPrice.getText();
            TP = Double.parseDouble(s1);
            System.out.println("DiscountBill>> TotalPrice: " + TP);

            System.out.println("DiscountBill>> row = " + row);
            
            grossTP = TP - dTk;
            jTable1.setValueAt(" ", row + 1, 0);
            jTable1.setValueAt(" ", row + 1, 1);
            jTable1.setValueAt(" ", row + 1, 2);
            jTable1.setValueAt("Pay = ", row + 1, 3);
            jTable1.setValueAt(grossTP, row + 1, 4);

            //gross total
            //double discountT = Double.parseDouble(discountTK);
            
            jTable1.setValueAt(" ", row + 2, 0);
            jTable1.setValueAt(" ", row + 2, 1);
            jTable1.setValueAt(" ", row + 2, 2);
            jTable1.setValueAt("Due =", row + 2, 3);
            jTable1.setValueAt(dTk, row + 2, 4);
            txtTotalPrice.setText(String.valueOf(grossTP));

            Double prevDueAmount = CustomerDBHelper.getDueAmountOfCustomer(item.getId(), connection);
            Double currentDueAmount = prevDueAmount + billDueAmount;
            jTable1.setValueAt(" ", row + 3, 0);
            jTable1.setValueAt(" ", row + 3, 1);
            jTable1.setValueAt(" ", row + 3, 2);
            jTable1.setValueAt("Previous Due =", row + 3, 3);
            jTable1.setValueAt(prevDueAmount, row + 3, 4);

            jTable1.setValueAt(" ", row + 4, 0);
            jTable1.setValueAt(" ", row + 4, 1);
            jTable1.setValueAt(" ", row + 4, 2);
            jTable1.setValueAt("Current Due =", row + 4, 3);
            jTable1.setValueAt(currentDueAmount, row + 4, 4);

//            Date time = Calendar.getInstance().getTime();
//            DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
//            String date = formatter.format(time);
//            
//            
//            
//            String amount = amountField.getText(), name = item.getDescription();
//
            //write activity log
            DatabaseUtils.WriteActivityLog(connection, Utilities.UserName, "Add Due Bill. CustomerName = " + item.getDescription() + " bill_date = " + date + " amount = " + billDueAmount + ", billNo = " + _receiptNo + "", Retailer_SellsFrame.class.getSimpleName() + ", btnDueBillActionPerformed()");
        }
    }//GEN-LAST:event_btnDueBillActionPerformed

    private void Add_BillDue(String name, String amount, int receiptNo, String date, int customer_id) {

        //inserting data into transaction table
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("INSERT INTO bill_due (customerName, amount, serial, bill_date, customer_id)"
                    + "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, amount);
            statement.setString(3, String.valueOf(receiptNo));
            statement.setString(4, date);
            statement.setString(5, String.valueOf(customer_id));

            statement.execute();
            System.out.println(" Result = Success bill_due");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //System.out.println("Error while inserting data!");
            JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
        } finally {
            DatabaseUtils.close(statement);
        }
    }

    private void addDueAmountToCustomerTable(int id, Double amount) {

        Double dueAmount = CustomerDBHelper.getDueAmountOfCustomer(id, connection);
        Double totalDue = dueAmount + amount;
        boolean result = CustomerDBHelper.updateDueAmountOfCustomer(id, totalDue, connection);
        System.out.println(result ? "Successfuly updated." : "Failed to update.");
    }

    private void Add_Due_Bill_For_Dashboard(String name, String amount, int receiptNo, String date, int customer_id) {

        //inserting data into transaction table
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO due_bill_for_dashboard (cusomerName, amount, serial, bill_date, customer_id)"
                    + "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, amount);
            statement.setString(3, String.valueOf(receiptNo));
            statement.setString(4, date);
            statement.setString(5, String.valueOf(customer_id));

            statement.execute();
            System.out.println(" Result = Success for Dashboard");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //System.out.println("Error while inserting data!");
            JOptionPane.showMessageDialog(rootPane, e.toString(), "Error", 1);
        } finally {
            DatabaseUtils.close(statement);
        }
    }

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        new Search_DueBills().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new Add_DueBill().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void btnTokenPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTokenPrintActionPerformed
        // TODO add your handling code here:
        if (jTable1.getValueAt(0, 0) != null && RowNo != 0) {
            //System.out.println("RowNo = "+RowNo);
            GetReceiptNo();
            if (InventoryPOS.printPageSizeBig) {
                printToken();
            } else {
                printToken_Small();
            }
            PrintBill(1);

        } else {
            JOptionPane.showMessageDialog(this, "No Item To Print!", "Warning", 2);
        }

    }//GEN-LAST:event_btnTokenPrintActionPerformed

    private void menuAddCardBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddCardBillActionPerformed
        // TODO add your handling code here:
        new Add_CardPayment().setVisible(true);
    }//GEN-LAST:event_menuAddCardBillActionPerformed

    private void btnPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusActionPerformed
        // TODO add your handling code here:

        Item item = (Item) cmbItems.getSelectedItem();
        int quantity = getQuantity(item.getDescription(), item.getId());
        int value = Integer.valueOf(tvCounter.getText()) + 1;
        if (value > quantity) {
            JOptionPane.showMessageDialog(Retailer_SellsFrame.this, " Not Available Item to Add. Available = " + quantity);
        } else {
            tvCounter.setText(String.valueOf(value));
        }

    }//GEN-LAST:event_btnPlusActionPerformed

    private void btnMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusActionPerformed
        // TODO add your handling code here:
        if (Integer.valueOf(tvCounter.getText()) != 0) {
            int value = Integer.valueOf(tvCounter.getText()) - 1;
            tvCounter.setText(String.valueOf(value));
        }

    }//GEN-LAST:event_btnMinusActionPerformed

    private void cmbItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemsActionPerformed
        // TODO add your handling code here:
        JComboBox comboBox = (JComboBox) evt.getSource();
        Item item = (Item) comboBox.getSelectedItem();
        System.out.println(item.getId() + " : " + item.getDescription());
    }//GEN-LAST:event_cmbItemsActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:
        if (Integer.parseInt(tvCounter.getText()) != 0) {
            Item item = (Item) cmbItems.getSelectedItem();
            addItems(item.getDescription(), item.getId(), Integer.parseInt(tvCounter.getText()));
        } else {
            JOptionPane.showMessageDialog(Retailer_SellsFrame.this, " Not Enough Item to Add.");
        }

    }//GEN-LAST:event_btnAddItemActionPerformed

    private void menuZeroBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZeroBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuZeroBillActionPerformed

    private void printToken() {

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
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
                String Time = "Date: " + formatter.format(date);
                //System.out.println("RowNo ="+RowNo);
                String CName = "New Shad Restora";

                int h = 1, height = 0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 16));
                g.drawString("Token# " + _receiptNo, 50, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(InventoryPOS.companyName, 20, 35);
                g.setFont(new Font("Courier New", Font.BOLD, 9));
                g.drawString(Time, 20, 50);
                g.dispose();
//        _printJob.end();

                return Printable.PAGE_EXISTS;
            }

        };

        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 253, 70);
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

    private void printToken_Small() {

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
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
                String Time = "Date: " + formatter.format(date);
                //System.out.println("RowNo ="+RowNo);
                String CName = "New Shad Restora";

                int h = 1, height = 0;;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 16));
                g.drawString("Token# " + _receiptNo, 20, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(InventoryPOS.companyName, 0, 35);
                g.setFont(new Font("Courier New", Font.BOLD, 8));
                g.drawString(Time, 0, 50);
                g.dispose();
//        _printJob.end();

                return Printable.PAGE_EXISTS;
            }

        };

        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 253, 70);
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

    private static class JobCompleteMonitor extends PrintJobAdapter {

        @Override
        public void printJobCompleted(PrintJobEvent jobEvent) {
            System.out.println("Job completed");
            jobRunning = false;
        }
    }
    //@Override

    public class Item {

        private int id;
        private String description;

        public Item(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String toString() {
            return description;
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
            java.util.logging.Logger.getLogger(Retailer_SellsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Retailer_SellsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Retailer_SellsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Retailer_SellsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Retailer_SellsFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnAddTax;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnDiscount;
    private javax.swing.JButton btnDueBill;
    private javax.swing.JButton btnMinus;
    private javax.swing.JButton btnPlus;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnTokenPrint;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_exit;
    private javax.swing.JComboBox<String> cmbItems;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JScrollPane jsc_item;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblElseCode;
    private javax.swing.JLabel lblElseCode1;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JMenuItem menuAddCardBill;
    private javax.swing.JMenuItem menuDateWise;
    private javax.swing.JMenu menuDueBill;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuGiveAdvance;
    private javax.swing.JMenuItem menuLogoutExit;
    private javax.swing.JMenu menuNotification;
    private javax.swing.JMenuItem menuRefresh;
    private javax.swing.JMenuItem menuSellsFrame;
    private javax.swing.JMenuItem menuZeroBill;
    private javax.swing.JPanel pnl_button;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JTextField tvCounter;
    private javax.swing.JTextField txtCashPaid;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

import employee.Saved_SalaryForm;
import employee.New_SalaryForm;
import employee.Update_Delete_Employee;
import employee.AddEmployee;
import employee.Employee_Advance_Salary;
import employee.Advance_Salary_NameWise;
import buy.BuyFrame_StockIn;
import dashboard.DashBoard_Category;
import dashboard.DashBoard;
import dashboard.DashBoard_Item;
import admin.Admin_Add_Category;
import admin.Admin_DeleteUser;
import admin.Admin_Add_FoodItem;
import admin.Admin_Update_Category;
import admin.Admin_Delete_FoodItem;
import admin.Admin_Update_Item;
import admin.Admin_AddUser;
import Utilities.Utilities;
import dashboard.DashBoard_CustomerList;
import dashboard.DashBoard_CustomerWise;
import dashboard.SendSMSNotification;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import reseller.Retailer_SellsFrame;

/**
 *
 * @author Hajjaz Bin Ibrahim
 */
public class AdminForm extends javax.swing.JFrame {

    public DBGateway dbg;
    ;
    Connection connection = null;
    int RowNo, rowIncrementer;
    Double grossTP1;
    int Height = 0;
    boolean cashTKTV = false, expenseTKTV = false;

    /**
     * Creates new form MainForm
     */
    public AdminForm() {
        this.dbg = new DBGateway();
        initComponents();
        connection = dbg.connectionTest();
        initFields();
        ShowReport();
    }

    private void initFields() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String Time = "Date: " + formatter.format(date);

        menuSearchDueBill.setVisible(false);

        lblName.setText(InventoryPOS.companyName);
        lblAddress.setText(InventoryPOS.companyAddress);
        lblDate.setText(Time);
        lblUserName.setText(Utilities.UserName);

        if (InventoryPOS.retailerSellsFrameOn) {
            menuRetailerSellsFrame.setVisible(true);
        } else {
            menuRetailerSellsFrame.setVisible(false);
        }

        customizeTable();
    }

    private void customizeTable() {
        final Color colorSkyBlue = new Color(135, 206, 235);
        JTableHeader header = jTable2.getTableHeader();
        header.setBackground(colorSkyBlue);
        header.setForeground(Color.WHITE);

        jTable2.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
            Color alternateColor = new Color(216, 236, 249);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(colorSkyBlue);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);

                    } else {
                        c.setBackground(alternateColor);
                    }
                }
                return c;
            }
        });
    }

    public void ShowReport() {

        for (int i = 0; i < jTable2.getRowCount(); i++) {
            for (int j = 0; j < jTable2.getColumnCount(); j++) {
                jTable2.setValueAt("", i, j);
            }
        }

        RowNo = 0;
        rowIncrementer = 0;
        Calendar calendar = Calendar.getInstance();
        java.sql.Date sqlDateObject = new java.sql.Date(calendar.getTime().getTime());
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);
        String from = "'" + date + "'";
        java.sql.Statement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.createStatement();
            rs = stm.executeQuery("SELECT cat_name from inv_category ");
            // print the results
            while (rs.next()) {
                jTable2.setValueAt(rs.getString(1), RowNo, 0);
                String catName = "'" + rs.getString(1) + "'";
                System.out.println("CatName = " + catName);

                //get value from sells transaction
                java.sql.Statement stm1 = null;
                ResultSet rs1 = null;
                try {
                    stm1 = connection.createStatement();
                    rs1 = stm1.executeQuery("SELECT sum(sell_price) from inv_transaction where cat_name=" + catName + " "
                            + "and to_date(tran_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
                    // print the results
                    while (rs1.next()) {
                        jTable2.setValueAt(rs1.getString(1), RowNo, 1);
                        System.out.println("Sell Sum = " + rs1.getString(1));
                        //jTable2.setValueAt(rs.getString(3), RowNo, 1);
                        //jTable2.setValueAt(rs.getString(4), RowNo, 2);
                        //jTable2.setValueAt(rs.getString(5), RowNo, 3);
                        //jTable2.setValueAt(rs.getString(5).substring(0,11), RowNo, 3);
                        //RowNo++;
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                    //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error1",1);
                } finally {
                    DatabaseUtils.close(rs1);
                    DatabaseUtils.close(stm1);
                }
                //
                //get value from buy transaction
                java.sql.Statement stm2 = null;
                ResultSet rs2 = null;
                try {
                    stm2 = connection.createStatement();
                    rs2 = stm2.executeQuery("SELECT sum(sell_price) from buy_transaction where cat_name=" + catName + " "
                            + "and to_date(tran_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
                    // print the results
                    while (rs2.next()) {
                        jTable2.setValueAt(rs2.getString(1), RowNo, 2);
                        System.out.println("Buy Sum = " + rs2.getString(1));
                        //jTable2.setValueAt(rs.getString(3), RowNo, 1);
                        //jTable2.setValueAt(rs.getString(4), RowNo, 2);
                        //jTable2.setValueAt(rs.getString(5), RowNo, 3);
                        //jTable2.setValueAt(rs.getString(5).substring(0,11), RowNo, 3);
                        //RowNo++;
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                    //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error1",1);
                } finally {
                    DatabaseUtils.close(rs2);
                    DatabaseUtils.close(stm2);
                }
                //

                RowNo++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error2",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm);
        }

        double TP = 0.0, p = 0.0, _vat = 0.0, _discount = 0.0, _due = 0.0, _dueForDashboard = 0.0, _dueCollection = 0.0;
        for (int j = 0; j < RowNo; j++) {
            String s = String.valueOf(jTable2.getValueAt(j, 1));
            //JOptionPane.showMessageDialog(rootPane, s);
            if (s.equals("null")) {
                p = 0.0;
            } else {
                p = Double.parseDouble(s);
            }
            System.out.println(p);
            TP += p;
        }
        double buyTotalPrice = 0.0, buyPrice = 0.0;
        for (int j = 0; j < RowNo; j++) {
            String s = String.valueOf(jTable2.getValueAt(j, 2));
            //JOptionPane.showMessageDialog(rootPane, s);
            if (s.equals("null")) {
                buyPrice = 0.0;
            } else {
                buyPrice = Double.parseDouble(s);
            }
            System.out.println(buyPrice);
            buyTotalPrice += buyPrice;
        }

        jTable2.setValueAt("                                       ", RowNo + rowIncrementer, 0);
        jTable2.setValueAt("", RowNo, 1);

        rowIncrementer++;
        jTable2.setValueAt("                 Total                    =", RowNo + rowIncrementer, 0);
        jTable2.setValueAt(TP, RowNo + rowIncrementer, 1);
        jTable2.setValueAt(buyTotalPrice, RowNo + rowIncrementer, 2);

        rowIncrementer++;
        jTable2.setValueAt("               VAT(+)                  =", RowNo + rowIncrementer, 0);
        //
        java.sql.Statement stm3 = null;
        ResultSet rs3 = null;
        try {
            stm3 = connection.createStatement();
            rs3 = stm3.executeQuery("SELECT sum(vat_amount) from inv_vat where "
                    + " to_date(vat_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs3.next()) {
                jTable2.setValueAt(rs3.getString(1), RowNo + rowIncrementer, 1);
                _vat = Double.parseDouble(String.valueOf(rs3.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error3",1);
        } finally {
            DatabaseUtils.close(rs3);
            DatabaseUtils.close(stm3);
        }
        //

        rowIncrementer++;
        jTable2.setValueAt("           Discount(-)              =", RowNo + rowIncrementer, 0);
        //
        java.sql.Statement stm4 = null;
        ResultSet rs4 = null;
        try {
            stm4 = connection.createStatement();
            rs4 = stm4.executeQuery("SELECT sum(disc_amount) from inv_discount where "
                    + " to_date(disc_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs4.next()) {
                jTable2.setValueAt(rs4.getString(1), RowNo + rowIncrementer, 1);
                _discount = Double.parseDouble(String.valueOf(rs4.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error4",1);
        } finally {
            DatabaseUtils.close(rs4);
            DatabaseUtils.close(stm4);
        }
        //

        rowIncrementer++;
        jTable2.setValueAt("          Total Due Bill(-)        =", RowNo + rowIncrementer, 0);
        //
        java.sql.Statement stm6d = null;
        ResultSet rs6d = null;
        try {
            stm6d = connection.createStatement();
            rs6d = stm6d.executeQuery("SELECT sum(amount) from due_bill_for_dashboard where "
                    + " to_date(bill_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs6d.next()) {
                jTable2.setValueAt(rs6d.getString(1), RowNo + rowIncrementer, 1);
                _dueForDashboard = Double.parseDouble(String.valueOf(rs6d.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error6",1);
        } finally {
            DatabaseUtils.close(rs6d);
            DatabaseUtils.close(stm6d);
        }
        //

        rowIncrementer++;
        jTable2.setValueAt("        Remaining Due          =", RowNo + rowIncrementer, 0);
        //
        java.sql.Statement stm6 = null;
        ResultSet rs6 = null;
        try {
            stm6 = connection.createStatement();
            rs6 = stm6.executeQuery("SELECT sum(amount) from bill_due where "
                    + " to_date(bill_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs6.next()) {
                jTable2.setValueAt(rs6.getString(1), RowNo + rowIncrementer, 1);
                _due = Double.parseDouble(String.valueOf(rs6.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error6",1);
        } finally {
            DatabaseUtils.close(rs6);
            DatabaseUtils.close(stm6);
        }
        //

        rowIncrementer++;
        jTable2.setValueAt("   Due Collection(+)           =", RowNo + rowIncrementer, 0);
        //
        java.sql.Statement stm7 = null;
        ResultSet rs7 = null;
        try {
            stm7 = connection.createStatement();
            rs7 = stm7.executeQuery("SELECT sum(amount) from due_collection where "
                    + " to_date(bill_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs7.next()) {
                jTable2.setValueAt(rs7.getString(1), RowNo + rowIncrementer, 1);
                _dueCollection = Double.parseDouble(String.valueOf(rs7.getString(1)));
            }
        } catch (Exception e) {
            System.out.println("Error at Due Collection: " + e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error7",1);
        } finally {
            DatabaseUtils.close(rs7);
            DatabaseUtils.close(stm7);
        }

        //Double vat = Double.parseDouble(String.valueOf(jTable2.getValueAt(RowNo+1, 1)));
        //Double discount = Double.parseDouble(String.valueOf(jTable2.getValueAt(RowNo+2, 1)));
        rowIncrementer++;
        double grossTP = TP + _vat - _discount - _dueForDashboard + _dueCollection - buyTotalPrice;
        DecimalFormat df2 = new DecimalFormat("###########0.00");
        grossTP = new Double(df2.format(grossTP)).doubleValue();
        jTable2.setValueAt("                 Total                     =", RowNo + rowIncrementer, 0);
        jTable2.setValueAt(grossTP, RowNo + rowIncrementer, 1);

        rowIncrementer++;
        jTable2.setValueAt("    Advance Salary(-)         =", RowNo + rowIncrementer, 0);
        double adv = 0.0;
        java.sql.Statement stm5 = null;
        ResultSet rs5 = null;
        try {
            stm5 = connection.createStatement();
            rs5 = stm5.executeQuery("SELECT sum(adv_salary) from inv_employee_advance where "
                    + " to_date(adv_date) between to_date(" + from + ") " + "and to_date(" + from + ")");
            // print the results
            while (rs5.next()) {
                jTable2.setValueAt(rs5.getString(1), RowNo + rowIncrementer, 1);
                adv = Double.parseDouble(String.valueOf(rs5.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error4",1);
        } finally {
            DatabaseUtils.close(rs5);
            DatabaseUtils.close(stm5);
        }
        rowIncrementer++;
        //rowIncrementer += rowIncrementer;

        jTable2.setValueAt("    Card Payment(-)           =", RowNo + rowIncrementer, 0);
        double cardPay = 0.0;
        java.sql.Statement stmCard = null;
        ResultSet rsCard = null;
        try {
            stmCard = connection.createStatement();
            rsCard = stmCard.executeQuery("SELECT sum(amount) from bill_card where "
                    + " bill_date between to_date(" + from + ") " + " and to_date(" + from + ")");
            // print the results
            while (rsCard.next()) {
                jTable2.setValueAt(rsCard.getString(1), RowNo + rowIncrementer, 1);
                cardPay = Double.parseDouble(String.valueOf(rsCard.getString(1)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error4",1);
            //Utilities.setErrorLog(AdminForm.class.getSimpleName()+" initFields()", "Exception", "SELECT sum(amount) from bill_card Query Execution Error", e.toString());
        } finally {
            DatabaseUtils.close(rsCard);
            DatabaseUtils.close(stmCard);
        }

        rowIncrementer++;
        grossTP1 = grossTP - adv - cardPay;
        jTable2.setValueAt("                 Total                     =", RowNo + rowIncrementer, 0);
        jTable2.setValueAt(grossTP1, RowNo + rowIncrementer, 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tvCash = new javax.swing.JTextField();
        btnPrintReport = new javax.swing.JButton();
        lblDate = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblElseCode = new javax.swing.JLabel();
        tvExpense = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuRefresh = new javax.swing.JMenu();
        menuAddUser = new javax.swing.JMenuItem();
        menuDeleteUser = new javax.swing.JMenuItem();
        menuGenerateToken = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        addCat = new javax.swing.JMenuItem();
        updateCat = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        addFood = new javax.swing.JMenuItem();
        updateFood = new javax.swing.JMenuItem();
        deleteFood = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuRetailerSellsFrame = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        dashBoard = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuCategoryWise = new javax.swing.JMenuItem();
        menu_customer_wise = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        menuSendNotification = new javax.swing.JMenuItem();
        menuDeleteData = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuAddEmployee = new javax.swing.JMenuItem();
        menuUpdateEmployee = new javax.swing.JMenuItem();
        menuDeleteEmployee = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        menuGiveAdv = new javax.swing.JMenuItem();
        MenuAdvTaken = new javax.swing.JMenuItem();
        menuNewSalary = new javax.swing.JMenuItem();
        menuSavedSalary = new javax.swing.JMenuItem();
        menuDueBill = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        menuSearchDueBill = new javax.swing.JMenuItem();
        menuCustomer = new javax.swing.JMenu();
        menuAddCustomer = new javax.swing.JMenuItem();
        menuUpdate_Customer = new javax.swing.JMenuItem();
        menuCustomerList = new javax.swing.JMenuItem();

        jMenuItem7.setText("jMenuItem7");

        jMenu7.setText("jMenu7");

        jMenuItem11.setText("jMenuItem11");

        jMenuItem5.setText("jMenuItem5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("POS - Admin ");
        setExtendedState(6);
        setPreferredSize(new java.awt.Dimension(1109, 651));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable2.setBackground(java.awt.SystemColor.control);
        jTable2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Category", "Total Sell", "Total Buy"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setFocusable(false);
        jTable2.setGridColor(java.awt.SystemColor.controlHighlight);
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Today's Report");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Remaining Cash (TK) :");

        tvCash.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tvCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvCashActionPerformed(evt);
            }
        });

        btnPrintReport.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPrintReport.setForeground(new java.awt.Color(0, 153, 51));
        btnPrintReport.setText("Print Report");
        btnPrintReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReportActionPerformed(evt);
            }
        });

        lblDate.setFont(new java.awt.Font("Stencil", 0, 16)); // NOI18N
        lblDate.setForeground(new java.awt.Color(0, 153, 153));
        lblDate.setText("Date");

        lblUserName.setFont(new java.awt.Font("Stencil", 0, 16)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(0, 153, 153));
        lblUserName.setText("UserName");

        jLabel8.setFont(new java.awt.Font("Stencil", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("User Name:");

        lblName.setFont(new java.awt.Font("Stencil", 1, 36)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 153, 153));
        lblName.setText("New Shad Restora");

        lblAddress.setFont(new java.awt.Font("Stencil", 0, 16)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(0, 153, 153));
        lblAddress.setText("<html>Plot# 35, Sonargaon Janapoth Road, Sector# 12,<br> &emsp &emsp &nbsp &nbsp &emsp &emsp &nbsp &nbsp Uttara, Dhaka 1230</html>"); // NOI18N

        lblElseCode.setText("<html>\n\t<p style=\"text-align:center;font-size:15px; color:black;background-color:Black;color:White\"> \n\t\t<span style=\"color:White; font-size:20px;background-color:black;font-weight:bold\">&nbsp  E L S E </span>\n\t\t<span style=\"color:Green; font-size:20px;background-color:black;font-weight:bold\"> C O D E  </span>\n\t\t<span style=\"color:White; font-size:12px;background-color:black;font-weight:normal\"><br/>  info@elsecode.com </span>\n\t\t<span style=\"color:White; font-size:10px;background-color:black;font-weight:normal\"><br/>Mobile: +8801674829764 </span>\n\t</p>\n<html>");

        tvExpense.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tvExpense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvExpenseActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Other Expense (TK) :");

        menuRefresh.setBorder(null);
        menuRefresh.setText("      File   ");
        menuRefresh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuAddUser.setText("Add User");
        menuAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddUserActionPerformed(evt);
            }
        });
        menuRefresh.add(menuAddUser);

        menuDeleteUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuDeleteUser.setText("Delete User");
        menuDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeleteUserActionPerformed(evt);
            }
        });
        menuRefresh.add(menuDeleteUser);

        menuGenerateToken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuGenerateToken.setText("Generate Token");
        menuGenerateToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGenerateTokenActionPerformed(evt);
            }
        });
        menuRefresh.add(menuGenerateToken);

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(0, 153, 102));
        jMenuItem1.setText("Refresh");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuRefresh.add(jMenuItem1);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem3.setForeground(new java.awt.Color(153, 0, 0));
        jMenuItem3.setText("Log Out / Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuRefresh.add(jMenuItem3);

        jMenuBar1.add(menuRefresh);

        jMenu1.setBackground(new java.awt.Color(204, 204, 255));
        jMenu1.setBorder(null);
        jMenu1.setText("    Category");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        addCat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addCat.setText("Add Category");
        addCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCatActionPerformed(evt);
            }
        });
        jMenu1.add(addCat);

        updateCat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateCat.setText("Update Category");
        updateCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCatActionPerformed(evt);
            }
        });
        jMenu1.add(updateCat);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(204, 204, 255));
        jMenu2.setBorder(null);
        jMenu2.setText("     Item   ");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        addFood.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addFood.setText("Add  Item");
        addFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFoodActionPerformed(evt);
            }
        });
        jMenu2.add(addFood);

        updateFood.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateFood.setText("Update  Item");
        updateFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateFoodActionPerformed(evt);
            }
        });
        jMenu2.add(updateFood);

        deleteFood.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteFood.setForeground(new java.awt.Color(153, 0, 0));
        deleteFood.setText("Delete  Item");
        deleteFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFoodActionPerformed(evt);
            }
        });
        jMenu2.add(deleteFood);

        jMenuBar1.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(153, 153, 255));
        jMenu3.setBorder(null);
        jMenu3.setText("   Sells Frame  ");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem6.setText("New Sells Frame");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        menuRetailerSellsFrame.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuRetailerSellsFrame.setText("Retailer Sells Frame");
        menuRetailerSellsFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRetailerSellsFrameActionPerformed(evt);
            }
        });
        jMenu3.add(menuRetailerSellsFrame);

        jMenuBar1.add(jMenu3);

        jMenu8.setText("     Buy Frame  ");
        jMenu8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem10.setText("Stock IN");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem10);

        jMenuBar1.add(jMenu8);

        jMenu4.setBackground(new java.awt.Color(153, 153, 255));
        jMenu4.setBorder(null);
        jMenu4.setText("     DashBoard    ");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        dashBoard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashBoard.setText("Date Wise");
        dashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashBoardActionPerformed(evt);
            }
        });
        jMenu4.add(dashBoard);

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem2.setText("Item WIse");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        menuCategoryWise.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuCategoryWise.setText("Category Wise");
        menuCategoryWise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCategoryWiseActionPerformed(evt);
            }
        });
        jMenu4.add(menuCategoryWise);

        menu_customer_wise.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menu_customer_wise.setText("Customer Wise");
        menu_customer_wise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_customer_wiseActionPerformed(evt);
            }
        });
        jMenu4.add(menu_customer_wise);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem9.setText("Export_To_Excel");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        menuSendNotification.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuSendNotification.setText("Send SMS");
        menuSendNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSendNotificationActionPerformed(evt);
            }
        });
        jMenu4.add(menuSendNotification);

        menuDeleteData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuDeleteData.setForeground(new java.awt.Color(153, 0, 0));
        menuDeleteData.setText("Delete Data");
        menuDeleteData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeleteDataActionPerformed(evt);
            }
        });
        jMenu4.add(menuDeleteData);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem4.setForeground(new java.awt.Color(0, 102, 0));
        jMenuItem4.setText("BackUp Data");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("    Employee  ");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuAddEmployee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuAddEmployee.setText("Add Employee");
        menuAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddEmployeeActionPerformed(evt);
            }
        });
        jMenu5.add(menuAddEmployee);

        menuUpdateEmployee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuUpdateEmployee.setText("Update Employee");
        menuUpdateEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUpdateEmployeeActionPerformed(evt);
            }
        });
        jMenu5.add(menuUpdateEmployee);

        menuDeleteEmployee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuDeleteEmployee.setText("Delete Employee");
        menuDeleteEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeleteEmployeeActionPerformed(evt);
            }
        });
        jMenu5.add(menuDeleteEmployee);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("    Salary  ");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuGiveAdv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuGiveAdv.setText("Give Advance");
        menuGiveAdv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGiveAdvActionPerformed(evt);
            }
        });
        jMenu6.add(menuGiveAdv);

        MenuAdvTaken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MenuAdvTaken.setText("Advance Taken NameWise");
        MenuAdvTaken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAdvTakenActionPerformed(evt);
            }
        });
        jMenu6.add(MenuAdvTaken);

        menuNewSalary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuNewSalary.setText("New Salary Form");
        menuNewSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewSalaryActionPerformed(evt);
            }
        });
        jMenu6.add(menuNewSalary);

        menuSavedSalary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuSavedSalary.setText("Saved Salary Form");
        menuSavedSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSavedSalaryActionPerformed(evt);
            }
        });
        jMenu6.add(menuSavedSalary);

        jMenuBar1.add(jMenu6);

        menuDueBill.setText("    Due Bill  ");
        menuDueBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem8.setText("Add Due Bill");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menuDueBill.add(jMenuItem8);

        menuSearchDueBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuSearchDueBill.setText("Search/Delete Due Bill");
        menuSearchDueBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSearchDueBillActionPerformed(evt);
            }
        });
        menuDueBill.add(menuSearchDueBill);

        jMenuBar1.add(menuDueBill);

        menuCustomer.setText("    Customer  ");
        menuCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        menuAddCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuAddCustomer.setText("Add Customer");
        menuAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddCustomerActionPerformed(evt);
            }
        });
        menuCustomer.add(menuAddCustomer);

        menuUpdate_Customer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuUpdate_Customer.setText("Update Customer");
        menuUpdate_Customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUpdate_CustomerActionPerformed(evt);
            }
        });
        menuCustomer.add(menuUpdate_Customer);

        menuCustomerList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        menuCustomerList.setText("Customer List");
        menuCustomerList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCustomerListActionPerformed(evt);
            }
        });
        menuCustomer.add(menuCustomerList);

        jMenuBar1.add(menuCustomer);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblElseCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(181, 181, 181))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPrintReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(230, 230, 230))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tvCash, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tvExpense, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(85, 85, 85))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAddress)
                                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))))
            .addGroup(layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(lblUserName))
                .addGap(137, 137, 137)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tvExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tvCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(btnPrintReport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(lblElseCode, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCatActionPerformed
        // TODO add your handling code here:
        new Admin_Add_Category().setVisible(true);
    }//GEN-LAST:event_addCatActionPerformed

    private void updateCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCatActionPerformed
        // TODO add your handling code here:
        new Admin_Update_Category().setVisible(true);
    }//GEN-LAST:event_updateCatActionPerformed

    private void addFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFoodActionPerformed
        // TODO add your handling code here:
        new Admin_Add_FoodItem().setVisible(true);
    }//GEN-LAST:event_addFoodActionPerformed

    private void updateFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateFoodActionPerformed
        // TODO add your handling code here:
        new Admin_Update_Item().setVisible(true);
    }//GEN-LAST:event_updateFoodActionPerformed

    private void deleteFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFoodActionPerformed
        // TODO add your handling code here:
        new Admin_Delete_FoodItem().setVisible(true);
    }//GEN-LAST:event_deleteFoodActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        //SellsFrame_Modified.UserName = "Admin";
        InventoryPOS.ReadSystemProperties();
        new SellsFrame_Modified().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void dashBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashBoardActionPerformed
        // TODO add your handling code here:
        new DashBoard().setVisible(true);
    }//GEN-LAST:event_dashBoardActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new DashBoard_Item().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuDeleteDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteDataActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(rootPane, "Are You Sure", "Confirm", JOptionPane.YES_NO_OPTION);
        PreparedStatement statement = null;
        if (result == JOptionPane.YES_OPTION) {
            //backup the transaction table
            Date time = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
            String date = formatter.format(time);
            try {
                Process p = Runtime.getRuntime().exec("cmd /c exp system/123456 tables=inv_transaction,"
                        + "file=C:\\POS_Table_inv_transaction_Backup_" + date + ".dmp");
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }

            } catch (IOException e1) {
            } catch (InterruptedException e2) {
            }

            //now delete all data
            try {
                statement = connection.prepareStatement("Delete from inv_transaction");
                statement.execute();
                JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            } finally {
                DatabaseUtils.close(statement);
            }
        }
    }//GEN-LAST:event_menuDeleteDataActionPerformed

    private void menuCategoryWiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCategoryWiseActionPerformed
        // TODO add your handling code here:
        new DashBoard_Category().setVisible(true);
    }//GEN-LAST:event_menuCategoryWiseActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error In Closing Database. Please Ignore it & Click OK.");
        }
        this.dispose();
        new AdminForm().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error In Closing Database. Please Ignore it & Click OK.");
        }
        this.dispose();
        new MainForm1().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void menuAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddUserActionPerformed
        // TODO add your handling code here:
        new Admin_AddUser().setVisible(true);
    }//GEN-LAST:event_menuAddUserActionPerformed

    private void menuDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteUserActionPerformed
        // TODO add your handling code here:
        new Admin_DeleteUser().setVisible(true);
    }//GEN-LAST:event_menuDeleteUserActionPerformed

    private void menuAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddEmployeeActionPerformed
        // TODO add your handling code here:
        new AddEmployee().setVisible(true);
    }//GEN-LAST:event_menuAddEmployeeActionPerformed

    private void menuUpdateEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUpdateEmployeeActionPerformed
        // TODO add your handling code here:
        Update_Delete_Employee.title = "Update Employee";
        Update_Delete_Employee.update = true;
        Update_Delete_Employee.delete = false;
        new Update_Delete_Employee().setVisible(true);
    }//GEN-LAST:event_menuUpdateEmployeeActionPerformed

    private void menuDeleteEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteEmployeeActionPerformed
        // TODO add your handling code here:
        Update_Delete_Employee.title = "Delete Employee";
        Update_Delete_Employee.update = false;
        Update_Delete_Employee.delete = true;
        new Update_Delete_Employee().setVisible(true);
    }//GEN-LAST:event_menuDeleteEmployeeActionPerformed

    private void menuGiveAdvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGiveAdvActionPerformed
        // TODO add your handling code here:
        new Employee_Advance_Salary().setVisible(true);
    }//GEN-LAST:event_menuGiveAdvActionPerformed

    private void MenuAdvTakenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAdvTakenActionPerformed
        // TODO add your handling code here:
        new Advance_Salary_NameWise().setVisible(true);
    }//GEN-LAST:event_MenuAdvTakenActionPerformed

    private void menuNewSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewSalaryActionPerformed
        // TODO add your handling code here:
        new New_SalaryForm().setVisible(true);
    }//GEN-LAST:event_menuNewSalaryActionPerformed

    private void menuSavedSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSavedSalaryActionPerformed
        // TODO add your handling code here:
        new Saved_SalaryForm().setVisible(true);
    }//GEN-LAST:event_menuSavedSalaryActionPerformed

    private void btnPrintReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReportActionPerformed
        // TODO add your handling code here:
        String S[][] = new String[100][5], s;
        System.out.println("Hajjaz RowNo: " + RowNo + ", rowIncrementer :" + rowIncrementer);
        for (int i = 0; i <= RowNo + rowIncrementer + 1; i++) {

            s = String.valueOf(jTable2.getValueAt(i, 0));
            if (s.equals("null")) {
                S[i][0] = "";
            } else {
                S[i][0] = s.replaceAll("\\s+", "");
            }
            s = String.valueOf(jTable2.getValueAt(i, 1));
            if (s.equals("null")) {
                S[i][1] = "";
            } else {
                S[i][1] = s.replaceAll("\\s+", "");
            }

            s = String.valueOf(jTable2.getValueAt(i, 2));
            if (s.equals("null")) {
                S[i][2] = "";
            } else {
                S[i][2] = s.replaceAll("\\s+", "");
            }
        }

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(" dd/MM/yyyy, hh:mm a");
        String Time = "Date: " + formatter.format(date);
        String Thank = "Thank You For Coming Here!",
                HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.", blank = " ";
        String lineS = "----------Sells Report-------------",
                line = "------------------------------------";

        DateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        String Time1 = formatter1.format(date);

        try {
            FileWriter filewriter = new FileWriter(InventoryPOS.printFileDirectory + "\\" + "Report" + Time1 + InventoryPOS.printReportFileName);
            PrintWriter out = new PrintWriter(filewriter);
            out.println("                  " + Utilities.ReadSystemProperties("companyName"));
            out.println("  ");
            out.println("        " + Utilities.ReadSystemProperties("strBillCompanyAddress1"));
            out.println("          " + Utilities.ReadSystemProperties("strBillCompanyAddress2"));

            out.println("  " + "   ");
            out.println(lineS);
            out.println("          " + Time);
            out.println(lineS);
            out.println("  " + "   ");
            out.println("  " + "   ");

            out.println(line);
            out.println("Description" + "            " + "Total Sell             " + "Total Buy     ");
            out.println(line);
            for (int j = 0; j <= RowNo + rowIncrementer + 1; j++) {
                out.println(S[j][0] + AddSpace(23, S[j][0].length()) + S[j][1] + AddSpace(23, S[j][1].length()) + S[j][2]);
            }

            out.println("  " + "   ");
            out.println("  " + "   ");
            out.println("  " + "   ");
            out.println("  " + "   ");
            out.println("       " + Utilities.ReadSystemProperties("strElseCode"));
            out.println(" " + blank);
            out.println(" " + " ");
            out.println(" " + " ");
            out.println(" " + " ");
            out.println();

            out.close();
        } catch (IOException ex) {

        }
        //if (InventoryPOS.printPageSizeBig) {
        printCard();
        //} else if (InventoryPOS.printPageSizeSmall) {
        //    printCard_Small();
        //}
        //print the bill file
        /*Desktop desktop = Desktop.getDesktop();
        try {
            desktop.print(new File(InventoryPOS.printFileDirectory + "\\" +"Report"+ Time1 + InventoryPOS.printReportFileName));
        } catch (IOException ex) {
            Logger.getLogger(SellsFrame_Modified.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_btnPrintReportActionPerformed

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

                String lineS = "-----------Sells Report----------------",
                        line = "---------------------------------------";
                String Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                        HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.";
                String CName = "New Shad Restora", CAddress1 = "Plot# 35,Sonargaon Janapath Road,", CAddress2 = "Sector# 12, Uttara, Dhaka 1230";
                String S[][] = new String[50][5];
                S[0][0] = "Description";
                S[0][1] = "Total Sell";
                S[0][2] = "Total Buy";
                String s;
                int size = RowNo;

                for (int i = 1; i <= size + rowIncrementer + 1; i++) {

                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 0))) != null) {
                        if (s.equals("null")) {
                            S[i][0] = "";
                        } else {
                            S[i][0] = s.replaceAll("\\s+", "");
                        }
                    }
                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 1))) != null) {
                        if (s.equals("null")) {
                            S[i][1] = "";
                        } else {
                            S[i][1] = s.replaceAll("\\s+", "");
                        }
                    }
                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 2))) != null) {
                        if (s.equals("null")) {
                            S[i][2] = "";
                        } else {
                            S[i][2] = s.replaceAll("\\s+", "");
                        }
                    }
                    System.out.println("S[" + i + "] = " + S[i][0]);
                }
                int h = 1, height = 0;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(Utilities.ReadSystemProperties("companyName"), 35, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 8));
                g.drawString(Utilities.ReadSystemProperties("strBillCompanyAddress1"), 20, 35);
                g.drawString(Utilities.ReadSystemProperties("strBillCompanyAddress2"), 20, 45);

                g.drawString(lineS, 20, 65);
                g.drawString(Time, 20, 75);
                g.drawString(lineS, 20, 85);
                for (int j = 0; j <= size + rowIncrementer + 1; j++) {
                    if (j == 0) {
                        h = 1;
                        g.drawString(line, 15, 110);
                        g.drawString(S[j][0], 20, 120 * h);
                        g.drawString(S[j][1], 120, 120 * h);
                        g.drawString(S[j][2], 200, 120 * h);
                        g.drawString(line, 15, 130);
                        height = 130;
                    } else if (j >= 1) {
                        h = j + 1;
                        g.drawString(S[j][0], 20, height + 10);
                        g.drawString(S[j][1], 120, height + 10);
                        g.drawString(S[j][2], 200, height + 10);
                        height += 10;
                    }
                }
                g.drawString(Utilities.ReadSystemProperties("strElseCode"), 20, height + 25);
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

                String lineS = "----------Sells Report-------------",
                        line = "---------------------------------------";
                String Thank = "Thank You For Coming Here!", vatReg = "Vat Reg# 18011057968",
                        HD = "Cell No: 01834268714, 01731267763.", elseCode = "Powered By ElseCode.";
                String CName = "New Shad Restora", CAddress1 = "Plot# 35,Sonargaon Janapath Road,", CAddress2 = "Sector# 12, Uttara, Dhaka 1230";
                String S[][] = new String[50][5];
                S[0][0] = "Description";
                S[0][1] = "Sell";
                S[0][2] = "Buy";
                String s;
                int size = RowNo;

                for (int i = 1; i <= size + rowIncrementer + 1; i++) {

                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 0))) != null) {
                        if (s.equals("null")) {
                            S[i][0] = "";
                        } else {
                            S[i][0] = s.replaceAll("\\s+", "");
                        }
                    }
                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 1))) != null) {
                        if (s.equals("null")) {
                            S[i][1] = "";
                        } else {
                            S[i][1] = s.replaceAll("\\s+", "");
                        }
                    }
                    if ((s = String.valueOf(jTable2.getValueAt(i - 1, 2))) != null) {
                        if (s.equals("null")) {
                            S[i][2] = "";
                        } else {
                            S[i][2] = s.replaceAll("\\s+", "");
                        }
                    }
                    System.out.println("S[" + i + "] = " + S[i][0]);
                }
                int h = 1, height = 0;
//        Properties properties = new Properties();
//        PrintJob _printJob = Toolkit.getDefaultToolkit().getPrintJob(TablePrint.this, "name", properties);
//        
//        Graphics g = _printJob.getGraphics();
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString(Utilities.ReadSystemProperties("companyName"), 0, 15);
                g.setFont(new Font("Courier New", Font.BOLD, 7));
                g.drawString(Utilities.ReadSystemProperties("strBillCompanyAddress1"), 0, 35);
                g.drawString(Utilities.ReadSystemProperties("strBillCompanyAddress2"), 0, 45);

                g.drawString(lineS, 0, 65);
                g.drawString(Time, 0, 75);
                g.drawString(lineS, 0, 85);
                for (int j = 0; j <= size + rowIncrementer + 1; j++) {
                    if (j == 0) {
                        h = 1;
                        g.drawString(line, 0, 110);
                        g.drawString(S[j][0], 0, 120 * h);
                        g.drawString(S[j][1], 75, 120 * h);
                        g.drawString(S[j][2], 107, 120 * h);
                        g.drawString(line, 0, 130);
                        height = 130;
                    } else if (j >= 1) {
                        h = j + 1;
                        g.drawString(S[j][0], 00, height + 10);
                        g.drawString(S[j][1], 75, height + 10);
                        g.drawString(S[j][2], 107, height + 10);
                        height += 10;
                    }
                }
                g.drawString(Utilities.ReadSystemProperties("strElseCode"), 0, height + 25);
                g.dispose();
                Height += height + 45;
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

    private void tvCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvCashActionPerformed
        // TODO add your handling code here:
        if (!cashTKTV) {
            double expense = 0.0, grossTotal = 0.0;
            rowIncrementer++;
            jTable2.setValueAt("              Expense                   =", RowNo + rowIncrementer, 0);
            jTable2.setValueAt(tvExpense.getText(), RowNo + rowIncrementer, 1);

            expense = Double.parseDouble(tvExpense.getText());
            grossTP1 = grossTP1 - expense;
            DecimalFormat df1 = new DecimalFormat("#########0.00");
            grossTotal = new Double(df1.format(grossTP1)).doubleValue();

            rowIncrementer++;
            jTable2.setValueAt("       Gross Total                      =", RowNo + rowIncrementer, 0);
            jTable2.setValueAt(grossTotal, RowNo + rowIncrementer, 1);

            double cash = 0.0, result = 0.0;
            String res = "";
            rowIncrementer++;
            jTable2.setValueAt("        Remaining Cash                =", RowNo + rowIncrementer, 0);
            jTable2.setValueAt(tvCash.getText(), RowNo + rowIncrementer, 1);

            cash = Double.parseDouble(tvCash.getText());
            result = grossTP1 - cash;
            //DecimalFormat df2 = new DecimalFormat("#########0.00");
            //result = new Double(df2.format(result)).doubleValue();

            if (result > 0) {
                res = "          Short                      ";
            } else if (result < 0) {
                res = "          Extra                      ";
                result = result * -1;
            } else {
                res = "         Remais                 ";
            }
            rowIncrementer++;
            jTable2.setValueAt(res + "     =", RowNo + rowIncrementer, 0);
            jTable2.setValueAt(result, RowNo + rowIncrementer, 1);
            cashTKTV = true;
        }

    }//GEN-LAST:event_tvCashActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error In Closing Database. Please Ignore it & Click OK.");
        }
    }//GEN-LAST:event_formWindowClosing

    private void menuSearchDueBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSearchDueBillActionPerformed
        // TODO add your handling code here:
        new Search_DueBills().setVisible(true);
    }//GEN-LAST:event_menuSearchDueBillActionPerformed

    private void StartProcess() {
        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date = formatter.format(time);
        //exp system/123456 tables=bill_due file=C:\table_data.dmp
        try {
            /*Process p=Runtime.getRuntime().exec("cmd /c exp system/123456 tables= inv_category,inv_item,inv_transaction,"
                + "inv_user,inv_discount,inv_vat,inv_vat_transaction,inv_supplier,inv_employee,inv_employee_advance,"
                + "inv_salary,bill_serial,bill_due,buy_category,buy_fooditem,buy_transaction,buy_bill_serial"
                + "file=C:\\NewShadPOS_DB_Backup.dmp"); */
            Process p = Runtime.getRuntime().exec("cmd /c exp system/123456 tables=inv_category,inv_item,inv_transaction,"
                    + "inv_user,inv_discount,inv_vat,inv_vat_transaction,inv_supplier,inv_employee,inv_employee_advance,"
                    + "inv_salary,bill_serial,bill_due,due_bill_for_dashboard,due_collection,activity_log,bill_card,buy_transaction,buy_bill_serial "
                    + "file=" + InventoryPOS.printFileDirectory + "\\DB_Backup_" + date + ".dmp");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (IOException e1) {
        } catch (InterruptedException e2) {
        }

        JOptionPane.showMessageDialog(this, "BackUp Successfull. Saved to Project_Folder\\" + InventoryPOS.printFileDirectory + "\\DB_Backup_" + date + ".dmp");
        System.out.println("Done");
    }
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        StartProcess();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        new Add_DueBill().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        new Export_To_Excel().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void tvExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvExpenseActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tvExpenseActionPerformed

    private void menuGenerateTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGenerateTokenActionPerformed
        // TODO add your handling code here:
        new Token_Generator().setVisible(true);
    }//GEN-LAST:event_menuGenerateTokenActionPerformed

    private void menuAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddCustomerActionPerformed
        // TODO add your handling code here:
        new Add_Customer().setVisible(true);
    }//GEN-LAST:event_menuAddCustomerActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new BuyFrame_StockIn().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void menuUpdate_CustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUpdate_CustomerActionPerformed
        // TODO add your handling code here:
        new Update_Customer().setVisible(true);
    }//GEN-LAST:event_menuUpdate_CustomerActionPerformed

    private void menu_customer_wiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_customer_wiseActionPerformed
        new DashBoard_CustomerWise().setVisible(true);
    }//GEN-LAST:event_menu_customer_wiseActionPerformed

    private void menuCustomerListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCustomerListActionPerformed
        new DashBoard_CustomerList().setVisible(true);
    }//GEN-LAST:event_menuCustomerListActionPerformed

    private void menuSendNotificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSendNotificationActionPerformed
        new SendSMSNotification().setVisible(true);
    }//GEN-LAST:event_menuSendNotificationActionPerformed

    private void menuRetailerSellsFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRetailerSellsFrameActionPerformed
        new Retailer_SellsFrame().setVisible(true);
    }//GEN-LAST:event_menuRetailerSellsFrameActionPerformed

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
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuAdvTaken;
    private javax.swing.JMenuItem addCat;
    private javax.swing.JMenuItem addFood;
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JMenuItem dashBoard;
    private javax.swing.JMenuItem deleteFood;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblElseCode;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JMenuItem menuAddCustomer;
    private javax.swing.JMenuItem menuAddEmployee;
    private javax.swing.JMenuItem menuAddUser;
    private javax.swing.JMenuItem menuCategoryWise;
    private javax.swing.JMenu menuCustomer;
    private javax.swing.JMenuItem menuCustomerList;
    private javax.swing.JMenuItem menuDeleteData;
    private javax.swing.JMenuItem menuDeleteEmployee;
    private javax.swing.JMenuItem menuDeleteUser;
    private javax.swing.JMenu menuDueBill;
    private javax.swing.JMenuItem menuGenerateToken;
    private javax.swing.JMenuItem menuGiveAdv;
    private javax.swing.JMenuItem menuNewSalary;
    private javax.swing.JMenu menuRefresh;
    private javax.swing.JMenuItem menuRetailerSellsFrame;
    private javax.swing.JMenuItem menuSavedSalary;
    private javax.swing.JMenuItem menuSearchDueBill;
    private javax.swing.JMenuItem menuSendNotification;
    private javax.swing.JMenuItem menuUpdateEmployee;
    private javax.swing.JMenuItem menuUpdate_Customer;
    private javax.swing.JMenuItem menu_customer_wise;
    private javax.swing.JTextField tvCash;
    private javax.swing.JTextField tvExpense;
    private javax.swing.JMenuItem updateCat;
    private javax.swing.JMenuItem updateFood;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.InvCustomer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import restaurantpos.DatabaseUtils;

/**
 *
 * @author Hajjaz
 */
public class CustomerDBHelper {
    
    public static List<InvCustomer> getListOfCustomers(Connection connection)
    {
        String sql = "Select * from inv_customer";
        List<InvCustomer> listData = new ArrayList<InvCustomer>();
        
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery(sql);
            // print the results
            while (rs.next()) {
                InvCustomer invCustomer = new InvCustomer(Integer.parseInt(rs.getString(1)),rs.getString(2),
                        rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6));
                listData.add(invCustomer);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }
        return listData;
    }
    
    public static Double getDueAmountOfCustomer(int id, Connection connection) {
        Double dueAmount = 0.0;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_id = " + id);
            // print the results
            while (rs.next()) {
                dueAmount = Double.parseDouble(rs.getString("due_amount"));

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return dueAmount;
    }
    
    public static int getCustomerId(String name,Connection connection) {
        int id = -1;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_name = '" + name +"'");
            // print the results
            while (rs.next()) {
                id = Integer.valueOf(rs.getString("customer_id"));

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return id;
    }
    
    public static String getCustomerAddress(int id,Connection connection) {
        String address = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_id = " + id);
            // print the results
            while (rs.next()) {
                address = rs.getString("customer_address");

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return address;
    }
    
    public static String getCustomerPhone(int id,Connection connection) {
        String phone = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_id = " + id);
            // print the results
            while (rs.next()) {
                phone = rs.getString("customer_contact");

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return phone;
    }
    
    public static String getCustomerEmail(int id,Connection connection) {
        String email = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_id = " + id);
            // print the results
            while (rs.next()) {
                email = rs.getString("customer_email");

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return email;
    }
    
    public static boolean updateDueAmountOfCustomer(int id, Double totalDue, Connection connection) {
        
        boolean result = false;
        //inserting data into transaction table
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("Update inv_customer set due_amount = " + totalDue + " where customer_id = " + id);

            statement.execute();
            result = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //System.out.println("Error while inserting data!");
        } finally {
            DatabaseUtils.close(statement);
        }
        return result;
    }
    
    public static void setCustomerActivity(int customer_id, String bill_no, String total_amount, String due_amount, String discount_amount, String due_collection, Connection connection){    
        Date time1 = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat(" dd-MMM-yyyy ");
        String date1 = formatter1.format(time1);
        PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement
        ("INSERT INTO customer_activity (customer_id, bill_no, total_amount, due_amount, discount_amount, due_collection, activity_date)VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, String.valueOf(customer_id));
                statement.setString(2, bill_no);
                statement.setString(3, total_amount);
                statement.setString(4, due_amount);
                statement.setString(5, discount_amount);
                statement.setString(6, due_collection);
                statement.setString(7, date1);

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
    
    public static String customerPhoneExists(String phone, Connection connection){
        String name = "";
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM inv_customer where customer_contact = '" + phone +"'");
            // print the results
            while (rs.next()) {
                name = rs.getString("customer_name");

            }
        } catch (Exception e) {
            //System.out.println(e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return name;
    }
}

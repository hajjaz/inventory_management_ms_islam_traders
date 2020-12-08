/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Model.InvCustomer;
import Model.InvItem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import restaurantpos.DatabaseUtils;

/**
 *
 * @author Hajjaz
 */
public class DB_Utilities {
    
    public static List<String> getListOfColumnValues(String sql,Connection connection, int columnIndex)
    {
        List<String> listData = new ArrayList<String>();
        int id = 0;
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery(sql);
            // print the results
            while (rs.next()) {
                listData.add(rs.getString(columnIndex));
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
    
    public static List<InvItem> getListOfItems(String sql,Connection connection)
    {
        List<InvItem> listData = new ArrayList<InvItem>();
        
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery(sql);
            // print the results
            while (rs.next()) {
                InvItem invItem = new InvItem(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)),
                        rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),Integer.parseInt(rs.getString(7)));
                listData.add(invItem);
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
    
    public static InvItem getItem(String sql,Connection connection)
    {
        InvItem invItem = null;
        
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery(sql);
            // print the results
            while (rs.next()) {
                invItem = new InvItem(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)),
                        rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),Integer.parseInt(rs.getString(7)));
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        }finally
            {
                DatabaseUtils.close(rs);
                DatabaseUtils.close(stm1);
            }
        return invItem;
    }
    
    /*public static List<InvCustomer> getListOfCustomers(String sql, Connection connection)
    {
        List<InvCustomer> listData = new ArrayList<InvCustomer>();
        
        //getting transaction id    
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery(sql);
            // print the results
            while (rs.next()) {
                InvCustomer invCustomer = new InvCustomer(Integer.parseInt(rs.getString(1)), rs.getString(2),
                        rs.getString(3),rs.getString(4));
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
    }*/
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Utilities.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import restaurantpos.DatabaseUtils;

/**
 *
 * @author Hajjaz
 */
public class ItemDBHelper {
    public static void AddItem_Quantity(String choice, int quantity, int catid, Connection connection) {

        int q = 0, cat_id = catid;
        q = getQuantity(choice, cat_id, connection);

        int quan = quantity + q;
        String itemName = "'" + choice + "'";
        String editQuantity = "'" + quan + "'";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("UPDATE inv_item set item_quantity =" + editQuantity + " where item_name = " + itemName + " and cat_id = " + cat_id + "");
            statement.execute();
            Utilities.setInfoLog("ItemDBHelper", "AddItem_Quantity()", "Quantity Added Successfully", "Quantity Added Successfully");
        } catch (Exception e) {
            Utilities.setErrorLog("ItemDBHelper", "AddItem_Quantity()", "Exception", e.toString());
        } finally {
            DatabaseUtils.close(statement);
        }
    }
    
    public static int getQuantity(String name, int cat, Connection connection) {
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
            Utilities.setErrorLog("ItemDBHelper", "AddItem_Quantity()", "Exception", e.toString());
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return quantity;
    }
    
    public static boolean updateItemBuyingPrice(String name, int buy_price, int catid, Connection connection){
        boolean result;
        String itemName = "'" + name + "'";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("UPDATE inv_item set item_price =" + buy_price + " where item_name = " + itemName + " and cat_id = " + catid + "");
            result = statement.execute();
            Utilities.setInfoLog("ItemDBHelper", "updateItemBuyingPrice()", "Buying Price Updated Successfully", "Buying Price Updated Successfully");
        } catch (Exception e) {
            result = false;
            Utilities.setErrorLog("ItemDBHelper", "AddItemupdateItemBuyingPrice_Quantity()", "Exception", e.toString());
        } finally {
            DatabaseUtils.close(statement);
        }
        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurantpos.InventoryPOS;

/**
 *
 * @author Hajjaz
 */
public class SMSSender {

    public static int sendSMS(String phone, String txt) {

        String message = txt + "\n" + InventoryPOS.strSMSMessage1 + "\n" + InventoryPOS.strSMSMessage2;
        String username = InventoryPOS.smsAPIUserName;
        String password = InventoryPOS.smsAPIPassword;
        String address = InventoryPOS.smsAPI;
        String port = InventoryPOS.smsPort;
        String response = "";

        URL url;
        try {
            url = new URL(
                    address + ":" + port + "/SendSMS?username=" + username + "&password=" + password
                    + "&phone=" + phone + "&message=" + URLEncoder.encode(message, "UTF-8"));

            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response = inputLine;
                System.out.println("Hajjaz SMS Response: "+inputLine);
            }
            bufferedReader.close();
        } catch (MalformedURLException ex) {
            System.out.println("Hajjaz MalformedURLException: " + ex.toString());
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Hajjaz UnsupportedEncodingException: " + ex.toString());
        } catch (IOException ex) {
            System.out.println("Hajjaz IOException: " + ex.toString());
        }
        
        System.out.println("Hajjaz SMS Response: "+response);
        if(response != null && response.contains("\"status\":\"200\"")){
            return 1;
        }
        else{
            return -1;
        }
    }

}

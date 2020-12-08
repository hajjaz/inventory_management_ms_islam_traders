/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import restaurantpos.DatabaseUtils;
import restaurantpos.InventoryPOS;

/**
 *
 * @author USER
 */
public class Utilities {
    public static String logString = "";
    public static String UserName = "";
    public static String DBName = "";
    
    /**
    * @param tag the Name of the tag
    * @return String
    */
    public static String ReadSystemProperties(String tag) {
        String result = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config\\system.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            result = prop.getProperty(tag);
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadSystemProperties", "IOException", "While Reading system.properties File", ex.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadSystemProperties", "IOException", "While Reading system.properties File", e.toString());
                }
            }
        }
        return result == null ? "" : result;
    }
    
    /**
    * @param tag the Name of the tag
    * @return String
    */
    public static String ReadCommonProperties(String tag) {
        String result = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config\\common.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            result = prop.getProperty(tag);
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadCommonProperties", "IOException", "While Reading common.properties File", ex.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadCommonProperties", "IOException", "While Reading common.properties File", e.toString());
                }
            }
        }
        return result == null ? "" : result;
    }
    
    public static HashMap<String, String> ReadLanguageProperties(String language) {
        
        Properties prop = new Properties();
        InputStream input = null;
        HashMap<String, String> mymap= new HashMap<String, String>();

        try {
            input = new FileInputStream("config\\"+language+".properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                mymap.put(key, value);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadLanguageProperties", "IOException", "While Reading language.properties File", ex.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Utilities.setErrorLog(Utilities.class.getSimpleName()+" ReadLanguageProperties", "IOException", "While Reading language.properties File", e.toString());
                }
            }
        }
        return mymap;
    }
    
    /**
     * set ErrorLog
     *
     * @param strFunctionName, strErrorOrException, strErrorDescription, strErrorString
     * @return void
     */
    public static void setErrorLog(String strFunctionName, String strErrorOrException, String strErrorDescription, 
            String strErrorString){
        //this.logString += Utilities.SystemCurrentDateTime()+" Error [menuLogOutActionPerformed-SQLException] Closing Database - ("+ex.toString()+")\n";
        StringBuilder logString1 = new StringBuilder();
        logString1.append(Utilities.getSystemCurrentDateTime()+" Error ["+strFunctionName+"-"+
                strErrorOrException+"] "+ strErrorDescription+" - ("+strErrorString+")\n");
        Utilities.WriteLogFile(logString1);
    }
    
    /**
     * set ErrorLog
     *
     * @param strFunctionName, strInfo, strInfoDescription, strInfoString
     * @return void
     */
    public static void setInfoLog(String strFunctionName, String strInfo, String strInfoDescription, 
            String strInfoString){
        //this.logString += Utilities.SystemCurrentDateTime()+" Error [menuLogOutActionPerformed-SQLException] Closing Database - ("+ex.toString()+")\n";
        StringBuilder logString1 = new StringBuilder();
        logString1.append(Utilities.getSystemCurrentDateTime()+" Info  ["+strFunctionName+"-"+
                strInfo+"] "+ strInfoDescription+" - ("+strInfoString+")\n");
        Utilities.WriteLogFile(logString1);
    }
    
    /**
     * get SystemCurrentDateFormat
     *
     * @param FormatString
     * @return String
     */
    public static String getSystemCurrentDateFormat(String FormatString){
        String strDateTime = "";
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(FormatString);//2016-05-29 13:00:00.039
        strDateTime = formatter.format(date);
        return strDateTime;
    }
    
    /**
     * get SystemCurrentDateTime
     *
     * @return String
     */
    public static String getSystemCurrentDateTime(){
        String strDateTime = "";
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//2016-05-29 13:00:00.039
        strDateTime = formatter.format(date);
        return strDateTime;
    }
    
    /**
     * Returns a string formatted for SQL query according to Database
     * 
     * @param date date format of the DateTime
     * @return String
     */
    public static String getSQLDate(Date date){
        
        String strDateTime = "";
        InventoryPOS.ReadSystemProperties();
        boolean OracleDB, MySQLDB;
        OracleDB = InventoryPOS.dbDataSourceOracle;
        MySQLDB = InventoryPOS.dbDataSourceMySQL;
        
        if(OracleDB){
            DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy ");
            String strDate = formatter.format(date);
            String from = "'"+strDate+"'";
            strDateTime = "to_date("+from+")";
        }
        else if(MySQLDB){
            java.sql.Date sqlDateObject = new java.sql.Date(date.getTime());
            strDateTime = "'"+sqlDateObject+"'";
        }
        else{
            
        }
        
        return strDateTime;
    }
    
    
    /**
     * WriteLogFile
     *
     * @param Utilities.logString
     * @return void
     */
    public static void WriteLogFile(StringBuilder strBuilder){
       
        String logDirectory = InventoryPOS.logDirectory;
        String systemID = InventoryPOS.systemID;
        String systemShortCode = InventoryPOS.systemShortCode;
        
        
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH");
        String Time = formatter.format(date);
        FileWriter fw = null;
        try {
            File file = new File(logDirectory+"\\"+systemID+"\\"+systemShortCode+"_"+Time+".log");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(strBuilder.toString());
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            strBuilder.append("IOException : "+ex.toString()+"\n");
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                strBuilder.append("IOException : "+ex.toString()+"\n");
            }
        }
        
//        try (FileWriter fw1 = new FileWriter("outfilename", true);
//                BufferedWriter bw = new BufferedWriter(fw);
//                PrintWriter out = new PrintWriter(bw)) {
//            out.println("the text");
//            //more code
//            out.println("more text");
//            //more code
//        } catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }
    }
    
    public static void initializeUI(Class objClass, JButton id){
        HashMap<String, String> mymap= new HashMap<String, String>();
        mymap = ReadLanguageProperties("bangla");
        JButton btn = id;
        Font f = new Font("Siyam Rupali",Font.BOLD,btn.getFont().getSize());
        btn.setFont(f);
        String text = mymap.get(btn.getActionCommand());
//        int lgth=text.length() * 10;
//        //btn.setBounds(1,1,lgth,20);
//        Dimension newDimension =  new Dimension(lgth+40,btn.getHeight());
//        btn.setPreferredSize(newDimension);
//        btn.setBounds(new Rectangle(btn.getLocation(), btn.getPreferredSize()));
        btn.setText(text);
    }
    
    public static HashMap<String, String> getPropertiesMap() {
        HashMap<String, String> mymap= new HashMap<String, String>();
        mymap = ReadLanguageProperties("bangla");
        return mymap;
    }
    
    public static void setJButtons(HashMap<String, JButton> keyMap) {
        HashMap<String, String> propertyMap = getPropertiesMap();
        for (String key: keyMap.keySet()) {
            try {
                JButton btn = keyMap.get(key);
                 String text = propertyMap.get(key);
                 Font f = new Font("Siyam Rupali",Font.BOLD,btn.getFont().getSize());
                   btn.setFont(f);
                    btn.setText(text);
            } catch (Exception jse) {
            }
        }
    }
    
    
    public static Date getSubscriptionDate(Connection connection) {
        Date date = null;
        java.sql.Statement stm1 = null;
        ResultSet rs = null;
        try {
            stm1 = connection.createStatement();
            rs = stm1.executeQuery("SELECT * FROM subscription");
            // print the results
            while (rs.next()) {
                date = rs.getDate(1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
        } finally {
            DatabaseUtils.close(rs);
            DatabaseUtils.close(stm1);
        }
        return date;
    }

    /**
     * Get a diff between two dates
     *
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}

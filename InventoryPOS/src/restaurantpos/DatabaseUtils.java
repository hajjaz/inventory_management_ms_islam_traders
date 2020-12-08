/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurantpos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Cor2Tect
 */
public class DatabaseUtils {
    
    public static void WriteActivityLog(Connection connection, String userName, String activity, String remarks)
    {
        //getting system date
        Date time = Calendar.getInstance().getTime();
         DateFormat formatter = new SimpleDateFormat(" dd-MMM-yyyy");
        String date = formatter.format(time);
        
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp sqlDateObject = new java.sql.Timestamp(calendar.getTime().getTime());
        
        //inserting data into inv_vat table
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement
        ("INSERT INTO activity_log (userName, activity_date, activity, remarks)VALUES (?, ?, ?, ?)");
                statement.setString(1, userName);
                statement.setTimestamp(2, sqlDateObject);
                statement.setString(3, activity);
                statement.setString(4, remarks);

                statement.execute();
                System.out.println("Data inserted Successfully.");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("Error while inserting data!");
                setErrorLog(DatabaseUtils.class.getSimpleName()+" WriteActivityLog()", "Exception", "INSERT INTO activity_log (userName, activity_date, activity, remarks)VALUES (?, ?, ?, ?)", e.toString());
                //JOptionPane.showMessageDialog(rootPane, e.toString(),"Error",1);
            }finally
            {
                DatabaseUtils.close(statement);
            }
    }
    
    /**
     * WriteLogFile
     *
     * @param Utilities.logString
     * @return void
     */
    public static void WriteLogFile(StringBuilder strBuilder){
       
        String logDirectory = "lib";
        String systemShortCode = "POS";
        
        
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH");
        String Time = formatter.format(date);
        FileWriter fw = null;
        try {
            File file = new File(logDirectory+"\\"+systemShortCode+"_"+Time+".log");
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
    
    public static void close(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            // log exception here.
        }
    }

    // similar methods for ResultSet and Statement
    public static void close(ResultSet connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            // log exception here.
        }
    }
    
    public static void close(Statement connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            // log exception here.
        }
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
        logString1.append(getSystemCurrentDateTime()+" Error ["+strFunctionName+"-"+
                strErrorOrException+"] "+ strErrorDescription+" - ("+strErrorString+")\n");
        WriteLogFile(logString1);
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
     * set ErrorLog
     *
     * @param strFunctionName, strInfo, strInfoDescription, strInfoString
     * @return void
     */
    public static void setInfoLog(String strFunctionName, String strInfo, String strInfoDescription, 
            String strInfoString){
        //this.logString += Utilities.SystemCurrentDateTime()+" Error [menuLogOutActionPerformed-SQLException] Closing Database - ("+ex.toString()+")\n";
        StringBuilder logString1 = new StringBuilder();
        logString1.append(getSystemCurrentDateTime()+" Info  ["+strFunctionName+"-"+
                strInfo+"] "+ strInfoDescription+" - ("+strInfoString+")\n");
        WriteLogFile(logString1);
    }
}

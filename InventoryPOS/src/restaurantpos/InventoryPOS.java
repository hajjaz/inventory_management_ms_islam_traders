/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

import Utilities.Utilities;

/**
 *
 * @author Cor2Tect
 */
public class InventoryPOS {

    public static boolean printPageSizeBig;
    public static boolean printPageSizeInvoice;
    public static boolean printPageSizeSmall;
    public static String printFileDirectory = "";
    public static String printBillFileName = "";
    public static String printReportFileName = "";
    public static String printSuppliersFileName = "";
    
    public static String printFontSizeHeader="";
    public static String printFontSizeItem="";
    public static String printFontSizeTableHeader="";
    public static String printFontSizeFooter="";
        
    //Database
    public static boolean dbDataSourceOracle;
    public static boolean dbDataSourceMySQL;
    public static String dbMySQLDriverClassName = "";
    public static String dbMySQLURL = "";
    public static String dbMySQLUser = "";
    public static String dbMySQLPassword = "";
    public static String dbOracleDriverClassName = "";
    public static String dbOracleURL = "";
    public static String dbOracleUser = "";
    public static String dbOraclePassword = "";
    //DB Tables
    public static String tableUser = "";
    public static String tableEmployee = "";
    public static String tableEmployeeSalary = "";
    public static String tableEmployeeSalaryAdvance = "";
    public static String tableSupplier = "";
    public static String tableSellItem = "";
    public static String tableSellCategory = "";
    public static String tableSellBillSerial = "";
    public static String tableSellBillCard = "";
    public static String tableSellBillDue = "";
    public static String tableSellDueCollection = "";
    public static String tableSellVat = "";
    public static String tableSellVatTransaction = "";
    public static String tableSellDiscount = "";
    public static String tableSellTransaction = "";
    public static String tableBuyCategory = "";
    public static String tableBuyItem = "";
    public static String tableBuyTransaction = "";
    public static String tableBuyBillSerial = "";
    //Logging
    public static String logDirectory = "";
    public static String systemID = "";
    public static String systemShortCode = "";
    //Customer Name, Address & others
    public static String strBillMessage1 = "";
    public static String strBillMessage2 = "";
    public static String strContactInfo = "";
    public static String companyName = "";
    public static String companyAddress = "";
    public static String strBillCompanyAddress1 = "";
    public static String strBillCompanyAddress2 = "";
    public static String strOtherMessage = "";
    public static String strElseCode = "";
    public static String strVatRegNo = "";
    public static double vat = 7.5;
    //Item Button Properties
    public static int buttonWidth;
    public static int buttonHeight;
    public static int horizontalGap;
    public static int verticalGap;
    public static int numberOfColumns;
    public static int fontSize;
    public static int fontType;
    public static String fontName;
    public static int fontColorR;
    public static int fontColorG;
    public static int fontColorB;

    //Email properties
    public static boolean sendEmail;
    public static String emailUserName = "", emailPassword = "", smtpHost = "", smtpPort = "";

    //SMS Properties
    public static boolean sendSMS;
    public static String smsAPI = "", smsAPIUserName = "", smsAPIPassword = "", smsPort = "", strSMSMessage1 = "", strSMSMessage2 = "";
    
    //Retailer enable/disable
    public static boolean retailerSellsFrameOn;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ReadSystemProperties();
        new MainForm1().setVisible(true);
    }

    /**
     * loads the properties file
     */
    public static void ReadSystemProperties() {

        //Print File Properties
        printPageSizeBig = Boolean.parseBoolean(Utilities.ReadSystemProperties("printPageSizeBig"));
        printPageSizeSmall = Boolean.parseBoolean(Utilities.ReadSystemProperties("printPageSizeSmall"));
        printPageSizeInvoice = Boolean.parseBoolean(Utilities.ReadSystemProperties("printPageSizeInvoice"));
        printFileDirectory = Utilities.ReadSystemProperties("printFileDirectory");
        printBillFileName = Utilities.ReadSystemProperties("printBillFileName");
        printReportFileName = Utilities.ReadSystemProperties("printReportFileName");
        printSuppliersFileName = Utilities.ReadSystemProperties("printSuppliersFileName");
        printFontSizeHeader= Utilities.ReadSystemProperties("printFontSizeHeader");
        printFontSizeItem= Utilities.ReadSystemProperties("printFontSizeItem");
        printFontSizeTableHeader=Utilities.ReadSystemProperties("printFontSizeTableHeader");
        printFontSizeFooter=Utilities.ReadSystemProperties("printFontSizeFooter");

        //Database Properties
        dbDataSourceOracle = Boolean.parseBoolean(Utilities.ReadSystemProperties("dbDataSourceOracle"));
        dbDataSourceMySQL = Boolean.parseBoolean(Utilities.ReadSystemProperties("dbDataSourceMySQL"));
        //MySQL
        dbMySQLDriverClassName = Utilities.ReadSystemProperties("dbMySQLDriverClassName");
        dbMySQLURL = Utilities.ReadSystemProperties("dbMySQLURL");
        dbMySQLUser = Utilities.ReadSystemProperties("dbMySQLUser");
        dbMySQLPassword = Utilities.ReadSystemProperties("dbMySQLPassword");
        //Oracle
        dbOracleDriverClassName = Utilities.ReadSystemProperties("dbOracleDriverClassName");
        dbOracleURL = Utilities.ReadSystemProperties("dbOracleURL");
        dbOracleUser = Utilities.ReadSystemProperties("dbOracleUser");
        dbOraclePassword = Utilities.ReadSystemProperties("dbOraclePassword");

        //Logging Properties
        logDirectory = Utilities.ReadSystemProperties("logDirectory");
        systemID = Utilities.ReadSystemProperties("systemID");
        systemShortCode = Utilities.ReadSystemProperties("systemShortCode");

        //Customer Name, Address & Others
        strBillMessage1 = Utilities.ReadSystemProperties("strBillMessage1");
        strBillMessage2 = Utilities.ReadSystemProperties("strBillMessage2");
        strContactInfo = Utilities.ReadSystemProperties("strContactInfo");
        companyName = Utilities.ReadSystemProperties("companyName");
        companyAddress = Utilities.ReadSystemProperties("companyAddress");
        strBillCompanyAddress1 = Utilities.ReadSystemProperties("strBillCompanyAddress1");
        strBillCompanyAddress2 = Utilities.ReadSystemProperties("strBillCompanyAddress2");
        strElseCode = Utilities.ReadSystemProperties("strElseCode");
        strVatRegNo = Utilities.ReadSystemProperties("strVatRegNo");
        vat = Double.parseDouble(Utilities.ReadSystemProperties("vat"));

        //Item Button Properties
        buttonWidth = Integer.parseInt(Utilities.ReadSystemProperties("buttonWidth"));
        buttonHeight = Integer.parseInt(Utilities.ReadSystemProperties("buttonHeight"));
        horizontalGap = Integer.parseInt(Utilities.ReadSystemProperties("horizontalGap"));
        verticalGap = Integer.parseInt(Utilities.ReadSystemProperties("verticalGap"));
        numberOfColumns = Integer.parseInt(Utilities.ReadSystemProperties("numberOfColumns"));
        fontSize = Integer.parseInt(Utilities.ReadSystemProperties("fontSize"));
        fontType = Integer.parseInt(Utilities.ReadSystemProperties("fontType"));
        fontName = Utilities.ReadSystemProperties("fontName");
        fontColorR = Integer.parseInt(Utilities.ReadSystemProperties("fontColorR"));
        fontColorG = Integer.parseInt(Utilities.ReadSystemProperties("fontColorG"));
        fontColorB = Integer.parseInt(Utilities.ReadSystemProperties("fontColorB"));

        //Database Tables from common.properties
        tableUser = Utilities.ReadCommonProperties("tableUser");
        tableEmployee = Utilities.ReadCommonProperties("tableEmployee");
        tableEmployeeSalary = Utilities.ReadCommonProperties("tableEmployeeSalary");
        tableEmployeeSalaryAdvance = Utilities.ReadCommonProperties("tableEmployeeSalaryAdvance");
        tableSupplier = Utilities.ReadCommonProperties("tableSupplier");
        tableSellItem = Utilities.ReadCommonProperties("tableSellItem");
        tableSellCategory = Utilities.ReadCommonProperties("tableSellCategory");
        tableSellBillSerial = Utilities.ReadCommonProperties("tableSellBillSerial");
        tableSellBillCard = Utilities.ReadCommonProperties("tableSellBillCard");
        tableSellBillDue = Utilities.ReadCommonProperties("tableSellBillDue");
        tableSellDueCollection = Utilities.ReadCommonProperties("tableSellDueCollection");
        tableSellVat = Utilities.ReadCommonProperties("tableSellVat");
        tableSellVatTransaction = Utilities.ReadCommonProperties("tableSellVatTransaction");
        tableSellDiscount = Utilities.ReadCommonProperties("tableSellDiscount");
        tableSellTransaction = Utilities.ReadCommonProperties("tableSellTransaction");
        tableBuyCategory = Utilities.ReadCommonProperties("tableBuyCategory");
        tableBuyItem = Utilities.ReadCommonProperties("tableBuyItem");
        tableBuyTransaction = Utilities.ReadCommonProperties("tableBuyTransaction");
        tableBuyBillSerial = Utilities.ReadCommonProperties("tableBuyBillSerial");

        //Email properties
        sendEmail = Boolean.parseBoolean(Utilities.ReadSystemProperties("sendEmail"));
        emailUserName = Utilities.ReadSystemProperties("emailUserName");
        emailPassword = Utilities.ReadSystemProperties("emailPassword");
        smtpHost = Utilities.ReadSystemProperties("smtpHost");
        smtpPort = Utilities.ReadSystemProperties("smtpPort");

        //SMS Properties
        sendSMS = Boolean.parseBoolean(Utilities.ReadSystemProperties("sendSMS"));
        smsAPI = Utilities.ReadSystemProperties("smsAPI");
        smsAPIUserName = Utilities.ReadSystemProperties("smsAPIUserName");
        smsAPIPassword = Utilities.ReadSystemProperties("smsAPIPassword");
        smsPort = Utilities.ReadSystemProperties("smsPort");
        strSMSMessage1 = Utilities.ReadSystemProperties("strSMSMessage1");
        strSMSMessage2 = Utilities.ReadSystemProperties("strSMSMessage2");
        
        //Retailer enable/disable
        retailerSellsFrameOn=Boolean.parseBoolean(Utilities.ReadSystemProperties("retailerSellsFrameOn"));
    }
}

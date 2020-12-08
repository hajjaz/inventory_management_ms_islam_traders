/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurantpos.InventoryPOS;

/**
 *
 * @author Hajjaz
 */
public class HTMLGenerator_Original {

    public static String GenerateHTMLFile(int receiptNo, String[][] S, int arrLength, int RowNo, String customerName, String address) {
        String result = "";
        File file = null;
        int rowNo = 10;
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
        try {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
            String Time = formatter.format(date);
            DateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
            String Time1 = formatter1.format(date);

            file = new File(InventoryPOS.printFileDirectory + "\\" + Time1 + receiptNo + ".html");

            StringBuffer htmlString = new StringBuffer();
            htmlString.append("<!DOCTYPE html>" + "\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "\n"
                    + "<head>" + "\n"
                    + "    <title>Bill</title>" + "\n"
                    + "   <style>" + "\n"
                    + "    footer {" + "\n"
                    + "    font-size: " + InventoryPOS.printFontSizeFooter + ";" + "\n"
                    + "    color: #000000;" + "\n"
                    + "   text-align: center;" + "\n"
                    + "   }" + "\n"
                    + "   @page {" + "\n"
                    + "     size: A4;" + "\n"
                    + "    margin: 11mm 8mm 8mm 8mm;" + "\n"
                    + "   }" + "\n"
                    + "   @media print {" + "\n"
                    + "    footer {" + "\n"
                    + "       position: fixed;" + "\n"
                    + "       bottom: 0;" + "\n"
                    + "     }" + "\n"
                    + "     .content-block, p {" + "\n"
                    + "       page-break-inside: avoid;" + "\n"
                    + "     }" + "\n"
                    + "     html, body {" + "\n"
                    + "       width: 210mm;" + "\n"
                    + "       height: 148mm;" + "\n"
                    + "     }" + "\n"
                    + "   }" + "\n"
                    + "   </style>" + "\n"
                    + "</head>" + "\n"
                    + "<body>" + "\n"
                    + "    <div style=\"margin-top: 1px; margin-left: 1%;\">" + "\n"
                    + "        <table style=\" width: 100%; text-align: center; font-size: " + InventoryPOS.printFontSizeHeader + "; \">" + "\n"
                    + "			<tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeHeader + "; font-weight:bold;\">" + "\n"
                    + "				<td><font face=\"Algerian\" color=\"Black\">" + InventoryPOS.companyName + "</font></td>" + "\n"
                    + "			</tr>" + "\n"
                    + "            <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeItem + "; font-weight:bold\">" + "\n"
                    + "                <td> " + "\n"
                    + "					<br/>" + InventoryPOS.strBillCompanyAddress1 + "\n"
                    + "					<br/>" + InventoryPOS.strBillCompanyAddress2 + "\n"
                    + "					<br/>" + InventoryPOS.strContactInfo + "\n"
                    + "					<br/>Serial #" + receiptNo + "\n"
                    + "				</td>" + "\n"
                    + "			</tr>" + "\n"
                    + "        </table>" + "\n"
                    + "		" + "\n"
                    + "		<div style=\"margin-left: 1%;text-align: center; font-size: " + InventoryPOS.printFontSizeFooter + "; font-weight:bold\">" + "\n"
                    + "\n"
                    + "		<p>" + "\n"
                    + "			<span style=\"background-color: #000000; color: white\">BILL # " + receiptNo + "\n"
                    + "		</p>" + "\n"
                    + "		</div>" + "\n"
                    + "		" + "\n"
                    + "		<table style=\" width: 100%; text-align: center; font-size: " + InventoryPOS.printFontSizeHeader + ";\">" + "\n"
                    + "			<tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeTableHeader + "; font-weight:bold\">" + "\n"
                    + "				<td>To.</td>" + "\n"
                    + "			</tr>" + "\n"
                    + "            <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeItem + "; font-weight:normal\">" + "\n"
                    + "				<td>Bill Date : " + Time + "</td>" + "\n"
                    + "			</tr>" + "\n"
                    + "            <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeItem + "; font-weight:normal\">" + "\n"
                    + "                <td>Name : " + customerName + "</td>" + "\n"
                    + "				<td></td>" + "\n"
                    + "			</tr>" + "\n"
                    + "			<tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeItem + "; font-weight:normal\">" + "\n"
                    + "                <td>Address : " + address + "</td>" + "\n"
                    + //"                <td>Delivery Date : " + Time + "</td>" + "\n" +
                    "			</tr>" + "\n"
                    + "        </table>" + "\n"
                    + "		" + "\n"
                    + "		<table style=\"border: 1px solid black; border-collapse: collapse; font-size: " + InventoryPOS.printFontSizeTableHeader + "; text-align: center; font-weight:bold; margin-top: 5px;\">" + "\n"
                    + "            <tr style=\"font-weight:bold\">" + "\n"
                    + "               <td style=\"border: 1px solid black; width: 100px\">SL No. </td>" + "\n"
                    + "               <td style=\"border: 1px solid black; width: 591px\">Description</td>" + "\n"
                    + "               <td style=\"border: 1px solid black; width: 200px\">Quantity</td>" + "\n"
                    + "               <td style=\"border: 1px solid black; width: 200px\">Rate/Price</td>" + "\n"
                    + "               <td style=\"border: 1px solid black; width: 269px\">Amount</td>" + "\n"
                    + "            </tr>\n");
            /*"			<tr style=\"font-style:bold;border-bottom-style:none; border-top-style: none;\">" +
                "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none; width: 100px\">1. </td>" +
                "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 591px\"> </td>" +
                "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px\"> </td>" +
                "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px\"></td>" +
                "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 269px\"></td>" +
                "           </tr>"*/
            int i = 1;
            Double amountTotal = 0.0;
            for (int j = 1; j <= RowNo; j++) {
                String desc = S[j][1];
                String rate = S[j][2];
                String quan = S[j][0];
                Double q = 0.0, r = 0.0;
//                    if (!quan.equals("") && !rate.equals(""))
//                    {
//                        q = Double.parseDouble(quan);
//                        r = Double.parseDouble(rate);
//                    }
                Double amount = Double.parseDouble(S[j][3]);

                //amount = Math.round(amount);
                //Double amount1 = new Double(decimalFormat.format(amount));
                if (!rate.equals("Total =")) {
                    htmlString.append("			<tr style=\"font-weight:normal;border-bottom-style:none; border-top-style: none;\">" + "\n"
                            + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none; width: 100px\">" + i + ". </td>" + "\n"
                            + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 591px;text-align: left;padding-left:5px\">" + desc + " </td>" + "\n"
                            + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px\">" + quan + " </td>" + "\n"
                            + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px;text-align: right;padding-right:5px\">" + rate + "</td>" + "\n"
                            + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 269px;text-align: right;padding-right:5px\">" + amount + "</td>" + "\n"
                            + "           </tr>\n");
                }
                //amountTotal += amount;
                i++;
            }
            for (int j = 0; j < rowNo - i - arrLength; j++) {
                htmlString.append("			<tr style=\"font-weight:normal;border-bottom-style:none; border-top-style: none;\">" + "\n"
                        + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none; width: 100px\">  </td>" + "\n"
                        + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 591px\">  </td>" + "\n"
                        + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px\">  </td>" + "\n"
                        + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 200px\"> </td>" + "\n"
                        + "               <td style=\"border: 1px solid black; border-bottom-style:none;border-top-style: none;width: 269px;text-align: right;padding-right:10px\"> | </td>" + "\n"
                        + "           </tr>\n");
            }

            //Double amountTotal1 = new Double(decimalFormat.format(amountTotal));
            amountTotal = Double.parseDouble(S[RowNo + 1][3]);
            
            String numberInWord = "Zero";
            if (amountTotal != 0) {
                //NumberConverter n = new NumberConverter();
                //numberInWord = n.Convert(amountTotal);
                NumberToWord n = new NumberToWord();
                numberInWord = n.Convert(amountTotal);
            }
            htmlString.append("			<!-- Last Row -->" + "\n"
                    + "			<tr style=\"font-weight:bold;border-bottom-style:none; border-top-style: none; \">" + "\n"
                    + "                <td style=\"border: 1px solid black;  width: 100px\"> </td>" + "\n"
                    + "                <td style=\"border: 1px solid black; width: 591px\"> </td>" + "\n"
                    + "                <td style=\"border: 1px solid black; width: 200px\"> </td>" + "\n"
                    + "                <td style=\"border: 1px solid black; width: 200px\">Net Payable </td>" + "\n"
                    + "                <td style=\"border: 1px solid black; width: 269px\">" + S[RowNo + 1][3] + " </td>" + "\n"
                    + "            </tr>" + "\n"
                    + "		</table>" + "\n"
                    + "       <table style=\" width: 100%; text-align: center; font-size: " + InventoryPOS.printFontSizeTableHeader + "; \">" + "\n"
                    + "           <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeItem + "; font-weight:normal\">" + "\n"
                    + "               <td>Amount In Words : " + numberInWord + "</td>" + "\n"
                    + "           </tr>" + "\n"
                    + "       </table>" + "\n");

            htmlString.append("	<table style=\" font-size: " + InventoryPOS.printFontSizeItem + "; text-align: center; font-weight:bold; margin-top: 5px;\">" + "\n");
            for (int j = RowNo + 2; j <= arrLength + 1; j++) {
                //if(!S[j][2].equals("Total =")){
                htmlString.append("            <tr style=\"font-weight:bold\">" + "\n"
                        + "               <td style=\" width: 100px\"> </td>" + "\n"
                        + "               <td style=\"width: 591px\"> </td>" + "\n"
                        + "               <td style=\"width: 200px\"> </td>" + "\n"
                        + "               <td style=\"width: 200px; text-align: right\">" + S[j][2] + "</td>" + "\n"
                        + "               <td style=\"width: 269px\">" + S[j][3] + "</td>" + "\n"
                        + "            </tr>\n");
                //}
            }

            htmlString.append("       </table>" + "\n");

            htmlString.append("       <table style=\" width: 100%; text-align: center; font-size: " + InventoryPOS.printFontSizeHeader + "; margin-top: 1% \">" + "\n"
                    + "           <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeTableHeader + "; font-weight:normal\">" + "\n"
                    + "               <td>---------------------</td>" + "\n"
                    + "           </tr>" + "\n"
                    + "           <tr style=\" width: 100%; text-align: left; font-size: " + InventoryPOS.printFontSizeTableHeader + "; font-weight:normal\">" + "\n"
                    + "               <td>Signature :</td>" + "\n"
                    + "               <td>   </td>" + "\n"
                    + "               <td style=\"padding-left:350px\"></td>" + "\n"
                    + "           </tr>" + "\n"
                    + "       </table>" + "\n");

            htmlString.append("     <footer style=\"padding-left:150px\">\n"
                    + "           " + InventoryPOS.strBillMessage1 + "<br/>" + InventoryPOS.strElseCode + "\n"
                    + "       </footer>" + "\n"
                    + "   </body>" + "\n"
                    + "</html>");

            //File file = new File("HealthReport.html");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(htmlString.toString());
            bw.close();

            System.out.println("Done");
            result = htmlString.toString();

        } catch (IOException e) {
            e.printStackTrace();
            result = "Failed";
        }
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(HTMLGenerator_Original.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

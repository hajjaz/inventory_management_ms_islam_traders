package Utilities;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author hajjaz.bin.ibrahim
 */
public class ReadWriteExcelFile {

    //private static final String FILE_PATH = "C:/Users/hajjaz.bin.ibrahim/Documents/NetBeansProjects/Adding_Number/MSISDN.xlsx";
    File excelFile = null;
    String FILE_PATH = null;

    public ReadWriteExcelFile() {

    }

    public ReadWriteExcelFile(File file) {
        this.excelFile = file;
    }
    
    public String WriteExcelFile(List<String> listName,List<String> listQuantity, List<String> listPrice, 
            List<String> listBalance) throws FileNotFoundException, IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
         
        int rowCount = -1,count = 0;
         
        for (String aMsisdn : listName) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = -1;

            //for (Object field : aBook) {
            Cell cell = row.createCell(++columnCount);
            cell.setCellValue(listName.get(count));
            //}
            cell = row.createCell(++columnCount);
            cell.setCellValue(listQuantity.get(count));
            //}
            cell = row.createCell(++columnCount);
            cell.setCellValue(listPrice.get(count));
            //}
            cell = row.createCell(++columnCount);
            cell.setCellValue(listBalance.get(count));
            //}
            
            count++;
        }
         
        //String s = excelFile.getAbsolutePath().replace(excelFile.getName(), "new_"+excelFile.getName());
        try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
            workbook.write(outputStream);
            return "Successfull";
        }
        
    }

    public void WriteExcel(List<String> listPackage, List<String> listDueDate) throws FileNotFoundException, IOException {

        String s = excelFile.getAbsolutePath().replace(excelFile.getName(), "new_"+excelFile.getName());
        FileInputStream fsIP = new FileInputStream(new File(s)); //Read the spreadsheet that needs to be updated

        XSSFWorkbook wb = new XSSFWorkbook(fsIP); //Access the workbook

        XSSFSheet worksheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
        List<String> strList = new ArrayList<String>();
        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");
        List<String> strList1 = new ArrayList<String>();
        strList1.add("a");
        strList1.add("b");
        strList1.add("c");
        strList1.add("d");
        strList1.add("e");
        int rowCount = 0, count = 0;

        for (int i=0; i<strList.size();i++) {
            Row row = worksheet.getRow(++rowCount);

            int columnCount = 1;

            //for (String str1 : strList1) {
                Cell cell = row.getCell(++columnCount);
                System.out.println(strList.get(i)+" "+strList.size());
                cell.setCellValue((String) strList.get(i));
                cell = row.getCell(columnCount+count);
                System.out.println(strList1.get(i)+" "+strList1.size());
                cell.setCellValue(strList1.get(i));
            //}
                count++;
        }

        
        try (FileOutputStream outputStream = new FileOutputStream(new File(s))) {
            wb.write(outputStream);
        }

    }

    /*public List<Numbers> getNumbersListFromExcel() {
        List<Numbers> numberList = new ArrayList<Numbers>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(excelFile.getAbsolutePath());

            // Using XSSF for xlsx format, for xls use HSSF
            Workbook workbook = new XSSFWorkbook(fis);

            int numberOfSheets = workbook.getNumberOfSheets();

            //looping over each workbook sheet
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();

                //iterating over each row
                while (rowIterator.hasNext()) {

                    Numbers number = new Numbers();
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    //Iterating over each cell (column wise)  in a particular row.
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        //The Cell Containing numeric value will contain 
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {

                            //Cell with index 1 contains 
                            if (cell.getColumnIndex() == 0) {
                                number.setMSISDN(cell.getStringCellValue());
                            } //Cell with index 2 contains 
                            else if (cell.getColumnIndex() == 1) {
                                number.setProductNameId(cell.getStringCellValue());
                            }
                        }
                    }
                    //end iterating a row, add all the elements of a row in list
                    numberList.add(number);
                }
            }

            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberList;
    }*/
    
    public List<String> getNumbersListFromExcel(int column) {
        List<String> numberList = new ArrayList<String>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(excelFile.getAbsolutePath());

            // Using XSSF for xlsx format, for xls use HSSF
            Workbook workbook = new XSSFWorkbook(fis);

            int numberOfSheets = workbook.getNumberOfSheets();

            //looping over each workbook sheet
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();

                //iterating over each row
                while (rowIterator.hasNext()) {

                    String number = "";
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    //Iterating over each cell (column wise)  in a particular row.
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        //The Cell Containing numeric value will contain 
                        //if (Cell.CELL_TYPE_STRING == cell.getCellType()) {

                            //Cell with index 1 contains 
                            if (cell.getColumnIndex() == column) {
                                number = cell.getStringCellValue();
                            }
                        //}
                    }
                    //end iterating a row, add all the elements of a row in list
                    numberList.add(number);
                }
            }

            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberList;
    }
}

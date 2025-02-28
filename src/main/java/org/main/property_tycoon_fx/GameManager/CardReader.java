package org.main.property_tycoon_fx.GameManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class CardReader {
    private String description;
    private String action;
    private String type;
    private int nrOfPotLucks;
    private int nrOfOpportunityKnocks;

    public void getCardDetails() {

        try {
            //getting path to the board data .xlsx file
            FileInputStream file = new FileInputStream(new File("data/~$PropertyTycoonCardData.xlsx"));

            //creating a workbook instance for reading the Excel file
            Workbook workbook = new XSSFWorkbook(file);

            //getting the excel sheet (only a single one exists)
            Sheet sheet = workbook.getSheetAt(0);

            //iterate over rows between 5 and 44 (inclusive)
            for (int i = 6; i <= nrOfPotLucks+nrOfOpportunityKnocks+9; i++) {  //card data starts at row 6, there are 9 empty spaces which do not hold card data
                Row row = sheet.getRow(i);
                if (row != null) {  //check if the row is not null
                    for (Cell cell : row) {
                        //process each cell value based on its type
                        int index = cell.getColumnIndex(); //get the index of the column in order to identify what it represents
                        //assigning the data to a variable based on its index
                        switch (index) {
                            case 0:
                                description = cell.getStringCellValue();
                                break;
                            case 1:
                                action = cell.getStringCellValue();
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    }


                }
            }

            //close the workbook
            workbook.close();
            file.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

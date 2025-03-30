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
    private int nrOfCards;
    private LinkedList<Card> cardList;

    public void getCardDetails() {
        nrOfPotLucks = 17;
        nrOfOpportunityKnocks = 16;
        nrOfCards = 0;
        cardList = new LinkedList<>();
        try {
            //getting path to the board data .xlsx file
            FileInputStream file = new FileInputStream(new File("data/PropertyTycoonCardData.xlsx"));

            //creating a workbook instance for reading the Excel file
            Workbook workbook = new XSSFWorkbook(file);

            //getting the excel sheet (only a single one exists)
            Sheet sheet = workbook.getSheetAt(0);

            //iterate over rows between 5 and 44 (inclusive)
            for (int i = 2; i <= nrOfPotLucks+nrOfOpportunityKnocks+2; i++) {
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
                            case 3:
                                action = cell.getStringCellValue();
                                break;
                            default:
                                break;
                        }
                    }
                    nrOfCards++;
                    if (nrOfCards <= nrOfPotLucks) {
                        type = "Potluck";
                    } else  {
                        type = "Opportunity Knocks";
                    }
                    Card card = new Card(type, description, action);
                    cardList.add(card);
                }
            }

            //close the workbook
            workbook.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Card> returnCardList() {return cardList;}
}

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class TileReader {

    private int noOfTiles = 40; //total number of tiles on the board
    private double position; //position on the board
    private String space; //name of the tile
    private String group; //group of the tile
    private String action; //action that the tile may trigger
    private boolean canBeBought; //shows if tile can be bought by the player
    private double cost; //shows cost of tile if it can be bought by player
    private double[] rent; //shows cost of rent if player has property on the tile and someone steps on it

    private LinkedList<Tile> tileList; //linked list of all of the tiles on the board
    public void getTileDetails() {
        rent = new double[6];
        tileList = new LinkedList<>();
        try {
            //getting path to the board data .xlsx file
            FileInputStream file = new FileInputStream(new File("data/PropertyTycoonBoardData.xlsx"));

            //creating a workbook instance for reading the Excel file
            Workbook workbook = new XSSFWorkbook(file);

            //getting the excel sheet (only a single one exists)
            Sheet sheet = workbook.getSheetAt(0);

            //iterate over rows between 5 and 44 (inclusive)
            for (int i = 4; i <= noOfTiles+3; i++) {  //row 5 is index 4, row 44 is index 43
                Row row = sheet.getRow(i);
                //clear all arraylists so they don't stack up with each iteration of the row
                if (row != null) {  //check if the row is not null
                    for (Cell cell : row) {
                        //process each cell value based on its type
                        int index = cell.getColumnIndex(); //get the index of the column in order to identify what it represents

                        //assigning the data to a variable based on its index
                        switch (index) {
                            case 0:
                                 position = cell.getNumericCellValue();
                                 break;
                            case 1:
                                space = cell.getStringCellValue();
                                break;
                            case 2:
                                break;
                            case 3:
                                group = cell.getStringCellValue();
                                break;
                            case 4:
                                action = cell.getStringCellValue();
                                break;
                            case 5:
                                if (cell.getStringCellValue().equalsIgnoreCase("No")) {
                                    canBeBought = false;
                                } else if (cell.getStringCellValue().equalsIgnoreCase("Yes")) {
                                    canBeBought = true;
                                }
                                break;
                            case 6:
                                break;
                            case 7:
                                cost = cell.getNumericCellValue();
                                break;
                            case 8:
                                try {
                                    rent[0] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            case 9:
                                break;
                            case 10:
                                try {
                                    rent[1] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            case 11:
                                try {
                                    rent[2] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            case 12:
                                try {
                                    rent[3] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            case 13:
                                try {
                                    rent[4] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            case 14:
                                try {
                                    rent[5] = cell.getNumericCellValue();
                                } catch (Exception e) {

                                }
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    }
                    Tile tile = new Tile(position, space, group, action, canBeBought, cost, rent.clone(), 0); //rent.clone needed so all tiles don't get the same copy of the rent array
                    tileList.add(tile);
                    action = null; //need to reset action as not all tiles have one
                    cost = -1; //need to reset cost so it doesn't carry over to tiles that don't have one
                    group = null; //need to reset group as not all tiles have one
                }
            }

            //close the workbook
            workbook.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList returnTileList() {
        return tileList;
    }
}

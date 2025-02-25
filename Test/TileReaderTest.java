import org.junit.jupiter.api.Test;
import org.main.property_tycoon_fx.GameManager.Tile;
import org.main.property_tycoon_fx.GameManager.TileReader;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TileReaderTest {
    //Test variables
    TileReader testTileReader = new TileReader();

    @Test
    void testPosition() { //testing that the position value of the tile is correct
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();

        assertEquals(1.0, testTile.getPosition()); //position of the GO tile is 1.0
    }

    @Test
    void testSpace() { //testing that the name of the tile is correct
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals("Go", testTile.getSpace()); //the name of the first tile is Go
    }

    @Test
    void testGroup() { //testing that the group of the tile is correct
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals(null, testTile.getGroup()); //as of 23/02 the group of Go is null, however note that this will chang
    }

    @Test
    void testAction() { //testing that the action value is correct
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals("Collect £200", testTile.getAction()); //the action of Go is to Collect £200
    }

    @Test
    void testCanBeBought() { //testing if the can be brought value of the tile is correct
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals(false, testTile.isCanBeBought()); //the Go tile cannot be bought
    }

    @Test
    void testCost() { //testing the cost of buying the tile
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals(0, testTile.getCost()); /**the Go tile cannot be bought thus its value is 0
         however note that all other tiles that cannot be bought will show -1
         */
    }

    @Test
    void testRent() { //testing the rent charged for housing on tiles
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        double[] testRent = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; //the Go tile cannot be bought thus all rent values are 0
        assertArrayEquals(testRent, testTile.getRent());
    }

    @Test
    void testOwnedBy() { //testing who owns the tile
        testTileReader.getTileDetails();
        LinkedList<Tile> testTileList = testTileReader.returnTileList();
        Tile testTile = testTileList.getFirst();
        assertEquals(0, testTile.getIsOwnedBy()); //the Go tile cannot be bought thus no one owns it
    }
}

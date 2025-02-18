import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TileReaderTest {
    //Test variables
    TileReader testTileReader = new TileReader();
    LinkedList<Tile> testTileList = testTileReader.returnTileList();
    Tile testTile = testTileList.get(0); //tile being tested - can change index value to test different tiles
    @Test
    void testPosition() {

      assertEquals(1.0, testTile.getPosition());
    }

    @Test
    void testSpace() {

        assertEquals("Go", testTile.getSpace());
    }

    @Test
    void testGroup() {

        assertEquals(null, testTile.getGroup());
    }

    @Test
    void testAction() {

        assertEquals("Collect 200£", testTile.getAction());
    }

    @Test
    void testCanBeBought() {

        assertEquals(false, testTile.isCanBeBought());
    }

    @Test
    void tesCost() {

        assertEquals(0, testTile.getCost());
    }

    @Test
    void testRent() {
        double[] testRent = {0,0,0,0,0,0};
        assertEquals(testRent, testTile.getRent());
    }

    @Test
    void testOwnedBy() {

        assertEquals(0, testTile.getIsOwnedBy());
    }
}
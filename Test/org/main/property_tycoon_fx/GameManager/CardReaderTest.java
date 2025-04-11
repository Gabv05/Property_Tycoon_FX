package org.main.property_tycoon_fx.GameManager;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CardReaderTest {
    CardReader testCardReader = new CardReader();

    @Test
    void testType() {
        testCardReader.getCardDetails();
        LinkedList<Card> cardList = testCardReader.returnCardList();
        Card testCard = cardList.getFirst();
        assertEquals("Potluck", testCard.getType());
    }

    @Test
    void testDescription() {
        testCardReader.getCardDetails();
        LinkedList<Card> cardList = testCardReader.returnCardList();
        Card testCard = cardList.getFirst();
        assertEquals("\"You inherit £200\"", testCard.getDescription());
    }

    @Test
    void testAction() {
        testCardReader.getCardDetails();
        LinkedList<Card> cardList = testCardReader.returnCardList();
        Card testCard = cardList.getFirst();
        assertEquals("Bank pays player £200", testCard.getAction());
    }
}
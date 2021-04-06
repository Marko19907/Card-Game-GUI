package no.ntnu.idata2001.oblig3.cardgame;

import no.ntnu.idata2001.oblig3.cardgame.model.PlayingCard;
import no.ntnu.idata2001.oblig3.cardgame.model.PlayingHand;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayingHandTest
{
    @Test
    @DisplayName("Test the constructor")
    void testConstructor()
    {
        PlayingHand playingHand = new PlayingHand();

        assertEquals(52, playingHand.getNumberOfCardsLeft());
    }

    @Test
    @DisplayName("Test drawing cards from the deck")
    void testDrawingCardsFromDeck()
    {
        PlayingHand playingHand = new PlayingHand();
        int numberBefore = playingHand.getNumberOfCardsLeft();

        playingHand.dealHand(5);

        assertEquals(numberBefore - 5, playingHand.getNumberOfCardsLeft());
    }

    @Test
    @DisplayName("Test drawing negative number of cards from the deck")
    void testDrawingNegativeCardsFromDeck()
    {
        PlayingHand playingHand = new PlayingHand();
        int numberBefore = playingHand.getNumberOfCardsLeft();

        playingHand.dealHand(-50);
        List<PlayingCard> cardList = playingHand.getActiveCards();

        assertEquals(numberBefore, playingHand.getNumberOfCardsLeft());
        assertTrue(cardList.isEmpty());
    }

    @Test
    @DisplayName("Test drawing a large number of cards from the deck")
    void testDrawingLargeNumberOfCardsFromDeck()
    {
        PlayingHand playingHand = new PlayingHand();
        int numberBefore = playingHand.getNumberOfCardsLeft();

        playingHand.dealHand(3000);
        List<PlayingCard> cardList = playingHand.getActiveCards();

        assertEquals(numberBefore, playingHand.getNumberOfCardsLeft());
        assertTrue(cardList.isEmpty());
    }

    @Test
    @DisplayName("Test drawing zero cards from the deck")
    void testDrawingZeroCardsFromDeck()
    {
        PlayingHand playingHand = new PlayingHand();
        int numberBefore = playingHand.getNumberOfCardsLeft();

        playingHand.dealHand(0);
        List<PlayingCard> cardList = playingHand.getActiveCards();

        assertEquals(numberBefore, playingHand.getNumberOfCardsLeft());
        assertTrue(cardList.isEmpty());
    }

    @Test
    @DisplayName("Test flush")
    void testFlush()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('H', 1));
        playingHand.addActiveCard(new PlayingCard('H', 2));
        playingHand.addActiveCard(new PlayingCard('H', 3));
        playingHand.addActiveCard(new PlayingCard('H', 4));
        playingHand.addActiveCard(new PlayingCard('H', 5));
        playingHand.addActiveCard(new PlayingCard('D', 12));

        playingHand.getActiveCards();

        assertTrue(playingHand.checkFlush());
    }

    @Test
    @DisplayName("Test flush with zero cards")
    void testFlushWithNoCards()
    {
        PlayingHand playingHand = new PlayingHand();

        playingHand.getActiveCards();

        assertFalse(playingHand.checkFlush());
    }

    @Test
    @DisplayName("Test negative flush")
    void testNegativeFlush()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('H', 1));
        playingHand.addActiveCard(new PlayingCard('H', 2));
        playingHand.addActiveCard(new PlayingCard('S', 7));
        playingHand.addActiveCard(new PlayingCard('H', 4));
        playingHand.addActiveCard(new PlayingCard('H', 5));
        playingHand.addActiveCard(new PlayingCard('D', 12));

        playingHand.getActiveCards();

        assertFalse(playingHand.checkFlush());
    }

    @Test
    @DisplayName("Test checking the Queen of spades card present")
    void testQueenOfSpadesPresence()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('H', 1));
        playingHand.addActiveCard(new PlayingCard('S', 12));

        playingHand.getActiveCards();

        assertTrue(playingHand.checkQueenOfSpadesPresence());
    }

    @Test
    @DisplayName("Test checking the Queen of spades card with no cards")
    void testQueenOfSpadesPresenceWithZeroCards()
    {
        PlayingHand playingHand = new PlayingHand();

        playingHand.getActiveCards();

        assertFalse(playingHand.checkQueenOfSpadesPresence());
    }

    @Test
    @DisplayName("Test checking the Queen of spades card not present")
    void testNoQueenOfSpadesPresence()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('C', 1));
        playingHand.addActiveCard(new PlayingCard('D', 7));

        playingHand.getActiveCards();

        assertFalse(playingHand.checkQueenOfSpadesPresence());
    }

    @Test
    @DisplayName("Test getting all cards of hearts as a String")
    void testCardsOfHeartsString()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('C', 1));
        playingHand.addActiveCard(new PlayingCard('H', 7));

        playingHand.getActiveCards();

        assertFalse(playingHand.getCardsOfHeartsString().isBlank());
        assertTrue(playingHand.getCardsOfHeartsString().equalsIgnoreCase("H7"));
    }

    @Test
    @DisplayName("Test getting all cards of hearts as a String with no cards")
    void testCardsOfHeartsStringWithZeroCards()
    {
        PlayingHand playingHand = new PlayingHand();

        playingHand.getActiveCards();

        assertTrue(playingHand.getCardsOfHeartsString().isBlank());
    }

    @Test
    @DisplayName("Test getting all cards of hearts as a String with no hearts cards")
    void testCardsOfHeartsStringWithoutHeartsCards()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('C', 1));
        playingHand.addActiveCard(new PlayingCard('D', 5));

        playingHand.getActiveCards();

        assertTrue(playingHand.getCardsOfHeartsString().isBlank());
    }

    @Test
    @DisplayName("Test getting the value of used cards")
    void testGettingCardsValue()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('H', 1));
        playingHand.addActiveCard(new PlayingCard('H', 2));
        playingHand.addActiveCard(new PlayingCard('S', 7));
        playingHand.addActiveCard(new PlayingCard('H', 4));
        playingHand.addActiveCard(new PlayingCard('H', 5));
        playingHand.addActiveCard(new PlayingCard('D', 12));
        int cardsValue = 1 + 2 + 7 + 4 + 5 + 12;

        playingHand.getActiveCards();

        assertEquals(cardsValue, playingHand.getUsedCardsValue());
    }

    @Test
    @DisplayName("Test getting the value of used cards without any cards")
    void testGettingCardsValueWithNoCards()
    {
        PlayingHand playingHand = new PlayingHand();
        int cardsValue = 0;

        playingHand.getActiveCards();

        assertEquals(cardsValue, playingHand.getUsedCardsValue());
    }

    @Test
    @DisplayName("Test getting active cards")
    void testGettingActiveCards()
    {
        PlayingHand playingHand = new PlayingHand();
        playingHand.addActiveCard(new PlayingCard('H', 1));
        playingHand.addActiveCard(new PlayingCard('H', 2));
        HashSet<PlayingCard> cardsHashSet = new HashSet<>();
        cardsHashSet.add(new PlayingCard('H', 1));
        cardsHashSet.add(new PlayingCard('H', 2));

        List<PlayingCard> cards = playingHand.getActiveCards();

        assertEquals(cardsHashSet.size(), cards.size());
    }

    @Test
    @DisplayName("Test getting active cards without any active cards")
    void testGettingActiveCardsWithNoCards()
    {
        PlayingHand playingHand = new PlayingHand();

        List<PlayingCard> cards = playingHand.getActiveCards();

        assertTrue(cards.isEmpty());
    }
}
package no.ntnu.idata2001.oblig3.cardgame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DeckOfCardsTest
{
    @Test
    @DisplayName("Test the constructor")
    void testConstructor()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        assertEquals(52, deckOfCards.getDeckSize());
    }

    @Test
    @DisplayName("Test the creation of the full deck of 52 unique cards")
    void testDeckCreation()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(52);
        HashSet<PlayingCard> cardHashSet = new HashSet<>(cardList);

        assertEquals(cardList.size(), cardHashSet.size());
        assertEquals(0, deckOfCards.getDeckSize());
    }

    @Test
    @DisplayName("Test taking 5 random cards from the deck")
    void testTakingOutRandomCards()
    {
        DeckOfCards deckOfCards = new DeckOfCards();
        int initialSize = deckOfCards.getDeckSize();

        List<PlayingCard> cardList = deckOfCards.dealHand(5);

        int afterSize = deckOfCards.getDeckSize();
        assertEquals(initialSize - cardList.size(), afterSize);
    }

    @Test
    @DisplayName("Test uniqueness of random cards taken out")
    void testTakingOutRandomCardsForDuplicates()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(15);
        HashSet<PlayingCard> cardHashSet = new HashSet<>(cardList);

        assertEquals(cardList.size(), cardHashSet.size());
    }

    @Test
    @DisplayName("Test taking out a negative number of random cards")
    void testTakingOutNegativeNumberOfCards()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(-10);

        assertTrue(cardList.isEmpty());
    }

    @Test
    @DisplayName("Test taking out a large number of random cards")
    void testTakingOutLargeNumberOfCards()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(2000);

        assertTrue(cardList.isEmpty());
    }

    @Test
    @DisplayName("Test taking out cards from an empty deck")
    void testTakingOutCardsFromEmptyDeck()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(52);
        List<PlayingCard> emptyCardList = deckOfCards.dealHand(5);

        assertEquals(52, cardList.size());
        assertTrue(emptyCardList.isEmpty());
    }

    @Test
    @DisplayName("Test taking out zero random cards from the deck")
    void testTakingOutZeroCards()
    {
        DeckOfCards deckOfCards = new DeckOfCards();

        List<PlayingCard> cardList = deckOfCards.dealHand(0);

        assertTrue(cardList.isEmpty());
    }
}
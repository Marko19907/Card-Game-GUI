package no.ntnu.idata2001.oblig3.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayingHand
{
    private final HashMap<String, PlayingCard> playingCards;
    private final HashMap<String, PlayingCard> usedCards;
    private final DeckOfCards deck;

    public PlayingHand()
    {
        this.playingCards = new HashMap<>();
        this.usedCards = new HashMap<>();

        this.deck = new DeckOfCards();
    }

    /**
     * Adds the specified number of cards to the active card list
     * @param numberOfCards The number of cards to add,
     *                      must be between and including 1 and 52
     */
    public void dealHand(int numberOfCards)
    {
        if ((numberOfCards >= 1) && (numberOfCards <= 52)) {
            for (PlayingCard card : this.deck.dealHand(numberOfCards)) {
                this.addActiveCard(card);
            }
        }
    }

    /**
     * Adds a given card to the deck
     * @param card The card to add,
     *             can not be null or a duplicate
     */
    public void addActiveCard(PlayingCard card)
    {
        if (card != null) {
            if (!this.playingCards.containsKey(card.getAsString())) {
                this.playingCards.put(card.getAsString(), card);
            }
        }
    }

    /**
     * Returns a List of active cards from the hand
     * @return a List of active cards from the hand
     */
    public List<PlayingCard> getActiveCards()
    {
        List<PlayingCard> activeCards = new ArrayList<>(this.playingCards.values());
        // The list is made with values from a HashMap and therefore is not in a random order
        //TODO: Move the player hand class to ArrayList(s)
        Collections.shuffle(activeCards);

        this.cleanActiveCards();

        return activeCards;
    }

    /**
     * Returns the value of used cards
     * @return The value of used cards as an int or
     * 0 if used cards do not contain any cards
     */
    public int getUsedCardsValue()
    {
        return this.usedCards.values().stream()
                .mapToInt(PlayingCard::getFace)
                .sum();
    }

    /**
     * Returns a String contacting all cards of hearts
     * @return a single String contacting all cards of hearts or
     * blank if used cards do not contain any cards of hearts
     */
    public String getCardsOfHeartsString()
    {
        return this.usedCards.values().stream()
                .filter(card -> card.getAsString().contains("H"))
                .map(PlayingCard::getAsString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns true if the Queen of spades card is present in used cards
     * @return True if the Queen of spades card is present in used cards, false otherwise
     */
    public boolean checkQueenOfSpadesPresence()
    {
        return this.usedCards.values().stream()
                .anyMatch(card -> card.getAsString().equals("S12"));
    }

    /**
     * Returns true if used cards contain a flush
     * A flush contains five or more cards all of the same suit
     * @return true if used cards contain a flush, false otherwise
     */
    public boolean checkFlush()
    {
        return this.usedCards.values().stream()
                .map(PlayingCard::getSuit)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream()
                .anyMatch(card -> card >= 5);
    }

    /**
     * Moves all cards from the active deck to the used deck and
     * cleans the active cards list
     */
    private void cleanActiveCards()
    {
        for (PlayingCard card : this.playingCards.values()) {
            if (!this.usedCards.containsKey(card.getAsString())) {
                this.usedCards.put(card.getAsString(), card);
            }
        }
        this.playingCards.clear();
    }

    /**
     * Returns the number of cards left in the deck
     * @return The number of cards left in the deck
     */
    public int getNumberOfCardsLeft()
    {
        return this.deck.getDeckSize();
    }
}

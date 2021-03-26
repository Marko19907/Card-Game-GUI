package no.ntnu.idata2001.oblig3.cardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        this.cleanActiveCards();

        return activeCards;
    }

    /**
     * Moves all cards from the active deck to the used deck and
     * cleans the active cards list
     */
    public void cleanActiveCards()
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

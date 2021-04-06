package no.ntnu.idata2001.oblig3.cardgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class DeckOfCards represents a full deck of 52 cards
 * The class is responsible for managing the deck and returning sets of random cards
 * @author Marko
 * @version 2021-03-29
 */
public class DeckOfCards
{
    private final char[] suits = {'S', 'H', 'D', 'C'};
    private final HashMap<String, PlayingCard> deck;
    private final Random randomGenerator;

    public DeckOfCards()
    {
        this.deck = new HashMap<>();
        this.randomGenerator = new Random();

        this.createDeck();
    }

    /**
     * Creates the entire deck of cards
     */
    private void createDeck()
    {
        for (char suit : this.suits) {
            for (int rank = 1; rank <= 13; rank++) {
                this.addCard(new PlayingCard(suit, rank));
            }
        }
    }

    /**
     * Adds a given card to the deck
     * @param card The card to add,
     *             can not be null or a duplicate
     */
    private void addCard(PlayingCard card)
    {
        if (card != null) {
            if (!this.deck.containsKey(card.getAsString())) {
                this.deck.put(card.getAsString(), card);
            }
        }
    }

    /**
     * Returns a List of randomly selected cards with a specified size
     * @param numberOfCards The number of cards to return,
     *                      number must be between and including 1 and 52
     * @return Returns a List of randomly selected cards,
     * the List will be empty is the numberOfCards parameter is invalid or
     * the numberOfCards is higher than the number of cards available in the deck
     */
    public List<PlayingCard> dealHand(int numberOfCards)
    {
        List<PlayingCard> toReturn = new ArrayList<>();
        if ((numberOfCards >= 1) && (numberOfCards <= 52) && (numberOfCards <= this.getDeckSize())) {
            int index = 0;
            while (index < numberOfCards) {
                toReturn.add(this.getRandomCard());
                index++;
            }
        }
        return toReturn;
    }

    /**
     * Returns a randomly selected card from the deck
     * @return One random card from the deck, null if the deck is empty
     */
    private PlayingCard getRandomCard()
    {
        PlayingCard card = null;
        if (!this.deck.isEmpty()) {
            ArrayList<PlayingCard> cards = new ArrayList<>(this.deck.values());
            int randomIndex = this.randomGenerator.nextInt(cards.size());
            card = cards.get(randomIndex);

            this.removeCard(card);
        }
        return card;
    }

    /**
     * Removes a given card from the deck
     * @param card The card to remove,
     *             can not be null
     */
    private void removeCard(PlayingCard card)
    {
        if (card != null) {
            this.deck.remove(card.getAsString());
        }
    }

    /**
     * Returns the size of the deck
     * @return the size of the deck as an int
     */
    public int getDeckSize()
    {
        return this.deck.size();
    }
}

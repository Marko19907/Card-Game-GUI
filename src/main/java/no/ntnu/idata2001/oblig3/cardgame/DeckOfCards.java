package no.ntnu.idata2001.oblig3.cardgame;

import java.util.HashMap;

public class DeckOfCards
{
    private final char[] suit = {'S', 'H', 'D', 'C'};
    private final HashMap<String, PlayingCard> deck;

    public DeckOfCards()
    {
        this.deck = new HashMap<>();
        this.createDeck();
    }

    /**
     * Creates the entire deck of cards
     */
    private void createDeck()
    {
        for (char suit : this.suit) {
            for (int rank = 1; rank <= 13; rank++) {
                PlayingCard card = new PlayingCard(suit, rank);
                this.addCard(card);
            }
        }
    }

    /**
     * Adds a given card to the deck
     * @param card The card to add,
     *             can not be null or a duplicate
     */
    public void addCard(PlayingCard card)
    {
        if (card != null) {
            if (!this.deck.containsKey(card.getAsString())) {
                this.deck.put(card.getAsString(), card);
            }
        }
    }
}

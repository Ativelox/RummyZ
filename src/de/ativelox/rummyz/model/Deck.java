package de.ativelox.rummyz.model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Provides a deck of {@link ICard}s. Further has basic functions to
 * {@link Deck#shuffle() shuffle} the deck, {@link Deck#draw() draw} cards
 * {@link Deck#generate() generate} a new deck and
 * {@link Deck#generateFrom(List) generate} a deck from a given list of cards.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Deck {

    /**
     * The underlying {@link Stack} representing this deck.
     */
    private final Stack<ICard> mDeck;

    /**
     * Creates a new {@link Deck}.
     */
    public Deck() {
	mDeck = new Stack<>();

    }

    /**
     * Draw a card from this deck.
     * 
     * @return The card from the top of the deck.
     */
    public ICard draw() {
	return mDeck.pop();

    }

    /**
     * Generates a new basic deck, and shuffles it randomly.
     */
    public void generate() {
	for (int i = 0; i <= 12; i++) {
	    for (int j = 0; j <= 3; j++) {
		mDeck.add(Card.get(j, i));

	    }
	}
	this.shuffle();

    }

    /**
     * Generates a new deck from the given cards. Also shuffles it randomly.
     * 
     * @param cards The cards to be present in this deck.
     */
    public void generateFrom(final List<ICard> cards) {
	mDeck.clear();

	for (final ICard card : cards) {
	    mDeck.add(card);
	}
	this.shuffle();

    }

    /**
     * Whether or not this deck is empty.
     * 
     * @return <tt>True</tt> if there are no more cards present in this deck,
     *         <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
	return mDeck.isEmpty();
    }

    /**
     * Shuffles this deck.
     * 
     * @see Collections#shuffle(List)
     */
    public void shuffle() {
	Collections.shuffle(mDeck);

    }
}

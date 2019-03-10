package de.ativelox.rummyz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * Provides a simple container for multiple {@link ICard}s. The most notable
 * method is {@link Hand#get(ECardType, ECardValue)} which returns the card
 * specified by the given parameters if present in the current hand.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Hand {

    /**
     * The underlying list structure to store the cards.
     */
    private final List<ICard> mCards;

    /**
     * Creates a new {@link Hand}.
     */
    public Hand() {
	mCards = new ArrayList<ICard>();

    }

    /**
     * Adds the given card to this hand.
     * 
     * @param card The card to add.
     * @return <tt>True</tt> if this hand changed as a result of this call,
     *         <tt>false</tt> otherwise.
     */
    public boolean add(final ICard card) {
	return mCards.add(card);

    }

    /**
     * Gets an possibly empty <tt>Optional</tt> of a card for the given
     * <tt>type</tt> and <tt>value</tt> from this hand.
     * 
     * @param type  The type of the card to get.
     * @param value The value of the card to get.
     * @return A non empty <tt>Optional</tt> if the specified card was present in
     *         this hand, an empty <tt>Optional</tt> otherwise.
     */
    public Optional<ICard> get(final ECardType type, final ECardValue value) {
	for (final ICard card : mCards) {
	    if (card.getType() == type && card.getValue() == value) {
		return Optional.of(card);

	    }
	}
	return Optional.ofNullable(null);

    }

    /**
     * Gets the card at the specified index in the underlying structure.
     * 
     * @param index The index of the card to get.
     * @return The card at the index, if the index was legal.
     */
    public ICard get(final int index) {
	return mCards.get(index);

    }

    /**
     * Gets all the cards currently present in this hand.
     * 
     * @return A list of all the cards.
     */
    public List<ICard> getAll() {
	return mCards;

    }

    /**
     * Gets the amount of cards currently present in this hand.
     * 
     * @return The amount of cards mentioned.
     */
    public int getAmount() {
	return mCards.size();

    }

    /**
     * Removes the given card from this hand.
     * 
     * @param card The card to remove.
     * @return <tt>True</tt> if the given card could be removed from this hand,
     *         <tt>false</tt> otherwise.
     */
    public boolean remove(final ICard card) {
	return mCards.remove(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	final StringJoiner sj = new StringJoiner("\t");

	int i = 0;
	for (final ICard card : mCards) {
	    sj.add(i + ": " + card.toString());
	    i++;

	}
	return sj.toString();

    }
}

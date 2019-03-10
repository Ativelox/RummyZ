package de.ativelox.rummyz.model;

import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * Provides a simple {@link ICard} implementation, mainly used as a container
 * for its underlying {@link ECardType} and {@link ECardValue}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Card implements ICard {

    /**
     * Gets a card from two given integer. The result is arbitrary, but ensures that
     * for different parameters, different cards will be generated. One should
     * ensure, that <tt>4 >= id1 >= 0</tt> and <tt>13 >= id2 >= 0</tt>.
     * 
     * @param id1 The first id.
     * @param id2 The second id.
     * @return An arbitrary card defined by <tt>id1</tt> and <tt>id2</tt>.
     */
    public static Card get(final int id1, final int id2) {

	ECardType type = null;
	ECardValue value = null;

	switch (id1) {
	case 0:
	    type = ECardType.CLUB;
	    break;

	case 1:
	    type = ECardType.DIAMOND;
	    break;

	case 2:
	    type = ECardType.HEART;
	    break;

	case 3:
	    type = ECardType.SPADE;
	    break;

	case 4:
	    type = ECardType.NONE;
	    break;

	default:
	    throw new IllegalArgumentException();

	}

	switch (id2) {
	case 0:
	    value = ECardValue.TWO;
	    break;

	case 1:
	    value = ECardValue.THREE;
	    break;

	case 2:
	    value = ECardValue.FOUR;
	    break;

	case 3:
	    value = ECardValue.FIVE;
	    break;

	case 4:
	    value = ECardValue.SIX;
	    break;

	case 5:
	    value = ECardValue.SEVEN;
	    break;

	case 6:
	    value = ECardValue.EIGHT;
	    break;

	case 7:
	    value = ECardValue.NINE;
	    break;

	case 8:
	    value = ECardValue.TEN;
	    break;

	case 9:
	    value = ECardValue.JACK;
	    break;

	case 10:
	    value = ECardValue.QUEEN;
	    break;

	case 11:
	    value = ECardValue.KING;
	    break;

	case 12:
	    value = ECardValue.ACE;
	    break;

	case 13:
	    value = ECardValue.JOKER;
	    break;

	default:
	    throw new IllegalArgumentException();

	}
	return new Card(type, value);

    }

    /**
     * The type of this card.
     */
    private final ECardType mType;

    /**
     * The value of this card.
     */
    private final ECardValue mValue;

    /**
     * Creates a new {@link Card}.
     * 
     * @param type  The type of this card.
     * @param value The value of this card.
     */
    public Card(final ECardType type, final ECardValue value) {
	mType = type;
	mValue = value;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ICard#getType()
     */
    @Override
    public ECardType getType() {
	return mType;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ICard#getValue()
     */
    @Override
    public ECardValue getValue() {
	return mValue;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "[" + mType.toString() + " | " + mValue.toString() + "]";
    }

}

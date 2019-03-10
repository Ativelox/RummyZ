package de.ativelox.rummyz.client.controller;

import java.util.ArrayList;
import java.util.List;

import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;
import de.ativelox.rummyz.model.util.ImmutablePair;

/**
 * Provides a helper class that defines the rules of the game. Has methods to
 * determine the points that a sequence of cards is worth and the like. This
 * should <b>always</b> be used when checking the validity of a <i>play</i>.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class GameRule {

    /**
     * The number of points initially needed to be able to execute normal game flow
     * mechanics, e.g. appending cards to given card sequences on the field.
     */
    private static final int POINT_THRESHOLD = 40;

    /**
     * Checks for a given sequence whether the {@link ECardValue#ACE} should be
     * counted as one.
     * 
     * @param cards    The sequence mentioned.
     * @param aceIndex The index the {@link ECardValue#ACE} is at.
     * @return <tt>True</tt> if the ace should be counted as one, <tt>false</tt>
     *         otherwise.
     */
    private static boolean checkIfAceAsOne(final List<ICard> cards, final int aceIndex) {
	if (aceIndex - 1 >= 0 && cards.get(aceIndex - 1).getValue() == ECardValue.KING) {
	    return false;
	}

	if (cards.size() <= aceIndex + 1) {
	    return false;

	}

	if (cards.get(aceIndex + 1).getValue() == ECardValue.TWO) {
	    return true;
	}

	return false;

    }

    /**
     * Gets a list of starting indices (inclusive) and the ending indices
     * (exclusive) in the given list, from which the induced <tt>sublist</tt> is a
     * valid <i>play</i>. This does <b>not</b> ignore sequence the cards appear in,
     * rather this is used to check whether the given sequence of cards has valid
     * <i>plays</i> and returns the indices these span.
     * 
     * @param cards The sequence of cards to get all valid <i>plays</i> from.
     * @return The indices mentioned.
     */
    public static List<ImmutablePair<Integer, Integer>> getAllValid(final List<ICard> cards) {
	final List<ImmutablePair<Integer, Integer>> result = new ArrayList<>();

	int index = 0;

	while (index < cards.size()) {
	    int toIndex = index + 3;

	    if (toIndex > cards.size()) {
		break;
	    }

	    int curPoints = 0;
	    while (toIndex <= cards.size()) {
		int oldPoints = curPoints;

		List<ICard> sub = cards.subList(index, toIndex);

		curPoints = getPointsAsSame(sub);

		// only check if its a street, if it wasn't a same
		if (curPoints <= 0) {
		    curPoints = getPointsAsStreet(sub);
		}

		if (curPoints > 0) {
		    toIndex++;

		    if (toIndex > cards.size()) {
			// end of list reached, but still searching for more points, just terminate the
			// search here, since its valid.
			result.add(new ImmutablePair<Integer, Integer>(index, toIndex - 1));
			index = toIndex - 1;
			break;
		    }

		    continue;
		}

		if (curPoints == 0 && oldPoints == 0) {
		    index++;
		    break;

		}

		if (curPoints == 0 && oldPoints > 0) {
		    result.add(new ImmutablePair<Integer, Integer>(index, toIndex - 1));
		    index = toIndex - 1;
		    break;
		}
	    }
	}
	return result;

    }

    /**
     * Returns the amount of points the given append action is worth. Checks whether
     * for the given <tt>toAppend</tt> list the given <tt>card</tt> inserted at
     * index <tt>insertIndex</tt> is <b>still</b> a valid play. We assume that the
     * <tt>toAppend</tt> list already represents a valid play.
     * 
     * @param toAppend    The <i>valid play list</i> to add the given card to at the
     *                    given index.
     * @param card        The card to append.
     * @param insertIndex The index at which to insert <tt>card</tt> into
     *                    <tt>toAppend</tt>.
     * @return The amount of points this append action is worth, or <tt>0</tt> if
     *         this append action is invalid.
     */
    public static int getAppendPoints(final List<ICard> toAppend, final ICard card, final int insertIndex) {
	// we assume that the given list of cards is valid, as in it was a valid play by
	// a player, and just assume its either a same, or a street.

	boolean isSame = false;

	if (getPointsAsSame(toAppend) > 0) {
	    isSame = true;

	}

	final ECardType type = card.getType();
	final ECardValue value = card.getValue();

	if (isSame) {
	    for (final ICard checkCard : toAppend) {
		if (!(checkCard.getValue() == value && checkCard.getType() != type)) {
		    return 0;

		}
	    }
	    return getPointsFromValue(value, false);

	}

	// check that type is the same as the type in the street to check against.

	final ECardType streetType = toAppend.get(0).getType();

	if (type != streetType) {
	    return 0;

	}

	if (insertIndex == 0) {
	    if (isNextInStreetRelation(value, toAppend.get(0).getValue())) {
		return getPointsFromValue(value, false);

	    }
	}

	if (insertIndex == toAppend.size()) {
	    if (isNextInStreetRelation(toAppend.get(toAppend.size() - 1).getValue(), value)) {
		return getPointsFromValue(value, false);
	    }
	}

	if (isNextInStreetRelation(toAppend.get(insertIndex - 1).getValue(), value)
		&& isNextInStreetRelation(value, toAppend.get(insertIndex).getValue())) {
	    return getPointsFromValue(value, false);
	}
	return 0;

    }

    /**
     * Gets the amount of points all the <i>plays</i> given are worth summed up. The
     * inner <tt>list</tt> represents a potential <i>play</i>, whereas the outer
     * list holds all these <i>plays</i>. This is a <i>quality of life</i> method
     * which simply sums up all the values for all the inner <tt>lists</tt> given
     * {@link GameRule#getPointsAsSame(List)} and
     * {@link GameRule#getPointsAsStreet(List)}.
     * 
     * @param cards The <tt>list</tt> structure described above.
     * @return The number of points all the given plays are worth summed up.
     *         <tt>0</tt> if all plays were invalid.
     */
    public static int getInstantPoints(final List<List<ICard>> cards) {
	int sum = 0;

	for (final List<ICard> possibleCards : cards) {
	    sum += getPointsAsSame(possibleCards);
	    sum += getPointsAsStreet(possibleCards);

	}
	return sum;

    }

    /**
     * Gets the amount of points the given sequence is worth when viewed as a
     * <i>same</i>. It is considered a valid </i>same</i> sequence when
     * {@link ICard#getValue()} is the same for every card in the sequence, and
     * {@link ICard#getType()} is pairwise different for each card in the sequence.
     * 
     * @param cards The sequence of cards viewed as a same.
     * @return The amount of points this sequence is worth as a same, <tt>0</tt> if
     *         the sequence is an invalid same.
     */
    private static int getPointsAsSame(final List<ICard> cards) {
	if (cards.size() < 3) {
	    return 0;

	}

	ECardValue lastValue = cards.get(0).getValue();
	int sum = getPointsFromValue(lastValue, false);

	for (int i = 1; i < cards.size(); i++) {
	    ECardValue currentValue = cards.get(i).getValue();

	    if (currentValue != lastValue) {
		return 0;
	    }

	    sum += getPointsFromValue(currentValue, false);
	}
	return sum;
    }

    /**
     * Gets the points the given card sequence is worth when viewed as a street. For
     * the given sequence to be a valid street it is required that
     * {@link GameRule#isNextInStreetRelation(ECardValue, ECardValue)} is true for
     * every card following another in the sequence, and that
     * {@link ICard#getType()} is the same for all cards.
     * 
     * @param cards The sequence of cards viewed as a street.
     * @return The number of points this sequence is worth as a street, <tt>0</tt>
     *         if it is an invalid street.
     */
    private static int getPointsAsStreet(final List<ICard> cards) {
	if (cards.size() < 3) {
	    return 0;

	}

	int lastValueInt = getPointsFromValue(cards.get(0).getValue(), GameRule.checkIfAceAsOne(cards, 0));
	ECardType lastType = cards.get(0).getType();
	int sum = lastValueInt;

	ECardValue lastValue = cards.get(0).getValue();

	for (int i = 1; i < cards.size(); i++) {
	    ECardValue currentValue = cards.get(i).getValue();
	    ECardType currentType = cards.get(i).getType();

	    int currentValueInt = 0;

	    if (currentType != lastType) {
		return 0;
	    }

	    if (currentValue == ECardValue.ACE) {
		currentValueInt = getPointsFromValue(currentValue, GameRule.checkIfAceAsOne(cards, i));

	    } else {
		currentValueInt = getPointsFromValue(currentValue, false);

	    }

	    if (!isNextInStreetRelation(lastValue, currentValue)) {
		return 0;

	    }
	    sum += currentValueInt;
	    lastValueInt = currentValueInt;
	    lastType = currentType;
	    lastValue = currentValue;

	}
	return sum;
    }

    /**
     * Gets the amount of points the given {@link ECardValue} is worth in the game.
     * 
     * @param value    The value for which to fetch the points for.
     * @param aceAsOne Whether to count the ace as a value of 1 or the default game
     *                 value.
     * @return The amount the given {@link ECardValue} is worth in the game.
     */
    private static int getPointsFromValue(final ECardValue value, final boolean aceAsOne) {
	switch (value) {
	case ACE:
	    if (aceAsOne) {
		return 1;
	    }
	    return 10;

	case EIGHT:
	    return 8;

	case FIVE:
	    return 5;

	case FOUR:
	    return 4;

	case JACK:
	    return 10;

	case JOKER:
	    return 0;

	case KING:
	    return 10;

	case NINE:
	    return 9;

	case QUEEN:
	    return 10;

	case SEVEN:
	    return 7;

	case SIX:
	    return 6;

	case TEN:
	    return 10;

	case THREE:
	    return 3;

	case TWO:
	    return 2;

	default:
	    return 0;

	}

    }

    /**
     * Defines a relation on cards, that returns <tt>true</tt> if two given cards
     * are in the <i>street relation</i>, e.g. SIX is in a street relation with
     * SEVEN. This relation fulfills the property of <tt>transitivity</tt> but
     * neither fulfills <tt>reflexivity</tt> nor <tt>symmetry</tt>.
     * 
     * @param value1 The first value in the relation.
     * @param value2 The second value in the relation.
     * @return <tt>True</tt> if <tt>value1</tt> is in a street relation with
     *         <tt>value2</tt>, <tt>false</tt> otherwise.
     */
    private static boolean isNextInStreetRelation(final ECardValue value1, final ECardValue value2) {

	if (value1 == ECardValue.ACE && value2 == ECardValue.TWO) {
	    return true;
	}

	if (value1 == ECardValue.TWO && value2 == ECardValue.THREE) {
	    return true;
	}

	if (value1 == ECardValue.THREE && value2 == ECardValue.FOUR) {
	    return true;
	}

	if (value1 == ECardValue.FOUR && value2 == ECardValue.FIVE) {
	    return true;
	}

	if (value1 == ECardValue.FIVE && value2 == ECardValue.SIX) {
	    return true;
	}

	if (value1 == ECardValue.SIX && value2 == ECardValue.SEVEN) {
	    return true;
	}

	if (value1 == ECardValue.SEVEN && value2 == ECardValue.EIGHT) {
	    return true;
	}

	if (value1 == ECardValue.EIGHT && value2 == ECardValue.NINE) {
	    return true;
	}

	if (value1 == ECardValue.NINE && value2 == ECardValue.TEN) {
	    return true;
	}

	if (value1 == ECardValue.TEN && value2 == ECardValue.JACK) {
	    return true;
	}

	if (value1 == ECardValue.JACK && value2 == ECardValue.QUEEN) {
	    return true;
	}

	if (value1 == ECardValue.QUEEN && value2 == ECardValue.KING) {
	    return true;
	}

	if (value1 == ECardValue.KING && value2 == ECardValue.ACE) {
	    return true;
	}

	return false;
    }

    /**
     * Checks whether the given <i>plays</i> are a valid initial play. That is,
     * whether the amount of points it is worth are higher than the
     * {@link GameRule#POINT_THRESHOLD}.
     * 
     * @param cards The <i>plays</i> to check for.
     * @return <tt>true</tt> if the given plays are a valid initial play,
     *         <tt>false</tt> otherwise.
     */
    public static boolean isValidInitial(final List<List<ICard>> cards) {
	return getInstantPoints(cards) >= POINT_THRESHOLD;

    }

    private GameRule() {

    }

}

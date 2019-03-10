package de.ativelox.rummyz.model.util;

import java.util.LinkedList;
import java.util.List;

import de.ativelox.rummyz.client.controller.IPlayerControllerSender;
import de.ativelox.rummyz.model.Card;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;
import de.ativelox.rummyz.server.controller.IGameControllerSender;

/**
 * Provides utility function for encoding and decoding of different data used
 * across this project.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class NetworkUtils {

    /**
     * Decodes the given <tt>args</tt>, being a result from a call to
     * {@link NetworkUtils#encodeAppendCard(ICard, int, int)}, into the
     * <tt>ICard</tt>, <tt>int</tt> and <tt>int</tt> that got passed to the previous
     * call.
     * 
     * @param args The args mentioned.
     * @return A triple containing the <tt>ICard</tt>, <tt>int</tt> and
     *         <tt>int</tt>.
     */
    public static ImmutableTriple<ICard, Integer, Integer> decodeAppendCard(final String[] args) {
	final ICard card = decodeCard(args);
	final int superIndex = Integer.parseInt(args[2]);
	final int insertIndex = Integer.parseInt(args[3]);

	return new ImmutableTriple<>(card, superIndex, insertIndex);

    }

    /**
     * Decodes the given <tt>args</tt>, being a result from a call to
     * {@link NetworkUtils#encodeCard(ICard)}, into the <tt>ICard</tt> that got
     * passed to the previous call.
     * 
     * @param args The args mentioned.
     * @return The decoded <tt>ICard</tt>.
     */
    public static ICard decodeCard(final String[] args) {
	return new Card(ensureEnumConversion(ECardType.class, args[0]),
		ensureEnumConversion(ECardValue.class, args[1]));
    }

    /**
     * This is equivalent to {@link NetworkUtils#decodeCards(String[], int)} passing
     * <tt>0</tt> as the latter parameter.
     * 
     * @param args The args mentioned.
     * @return A sequence of <tt>ICard</tt> decoded from the given array.
     */
    public static List<ICard> decodeCards(final String[] args) {
	return decodeCards(args, 0);
    }

    /**
     * Decodes the given <tt>args</tt>, being a result from a call to
     * {@link NetworkUtils#encodeCards(List)}, into the sequence of <tt>ICard</tt>s
     * passed to the previous call.
     * 
     * @param args        The args mentioned.
     * @param indexOffset the index at which to start decoding from the given args
     *                    array.
     * @return A sequence of decoded <tt>ICard</tt>, for the given
     *         <tt>indexOffset</tt>.
     */
    public static List<ICard> decodeCards(final String[] args, int indexOffset) {
	int numOfCards = Integer.parseInt(args[indexOffset]);
	final List<ICard> cards = new LinkedList<>();

	for (int i = 0; i < numOfCards; i++) {
	    int typeIndex = ((i * 2) + 1) + indexOffset;
	    int valueIndex = typeIndex + 1;

	    cards.add(new Card(ensureEnumConversion(ECardType.class, args[typeIndex]),
		    ensureEnumConversion(ECardValue.class, args[valueIndex])));

	}
	return cards;

    }

    /**
     * Decodes the given <tt>args</tt>, being a result from a call to
     * {@link NetworkUtils#encodeCardsPlayed(List, String[])}, into the sequence of
     * <tt>ICard</tt>s and their associated IDs that got passed to the previous
     * call.
     * 
     * @param args The args mentioned.
     * @return A pair containing the decoded list of sequences of cards and its
     *         associated IDs.
     */
    public static ImmutablePair<List<List<ICard>>, String[]> decodeCardsPlayed(final String[] args) {
	final List<List<ICard>> toReturn = new LinkedList<>();

	int listStartIndex = 0;

	boolean hasIds = false;

	while (listStartIndex < args.length) {
	    if (args[listStartIndex].equals("+")) {
		hasIds = true;
		break;
	    }

	    List<ICard> tempResult = decodeCards(args, listStartIndex);
	    toReturn.add(tempResult);

	    listStartIndex += (2 * tempResult.size()) + 1;

	}

	String[] ids = null;

	if (hasIds) {
	    ids = new String[toReturn.size()];

	    int j = 0;
	    for (int i = listStartIndex + 1; i < args.length; i++) {
		ids[j] = args[i];

		j++;
	    }
	}
	return new ImmutablePair<List<List<ICard>>, String[]>(toReturn, ids);

    }

    /**
     * Encodes the <tt>card</tt>, which gets sent by
     * {@link IPlayerControllerSender#sendAppendCard(ICard, int, int)}, to allow
     * transferring of the two integer along with the <tt>card</tt>.
     * 
     * @param card        The card to encode.
     * @param superIndex  An index to encode alongside with this card.
     * @param insertIndex Another index to encode alongside with this card.
     * @return An encoding for the <tt>card</tt> and the two integers given.
     */
    public static String[] encodeAppendCard(final ICard card, final int superIndex, final int insertIndex) {
	final String[] encodedCard = encodeCard(card);
	return new String[] { encodedCard[0], encodedCard[1], superIndex + "", insertIndex + "" };

    }

    /**
     * Encodes the given <tt>card</tt> into a <tt>String[]</tt>.
     * 
     * @param card The card to encode.
     * @return An encoding for the given card.
     */
    public static String[] encodeCard(final ICard card) {
	return new String[] { card.getType().ordinal() + "", card.getValue().ordinal() + "" };

    }

    public static String[] encodeCards(final List<ICard> toEncode) {
	final String[] args = new String[1 + (toEncode.size() * 2)];

	args[0] = toEncode.size() + "";

	int i = 1;
	for (final ICard card : toEncode) {
	    args[(2 * i) - 1] = card.getType().ordinal() + "";
	    args[2 * i] = card.getValue().ordinal() + "";

	    i++;
	}
	return args;

    }

    /**
     * This is equivalent to {@link NetworkUtils#encodeCardsPlayed(List, String[])}
     * where the latter parameter is <tt>null</tt>, so no ID encoding.
     * 
     * @param toEncode The list of sequence of cards to encode.
     * @return An encoding for the parameters given.
     */
    public static String[] encodeCardsPlayed(final List<List<ICard>> toEncode) {
	return encodeCardsPlayed(toEncode, null);

    }

    /**
     * Encodes the given list of sequence of cards with its associated IDs, being
     * sent by
     * {@link IGameControllerSender#sendCardsPlayedUpdate(int, List, String[])}, to
     * allow transferring of all the card sequences with their IDs.
     * 
     * @param toEncode The list of card sequences to encode.
     * @param ids      The IDs for every card sequence to encode alongside its
     *                 sequence.
     * @return An encoding for the given parameters.
     */
    public static String[] encodeCardsPlayed(final List<List<ICard>> toEncode, final String[] ids) {
	final List<String[]> encodings = new LinkedList<>();
	int idSize = 0;

	if (ids != null) {
	    idSize = ids.length + 1;
	}

	int size = 0;

	for (final List<ICard> cards : toEncode) {
	    final String[] encoded = encodeCards(cards);

	    encodings.add(encodeCards(cards));
	    size += encoded.length;

	}

	final String[] args = new String[size + idSize];

	int j = 0;
	for (final String[] tempArgs : encodings) {
	    for (int i = 0; i < tempArgs.length; i++) {
		args[j] = tempArgs[i];

		j++;
	    }
	}

	if (ids != null) {
	    args[j] = "+";
	    j++;

	    for (int i = 0; i < idSize - 1; i++) {
		args[j + i] = ids[i];

	    }
	}

	return args;
    }

    /**
     * Tries to convert <tt>toConvert</tt> to an enumeration of the given class
     * <tt>c</tt>. Assumes that <tt>toConvert</tt> has been formerly created like
     * <tt>toConvert = c.ENUM.ordinal() + ""</tt>, otherwise the behavior of this
     * method is not defined.
     * 
     * 
     * @param c         The class of the enumeration to convert the given string to.
     * @param toConvert The string to try to convert to an enumeration of the class
     *                  c.
     * @return An enumeration of <tt>c</tt> represented by <tt>toConvert</tt>.
     * @throws IllegalArgumentException If the given string could not be converted
     *                                  to an enumeration of <tt>c</tt>.
     */
    public static <C extends Enum<C>> C ensureEnumConversion(final Class<C> c, final String toConvert) {
	for (final C enumField : c.getEnumConstants()) {
	    if (toConvert.equals(enumField.ordinal() + "")) {
		return enumField;

	    }
	}
	throw new IllegalArgumentException("Could not convert " + toConvert + " to an enum from class " + c);

    }

    private NetworkUtils() {

    }
}

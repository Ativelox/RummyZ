package de.ativelox.rummyz.model.util;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ativelox.rummyz.model.Card;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * Provides JUnit test cases for {@link NetworkUtils}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class NetworkUtilsTest {

    /**
     * Test method for
     * {@link de.ativelox.rummyz.model.util.NetworkUtils#decodeCardsPlayed(java.lang.String[])}.
     */
    @Test
    public void testDecodeCardsPlayed() {
	final List<List<ICard>> cards = new LinkedList<>();
	final List<ICard> inCards = new LinkedList<>();
	final List<ICard> inCard2 = new LinkedList<>();

	final ICard card1 = new Card(ECardType.CLUB, ECardValue.ACE);
	final ICard card2 = new Card(ECardType.DIAMOND, ECardValue.ACE);
	final ICard card3 = new Card(ECardType.SPADE, ECardValue.ACE);
	final ICard card4 = new Card(ECardType.HEART, ECardValue.ACE);

	final ICard card5 = new Card(ECardType.HEART, ECardValue.SIX);
	final ICard card6 = new Card(ECardType.HEART, ECardValue.SEVEN);
	final ICard card7 = new Card(ECardType.HEART, ECardValue.EIGHT);

	inCards.add(card1);
	inCards.add(card2);
	inCards.add(card3);
	inCards.add(card4);

	inCard2.add(card5);
	inCard2.add(card6);
	inCard2.add(card7);

	cards.add(inCards);
	cards.add(inCard2);

	String[] split = NetworkUtils.encodeCardsPlayed(cards, new String[] { "0", "1" });

	ImmutablePair<List<List<ICard>>, String[]> pair = NetworkUtils.decodeCardsPlayed(split);

	List<List<ICard>> decoded = pair.getKey();
	String[] ids = pair.getValue();

	Assert.assertTrue(decoded.get(0).get(0).toString().equals(card1.toString()));
	Assert.assertTrue(decoded.get(0).get(1).toString().equals(card2.toString()));
	Assert.assertTrue(decoded.get(0).get(2).toString().equals(card3.toString()));
	Assert.assertTrue(decoded.get(0).get(3).toString().equals(card4.toString()));
	Assert.assertTrue(decoded.get(1).get(0).toString().equals(card5.toString()));
	Assert.assertTrue(decoded.get(1).get(1).toString().equals(card6.toString()));
	Assert.assertTrue(decoded.get(1).get(2).toString().equals(card7.toString()));

	Assert.assertTrue(ids[0].equals("0"));
	Assert.assertTrue(ids[1].equals("1"));

    }

    /**
     * Test method for
     * {@link de.ativelox.rummyz.model.util.NetworkUtils#encodeCardsPlayed(java.util.List, java.lang.String[])}.
     */
    @Test
    public void testEncodeCardsPlayedListOfListOfCardStringArray() {
	final List<List<ICard>> cards = new LinkedList<>();
	final List<ICard> inCards = new LinkedList<>();
	final List<ICard> inCard2 = new LinkedList<>();

	inCards.add(new Card(ECardType.CLUB, ECardValue.ACE));
	inCards.add(new Card(ECardType.DIAMOND, ECardValue.ACE));
	inCards.add(new Card(ECardType.SPADE, ECardValue.ACE));
	inCards.add(new Card(ECardType.HEART, ECardValue.ACE));

	inCard2.add(new Card(ECardType.HEART, ECardValue.SIX));
	inCard2.add(new Card(ECardType.HEART, ECardValue.SEVEN));
	inCard2.add(new Card(ECardType.HEART, ECardValue.EIGHT));

	cards.add(inCards);
	cards.add(inCard2);

	String[] split = NetworkUtils.encodeCardsPlayed(cards, new String[] { "0", "1" });

	Assert.assertTrue(split[0].equals(inCards.size() + ""));

	Assert.assertTrue(split[1].equals(ECardType.CLUB.ordinal() + ""));
	Assert.assertTrue(split[2].equals(ECardValue.ACE.ordinal() + ""));

	Assert.assertTrue(split[3].equals(ECardType.DIAMOND.ordinal() + ""));
	Assert.assertTrue(split[4].equals(ECardValue.ACE.ordinal() + ""));

	Assert.assertTrue(split[5].equals(ECardType.SPADE.ordinal() + ""));
	Assert.assertTrue(split[6].equals(ECardValue.ACE.ordinal() + ""));

	Assert.assertTrue(split[7].equals(ECardType.HEART.ordinal() + ""));
	Assert.assertTrue(split[8].equals(ECardValue.ACE.ordinal() + ""));

	Assert.assertTrue(split[9].equals(inCard2.size() + ""));

	Assert.assertTrue(split[10].equals(ECardType.HEART.ordinal() + ""));
	Assert.assertTrue(split[11].equals(ECardValue.SIX.ordinal() + ""));

	Assert.assertTrue(split[12].equals(ECardType.HEART.ordinal() + ""));
	Assert.assertTrue(split[13].equals(ECardValue.SEVEN.ordinal() + ""));

	Assert.assertTrue(split[14].equals(ECardType.HEART.ordinal() + ""));
	Assert.assertTrue(split[15].equals(ECardValue.EIGHT.ordinal() + ""));

	Assert.assertTrue(split[16].equals("+"));

	Assert.assertTrue(split[17].equals("0"));
	Assert.assertTrue(split[18].equals("1"));

    }

}

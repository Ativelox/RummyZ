package de.ativelox.rummyz.model.assets;

import java.awt.image.BufferedImage;

import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * Provides static access to assets, images and the like.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see Assets#init()
 *
 */
public final class Assets {

    /**
     * The location of the res folder.
     */
    private static final String RES_FOLDER = "res/";

    /**
     * Every image for the deck.
     */
    public static BufferedImage BLACK_DECK, BLUE_DECK, GREEN_DECK, ORANGE_DECK, PURPLE_DECK, RED_DECK;

    /**
     * Every image for the clubs.
     */
    public static BufferedImage CLUBS_TWO, CLUBS_THREE, CLUBS_FOUR, CLUBS_FIVE, CLUBS_SIX, CLUBS_SEVEN, CLUBS_EIGHT,
	    CLUBS_NINE, CLUBS_TEN, CLUBS_PRINCE, CLUBS_QUEEN, CLUBS_KING, CLUBS_ACE;

    /**
     * An array holding the different deck colors.
     */
    public static BufferedImage[] DECKS;

    /**
     * Every image for the diamonds.
     */
    public static BufferedImage DIAMONDS_TWO, DIAMONDS_THREE, DIAMONDS_FOUR, DIAMONDS_FIVE, DIAMONDS_SIX,
	    DIAMONDS_SEVEN, DIAMONDS_EIGHT, DIAMONDS_NINE, DIAMONDS_TEN, DIAMONDS_PRINCE, DIAMONDS_QUEEN, DIAMONDS_KING,
	    DIAMONDS_ACE;

    /**
     * Every image for the hearts.
     */
    public static BufferedImage HEART_TWO, HEART_THREE, HEART_FOUR, HEART_FIVE, HEART_SIX, HEART_SEVEN, HEART_EIGHT,
	    HEART_NINE, HEART_TEN, HEART_PRINCE, HEART_QUEEN, HEART_KING, HEART_ACE;

    /**
     * Every image for the jokers.
     */
    public static BufferedImage JOKER_BLACK, JOKER_RED;

    /**
     * Every image for the spades.
     */
    public static BufferedImage SPADE_TWO, SPADE_THREE, SPADE_FOUR, SPADE_FIVE, SPADE_SIX, SPADE_SEVEN, SPADE_EIGHT,
	    SPADE_NINE, SPADE_TEN, SPADE_PRINCE, SPADE_QUEEN, SPADE_KING, SPADE_ACE;

    /**
     * The respective array holding each of their card types.
     */
    public static BufferedImage[] SPADES, HEARTS, DIAMONDS, CLUBS, JOKERS;

    /**
     * The image for the tabletop.
     */
    public static BufferedImage TABLETOP;

    /**
     * The height of every card in the sheet.
     */
    private static final int CARD_HEIGHT = 230;

    /**
     * The width of every card in the sheet.
     */
    private static final int CARD_WIDTH = 166;

    /**
     * The x offset of the sheet itself.
     */
    private static final int CARD_X_OFFSET = 3;

    /**
     * The x-axis space between every card in the deck sheet.
     */
    private static final int CARD_X_STEP = 8;

    /**
     * The y offset of the sheet itself.
     */
    private static final int CARD_Y_OFFSET = 3;

    /**
     * The y-axis space between every card in the deck sheet.
     */
    private static final int CARD_Y_STEP = 3;

    /**
     * The file name for the card sheet.
     */
    private static final String CARDS_FILE = "cards.png";

    /**
     * The height of every card in the deck sheet.
     */
    private static final int DECK_CARD_HEIGHT = 250;

    /**
     * The width of every card in the deck sheet.
     */
    private static final int DECK_CARD_WIDTH = 176;

    /**
     * The file name for the deck card sheet.
     */
    private static final String DECK_FILE = "deckCards.png";

    /**
     * The file name for the background displaying a tabletop.
     */
    private static final String TABLETOP_FILE = "tabletop.png";

    /**
     * Gets a {@link BufferedImage} which represents the card specified by the given
     * <tt>type</tt> and <tt>value</tt>. The returned value might not represent
     * accurate data if {@link Assets#init()} has not yet been called.
     * 
     * @param type  The type of the card.
     * @param value The value of the card.
     * @return The {@link BufferedImage} mentioned.
     */
    public static BufferedImage getBy(final ECardType type, final ECardValue value) {
	switch (type) {
	case CLUB:
	    switch (value) {
	    case ACE:
		return CLUBS_ACE;

	    case EIGHT:
		return CLUBS_EIGHT;

	    case FIVE:
		return CLUBS_FIVE;

	    case FOUR:
		return CLUBS_FOUR;

	    case JACK:
		return CLUBS_PRINCE;

	    case JOKER:
		return null;

	    case KING:
		return CLUBS_KING;

	    case NINE:
		return CLUBS_NINE;

	    case QUEEN:
		return CLUBS_QUEEN;

	    case SEVEN:
		return CLUBS_SEVEN;

	    case SIX:
		return CLUBS_SIX;

	    case TEN:
		return CLUBS_TEN;

	    case THREE:
		return CLUBS_THREE;

	    case TWO:
		return CLUBS_TWO;

	    default:
		break;

	    }
	    break;

	case DIAMOND:
	    switch (value) {
	    case ACE:
		return DIAMONDS_ACE;

	    case EIGHT:
		return DIAMONDS_EIGHT;

	    case FIVE:
		return DIAMONDS_FIVE;

	    case FOUR:
		return DIAMONDS_FOUR;

	    case JACK:
		return DIAMONDS_PRINCE;

	    case JOKER:
		return null;

	    case KING:
		return DIAMONDS_KING;

	    case NINE:
		return DIAMONDS_NINE;

	    case QUEEN:
		return DIAMONDS_QUEEN;

	    case SEVEN:
		return DIAMONDS_SEVEN;

	    case SIX:
		return DIAMONDS_SIX;

	    case TEN:
		return DIAMONDS_TEN;

	    case THREE:
		return DIAMONDS_THREE;

	    case TWO:
		return DIAMONDS_TWO;

	    default:
		break;

	    }
	    break;

	case HEART:
	    switch (value) {
	    case ACE:
		return HEART_ACE;

	    case EIGHT:
		return HEART_EIGHT;

	    case FIVE:
		return HEART_FIVE;

	    case FOUR:
		return HEART_FOUR;

	    case JACK:
		return HEART_PRINCE;

	    case JOKER:
		return null;

	    case KING:
		return HEART_KING;

	    case NINE:
		return HEART_NINE;

	    case QUEEN:
		return HEART_QUEEN;

	    case SEVEN:
		return HEART_SEVEN;

	    case SIX:
		return HEART_SIX;

	    case TEN:
		return HEART_TEN;

	    case THREE:
		return HEART_THREE;

	    case TWO:
		return HEART_TWO;

	    default:
		break;

	    }
	    break;

	case SPADE:
	    switch (value) {
	    case ACE:
		return SPADE_ACE;

	    case EIGHT:
		return SPADE_EIGHT;

	    case FIVE:
		return SPADE_FIVE;

	    case FOUR:
		return SPADE_FOUR;

	    case JACK:
		return SPADE_PRINCE;

	    case JOKER:
		return null;

	    case KING:
		return SPADE_KING;

	    case NINE:
		return SPADE_NINE;

	    case QUEEN:
		return SPADE_QUEEN;

	    case SEVEN:
		return SPADE_SEVEN;

	    case SIX:
		return SPADE_SIX;

	    case TEN:
		return SPADE_TEN;

	    case THREE:
		return SPADE_THREE;

	    case TWO:
		return SPADE_TWO;

	    default:
		break;

	    }
	    break;

	default:
	    break;

	}
	return null;
    }

    /**
     * Gets the height for the cards.
     * 
     * @return The height mentioned.
     */
    public static int getCardHeight() {
	return CARD_HEIGHT / 2;
    }

    /**
     * Gets the width for the cards.
     * 
     * @return The width mentioned.
     */
    public static int getCardWidth() {
	return CARD_WIDTH / 2;
    }

    /**
     * Initializes the Asset fields. This should be called before trying to access
     * any member, since this assures that the members actually holds accurate data.
     */
    public static void init() {

	BufferedImage cardSheet = Utils.loadImage(RES_FOLDER + CARDS_FILE).get();
	BufferedImage tabletop = Utils.loadImage(RES_FOLDER + TABLETOP_FILE).get();
	BufferedImage deckSheet = Utils.loadImage(RES_FOLDER + DECK_FILE).get();

	SPADES = new BufferedImage[13];
	HEARTS = new BufferedImage[13];
	DIAMONDS = new BufferedImage[13];
	CLUBS = new BufferedImage[13];
	JOKERS = new BufferedImage[2];
	DECKS = new BufferedImage[6];

	SPADE_TWO = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_THREE = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_FOUR = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_FIVE = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_SIX = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_SEVEN = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	SPADE_EIGHT = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_NINE = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_TEN = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_PRINCE = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_QUEEN = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_KING = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	SPADE_ACE = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);

	SPADES[0] = SPADE_TWO;
	SPADES[1] = SPADE_THREE;
	SPADES[2] = SPADE_FOUR;
	SPADES[3] = SPADE_FIVE;
	SPADES[4] = SPADE_SIX;
	SPADES[5] = SPADE_SEVEN;
	SPADES[6] = SPADE_EIGHT;
	SPADES[7] = SPADE_NINE;
	SPADES[8] = SPADE_TEN;
	SPADES[9] = SPADE_PRINCE;
	SPADES[10] = SPADE_QUEEN;
	SPADES[11] = SPADE_KING;
	SPADES[12] = SPADE_ACE;

	HEART_TWO = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	HEART_THREE = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT);
	HEART_FOUR = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_FIVE = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_SIX = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_SEVEN = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_EIGHT = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_NINE = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_TEN = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_PRINCE = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_QUEEN = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET + 2 + 2 * CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	HEART_KING = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	HEART_ACE = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET + CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);

	HEARTS[0] = HEART_TWO;
	HEARTS[1] = HEART_THREE;
	HEARTS[2] = HEART_FOUR;
	HEARTS[3] = HEART_FIVE;
	HEARTS[4] = HEART_SIX;
	HEARTS[5] = HEART_SEVEN;
	HEARTS[6] = HEART_EIGHT;
	HEARTS[7] = HEART_NINE;
	HEARTS[8] = HEART_TEN;
	HEARTS[9] = HEART_PRINCE;
	HEARTS[10] = HEART_QUEEN;
	HEARTS[11] = HEART_KING;
	HEARTS[12] = HEART_ACE;

	DIAMONDS_TWO = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_THREE = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_FOUR = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	DIAMONDS_FIVE = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_SIX = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_SEVEN = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_EIGHT = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_NINE = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_TEN = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_PRINCE = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_QUEEN = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	DIAMONDS_KING = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET + 5 + 5 * CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	DIAMONDS_ACE = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);

	DIAMONDS[0] = DIAMONDS_TWO;
	DIAMONDS[1] = DIAMONDS_THREE;
	DIAMONDS[2] = DIAMONDS_FOUR;
	DIAMONDS[3] = DIAMONDS_FIVE;
	DIAMONDS[4] = DIAMONDS_SIX;
	DIAMONDS[5] = DIAMONDS_SEVEN;
	DIAMONDS[6] = DIAMONDS_EIGHT;
	DIAMONDS[7] = DIAMONDS_NINE;
	DIAMONDS[8] = DIAMONDS_TEN;
	DIAMONDS[9] = DIAMONDS_PRINCE;
	DIAMONDS[10] = DIAMONDS_QUEEN;
	DIAMONDS[11] = DIAMONDS_KING;
	DIAMONDS[12] = DIAMONDS_ACE;

	CLUBS_TWO = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_THREE = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_FOUR = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_FIVE = cardSheet.getSubimage(CARD_X_OFFSET + 3 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_SIX = cardSheet.getSubimage(CARD_X_OFFSET + 2 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_SEVEN = cardSheet.getSubimage(CARD_X_OFFSET + CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	CLUBS_EIGHT = cardSheet.getSubimage(CARD_X_OFFSET, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT, CARD_WIDTH,
		CARD_HEIGHT);
	CLUBS_NINE = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_TEN = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_PRINCE = cardSheet.getSubimage(CARD_X_OFFSET + 6 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_QUEEN = cardSheet.getSubimage(CARD_X_OFFSET + 5 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_KING = cardSheet.getSubimage(CARD_X_OFFSET + 4 * CARD_WIDTH, CARD_Y_OFFSET + 4 + 4 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);
	CLUBS_ACE = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET + 3 + 3 * CARD_HEIGHT,
		CARD_WIDTH, CARD_HEIGHT);

	CLUBS[0] = CLUBS_TWO;
	CLUBS[1] = CLUBS_THREE;
	CLUBS[2] = CLUBS_FOUR;
	CLUBS[3] = CLUBS_FIVE;
	CLUBS[4] = CLUBS_SIX;
	CLUBS[5] = CLUBS_SEVEN;
	CLUBS[6] = CLUBS_EIGHT;
	CLUBS[7] = CLUBS_NINE;
	CLUBS[8] = CLUBS_TEN;
	CLUBS[9] = CLUBS_PRINCE;
	CLUBS[10] = CLUBS_QUEEN;
	CLUBS[11] = CLUBS_KING;
	CLUBS[12] = CLUBS_ACE;

	JOKER_RED = cardSheet.getSubimage(CARD_X_OFFSET + 8 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);
	JOKER_BLACK = cardSheet.getSubimage(CARD_X_OFFSET + 7 * CARD_WIDTH, CARD_Y_OFFSET, CARD_WIDTH, CARD_HEIGHT);

	JOKERS[0] = JOKER_RED;
	JOKERS[1] = JOKER_BLACK;

	TABLETOP = tabletop;

	BLACK_DECK = deckSheet.getSubimage(0, 0, DECK_CARD_WIDTH, DECK_CARD_HEIGHT);
	BLUE_DECK = deckSheet.getSubimage(DECK_CARD_WIDTH + CARD_X_STEP, 0, DECK_CARD_WIDTH, DECK_CARD_HEIGHT);
	GREEN_DECK = deckSheet.getSubimage((DECK_CARD_WIDTH + CARD_X_STEP) * 2, 0, DECK_CARD_WIDTH, DECK_CARD_HEIGHT);
	ORANGE_DECK = deckSheet.getSubimage(0, DECK_CARD_HEIGHT + CARD_Y_STEP, DECK_CARD_WIDTH, DECK_CARD_HEIGHT);
	PURPLE_DECK = deckSheet.getSubimage(DECK_CARD_WIDTH + CARD_X_STEP, DECK_CARD_HEIGHT + CARD_Y_STEP,
		DECK_CARD_WIDTH, DECK_CARD_HEIGHT);
	RED_DECK = deckSheet.getSubimage((DECK_CARD_WIDTH + CARD_X_STEP) * 2, DECK_CARD_HEIGHT + CARD_Y_STEP,
		DECK_CARD_WIDTH, DECK_CARD_HEIGHT);

	DECKS[0] = BLACK_DECK;
	DECKS[1] = BLUE_DECK;
	DECKS[2] = GREEN_DECK;
	DECKS[3] = ORANGE_DECK;
	DECKS[4] = PURPLE_DECK;
	DECKS[5] = RED_DECK;
    }

    private Assets() {

    }

}

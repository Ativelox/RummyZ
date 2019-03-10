package de.ativelox.rummyz.model;

/**
 * Provides a basic {@link IPlayer} implementation.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Player implements IPlayer {

    /**
     * The hand of this player.
     */
    private final Hand mHand;

    /**
     * The ID of this player.
     */
    private int mPlayerId;

    /**
     * Creates a new {@link Player}.
     */
    public Player() {
	mHand = new Hand();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.IPlayer#getCards()
     */
    @Override
    public Hand getCards() {
	return mHand;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.IPlayer#getId()
     */
    @Override
    public int getId() {
	return mPlayerId;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.model.IPlayer#onCardLost(de.ativelox.rummyz.model.Card)
     */
    @Override
    public void onCardLost(final ICard card) {
	mHand.remove(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.model.IPlayer#onCardReceive(de.ativelox.rummyz.model.Card)
     */
    @Override
    public void onCardReceive(final ICard card) {
	mHand.add(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.IPlayer#setId(int)
     */
    @Override
    public void setId(final int playerId) {
	mPlayerId = playerId;

    }

}

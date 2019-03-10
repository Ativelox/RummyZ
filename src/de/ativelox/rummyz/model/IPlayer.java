package de.ativelox.rummyz.model;

/**
 * Provides an interface for player implementations, being mainly used as
 * container objects.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IPlayer {

    /**
     * Gets the {@link Hand} of this player.
     * 
     * @return The hand mentioned.
     */
    Hand getCards();

    /**
     * Gets the id from this player.
     * 
     * @return The id from this player.
     */
    int getId();

    /**
     * Gets called when this instances loses a card.
     * 
     * @param card The card this player lost.
     */
    void onCardLost(final ICard card);

    /**
     * Gets called when this instance receives a card.
     * 
     * @param card The card this player received.
     */
    void onCardReceive(final ICard card);

    /**
     * Sets the id for this player.
     * 
     * @param playerId The new id for this player.
     */
    void setId(final int playerId);

}

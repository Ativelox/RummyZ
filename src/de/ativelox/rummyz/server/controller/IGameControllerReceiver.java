package de.ativelox.rummyz.server.controller;

import java.util.List;

import de.ativelox.rummyz.model.ICard;

/**
 * Provides an interface to receive data to control the game flow.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameControllerReceiver {

    /**
     * Signifies that the player with <tt>playerId</tt> has appended a card to
     * existing cards on the board.
     * 
     * @param playerId    The id of the player.
     * @param card        The card appended.
     * @param superIndex  The index representing the sequence of cards this card got
     *                    appended to.
     * @param insertIndex The index at which this card got appended.
     */
    void onCardAppend(final int playerId, final ICard card, final int superIndex, final int insertIndex);

    /**
     * Signifies that the player with <tt>playerId</tt> has discarded a card.
     * 
     * @param playerId The id of the player.
     * @param card     The card the player discarded.
     */
    void onCardDiscard(final int playerId, final ICard card);

    /**
     * Signifies that the player with <tt>playerId</tt> has requested to draw
     * <tt>amount</tt> cards.
     * 
     * @param playerId The id of the player.
     * @param amount   The amount of cards the player requested.
     */
    void onCardDrawRequest(final int playerId, final int amount);

    /**
     * Signifies that the player with <tt>playerId</tt> has put cards on the board.
     * 
     * @param cards    The list of sequence of cards the player put on the board.
     * @param playerId The id of the player.
     */
    void onCardsPlayed(final List<List<ICard>> cards, final int playerId);

    /**
     * Signifies that the player with <tt>playerId</tt> has picked up the card at
     * the top of the grave yard.
     * 
     * @param playerId The id of the player.
     */
    void onGraveyardPickup(final int playerId);

    /**
     * Signifies that the player with <tt>playerId</tt> is ready to play.
     * 
     * @param playerId The id of the player.
     */
    void onReady(final int playerId);

    /**
     * Signifies that the player with <tt>playerId</tt> has ended his turn.
     * 
     * @param playerId The id of the player.
     */
    void onTurnEnd(final int playerId);

    /**
     * Signifies that the player with <tt>playerId</tt> has won.
     * 
     * @param playerId The id of the player.
     */
    void onVictory(final int playerId);

}

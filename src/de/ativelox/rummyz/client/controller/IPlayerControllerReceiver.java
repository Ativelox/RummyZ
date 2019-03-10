package de.ativelox.rummyz.client.controller;

import java.util.List;

import de.ativelox.rummyz.model.ICard;

/**
 * Provides an interface to receive data needed to play the game.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IPlayerControllerReceiver {

    /**
     * Signifies that this player has been blocked from performing any
     * <tt>critical<tt> actions, i.e. drawing cards or the like.
     */
    void onBlock();

    /**
     * Signifies that a card got appended to an already existing play on the board.
     * 
     * @param card        The card that got appended.
     * @param superIndex  The unique index of the play sent to this player formerly.
     * @param insertIndex The index at which the card was appended to the play
     *                    described by the <tt>superIndex</tt> given.
     */
    void onCardAppendUpdate(final ICard card, final int superIndex, final int insertIndex);

    /**
     * Signifies that a player has played some cards. This is useful to update the
     * associated view, to properly show the state of the game. The ids given act as
     * a unique identifier for each <i>play</i> so they can be used uniformly
     * between all clients and the server.
     * 
     * @param cards A list of card sequences representing the plays made by a
     *              player.
     * @param ids   The IDs for each of the plays sent.
     */
    void onCardsPlayedUpdate(final List<List<ICard>> cards, final String[] ids);

    /**
     * Signifies that this player has received cards.
     * 
     * @param cards The cards the player has been given.
     */
    void onCardsReceived(final List<ICard> cards);

    /**
     * Signifies that this player has lost the game.
     */
    void onDefeat();

    /**
     * Signifies that this player should empty his grave yard (the server shuffled
     * the grave yard into its deck, since it was empty).
     */
    void onGraveyardEmpty();

    /**
     * Signifies that the grave yard got updated by a card (another player discarded
     * a card).
     * 
     * @param card The card to add to the grave yard.
     */
    void onGraveyardUpdate(final ICard card);

    /**
     * Signifies that this player with id {@link playerId} has been registered by
     * the server.
     * 
     * @param playerId The (unique) id of this player.
     */
    void onServerWelcome(final int playerId);

    /**
     * Signifies that this players turn has ended.
     */
    void onTurnEnd();

    /**
     * Signifies that this players turn has started.
     */
    void onTurnStart();

    /**
     * Signifies that this player has won the game.
     */
    void onVictory();

}

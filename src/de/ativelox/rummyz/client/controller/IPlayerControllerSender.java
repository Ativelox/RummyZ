package de.ativelox.rummyz.client.controller;

import java.util.List;

import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;

/**
 * Provides an interface to send data to properly play the game.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IPlayerControllerSender<POut, PIn> {

    /**
     * Registers an {@link INetworkController} for this instance, used to send data
     * to the receiving end.
     * 
     * @param networkController The {@link INetworkController} used to send data.
     */
    void register(final INetworkController<POut, PIn> networkController);

    /**
     * Sends data to signify that this player has appended a card to cards being on
     * the board.
     * 
     * @param card        The card appended.
     * @param superIndex  The unique index used to fetch the sequence of cards this
     *                    got appended to.
     * @param insertIndex The index to append this into the list mentioned.
     * @return <tt>True</tt> if the append action was <i>valid</i>, <tt>false</tt>
     *         otherwise.
     */
    boolean sendAppendCard(final ICard card, final int superIndex, final int insertIndex);

    /**
     * Sends data to signify that this player has played cards onto the board.
     * 
     * @param cards The sequence of cards this player has played.
     */
    void sendCardsPlayed(final List<List<ICard>> cards);

    /**
     * Sends data to signify that this player has discarded a card.
     * 
     * @param card The card discarded by the player.
     */
    void sendDiscardCard(final ICard card);

    /**
     * Sends data to signify that this player wants to draw <tt>amount</tt> cards.
     * 
     * @param amount The amount of cards to request.
     */
    void sendDrawCards(final int amount);

    /**
     * Sends data to signify that this player has been accepted by the server and is
     * ready to play.
     */
    void sendReady();

    /**
     * Sends data to signify that this players turn has ended.
     */
    void sendTurnEnd();

}

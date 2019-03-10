package de.ativelox.rummyz.server.controller;

import java.util.List;

import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;

/**
 * Provides an interface to send data, to properly control the game flow.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameControllerSender<POut, PIn> {

    /**
     * Registers a {@link INetworkController} to this instance, which eases stream
     * communication.
     * 
     * @param playerId          The id of the player.
     * @param networkController The network controller, controlling communication
     *                          with this player.
     */
    void register(final int playerId, final INetworkController<POut, PIn> networkController);

    /**
     * Sends data to the player with <tt>playerId</tt> to block critical actions,
     * e.g. appending cards when he hasn't done his initial play.
     * 
     * @param playerId The id of the player.
     */
    void sendBlock(final int playerId);

    /**
     * Sends a sequence of cards to the player with <tt>playerId</tt>.
     * 
     * @param playerId The id of the player.
     * @param toSend   The sequence of cards to send.
     */
    void sendCards(final int playerId, final List<ICard> toSend);

    /**
     * Sends data to the player with <tt>playerId</tt> that signalizes the player to
     * update his board with the given sequence of cards and their unique IDs.
     * 
     * @param playerId The id of the player.
     * @param cards    The cards the player should add to his board.
     * @param ids      The IDs corresponding to each sequence of cards held by the
     *                 outer list.
     */
    void sendCardsPlayedUpdate(final int playerId, final List<List<ICard>> cards, final String[] ids);

    /**
     * Sends data to the player with <tt>playerId</tt> that signalizes that this
     * player has lost.
     * 
     * @param playerId The id of the player.
     */
    void sendDefeat(final int playerId);

    /**
     * Sends data to the player with <tt>playerId</tt> to start his turn.
     * 
     * @param playerId The id of the player.
     */
    void sendTurnStart(final int playerId);

    /**
     * Sends data to the player with <tt>playerId</tt> that signalizes that this
     * player has won.
     * 
     * @param playerId The id of the player.
     */
    void sendVictory(final int playerId);

    /**
     * Sends data to the player with <tt>playerId</tt> that signalizes that the
     * server has registered this player.
     * 
     * @param playerId
     */
    void sendWelcome(final int playerId);

}

package de.ativelox.rummyz.client.view;

import java.util.List;

import de.ativelox.rummyz.client.controller.IPlayerControllerSender;
import de.ativelox.rummyz.model.ICard;

/**
 * Provides an interface for for the client view.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IClientView<POut, PIn> extends Runnable {

    /**
     * Gets called when a card gets added to this players hand.
     * 
     * @param card The card to add to this players hand.
     */
    void addCard(final ICard card);

    /**
     * Adds a the card at the top of the grave yard to this view.
     */
    void addCardFromGraveyard();

    /**
     * Gets called when a player has played (possibly multiple) sequences of cards
     * on the board.
     * 
     * @param cards The cards that got played on the board.
     * @param ids   The ids that correspond the the sequence of cards that got
     *              played on the board.
     */
    void addCardsPlayed(final List<List<ICard>> cards, final String[] ids);

    /**
     * Gets called when a card got added to the grave yard.
     * 
     * @param card The card to add to the grave yard
     */
    void addCardToGraveyard(final ICard card);

    /**
     * Gets called when points get added to this players point count.
     * 
     * @param points The amount of points this player received.
     */
    void addPoints(final int points);

    /**
     * Gets called when a card got appended to any sequence of cards on the board.
     * 
     * @param card        The card that got appended.
     * @param superIndex  The id of the sequence of cards this card got appended to.
     * @param insertIndex The index this card got inserted to.
     */
    void appendCard(final ICard card, final int superIndex, final int insertIndex);

    /**
     * Gets called when the play issued from this view was invalid.
     */
    void invalidPlay();

    /**
     * Gets called when this players actions have been blocked/unblocked by the
     * server.
     * 
     * @param block Whether to block this player view actions or not.
     */
    void onBlockStateChange(final boolean block);

    /**
     * Gets called when this player has lost.
     */
    void onDefeat();

    /**
     * Gets called when this player should empty his grave yard.
     */
    void onGraveyardEmpty();

    /**
     * Gets called when this players turn has started.
     */
    void onTurnStart();

    /**
     * Gets called when this player has won.
     */
    void onVictory();

    /**
     * Registers the {@link IPlayerControllerSender} to this view. Used to trigger
     * actions in the {@link IPlayerController} from the view.
     * 
     * @param pcSender The controller having access to send requests.s
     */
    void register(final IPlayerControllerSender<POut, PIn> pcSender);

    /**
     * Gets called when a card gets removed from this players hand.
     * 
     * @param card The hand to remove from this players hand.
     */
    void removeCard(final ICard card);

    /**
     * Completely removes the card at the top of the grave yard from this view.
     */
    void removeCardFromGraveyard();

    /**
     * Gets called when this view should be terminated.
     */
    void terminate();

}

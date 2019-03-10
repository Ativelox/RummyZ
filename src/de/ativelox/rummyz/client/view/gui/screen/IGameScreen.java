package de.ativelox.rummyz.client.view.gui.screen;

import java.util.List;

import de.ativelox.rummyz.client.view.gui.items.SnapArea;
import de.ativelox.rummyz.client.view.gui.manager.MouseManager;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.IMoverCallback;
import de.ativelox.rummyz.client.view.gui.utils.IElementContainer;
import de.ativelox.rummyz.model.ICard;

/**
 * Provides an interface for game screens. These handle the current view of all
 * the elements on the screen.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameScreen extends IScreen, IMoverCallback {

    /**
     * Gets called when a card gets appended to a sequence of cards.
     * 
     * @param card        The card that gets appended.
     * @param superIndex  The index associated with the sequence of cards this card
     *                    got appended to.
     * @param insertIndex The index this card got appended at.
     */
    void addAppendCard(final ICard card, final int superIndex, final int insertIndex);

    /**
     * Gets called when sequence of cards get added to the board.
     * 
     * @param cards The list of sequence of cards that get added to the board.
     * @param ids   The IDs associated with each sequence of cards.
     */
    void addCardsPlayed(final List<List<ICard>> cards, final String[] ids);

    /**
     * Adds a card to the grave yard view.
     * 
     * @param card The card to add.
     */
    void addGraveyardCard(final ICard card);

    /**
     * Adds a card to this screens own hand.
     * 
     * @param card The card to add.
     */
    void addOwnCard(final ICard card);

    /**
     * Gets the {@link ICard} from its graphical representation.
     * 
     * @param hoverable The graphical representation of the card.
     * @return The card representation by <tt>hoverable</tt>.
     */
    ICard get(final IHoverable hoverable);

    /**
     * Gets the hand view from this view.
     * 
     * @return The view mentioned.
     */
    IElementContainer<IHoverable> getHandView();

    /**
     * Gets the (multiple) sequence of cards that is present in the current sequence
     * in this screens hand view.
     * 
     * @return The sequences mentioned.
     */
    List<List<ICard>> getPlay();

    /**
     * Gets called when the actions should be blocked / unblocked from the view.
     * 
     * @param block Whether to block or unblock actions from the view.
     */
    void onBlockStateChange(final boolean block);

    /**
     * Gets called when the grave yard view should be emptied.
     * 
     */
    void onGraveyardEmpty();

    /**
     * Removes a card from this screens own hand.
     * 
     * @param card The card to remove.
     */
    void removeOwnCard(final ICard card);

    /**
     * Sets this grave yards snap area to the one given.
     * 
     * @param snap The new snap area.
     */
    void setGraveyardSnap(final SnapArea snap);

    /**
     * Sets the mouse manager for this view.
     * 
     * @param manager The new mouse manager.
     */
    void setMouseManager(final MouseManager manager);

}

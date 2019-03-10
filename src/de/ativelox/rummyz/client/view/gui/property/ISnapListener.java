package de.ativelox.rummyz.client.view.gui.property;

import de.ativelox.rummyz.client.view.gui.manager.MouseManager;

/**
 * Provides a callback for when components snap to snap areas and the mouse
 * button is clicked.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface ISnapListener {

    /**
     * Gets called when this instance is registered to a {@link MouseManager} and a
     * component is snapped to a snap area and a mouse button is pressed.
     * 
     * @param snap       The snap area on which above requirements are fulfilled.
     * @param bound      The component that got snapped to this snap area.
     * @param superIndex The superIndex of the card sequence this snap area belong
     *                   to. Only relevant if the snap area is a part of said card
     *                   sequence.
     * @param isLeft     Whether the snap area is left of the card sequence or
     *                   right. Only relevant if the snap area is a part of said
     *                   card sequence.
     * @return
     */
    boolean onSnap(final IHoverable snap, final IHoverable bound, final int superIndex, final boolean isLeft);

}

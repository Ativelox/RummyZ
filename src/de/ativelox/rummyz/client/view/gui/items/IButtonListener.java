package de.ativelox.rummyz.client.view.gui.items;

import java.util.List;

/**
 * Provides a callback interface for button related actions.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IButtonListener {

    /**
     * Gets all the buttons this instance has registered.
     * 
     * @return A list of all the buttons mentioned.
     */
    List<IButton> getButtons();

    /**
     * Gets called when a button was clicked.
     * 
     * @param button The button that got clicked.
     */
    void onButtonClicked(final IButton button);

}

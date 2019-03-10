package de.ativelox.rummyz.client.view.gui.property;

import java.awt.Graphics;

/**
 * Provides an interface for every element that can get rendered onto the view.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IRenderable {

    /**
     * Gets the layer this component get rendered on.
     * 
     * @return The layer mentioned.
     */
    int getLayer();

    /**
     * Renders this component on the view.
     * 
     * @param g The graphics used to draw component.
     */
    void render(final Graphics g);

}

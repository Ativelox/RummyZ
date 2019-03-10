package de.ativelox.rummyz.client.view.gui.property;

import java.awt.Rectangle;

/**
 * Provides an interface for all components that have a position on the view.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface ISpatial {

    /**
     * Returns a {@link Rectangle} that describes the dimensions of this component.
     * 
     * @return The rectangle mentioned.
     */
    default Rectangle getBoundingBox() {
	return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Gets this components height.
     * 
     * @return The components height.
     */
    int getHeight();

    /**
     * Gets this components width.
     * 
     * @return The components width.
     */
    int getWidth();

    /**
     * Gets this components x coordinate.
     * 
     * @return The components x coordinate.
     */
    int getX();

    /**
     * Gets this components y coordinate.
     * 
     * @return The components y coordinate.
     */
    int getY();

}

package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides an interface for every component that is moveable after creation.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see ISpatial
 * @see IRenderable
 *
 */
public interface IMoveable extends ISpatial, IRenderable {

    /**
     * Sets the layer for this component.
     * 
     * @param layer The new layer.
     */
    void setLayer(final int layer);

    /**
     * Sets the x coordinate for this component.
     * 
     * @param newX the new x coordinate for this component.
     */
    void setX(final int newX);

    /**
     * Sets the y coordinate for this component.
     * 
     * @param newY The new y coordinate for this component.
     */
    void setY(final int newY);
}

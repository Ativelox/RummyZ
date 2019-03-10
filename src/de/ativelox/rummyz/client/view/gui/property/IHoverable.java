package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides an interface for all components that are able to be hovered.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 * @see IMoveable
 */
public interface IHoverable extends IMoveable {

    /**
     * Gets the current label for this instance. Can be used to further distinguish
     * implementations of {@link IHoverable}.
     * 
     * @return The label mentioned.
     */
    EHoverLabel getLabel();

    /**
     * Gets whether this component is currently hovered or not.
     * 
     * @return <tt>True</tt> if this component is currently hovered, <tt>false</tt>
     *         otherwise.
     */
    boolean isHovered();

    /**
     * Gets called when hovering on this component stops.
     */
    void onHoverLoss();

    /**
     * Gets called when hovering on this component starts.
     */
    void onHoverStart();

}

package de.ativelox.rummyz.client.view.gui.screen;

import de.ativelox.rummyz.client.view.gui.property.IRenderable;

/**
 * Provides an interface for screens.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 * @see IRenderable
 */
public interface IScreen extends IRenderable {

    /**
     * Whether to render the lower screen or not.
     * 
     * @return <tt>True</tt> when the lower screen should be rendered,
     *         <tt>false</tt> otherwise.
     */
    boolean renderLower();

}

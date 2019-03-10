package de.ativelox.rummyz.client.view.gui.manager;

import de.ativelox.rummyz.client.view.gui.property.IRenderable;

/**
 * Provides an interface for render managers, handling proper rendering.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see IRenderable
 *
 */
public interface IRenderManager extends IRenderable {

    /**
     * Adds the given renderable to this instances render cycle.
     * 
     * @param renderable The renderable to add.
     */
    void add(final IRenderable renderable);

    /**
     * Removes the given renderable from this instances render cycle.
     * 
     * @param renderable The renderable to remove.
     */
    void remove(final IRenderable renderable);

}

package de.ativelox.rummyz.client.view.gui.property;

import java.util.List;

import de.ativelox.rummyz.client.view.gui.utils.AElementContainer;

/**
 * Provides an interface for callbacks on when elements get adjusted by
 * implementations of {@link AElementContainer}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see ISpatial
 *
 * 
 */
public interface IElementAdjustCallback<E extends ISpatial> {

    /**
     * Gets called when the elements position gets adjusted.
     * 
     * @param elements All the elements currently present, after adjusting.
     */
    void onAdjust(final List<E> elements);

}

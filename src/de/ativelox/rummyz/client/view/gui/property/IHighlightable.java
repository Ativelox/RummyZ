package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides an interface for all components that can get highlighted.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IHighlightable {

    /**
     * Sets this component to either be highlighted or dehighlighted.
     * 
     * @param highlight Whether to highlight this component or not.
     */
    void highlight(final boolean highlight);

}

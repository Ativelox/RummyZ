package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides callbacks for instances registered to implementations of
 * {@link IMover}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IMoverCallback {

    /**
     * Gets called when {@link IMover#bind(IHoverable)} gets called on this
     * instances registered {@link IMover}.
     * 
     * @param moveable The component that got bound.
     */
    void onBind(final IHoverable moveable);

    /**
     * Gets called when {@link IMover#unbind()} gets called on this instances
     * registered {@link IMover}.
     * 
     * @param index    The index of the currently hovered component, when this got
     *                 unbound.
     * @param moveable The component that got unbound.
     */
    void onUnbind(final int index, final IHoverable moveable);

}

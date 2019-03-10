package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides an interface for every object that is able to move components.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IMover {

    /**
     * Binds an element to this instance, such that it can get moved by a source
     * specified by this instance.
     * 
     * @param moveable The component to bind.
     */
    void bind(final IHoverable moveable);

    /**
     * Registers an {@link IMoverCallback} to this instance, able to receive its
     * callbacks.
     * 
     * @param callback The callback to register.
     */
    void register(final IMoverCallback callback);

    /**
     * Unbinds this element from this instance, such that it can't get moved
     * anymore.
     */
    void unbind();

    /**
     * Unregisters the callback given, unable to receive its callbacks.
     * 
     * @param callback The callback to unregister.
     */
    void unregister(final IMoverCallback callback);

}

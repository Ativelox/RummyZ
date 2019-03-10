package de.ativelox.rummyz.client.view.gui.utils;

import java.util.List;
import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IElementAdjustCallback;
import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides an interface for container that contain elements, and display them
 * in a specific style, varying on implementation.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IElementContainer<E extends IMoveable> extends IMoveable {

    /**
     * Adds the given element to this container.
     * 
     * @param toAdd The element to add.
     */
    void addElement(final E toAdd);

    /**
     * Adds the given element at the specified index in the internally used
     * structure.
     * 
     * @param index The index to add this element in.
     * @param toAdd The element to add.
     */
    void addElement(final int index, final E toAdd);

    /**
     * Tries to get an element in this container from the given x and y coordinates.
     * 
     * 
     * @param x The given x coordinate.
     * @param y The given y coordinate.
     * @return <tt>Optional(E)</tt> if there's an element in this container whose
     *         bounding box contains the point <tt>(x, y)</tt>,
     *         <tt>Optional.empty()</tt> otherwise.
     */
    Optional<E> get(final int x, final int y);

    /**
     * Gets all the elements currently present in the container.
     * 
     * @return A list of the elements mentioned.
     */
    List<E> getElements();

    /**
     * Gets the internally used index for the given element.
     * 
     * @param e The element whose index to get.
     * @return The index of the element.
     */
    int indexOf(final E e);

    /**
     * Registers a callback to this instance.
     * 
     * @param callback The callback mentioned.
     */
    void register(final IElementAdjustCallback<E> callback);

    /**
     * Removes the given element from this container.
     * 
     * @param toRemove The element to remove.
     */
    void removeElement(final E toRemove);

}

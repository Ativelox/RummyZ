package de.ativelox.rummyz.client.view.gui.utils;

import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides an implementation for {@link AElementContainer}. Distributes its
 * elements in such a fashion, that one half of every element is overlapped by
 * another element on the horizontal alignment.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 * @see AElementContainer
 */
public final class HalfStackHorizontalEvenlyDistributingElementContainer<E extends IMoveable>
	extends AElementContainer<E> {

    /**
     * Creates a new {@link HalfStackHorizontalEvenlyDistributingElementContainer}.
     * 
     * @param x      The components x coordinate.
     * @param y      The components y coordinate.
     * @param width  The components width.
     * @param height The components height.
     */
    public HalfStackHorizontalEvenlyDistributingElementContainer(final int x, final int y, final int width,
	    final int height) {
	super(x, y, width, height);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.AElementContainer#adjust()
     */
    @Override
    protected void adjust() {
	if (mElements.size() <= 0) {
	    return;
	}

	final int spacePerElement = mElementWidth / 2;
	int multiplier = 0;

	for (final E element : mElements) {
	    element.setX(mX + multiplier * spacePerElement);
	    element.setY(mY);
	    multiplier++;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.AElementContainer#get(int, int)
     */
    @Override
    public Optional<E> get(final int x, final int y) {
	return Optional.empty();
    }
}

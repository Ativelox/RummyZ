package de.ativelox.rummyz.client.view.gui.utils;

import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides an implementation for {@link AElementContainer}. Distributes its
 * elements in such a fashion that every element is equally distributed along
 * the vertical alignment given this components height. Also starts a new
 * <i>column</i> if an element would be out of this components bounds.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 * @see AElementContainer
 */
public final class VerticalEquallyDistributingElementContainer<E extends IMoveable> extends AElementContainer<E> {

    /**
     * The space at which to start a new column if needed.
     */
    private final int mHspace;

    /**
     * Creates a new {@link VerticalEquallyDistributingElementContainer}.
     * 
     * @param x      The x coordinate of this component.
     * @param y      The y coordinate of this component.
     * @param width  The width of this component.
     * @param height The height of this component.
     * @param hspace The space at which to start a new column if needed.
     */
    public VerticalEquallyDistributingElementContainer(final int x, final int y, final int width, final int height,
	    final int hspace) {
	super(x, y, width, height);

	mHspace = hspace;

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

	final int allocatedSpace = mHeight / mElements.size();

	int multiplier = 0;

	int hspaceMultiplier = 0;

	for (final E element : mElements) {
	    final int y = mY + (multiplier * allocatedSpace);

	    if (y + mElementHeight > mY + mHeight) {
		hspaceMultiplier++;
		multiplier = 0;
	    }

	    element.setX(mX + (hspaceMultiplier * mHspace));
	    element.setY(mY + (multiplier * allocatedSpace));

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
	throw new UnknownError("Not yet implemented");

    }
}

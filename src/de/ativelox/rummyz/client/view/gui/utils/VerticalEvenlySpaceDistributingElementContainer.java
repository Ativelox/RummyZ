package de.ativelox.rummyz.client.view.gui.utils;

import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides an implementation for {@link AElementContainer}. Distributes its
 * elements such that every element has a given amount of spacing between its
 * other elements vertically and starts a new column if an element would cross
 * this components bounds.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class VerticalEvenlySpaceDistributingElementContainer<E extends IMoveable> extends AElementContainer<E> {

    /**
     * The space to space these components elements vertically.
     */
    private final int mVSpace;

    /**
     * The space to space these components horizontally, if a new column has been
     * created.
     */
    private final int mHSpace;

    /**
     * Creates a new {@link VerticalEquallyDistributingElementContainer}.
     * 
     * @param x      The x coordinate of this component.
     * @param y      The y coordinate of this component.
     * @param width  The width of this component.
     * @param height The height of this component.
     * @param vspace The space at which to space its elements vertically.
     * @param hspace The space at which to start a new column if needed.
     */
    public VerticalEvenlySpaceDistributingElementContainer(final int x, final int y, final int width, final int height,
	    final int vspace, final int hspace) {
	super(x, y, width, height);
	mVSpace = vspace;
	mHSpace = hspace;
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

	final int spacePerElement = mElementHeight + mVSpace;
	int multiplier = 0;
	int hmultiplier = 0;

	for (final E element : mElements) {
	    final int y = mY + (spacePerElement * multiplier);

	    if (y > mY + mHeight) {
		hmultiplier++;
		multiplier = 0;
	    }

	    element.setX(mX + (hmultiplier * mHSpace));
	    element.setY(mY + (spacePerElement * multiplier));
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

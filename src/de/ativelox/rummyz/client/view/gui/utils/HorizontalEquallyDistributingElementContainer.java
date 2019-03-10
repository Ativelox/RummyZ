package de.ativelox.rummyz.client.view.gui.utils;

import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IElementAdjustCallback;
import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides an implementation for {@link AElementContainer}. Distributes its
 * elements in such a fashion that they are equally distributed along the
 * horizontal alignment and this components width.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see AElementContainer
 *
 */
public final class HorizontalEquallyDistributingElementContainer<E extends IMoveable> extends AElementContainer<E> {

    /**
     * Creates a new {@link HorizontalEquallyDistributingElementContainer}.
     * 
     * @param x      The components x coordinate.
     * @param y      The components y coordinate.
     * @param width  The components width.
     * @param height The components height.
     */
    public HorizontalEquallyDistributingElementContainer(final int x, final int y, final int width, final int height) {
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

	int allocatedSpace = mWidth / mElements.size();

	int multiplier = 0;

	for (final E element : mElements) {
	    element.setX(mX + (multiplier * allocatedSpace));
	    element.setY(mY);

	    multiplier++;
	}

	for (final IElementAdjustCallback<E> callback : mCallbacks) {
	    callback.onAdjust(mElements);

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.AElementContainer#get(int, int)
     */
    @Override
    public Optional<E> get(final int x, final int y) {
	if (y < mY || y > mY + mHeight || x < mX - mElementWidth || x > mX + mWidth + mElementWidth
		|| mElements.size() <= 0) {
	    return Optional.empty();

	}

	// try to approximate the item wanted, by reversing the allocation step.
	int relativeX = x - mX;
	int spacePerElement = mWidth / mElements.size();

	int index = (int) Math.floor((float) relativeX / spacePerElement);

	if (index >= mElements.size() || index < 0) {
	    return Optional.empty();

	}
	final E potentialCandidate = mElements.get(index);

	if (potentialCandidate.getBoundingBox().contains(x, y)) {
	    return Optional.of(potentialCandidate);

	}
	return Optional.empty();

    }
}

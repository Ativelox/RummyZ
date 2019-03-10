package de.ativelox.rummyz.client.view.gui.utils;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Provides an {@link Comparator} implementation that sorts its elements in a
 * ascending fashion.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see Comparator
 *
 */
public final class AscendingComparator<T> implements Comparator<T> {

    /**
     * The function used to compare the entries.
     */
    private final Function<T, Integer> mFunction;

    /**
     * Creates a new {@link AscendingComparator}.
     * 
     * @param function The function used to compare the entries.
     */
    public AscendingComparator(final Function<T, Integer> function) {
	mFunction = function;
    }

    /*
     * (non-Javadoc)
     * 
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final T o1, final T o2) {
	return mFunction.apply(o1).compareTo(mFunction.apply(o2));
    }

}

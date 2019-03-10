package de.ativelox.rummyz.model.util;

/**
 * Provides an immutable triple for the (possibly) different types <tt>V1</tt>,
 * <tt>V2</tt> and <tt>V3</tt>.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class ImmutableTriple<V1, V2, V3> {

    /**
     * The first element of this triple.
     */
    private final V1 mFirst;

    /**
     * The second element of this triple.
     */
    private final V2 mSecond;

    /**
     * The third element of this triple.
     */
    private final V3 mThird;

    /**
     * Creates a new {@link ImmutableTriple}.
     * 
     * @param first  The first value for this triple.
     * @param second The second value for this triple.
     * @param third  The third value for this triple.
     */
    public ImmutableTriple(final V1 first, final V2 second, final V3 third) {
	mFirst = first;
	mSecond = second;
	mThird = third;

    }

    /**
     * Gets the first value of this triple.
     * 
     * @return The value mentioned.
     */
    public V1 getFirst() {
	return mFirst;

    }

    /**
     * Gets the second value of this triple.
     * 
     * @return The value mentioned.
     */
    public V2 getSecond() {
	return mSecond;

    }

    /**
     * Gets the third value of this triple.
     * 
     * @return The value mentioned.
     */
    public V3 getThird() {
	return mThird;

    }

}

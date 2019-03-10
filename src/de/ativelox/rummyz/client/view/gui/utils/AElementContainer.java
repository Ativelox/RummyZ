package de.ativelox.rummyz.client.view.gui.utils;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.property.IElementAdjustCallback;
import de.ativelox.rummyz.client.view.gui.property.IMoveable;

/**
 * Provides a basic abstract implementation of {@link IElementContainer}.
 * Handles coordinate setting, adding of callbacks, e.t.c.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public abstract class AElementContainer<E extends IMoveable> implements IElementContainer<E> {

    protected int mX;

    protected int mY;

    protected int mWidth;

    protected int mHeight;

    protected final List<E> mElements;

    protected int mElementWidth;

    protected int mElementHeight;

    protected final List<IElementAdjustCallback<E>> mCallbacks;

    public AElementContainer(final int x, final int y, final int width, final int height) {
	mX = x;
	mY = y;
	mWidth = width;
	mHeight = height;

	mElements = new ArrayList<>();
	mCallbacks = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.utils.IElementContainer#addElement(de.
     * ativelox.rummyz.client.view.gui.items.ISpatial)
     */
    @Override
    public void addElement(final E toAdd) {
	mElementWidth = toAdd.getWidth();
	mElementHeight = toAdd.getHeight();
	this.mElements.add(toAdd);
	this.adjust();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.utils.IElementContainer#addElement(int,
     * de.ativelox.rummyz.client.view.gui.property.ISpatial)
     */
    @Override
    public void addElement(final int index, final E toAdd) {
	mElements.add(index, toAdd);
	this.adjust();

    }

    protected abstract void adjust();

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.IElementContainer#get(int, int)
     */
    @Override
    public abstract Optional<E> get(final int x, final int y);

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.IElementContainer#getElements()
     */
    @Override
    public List<E> getElements() {
	return mElements;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#getHeight()
     */
    @Override
    public int getHeight() {
	return mHeight;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	// TODO Auto-generated method stub
	return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#getWidth()
     */
    @Override
    public int getWidth() {
	return mWidth;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#getX()
     */
    @Override
    public int getX() {
	return mX;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#getY()
     */
    @Override
    public int getY() {
	return mY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.IElementContainer#indexOf(de.
     * ativelox.rummyz.client.view.gui.property.ISpatial)
     */
    @Override
    public int indexOf(final E e) {
	return mElements.indexOf(e);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.utils.IElementContainer#register(de.
     * ativelox.rummyz.client.view.gui.property.IElementAdjustCallback)
     */
    @Override
    public void register(final IElementAdjustCallback<E> callback) {
	mCallbacks.add(callback);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.utils.IElementContainer#removeElement(de.
     * ativelox.rummyz.client.view.gui.items.ISpatial)
     */
    @Override
    public void removeElement(final E toRemove) {
	this.mElements.remove(toRemove);
	this.adjust();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#render(java.awt.
     * Graphics)
     */
    @Override
    public void render(final Graphics g) {
	return;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setLayer(int)
     */
    @Override
    public void setLayer(final int layer) {
	return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setX(int)
     */
    @Override
    public void setX(final int newX) {
	this.mX = newX;

	this.adjust();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setY(int)
     */
    @Override
    public void setY(final int newY) {
	this.mY = newY;

	this.adjust();

    }
}

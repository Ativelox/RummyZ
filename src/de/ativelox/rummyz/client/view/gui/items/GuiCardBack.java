package de.ativelox.rummyz.client.view.gui.items;

import java.awt.Graphics;
import java.awt.Image;

import de.ativelox.rummyz.client.view.gui.property.IMoveable;
import de.ativelox.rummyz.client.view.gui.property.IRenderable;
import de.ativelox.rummyz.client.view.gui.property.ISpatial;
import de.ativelox.rummyz.client.view.gui.property.ITextured;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;
import de.ativelox.rummyz.model.assets.Assets;

/**
 * Represents a component, resembling a card back.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see ISpatial
 * @see ITextured
 * @see IRenderable
 * @see IMoveable
 *
 */
public final class GuiCardBack implements ISpatial, ITextured, IRenderable, IMoveable {

    /**
     * The x coordinate of this component.
     */
    private int mX;

    /**
     * The y coordinate of this component.
     */
    private int mY;

    /**
     * The width of this component.
     */
    private int mWidth;

    /**
     * The height of this component.
     */
    private int mHeight;

    /**
     * The image used in this components render call.
     */
    private Image mImage;

    /**
     * Creates a new {@link GuiCardBack}.
     */
    public GuiCardBack() {
	mImage = Assets.BLACK_DECK;

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
     * @see de.ativelox.rummyz.client.view.gui.items.ITextured#getImage()
     */
    @Override
    public Image getImage() {
	return mImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	return LayerConstants.DEFAULT_CARD_LAYER;
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
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#render(java.awt.
     * Graphics)
     */
    @Override
    public void render(final Graphics g) {
	g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setLayer(int)
     */
    @Override
    public void setLayer(final int layer) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setX(int)
     */
    @Override
    public void setX(final int newX) {
	this.mX = newX;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setY(int)
     */
    @Override
    public void setY(final int newY) {
	this.mY = newY;

    }

}

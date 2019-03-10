package de.ativelox.rummyz.client.view.gui.items;

import java.awt.Color;
import java.awt.Graphics;

import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;
import de.ativelox.rummyz.client.view.gui.utils.DrawUtils;

/**
 * Provides graphical card outline, onto which certain components get snapped
 * to.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see IHoverable
 *
 */
public final class SnapArea implements IHoverable {

    /**
     * The x coordinate of this component.
     */
    private int mX;

    /**
     * The y coordinate of this component.
     */
    private int mY;

    /**
     * The width of this component
     */
    private int mWidth;

    /**
     * The height of this component
     */
    private int mHeight;

    /**
     * Whether this component is currently hovered or not.
     */
    private boolean mIsHovered;

    /**
     * The label for this component, used to further distinguish implementations of
     * {@link IHoverable}.
     */
    private final EHoverLabel mLabel;

    public SnapArea(final int x, final int y, final int width, final int height, final EHoverLabel label) {
	mX = x;
	mY = y;
	mWidth = width;
	mHeight = height;

	mLabel = label;

	mIsHovered = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatial#getHeight()
     */
    @Override
    public int getHeight() {
	return mHeight;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#getLabel()
     */
    @Override
    public EHoverLabel getLabel() {
	return mLabel;
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
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatial#getWidth()
     */
    @Override
    public int getWidth() {
	return mWidth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatial#getX()
     */
    @Override
    public int getX() {
	return mX;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatial#getY()
     */
    @Override
    public int getY() {
	return mY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#isHovered()
     */
    @Override
    public boolean isHovered() {
	return mIsHovered;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#onHoverLoss()
     */
    @Override
    public void onHoverLoss() {
	mIsHovered = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#onHoverStart()
     */
    @Override
    public void onHoverStart() {
	mIsHovered = true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#render(java.awt.
     * Graphics)
     */
    @Override
    public void render(final Graphics g) {
	DrawUtils.drawCardBorder(this, g, Color.BLACK);

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
	mX = newX;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IMoveable#setY(int)
     */
    @Override
    public void setY(final int newY) {
	mY = newY;

    }

}

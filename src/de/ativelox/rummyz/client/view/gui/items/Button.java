package de.ativelox.rummyz.client.view.gui.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;

/**
 * A basic {@link IButton} implementation.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see IButton
 *
 */
public final class Button implements IButton {

    /**
     * The x coordinate of this element.
     */
    private int mX;

    /**
     * The y coordinate of this element.
     */
    private int mY;

    /**
     * The width of this element.
     */
    private int mWidth;

    /**
     * The height of this element.
     */
    private int mHeight;

    /**
     * Whether this component is currently hovered or not.
     */
    private boolean mIsHovered;

    /**
     * The color of this buttons border.
     */
    private Color mBorderColor;

    /**
     * The inner color of this button.
     */
    private Color mInnerColor;

    /**
     * The offset from the top the label of this button gets drawn onto.
     */
    private final int mOffsetTop;

    /**
     * The offset from the left the label of this button gets drawn onto.
     */
    private final int mOffsetLeft;

    /**
     * The label on this button (the text).
     */
    private final String mLabel;

    /**
     * The font size of the label for this button.
     */
    private final int mFontSize;

    /**
     * Creates a new {@link Button}.
     * 
     * @param label      The text on the button.
     * @param offsetTop  The offset from the top for the label, relative to this
     *                   component.
     * @param offsetLeft The offset from the left for the label, relative to this
     *                   component.
     * @param fontSize   The size of the font for the label.
     * @param x          The x coordinate of this component.
     * @param y          The y coordinate of this component.
     * @param width      The width of this component.
     * @param height     The height of this component.
     */
    public Button(final String label, final int offsetTop, final int offsetLeft, final int fontSize, final int x,
	    final int y, final int width, final int height) {
	mX = x;
	mY = y;
	mWidth = width;
	mHeight = height;
	mFontSize = fontSize;

	mIsHovered = false;

	mBorderColor = Color.GRAY;
	mInnerColor = new Color(0, 0, 0, 150);

	mOffsetLeft = offsetLeft;
	mOffsetTop = offsetTop;
	mLabel = label;

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
	return EHoverLabel.BUTTON;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	return LayerConstants.BUTTON_LAYER;
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
	if (!mIsHovered) {
	    return;
	}

	mInnerColor = new Color(mInnerColor.getRed(), mInnerColor.getGreen(), mInnerColor.getBlue(),
		mInnerColor.getAlpha() - 20);
	mIsHovered = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#onHoverStart()
     */
    @Override
    public void onHoverStart() {
	if (mIsHovered) {
	    return;
	}

	mInnerColor = new Color(mInnerColor.getRed(), mInnerColor.getGreen(), mInnerColor.getBlue(),
		mInnerColor.getAlpha() + 20);
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
	final Color old = g.getColor();

	g.setColor(mInnerColor);

	g.fillRect(mX, mY, mWidth, mHeight);

	g.setColor(mBorderColor);

	g.drawLine(mX, mY, mX + mWidth, mY);
	g.drawLine(mX + mWidth, mY, mX + mWidth, mY + mHeight);
	g.drawLine(mX + mWidth, mY + mHeight, mX, mY + mHeight);
	g.drawLine(mX, mY + mHeight, mX, mY);

	g.setColor(Color.white);

	g.setFont(new Font("Arial", 0, mFontSize));
	g.drawString(mLabel, mX + mOffsetLeft, mY + mOffsetTop);

	g.setColor(old);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#setLayer(int)
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

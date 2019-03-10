package de.ativelox.rummyz.client.view.gui.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.IHighlightable;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.IMoveable;
import de.ativelox.rummyz.client.view.gui.property.IRenderable;
import de.ativelox.rummyz.client.view.gui.property.ISpatial;
import de.ativelox.rummyz.client.view.gui.property.ITextured;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;
import de.ativelox.rummyz.client.view.gui.utils.DrawUtils;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.assets.Assets;
import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * A graphical implementation of the {@link ICard} specification.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see ICard
 * @see ITextured
 * @see ISpatial
 * @see IHoverable
 * @see IRenderable
 * @see IMoveable
 * @see IHighlightable
 *
 */
public final class GuiCard implements ICard, ITextured, ISpatial, IHoverable, IRenderable, IMoveable, IHighlightable {

    /**
     * The width of this component.
     */
    public static final int WIDTH = Assets.getCardWidth();

    /**
     * The height of this component.
     */
    public static final int HEIGHT = Assets.getCardHeight();

    /**
     * The amount of pixels this moves up when hovered.
     */
    private static final int HOVER_UP = 30;

    /**
     * The x coordinate of this component.
     */
    private int mX;

    /**
     * The y coordinate of this component.
     */
    private int mY;

    /**
     * The image this component uses in its render call.
     */
    private Image mImage;

    /**
     * The type of this card.
     */
    private final ECardType mType;

    /**
     * The value of this card.
     */
    private final ECardValue mValue;

    /**
     * Whether this component is currently highlighted or not.
     */
    private boolean mIsHighlighted;

    /**
     * Whether this component is currently hovered or not.
     */
    private boolean mIsHovered;

    /**
     * The layer of this component.
     */
    private int mLayer;

    /**
     * Creates a new {@link GuiCard}.
     * 
     * @param card The card to base this off of.
     */
    public GuiCard(final ICard card) {
	mType = card.getType();
	mValue = card.getValue();
	mImage = Assets.getBy(mType, mValue);
	mIsHovered = false;

	mLayer = LayerConstants.DEFAULT_CARD_LAYER;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.IScreenElement#getHeight()
     */
    @Override
    public int getHeight() {
	return HEIGHT;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ITexturedElement#getImage()
     */
    @Override
    public Image getImage() {
	return mImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#getLabel()
     */
    @Override
    public EHoverLabel getLabel() {
	return EHoverLabel.CARD_OWN_HAND;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	return mLayer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ICard#getType()
     */
    @Override
    public ECardType getType() {
	return mType;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ICard#getValue()
     */
    @Override
    public ECardValue getValue() {
	return mValue;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.IScreenElement#getWidth()
     */
    @Override
    public int getWidth() {
	return WIDTH;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.IScreenElement#getX()
     */
    @Override
    public int getX() {
	return mX;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.IScreenElement#getY()
     */
    @Override
    public int getY() {
	return mY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.IHighlightable#highlight(boolean)
     */
    @Override
    public void highlight(final boolean highlight) {
	mIsHighlighted = highlight;

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
	if (mIsHovered) {
	    this.setY(this.getY() + HOVER_UP);
	}
	setLayer(LayerConstants.DEFAULT_CARD_LAYER);
	mIsHovered = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IHoverable#onHoverStart()
     */
    @Override
    public void onHoverStart() {
	if (!mIsHovered) {
	    this.setY(this.getY() - HOVER_UP);
	}
	setLayer(LayerConstants.HOVER_CARD_LAYER);
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
	if (mIsHighlighted) {
	    DrawUtils.drawCardBorder(this, g, Color.CYAN);
	}

	g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#setLayer()
     */
    @Override
    public void setLayer(final int layer) {
	mLayer = layer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#setX(int)
     */
    @Override
    public void setX(final int newX) {
	this.mX = newX;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.ISpatial#setY(int)
     */
    @Override
    public void setY(final int newY) {
	this.mY = newY;

    }
}

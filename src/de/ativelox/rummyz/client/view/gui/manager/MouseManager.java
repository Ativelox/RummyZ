package de.ativelox.rummyz.client.view.gui.manager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.ativelox.rummyz.client.view.gui.items.IButton;
import de.ativelox.rummyz.client.view.gui.items.IButtonListener;
import de.ativelox.rummyz.client.view.gui.items.SnapArea;
import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.IMover;
import de.ativelox.rummyz.client.view.gui.property.IMoverCallback;
import de.ativelox.rummyz.client.view.gui.property.ISnapListener;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;
import de.ativelox.rummyz.client.view.gui.screen.IGameScreen;
import de.ativelox.rummyz.client.view.gui.utils.IElementContainer;

/**
 * Generally handles all mouse related stuff, hovering, moving components,
 * highlighting, e.t.c.
 * 
 * This is a clunky class, it does all sorts of things, which is bad practice,
 * but I generally ran out of time on this project at this point.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see MouseListener
 * @see MouseMotionListener
 * @see IMover
 */
public final class MouseManager implements MouseListener, MouseMotionListener, IMover {

    /**
     * All the {@link IMoverCallback}s registered to this instance.
     */
    private final List<IMoverCallback> mCallbacks;

    /**
     * All {@link IButtonListener}s registered to this instance.
     */
    private final List<IButtonListener> mButtonListener;

    /**
     * A backing of the hand view used in the {@link IGameScreen}.
     */
    private final IElementContainer<IHoverable> mHandView;

    /**
     * The component that is currently bound (moved) by the mouse cursor.
     */
    private IHoverable mCurrentlyBound;

    /**
     * The component that is currently hovered by the mouse cursor.
     */
    private IHoverable mCurrentlyHovered;

    /**
     * The {@link ISnapListener} registered to this instance.
     */
    private ISnapListener mSnapListener;

    /**
     * All the {@link SnapArea}s this instance manages.
     */
    private final List<IHoverable> mSnapAreas;

    /**
     * A mapping from each snap area that is currently on the board to its
     * superIndex, used to fetch the sequence of cards this belongs to.
     */
    private final Map<IHoverable, Integer> mOnFieldMapping;

    /**
     * A mapping from each snap area to whether its left, or right of the current
     * sequence on the board.
     */
    private final Map<IHoverable, Boolean> mIsLeft;

    /**
     * Creates a new {@link MouseManager}.
     * 
     * @param handView      The view of the hand.
     * @param graveyardSnap The snap area used for the grave yard.
     */
    public MouseManager(final IElementContainer<IHoverable> handView, final SnapArea graveyardSnap) {
	mCallbacks = new ArrayList<>();
	mButtonListener = new ArrayList<>();

	mSnapAreas = new ArrayList<>();
	mSnapAreas.add(graveyardSnap);

	mOnFieldMapping = new HashMap<>();
	mIsLeft = new HashMap<>();

	mHandView = handView;

    }

    /**
     * Adds two snap areas to this instance, that are each left and right of card
     * sequences of the board.
     * 
     * @param left       The left snap area.
     * @param right      The right snap area.
     * @param superIndex The index of the card sequence these snap areas are
     *                   attached to.
     */
    public void addSnapAreas(final IHoverable left, final IHoverable right, final int superIndex) {
	this.mSnapAreas.add(left);
	this.mSnapAreas.add(right);

	this.mOnFieldMapping.put(left, superIndex);
	this.mOnFieldMapping.put(right, superIndex);
	this.mIsLeft.put(left, true);
	this.mIsLeft.put(right, false);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.ISpatialMover#bind(de.ativelox.
     * rummyz.client.view.gui.items.ISpatial)
     */
    @Override
    public void bind(final IHoverable moveable) {

	for (final IMoverCallback callback : mCallbacks) {
	    callback.onBind(moveable);

	}
	mCurrentlyBound = moveable;
	mCurrentlyBound.setLayer(LayerConstants.MOVING_CARD_LAYER);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(final MouseEvent arg0) {
	this.updateMousePosition(arg0.getX(), arg0.getY());

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(final MouseEvent e) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(final MouseEvent e) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(final MouseEvent arg0) {
	this.updateMousePosition(arg0.getX(), arg0.getY());

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(final MouseEvent e) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
	if (mCurrentlyHovered == null) {
	    return;
	}

	if (mCurrentlyHovered.getLabel() == EHoverLabel.BUTTON) {
	    for (final IButtonListener listener : mButtonListener) {
		listener.onButtonClicked((IButton) mCurrentlyHovered);

	    }

	} else if (mCurrentlyHovered.getLabel() == EHoverLabel.CARD_OWN_HAND) {
	    if (mCurrentlyBound == null) {
		mCurrentlyHovered.onHoverLoss();
		this.bind(mCurrentlyHovered);

		mCurrentlyHovered = null;

	    } else {
		mCurrentlyHovered.onHoverLoss();
		this.unbind();
		mCurrentlyHovered = null;
	    }

	} else if (mCurrentlyHovered.getLabel() == EHoverLabel.GRAVEYARD_SNAP_AREA && mCurrentlyBound != null) {
	    if (mSnapListener.onSnap(mCurrentlyHovered, mCurrentlyBound, 0, false)) {
		mCurrentlyBound = null;

	    }
	} else if (mCurrentlyHovered.getLabel() == EHoverLabel.CARD_PLAYED_SNAP_AREA && mCurrentlyBound != null) {
	    if (mSnapListener.onSnap(mCurrentlyHovered, mCurrentlyBound, mOnFieldMapping.get(mCurrentlyHovered),
		    mIsLeft.get(mCurrentlyHovered))) {
		mCurrentlyBound = null;

	    }
	} else if (mCurrentlyHovered.getLabel() == EHoverLabel.GRAVEYARD_SNAP_AREA && mCurrentlyBound == null) {
	    mSnapListener.onSnap(mCurrentlyHovered, null, 0, false);
	}
    }

    /**
     * Registers the given listener to receive callbacks from this instance.
     * 
     * @param listener The listener to register.
     */
    public void register(final IButtonListener listener) {
	this.mButtonListener.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatialMover#register(de.
     * ativelox.rummyz.client.view.gui.property.ISpatialMoverCallback)
     */
    @Override
    public void register(final IMoverCallback callback) {
	mCallbacks.add(callback);

    }

    /**
     * Registers the given listener to receive callbacks from this instance.
     * 
     * @param listener The listener to register.
     */
    public void register(final ISnapListener listener) {
	mSnapListener = listener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.IMover#unbind(de.ativelox.rummyz.
     * client.view.gui.property.ISpatial)
     */
    @Override
    public void unbind() {

	final int index = mHandView.indexOf(mCurrentlyHovered);

	for (final IMoverCallback callback : mCallbacks) {
	    callback.onUnbind(index, mCurrentlyBound);
	}

	mCurrentlyBound.setLayer(LayerConstants.DEFAULT_CARD_LAYER);
	mCurrentlyBound = null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.ISpatialMover#unregister(de.
     * ativelox.rummyz.client.view.gui.property.ISpatialMoverCallback)
     */
    @Override
    public void unregister(final IMoverCallback callback) {
	mCallbacks.remove(callback);

    }

    /**
     * Updates the hovering on its components, and moves opponents if applicable.
     * 
     * @param x The new x coordinate of the mouse cursor.
     * @param y The new y coordinate of the mouse cursor.
     */
    private void updateMousePosition(final int x, final int y) {
	boolean removeHover = true;

	Optional<IHoverable> potential = mHandView.get(x, y);

	// handle hand view hovering
	if (potential.isPresent()) {
	    removeHover = removeHover && false;
	    IHoverable actual = potential.get();

	    if (!actual.isHovered()) {
		if (mCurrentlyHovered != null && !actual.equals(mCurrentlyHovered)) {
		    mCurrentlyHovered.onHoverLoss();

		}
		actual.onHoverStart();
		mCurrentlyHovered = actual;

	    }

	}

	// handle button hovering

	for (final IButtonListener listener : mButtonListener) {
	    for (final IButton button : listener.getButtons()) {
		if (button.getBoundingBox().contains(x, y)) {
		    removeHover = removeHover && false;

		    if (mCurrentlyHovered == null && !button.isHovered()) {
			button.onHoverStart();
			mCurrentlyHovered = button;

		    }
		}

	    }
	}

	// handle snap area hovering
	for (final IHoverable hoverable : mSnapAreas) {
	    if (hoverable.getBoundingBox().contains(x, y)) {
		removeHover = removeHover && false;

		if (mCurrentlyHovered == null && !hoverable.isHovered()) {
		    hoverable.onHoverStart();
		    mCurrentlyHovered = hoverable;
		}
	    }
	}

	if (removeHover && mCurrentlyHovered != null) {
	    mCurrentlyHovered.onHoverLoss();
	    mCurrentlyHovered = null;

	}

	// to moving
	if (mCurrentlyBound != null) {
	    if (mCurrentlyHovered != null && (mCurrentlyHovered.getLabel() == EHoverLabel.GRAVEYARD_SNAP_AREA
		    || mCurrentlyHovered.getLabel() == EHoverLabel.CARD_PLAYED_SNAP_AREA)) {
		mCurrentlyBound.setX(mCurrentlyHovered.getX());
		mCurrentlyBound.setY(mCurrentlyHovered.getY());

	    } else {
		mCurrentlyBound.setX(x - mCurrentlyBound.getWidth() / 2);
		mCurrentlyBound.setY(y - mCurrentlyBound.getHeight() / 2);

	    }

	}
    }
}

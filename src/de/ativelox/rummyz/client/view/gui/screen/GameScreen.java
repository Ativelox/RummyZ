package de.ativelox.rummyz.client.view.gui.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.ativelox.rummyz.client.controller.GameRule;
import de.ativelox.rummyz.client.view.gui.items.GuiCard;
import de.ativelox.rummyz.client.view.gui.items.SnapArea;
import de.ativelox.rummyz.client.view.gui.manager.IRenderManager;
import de.ativelox.rummyz.client.view.gui.manager.MouseManager;
import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.IElementAdjustCallback;
import de.ativelox.rummyz.client.view.gui.property.IHighlightable;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.LayerConstants;
import de.ativelox.rummyz.client.view.gui.utils.HalfStackHorizontalEvenlyDistributingElementContainer;
import de.ativelox.rummyz.client.view.gui.utils.HorizontalEquallyDistributingElementContainer;
import de.ativelox.rummyz.client.view.gui.utils.IElementContainer;
import de.ativelox.rummyz.client.view.gui.utils.VerticalEvenlySpaceDistributingElementContainer;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.assets.Assets;
import de.ativelox.rummyz.model.util.ImmutablePair;

/**
 * Implementation of {@link IGameScreen}. Manages all the elements on the
 * screen.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class GameScreen implements IGameScreen, IElementAdjustCallback<IHoverable> {

    /**
     * The padding from the left for the hand view.
     */
    private final static int HAND_PADDING_LEFT = 200;

    /**
     * The padding from the right for the hand view.
     */
    private final static int HAND_PADDING_RIGHT = 200;

    /**
     * The padding from the bottom for the hand view.
     */
    private final static int HAND_PADDING_BOTTOM = 100;

    /**
     * The current view of the hand.
     */
    private final IElementContainer<IHoverable> mHandView;

    /**
     * The current view of the grave yard.
     */
    private final Stack<IHoverable> mGraveyardView;

    /**
     * The current view of all the card sequences on the field.
     */
    private final IElementContainer<IElementContainer<IHoverable>> mOnFieldView;

    /**
     * A mapping from each superIndex to the view of card sequences on the field.
     */
    private final Map<Integer, IElementContainer<IHoverable>> mInnerFieldMapping;

    /**
     * A mapping from each {@link ICard} to its representing graphical view.
     */
    private final Map<ICard, IHoverable> mCardMapping;

    /**
     * A mapping from each graphical view to its associated {@link ICard}.
     */
    private final Map<IHoverable, ICard> mReversedCardMapping;

    /**
     * The width of the screen
     */
    private final int mWidth;

    /**
     * The height of the screen.
     */
    private final int mHeight;

    /**
     * Contains the current list of sequence of cards that are present in the
     * current sequence of cards representing the hand view.
     */
    private List<List<ICard>> mPlay;

    /**
     * The mouse manager of this view.
     */
    private MouseManager mMouseManager;

    /**
     * The snap area for the grave yard.
     */
    private SnapArea mSnap;

    /**
     * The render manager for this view.
     */
    private final IRenderManager mRenderManager;

    /**
     * Creates a new {@link GameScreen}.
     * 
     * @param playerAmount  The amount of players present in the game.
     * @param renderManager The render manager for this view.
     * @param width         The width of the screen
     * @param height        The height of the screen.
     */
    public GameScreen(final int playerAmount, final IRenderManager renderManager, final int width, final int height) {
	mHandView = new HorizontalEquallyDistributingElementContainer<>(HAND_PADDING_LEFT,
		height - HAND_PADDING_BOTTOM - GuiCard.HEIGHT, width - HAND_PADDING_LEFT - HAND_PADDING_RIGHT,
		GuiCard.HEIGHT);

	mGraveyardView = new Stack<>();
	mInnerFieldMapping = new HashMap<>();
	mOnFieldView = new VerticalEvenlySpaceDistributingElementContainer<>(HAND_PADDING_LEFT, 50,
		width - HAND_PADDING_LEFT - HAND_PADDING_RIGHT, height - 400, 20, 400);

	mHandView.register(this);

	mCardMapping = new HashMap<>();
	mReversedCardMapping = new HashMap<>();
	mPlay = new ArrayList<>();

	mWidth = width;
	mHeight = height;

	renderManager.add(this);

	mRenderManager = renderManager;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IGameScreen#addAppendCard(de.
     * ativelox.rummyz.client.view.gui.items.GuiCard, int, int)
     */
    @Override
    public void addAppendCard(final ICard card, final int superIndex, final int insertIndex) {
	final GuiCard guiCard = new GuiCard(card);

	mCardMapping.put(card, guiCard);
	mReversedCardMapping.put(guiCard, card);
	mRenderManager.add(guiCard);
	mInnerFieldMapping.get(superIndex).addElement(insertIndex + 1, guiCard);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#addCardsPlayed(java.
     * util.List)
     */
    @Override
    public void addCardsPlayed(final List<List<ICard>> cards, final String[] ids) {
	for (int i = 0; i < ids.length; i++) {
	    IElementContainer<IHoverable> inner = new HalfStackHorizontalEvenlyDistributingElementContainer<>(0, 0,
		    mOnFieldView.getWidth(), GuiCard.HEIGHT);

	    final SnapArea leftSa = new SnapArea(0, 0, GuiCard.WIDTH, GuiCard.HEIGHT,
		    EHoverLabel.CARD_PLAYED_SNAP_AREA);

	    final SnapArea rightSa = new SnapArea(0, 0, GuiCard.WIDTH, GuiCard.HEIGHT,
		    EHoverLabel.CARD_PLAYED_SNAP_AREA);

	    this.mMouseManager.addSnapAreas(leftSa, rightSa, Integer.parseInt(ids[i]));

	    inner.addElement(leftSa);
	    mRenderManager.add(leftSa);

	    mInnerFieldMapping.put(Integer.parseInt(ids[i]), inner);

	    for (final ICard card : cards.get(i)) {
		final GuiCard guiCard = new GuiCard(card);

		mCardMapping.put(card, guiCard);
		mReversedCardMapping.put(guiCard, card);
		mRenderManager.add(guiCard);

		inner.addElement(guiCard);

	    }
	    inner.addElement(rightSa);
	    mRenderManager.add(rightSa);
	    this.mOnFieldView.addElement(inner);

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#addGraveyardCard(de.
     * ativelox.rummyz.client.view.gui.items.GuiCard)
     */
    @Override
    public void addGraveyardCard(final ICard card) {
	final GuiCard guiCard = new GuiCard(card);
	guiCard.setX(mSnap.getX());
	guiCard.setY(mSnap.getY());

	mCardMapping.put(card, guiCard);
	mReversedCardMapping.put(guiCard, card);

	if (mGraveyardView.size() > 0) {
	    mRenderManager.remove(mGraveyardView.peek());
	}
	mRenderManager.add(guiCard);
	mGraveyardView.add(guiCard);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#addOwnCard(de.ativelox.
     * rummyz.client.view.gui.items.GuiCard)
     */
    @Override
    public void addOwnCard(final ICard card) {
	final GuiCard guiCard = new GuiCard(card);

	mCardMapping.put(card, guiCard);
	mReversedCardMapping.put(guiCard, card);
	mRenderManager.add(guiCard);
	mHandView.addElement(guiCard);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#get(de.ativelox.rummyz.
     * client.view.gui.property.IHoverable)
     */
    @Override
    public ICard get(final IHoverable hoverable) {
	return mReversedCardMapping.get(hoverable);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IGameScreen#getHandView()
     */
    @Override
    public IElementContainer<IHoverable> getHandView() {
	return mHandView;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	return LayerConstants.BACKGROUND_LAYER;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IGameScreen#getPlay()
     */
    @Override
    public List<List<ICard>> getPlay() {
	return mPlay;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.IElementAdjustCallback#onAdjust(
     * java.util.List)
     */
    @Override
    public void onAdjust(final List<IHoverable> elements) {
	mPlay = new ArrayList<>();

	for (final IHoverable card : mHandView.getElements()) {
	    if (card instanceof IHighlightable) {
		((IHighlightable) card).highlight(false);
	    }
	}

	List<ICard> cards = new ArrayList<>();
	for (final IHoverable card : elements) {
	    cards.add(mReversedCardMapping.get(card));

	}
	List<ImmutablePair<Integer, Integer>> indices = GameRule.getAllValid(cards);

	for (final ImmutablePair<Integer, Integer> index : indices) {
	    List<ICard> subList = cards.subList(index.getKey(), index.getValue());

	    mPlay.add(subList);

	    for (final ICard card : subList) {
		final IHoverable candidate = mCardMapping.get(card);

		if (candidate instanceof IHighlightable) {
		    ((IHighlightable) candidate).highlight(true);

		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.IMoverCallback#onBind(de.ativelox
     * .rummyz.client.view.gui.items.ISpatial)
     */
    @Override
    public void onBind(final IHoverable spatial) {
	this.mHandView.removeElement(spatial);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#onBlockStateChange(
     * boolean)
     */
    @Override
    public void onBlockStateChange(final boolean block) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IGameScreen#onGraveyardEmpty()
     */
    @Override
    public void onGraveyardEmpty() {
	mRenderManager.remove(mGraveyardView.peek());

	while (!mGraveyardView.isEmpty()) {
	    IHoverable viewCard = mGraveyardView.pop();

	    ICard card = mReversedCardMapping.get(viewCard);

	    mCardMapping.remove(card);
	    mReversedCardMapping.remove(viewCard);

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.ISpatialMoverCallback#onUnbind(de
     * .ativelox.rummyz.client.view.gui.items.ISpatial)
     */
    @Override
    public void onUnbind(final int index, final IHoverable spatial) {
	this.mHandView.addElement(index, spatial);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IGameScreen#removeOwnCard(de.
     * ativelox.rummyz.client.view.gui.items.GuiCard)
     */
    @Override
    public void removeOwnCard(final ICard card) {
	mHandView.removeElement(mCardMapping.get(card));
	mRenderManager.remove(mCardMapping.get(card));

	mReversedCardMapping.remove(mCardMapping.get(card));
	mCardMapping.remove(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#render(java.awt.
     * Graphics)
     */
    @Override
    public void render(final Graphics g) {
	g.drawImage(Assets.TABLETOP, 0, 0, mWidth, mHeight, null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.screen.IScreen#renderLower()
     */
    @Override
    public boolean renderLower() {
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#setGraveyardSnap(de.
     * ativelox.rummyz.client.view.gui.items.SnapArea)
     */
    @Override
    public void setGraveyardSnap(final SnapArea snap) {
	mSnap = snap;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.screen.IGameScreen#setMouseManager(de.
     * ativelox.rummyz.client.view.gui.manager.MouseManager)
     */
    @Override
    public void setMouseManager(final MouseManager manager) {
	this.mMouseManager = manager;

    }
}

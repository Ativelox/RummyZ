package de.ativelox.rummyz.client.view.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import de.ativelox.rummyz.client.view.AClientView;
import de.ativelox.rummyz.client.view.gui.items.Button;
import de.ativelox.rummyz.client.view.gui.items.IButton;
import de.ativelox.rummyz.client.view.gui.items.IButtonListener;
import de.ativelox.rummyz.client.view.gui.items.SnapArea;
import de.ativelox.rummyz.client.view.gui.manager.IRenderManager;
import de.ativelox.rummyz.client.view.gui.manager.MouseManager;
import de.ativelox.rummyz.client.view.gui.manager.RenderManager;
import de.ativelox.rummyz.client.view.gui.property.EHoverLabel;
import de.ativelox.rummyz.client.view.gui.property.IHoverable;
import de.ativelox.rummyz.client.view.gui.property.ISnapListener;
import de.ativelox.rummyz.client.view.gui.screen.GameScreen;
import de.ativelox.rummyz.client.view.gui.screen.IGameScreen;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.assets.Assets;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * Provides an implementation for {@link AClientView}. This is a graphical view
 * implementation.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see AClientView
 * @see IButtonListener
 * @see ISnapListener
 *
 */
public final class GraphicalView extends AClientView<EC2S, ES2C> implements IButtonListener, ISnapListener {

    /**
     * The width for this view.
     */
    private static final int WIDTH = 1280;

    /**
     * The height for this height.
     */
    private static final int HEIGHT = 720;

    /**
     * The game screen of this view.
     */
    private final IGameScreen mGameScreen;

    /**
     * The underlying frame of this view.
     */
    private final JFrame mFrame;

    /**
     * The canvas onto which gets rendered.
     */
    private final Canvas mCanvas;

    /**
     * The buffer strategy used.
     */
    private BufferStrategy mBs;

    /**
     * The render manager used to handle rendering properly.
     */
    private IRenderManager mRenderManager;

    /**
     * The mouse manager used to handle mouse actions.
     */
    private final MouseManager mMouseManager;

    /**
     * The graphics object used to draw onto.
     */
    private Graphics mG;

    /**
     * The button which handles plays user input.
     */
    private final IButton mPlayButton;

    /**
     * A list of all the buttons this view has.
     */
    private final List<IButton> mButtons;

    /**
     * The snap area used for the grave yard.
     */
    private final SnapArea mSnap;

    /**
     * Creates a new {@link GraphicalView}.
     * 
     * @param playerId     The id of this player.
     * @param playerAmount The amount of players participating in the game.
     */
    public GraphicalView(final int playerId, final int playerAmount) {
	mRenderManager = new RenderManager();
	mGameScreen = new GameScreen(playerAmount, mRenderManager, WIDTH, HEIGHT);
	mButtons = new ArrayList<>();

	mSnap = new SnapArea(70, HEIGHT - 100 - Assets.getCardHeight(), Assets.getCardWidth(), Assets.getCardHeight(),
		EHoverLabel.GRAVEYARD_SNAP_AREA);

	mGameScreen.setGraveyardSnap(mSnap);

	mMouseManager = new MouseManager(mGameScreen.getHandView(), mSnap);
	mMouseManager.register(mGameScreen);
	mMouseManager.register((IButtonListener) this);
	mMouseManager.register((ISnapListener) this);

	mGameScreen.setMouseManager(mMouseManager);

	// TODO: make positioning more dynamic.
	mPlayButton = new Button("Play", 28, 30, 20, 1120, 540, 100, 40);

	mFrame = new JFrame("Rummy" + "\t" + "Player: " + playerId);

	// TODO: change dimensions
	mFrame.setSize(WIDTH, HEIGHT);
	mFrame.setResizable(false);
	mFrame.setLocationRelativeTo(null);
	mFrame.setVisible(true);
	mFrame.setFocusable(false);

	mCanvas = new Canvas();
	mCanvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	mCanvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
	mCanvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	mCanvas.setFocusable(false);
	mCanvas.setBounds(0, 0, WIDTH, HEIGHT);

	mFrame.add(mCanvas);

	mCanvas.addMouseListener(mMouseManager);
	mCanvas.addMouseMotionListener(mMouseManager);

	mRenderManager.add(mPlayButton);
	mRenderManager.add(mSnap);
	mButtons.add(mPlayButton);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#addCard(de.ativelox.rummyz.model.
     * ICard)
     */
    @Override
    public void addCard(final ICard card) {
	super.addCard(card);

	mGameScreen.addOwnCard(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#addCardFromGraveyard()
     */
    @Override
    public void addCardFromGraveyard() {
	super.addCardFromGraveyard();

	mGameScreen.addCardFromGraveyard();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#addCardsPlayed(java.util.List,
     * java.lang.String[])
     */
    @Override
    public void addCardsPlayed(final List<List<ICard>> cards, final String[] ids) {
	super.addCardsPlayed(cards, ids);

	mGameScreen.addCardsPlayed(cards, ids);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#addCardToGraveyard(de.ativelox.
     * rummyz.model.ICard)
     */
    @Override
    public void addCardToGraveyard(final ICard card) {
	super.addCardToGraveyard(card);

	mGameScreen.addGraveyardCard(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#appendCard(de.ativelox.rummyz.
     * model.ICard, int, int)
     */
    @Override
    public void appendCard(final ICard card, final int superIndex, final int insertIndex) {
	mGameScreen.addAppendCard(card, superIndex, insertIndex);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.items.IButtonListener#getButton()
     */
    @Override
    public List<IButton> getButtons() {
	// TODO Auto-generated method stub
	return mButtons;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.AClientView#invalidPlay()
     */
    @Override
    public void invalidPlay() {
	// TODO make more interactive
	System.out.println("invalid play");

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.items.IButtonListener#onButtonClicked()
     */
    @Override
    public void onButtonClicked(final IButton button) {

	// do not allow user driven send input when not current turn.
	if (mBlocked) {
	    return;
	}

	if (button.equals(mPlayButton)) {
	    List<List<ICard>> cards = mGameScreen.getPlay();
	    this.mPcSender.sendCardsPlayed(cards);

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.AClientView#onGraveyardEmpty()
     */
    @Override
    public void onGraveyardEmpty() {
	super.onGraveyardEmpty();

	mGameScreen.onGraveyardEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.property.ISnapListener#onSnap(de.ativelox.
     * rummyz.client.view.gui.property.IHoverable)
     */
    @Override
    public boolean onSnap(final IHoverable snap, final IHoverable bound, final int superIndex, final boolean isLeft) {
	if (mBlocked) {
	    return false;

	}
	if (snap.getLabel() == EHoverLabel.GRAVEYARD_SNAP_AREA) {
	    if (bound == null) {
		this.mPcSender.sendGraveyardPickupCard();
		return true;

	    }

	    this.mPcSender.sendDiscardCard(mGameScreen.get(bound));
	    this.mPcSender.sendTurnEnd();
	    return true;
	}

	if (snap.getLabel() == EHoverLabel.CARD_PLAYED_SNAP_AREA) {
	    int index = 0;

	    if (!isLeft) {
		index = this.mCardsOnField.get(superIndex).size();

	    }
	    return this.mPcSender.sendAppendCard(mGameScreen.get(bound), superIndex, index);

	}
	return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#removeCard(de.ativelox.rummyz.
     * model.ICard)
     */
    @Override
    public void removeCard(final ICard card) {
	super.removeCard(card);

	mGameScreen.removeOwnCard(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#removeCardFromGraveyard(boolean)
     */
    @Override
    public void removeCardFromGraveyard() {
	mGraveyard.pop();

	mGameScreen.removeGraveyardCard();
    }

    /**
     * Renders one frame, by calling {@link IRenderManager#render(Graphics)}.
     */
    private void render() {
	mBs = mCanvas.getBufferStrategy();

	if (mBs == null) {
	    mCanvas.createBufferStrategy(3);
	    return;
	}
	mG = mBs.getDrawGraphics();
	mG.clearRect(0, 0, WIDTH, HEIGHT);

	// render screens.
	mRenderManager.render(mG);

	mBs.show();
	mG.dispose();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

	int fps = 60;
	double timePerTick = 1_000_000_000 / fps;
	double ticksToProcess = 0;
	int timer = 0;
	long lastTime = System.nanoTime();

	while (mRunning) {
	    long now = System.nanoTime();
	    ticksToProcess += (now - lastTime) / timePerTick;
	    timer += now - lastTime;
	    lastTime = now;

	    if (ticksToProcess >= 1) {
		render();
		ticksToProcess--;

	    }

	    if (timer > 1_000_000_000) {
		timer = 0;

	    }

	    try {
		Thread.sleep(1000 / 60);

	    } catch (InterruptedException e) {
		e.printStackTrace();

	    }
	}
    }

}

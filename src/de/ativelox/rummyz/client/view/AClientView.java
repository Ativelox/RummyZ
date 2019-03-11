package de.ativelox.rummyz.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.ativelox.rummyz.client.controller.IPlayerControllerSender;
import de.ativelox.rummyz.model.Hand;
import de.ativelox.rummyz.model.ICard;

/**
 * Provides a basic abstract implementation of a {@link IClientView}, that
 * handles some flag setting, and has a valid game-state at all times, which can
 * be useful to display it.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public abstract class AClientView<POut, PIn> implements IClientView<POut, PIn> {

    /**
     * Whether view actions are currently blocked or not.
     */
    protected boolean mBlocked;

    /**
     * The hand of this players view.
     */
    protected final Hand mHand;

    /**
     * A mapping from any superIndex to its associated sequence of cards on the
     * board.
     */
    protected final Map<Integer, List<ICard>> mCardsOnField;

    /**
     * The current state of the grave yard.
     */
    protected final Stack<ICard> mGraveyard;

    /**
     * The controller used to trigger send requests to the server.
     */
    protected IPlayerControllerSender<POut, PIn> mPcSender;

    /**
     * The amount of points this view currently has.
     */
    protected int mPoints;

    /**
     * Whether this view is currently running or not.
     */
    protected boolean mRunning;

    public AClientView() {
	mBlocked = true;
	mCardsOnField = new HashMap<>();
	mHand = new Hand();
	mGraveyard = new Stack<>();
	mPoints = 0;
	mRunning = true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#addCard(de.ativelox.rummyz.model.
     * Card)
     */
    @Override
    public void addCard(final ICard card) {
	mHand.add(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#addCardFromGraveyard()
     */
    @Override
    public void addCardFromGraveyard() {
	this.mGraveyard.pop();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#addCardsPlayed(java.util.List,
     * java.lang.String[])
     */
    @Override
    public void addCardsPlayed(final List<List<ICard>> cards, final String[] ids) {
	for (int i = 0; i < ids.length; i++) {
	    mCardsOnField.put(Integer.parseInt(ids[i]), cards.get(i));

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#addCardToGraveyard(de.ativelox.
     * rummyz.model.Card)
     */
    @Override
    public void addCardToGraveyard(final ICard card) {
	mGraveyard.add(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#addPoints(int)
     */
    @Override
    public void addPoints(final int points) {
	mPoints += points;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#appendCard(de.ativelox.rummyz.
     * model.Card, int, int)
     */
    @Override
    public void appendCard(final ICard card, final int superIndex, final int insertIndex) {
	if (superIndex >= mCardsOnField.size()) {
	    throw new IllegalArgumentException();
	}

	final List<ICard> list = mCardsOnField.get(superIndex);

	if (insertIndex < 0 || insertIndex > list.size()) {
	    throw new IllegalArgumentException();
	}

	list.add(insertIndex, card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#invalidPlay()
     */
    @Override
    public abstract void invalidPlay();

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onBlockStateChange(boolean)
     */
    @Override
    public void onBlockStateChange(final boolean block) {
	this.mBlocked = block;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onDefeat()
     */
    @Override
    public void onDefeat() {
	this.onBlockStateChange(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onGraveyardEmpty()
     */
    @Override
    public void onGraveyardEmpty() {
	this.mGraveyard.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onTurnStart()
     */
    @Override
    public void onTurnStart() {
	this.onBlockStateChange(false);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onVictory()
     */
    @Override
    public void onVictory() {
	this.onBlockStateChange(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#register(de.ativelox.rummyz.client
     * .controller.IPlayerControllerSender)
     */
    @Override
    public void register(final IPlayerControllerSender<POut, PIn> pcSender) {
	this.mPcSender = pcSender;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#removeCard(de.ativelox.rummyz.
     * model.Card)
     */
    @Override
    public void removeCard(final ICard card) {
	mHand.remove(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.IClientView#removeCardFromGraveyard(boolean)
     */
    @Override
    public void removeCardFromGraveyard() {
	this.mGraveyard.pop();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public abstract void run();

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#terminate()
     */
    @Override
    public void terminate() {
	mRunning = false;

    }
}

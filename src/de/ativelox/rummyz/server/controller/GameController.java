package de.ativelox.rummyz.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.ativelox.rummyz.model.Deck;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;
import de.ativelox.rummyz.model.util.NetworkUtils;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * Provides a basic implementation of an {@link IGameController}. This
 * implementation doesn't care about <i>packet loss</i>, resulting in a
 * completely out of synch experience on the server and client sides if
 * happening, also doesn't care about encryption, so all network traffic is
 * <b>plain text</b> and doesn't care about checking if clients are doing
 * inappropriate stuff, e.g. a client could easily send {@link EC2S#VICTORY} and
 * would instantly win, without checking if it was actually <i>valid</i>. On the
 * other hand this provides an easy to understand and simple
 * {@link IGameController}, which could easily be extended to adhere to such
 * standards.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class GameController implements IGameController<ES2C, EC2S> {

    /**
     * Provides a mapping from each player id to its associated
     * {@link INetworkController}.
     */
    private final Map<Integer, INetworkController<ES2C, EC2S>> mPIdToNetwork;

    /**
     * Reflects the state of the current cards on the board, mapping each superIndex
     * to its respective sequence of cards.
     * 
     */
    private final Map<Integer, List<ICard>> mOnFieldState;

    /**
     * Reflects the state of the current grave yard.
     */
    private final Stack<ICard> mGraveyard;

    /**
     * The amount of players this instance manages and are currently participating
     * in the game.
     */
    private final int mPlayerAmount;

    /**
     * The amount of players ready to play.
     */
    private int mReadyPlayers;

    /**
     * Whether the game is running or not.
     */
    private boolean mGameOngoing;

    /**
     * The id of the player that is currently on his turn.
     */
    private int mCurrentTurnPlayerId;

    /**
     * The deck used in the game.
     */
    private Deck mDeck;

    /**
     * The current superIndex used to uniformly address the sequence of cards on the
     * board.
     */
    private int mCurrentPlayedCardsID;

    /**
     * Creates a new {@link GameController}.
     * 
     * @param playerAmount The amount of players this instance manages.
     */
    public GameController(final int playerAmount) {
	mPIdToNetwork = new HashMap<>();
	mOnFieldState = new HashMap<>();
	mGraveyard = new Stack<>();

	mPlayerAmount = playerAmount;
	mReadyPlayers = 0;
	mGameOngoing = false;
	mCurrentTurnPlayerId = 0;
	mDeck = new Deck();

	mCurrentPlayedCardsID = 0;

    }

    /**
     * Passes the turn onto the next player and sends all the required messages to
     * all the connected clients.
     */
    private void nextTurn() {
	mCurrentTurnPlayerId = (mCurrentTurnPlayerId % 2) + 1;
	this.sendExcluding(ES2C.BLOCK, mCurrentTurnPlayerId);

	this.onCardDrawRequest(mCurrentTurnPlayerId, 1);

	this.sendTurnStart(mCurrentTurnPlayerId);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerReceiver#onCardAppend(
     * int, de.ativelox.rummyz.model.Card, int, int)
     */
    @Override
    public void onCardAppend(final int playerId, final ICard card, final int superIndex, final int insertIndex) {
	mOnFieldState.get(superIndex).add(insertIndex, card);

	sendToAll(ES2C.CARD_APPEND_UPDATE, NetworkUtils.encodeAppendCard(card, superIndex, insertIndex));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerReceiver#onCardDiscard(
     * int, de.ativelox.rummyz.model.Card)
     */
    @Override
    public void onCardDiscard(final int playerId, final ICard card) {
	mGraveyard.add(card);

	sendToAll(ES2C.GRAVEYARD_UPDATE, NetworkUtils.encodeCard(card));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameControllerReceiver#
     * onCardDrawRequest(int)
     */
    @Override
    public void onCardDrawRequest(final int playerId, final int amount) {
	final List<ICard> toSend = new LinkedList<>();

	for (int i = 0; i < amount; i++) {
	    if (mDeck.isEmpty()) {
		// fetch all cards from the graveyard stack, and shuffle those back into the
		// deck.
		List<ICard> toShuffle = new ArrayList<>();

		while (!mGraveyard.isEmpty()) {
		    toShuffle.add(mGraveyard.pop());
		}
		mDeck.generateFrom(toShuffle);
		this.sendToAll(ES2C.GRAVEYARD_EMPTY, null);
	    }
	    toSend.add(mDeck.draw());

	}
	this.sendCards(playerId, toSend);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameController#onCardsPlayed(java.util.
     * List, int)
     */
    @Override
    public void onCardsPlayed(final List<List<ICard>> cards, final int playerId) {
	// register the played cards, and assign an ID, so everyone can refer to them in
	// a uniform manner.

	String[] ids = new String[cards.size()];

	for (int i = 0; i < ids.length; i++) {
	    ids[i] = mCurrentPlayedCardsID + "";

	    mOnFieldState.put(mCurrentPlayedCardsID, cards.get(i));

	    mCurrentPlayedCardsID++;

	}

	for (int i = 1; i <= mPlayerAmount; i++) {
	    this.sendCardsPlayedUpdate(i, cards, ids);

	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameController#onReady(int)
     */
    @Override
    public void onReady(final int playerId) {
	mReadyPlayers++;

	if (mReadyPlayers >= mPlayerAmount && !mGameOngoing) {
	    mDeck.generate();

	    // deal the hand
	    for (int i = 1; i <= mPlayerAmount; i++) {
		this.onCardDrawRequest(i, 10);

	    }
	    this.nextTurn();
	    mGameOngoing = true;

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameController#onTurnEnd(int)
     */
    @Override
    public void onTurnEnd(final int playerId) {
	this.nextTurn();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerReceiver#onVictory(int)
     */
    @Override
    public void onVictory(final int playerId) {
	sendExcluding(ES2C.DEFEAT, playerId);
	this.sendVictory(playerId);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameControllerSender#register(int,
     * de.ativelox.rummyz.model.INetworkController)
     */
    @Override
    public void register(final int playerId, final INetworkController<ES2C, EC2S> networkController) {
	this.mPIdToNetwork.put(playerId, networkController);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendBlock(int)
     */
    @Override
    public void sendBlock(final int playerId) {
	this.mPIdToNetwork.get(playerId).send(ES2C.BLOCK);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendCards(int,
     * java.util.List)
     */
    @Override
    public void sendCards(final int playerId, final List<ICard> toSend) {
	mPIdToNetwork.get(playerId).send(ES2C.SEND_CARDS, NetworkUtils.encodeCards(toSend));

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameControllerSender#
     * sendCardsPlayedUpdate(int, java.util.List)
     */
    @Override
    public void sendCardsPlayedUpdate(final int playerId, final List<List<ICard>> cards, final String[] ids) {
	mPIdToNetwork.get(playerId).send(ES2C.CARDS_PLAYED_UPDATE, NetworkUtils.encodeCardsPlayed(cards, ids));

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendDefeat(int)
     */
    @Override
    public void sendDefeat(final int playerId) {
	this.mPIdToNetwork.get(playerId).send(ES2C.DEFEAT);

    }

    /**
     * Sends the given message to all players except the one with
     * <tt>excludePlayerId</tt>;
     * 
     * @param protocol        The protocol to send.
     * @param excludePlayerId The player id <b>not</b> to send this to.
     * 
     * @see GameController#sendExcluding(ES2C, String[], int)
     */
    private void sendExcluding(final ES2C protocol, final int excludePlayerId) {
	this.sendExcluding(protocol, null, excludePlayerId);
    }

    /**
     * Sends the given message to all players, except the one with
     * <tt>excludePlayerId</tt>.
     * 
     * @param protocol        The protocol to send.
     * @param args            The additional arguments.
     * @param excludePlayerId The player id <b>not</b> to send this to.
     */
    private void sendExcluding(final ES2C protocol, final String[] args, final int excludePlayerId) {

	for (int i = 0; i < mPlayerAmount - 1; i++) {
	    int playerIdToSend = ((excludePlayerId + i) % mPlayerAmount) + 1;
	    this.mPIdToNetwork.get(playerIdToSend).send(protocol, args);

	}
    }

    /**
     * Sends the given message to all players.
     * 
     * @param protocol The protocol to send.
     * @param args     The additional arguments.
     */
    private void sendToAll(final ES2C protocol, final String[] args) {
	for (int i = 1; i <= mPlayerAmount; i++) {
	    mPIdToNetwork.get(i).send(protocol, args);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendTurnStart(int)
     */
    @Override
    public void sendTurnStart(final int playerId) {
	this.mPIdToNetwork.get(playerId).send(ES2C.TURN_START);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendVictory(int)
     */
    @Override
    public void sendVictory(final int playerId) {
	this.mPIdToNetwork.get(playerId).send(ES2C.VICTORY);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.server.controller.IGameControllerSender#sendWelcome(int)
     */
    @Override
    public void sendWelcome(final int playerId) {
	this.mPIdToNetwork.get(playerId).send(ES2C.WELCOME, new String[] { playerId + "" });

    }
}

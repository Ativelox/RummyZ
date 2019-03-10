package de.ativelox.rummyz.client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;

import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * A console view implementation of {@link IClientView}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see AClientView
 *
 */
public final class ConsoleClientView extends AClientView<EC2S, ES2C> {

    /**
     * The amount in milliseconds this Thread is sleeping.
     */
    private static final int SLEEP_MS = 400;

    /**
     * The reader used to read from the console.
     */
    private final BufferedReader mBr;

    /**
     * Creates a new {@link ConsoleClientView}.
     */
    public ConsoleClientView() {
	super();

	mBr = new BufferedReader(new InputStreamReader(System.in));

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#addCard(de.ativelox.rummyz.model.
     * Card)
     */
    @Override
    public void addCard(final ICard card) {
	super.addCard(card);

	System.out.println("Hand changed. Current hand:");
	System.out.println(mHand);
	System.out.println();

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

	System.out.println("Field changed: ");
	this.renderField();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#appendCard(de.ativelox.rummyz.
     * model.Card, int, int)
     */
    @Override
    public void appendCard(final ICard card, final int superIndex, final int insertIndex) {
	super.appendCard(card, superIndex, insertIndex);

	System.out.println("Field changed: ");
	this.renderField();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#invalidPlay()
     */
    @Override
    public void invalidPlay() {
	System.out.println("Invalid play.");

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onDefeat()
     */
    @Override
    public void onDefeat() {
	super.onDefeat();

	System.out.println("You lose!" + " Points: " + mPoints);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.AClientView#onTurnStart()
     */
    @Override
    public void onTurnStart() {
	super.onTurnStart();

	System.out.println("Current Hand:");
	System.out.println(mHand.toString());
	System.out.println();

	System.out.println("Current Field:");
	renderField();
	System.out.println();

	System.out.println("Current Graveyard:");
	if (mGraveyard.size() > 0) {
	    System.out.println(mGraveyard.peek().toString());
	}
	System.out.println();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.IClientView#onVictory()
     */
    @Override
    public void onVictory() {
	super.onVictory();

	System.out.println("You win!" + " Points: " + mPoints);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.AClientView#removeCard(de.ativelox.rummyz.
     * model.Card)
     */
    @Override
    public void removeCard(final ICard card) {
	super.removeCard(card);

	System.out.println("Hand changed. Current hand:");
	System.out.println(mHand);
	System.out.println();

    }

    /**
     * Renders the current cards on the field onto the console.
     */
    private void renderField() {
	for (Entry<Integer, List<ICard>> entry : mCardsOnField.entrySet()) {

	    StringJoiner sj = new StringJoiner("\t");
	    sj.add(entry.getKey() + ": ");

	    for (final ICard card : entry.getValue()) {
		sj.add(card.toString());
	    }

	    System.out.println(sj.toString());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	while (mRunning) {
	    if (mBlocked) {
		try {
		    Thread.sleep(SLEEP_MS);

		} catch (final InterruptedException e) {
		    e.printStackTrace();

		}
		continue;

	    }

	    try {
		while (mBr.ready()) {
		    String query = mBr.readLine();

		    if (query == null) {
			break;

		    }
		    this.serve(query);

		}
	    } catch (final IOException e) {
		e.printStackTrace();
	    }

	}

    }

    /**
     * Tries to interpret the given query and executes it. Supported queries
     * are:<br>
     * <tt>discard [hand card id]</tt><br>
     * <tt>play</tt><br>
     * <tt>append [hand card id] [card sequence id] [insert index]</tt>
     * 
     * @param query The query mentioned.
     */
    private void serve(final String query) {
	if (query.startsWith("discard")) {
	    final String[] split = query.split("\\s+");

	    if (split.length != 2) {
		return;

	    }
	    final int index = Integer.parseInt(split[1]);
	    mPcSender.sendDiscardCard(mHand.get(index));
	    mPcSender.sendTurnEnd();
	    return;

	}

	if (query.startsWith("play")) {
	    final String[] cardIds = query.split("\\s+");

	    final List<List<ICard>> queriedPlay = new ArrayList<>();
	    queriedPlay.add(new LinkedList<>());

	    int curId = 0;

	    for (int i = 1; i < cardIds.length; i++) {
		if (cardIds[i].equals("+")) {
		    queriedPlay.add(new LinkedList<>());
		    curId++;
		    continue;
		}
		queriedPlay.get(curId).add(mHand.get(Integer.parseInt(cardIds[i])));

	    }
	    mPcSender.sendCardsPlayed(queriedPlay);
	}

	if (query.startsWith("append")) {
	    final String[] args = query.split("\\s+");
	    int cardIndex = Integer.parseInt(args[1]);
	    int superIndex = Integer.parseInt(args[2]);
	    int insertIndex = Integer.parseInt(args[3]);

	    this.mPcSender.sendAppendCard(mHand.get(cardIndex), superIndex, insertIndex);
	}
    }
}

package de.ativelox.rummyz.server.controller;

import java.io.InputStream;
import java.io.OutputStream;

import de.ativelox.rummyz.model.ANetworkController;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;
import de.ativelox.rummyz.model.util.ImmutableTriple;
import de.ativelox.rummyz.model.util.NetworkUtils;
import de.ativelox.rummyz.network.exception.UnsupportedProtocolException;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * Provides a {@link INetworkController} for the server, which sends the
 * protocol specified by {@link ES2C} to its given {@link OutputStream} and
 * reads the protocol specified by {@link EC2S} from its given
 * {@link InputStream}. This implementation does <b>not</b> care about
 * encryption.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see INetworkController
 * @see ANetworkController
 *
 */
public final class ServerNetworkController extends ANetworkController<ES2C, EC2S> {

    /**
     * The {@link IGameControllerReceiver} for this {@link INetworkController}
     * designed to receive messages read from this instances underlying
     * {@link InputStream}.
     */
    private final IGameControllerReceiver mGc;

    /**
     * The id of the player this instance is managing.
     */
    private final int mPlayerId;

    /**
     * Creates a new {@link ServerNetworkController}.
     * 
     * @param gc       The controller used to manage the game flow.
     * @param playerId The id of the player this instance manages.
     * @param is       The input stream this instance reads from.
     * @param os       The output stream this instance writes to.
     */
    public ServerNetworkController(final IGameControllerReceiver gc, final int playerId, final InputStream is,
	    final OutputStream os) {
	super(is, os, EC2S.class);

	mPlayerId = playerId;
	mGc = gc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ANetworkController#send(java.lang.Enum,
     * java.lang.String[])
     */
    @Override
    public void send(final ES2C protocol, final String[] additional) {
	super.send(protocol, additional);

	System.out.println("Sending " + protocol.toString() + " to player " + mPlayerId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#serve(java.lang.String)
     */
    @Override
    public void serve(final EC2S protocol, final String[] op) {
	System.out.println("Received " + protocol.toString() + " from player " + mPlayerId);

	switch (protocol) {
	case CARDS_PLAYED:
	    mGc.onCardsPlayed(NetworkUtils.decodeCardsPlayed(op).getKey(), mPlayerId);
	    break;

	case READY:
	    mGc.onReady(mPlayerId);
	    break;

	case TURN_END:
	    mGc.onTurnEnd(mPlayerId);
	    break;

	case DRAW_CARDS:
	    mGc.onCardDrawRequest(mPlayerId, Integer.parseInt(op[0]));

	case CARD_DISCARD:
	    mGc.onCardDiscard(mPlayerId, NetworkUtils.decodeCard(op));
	    break;

	case CARD_APPEND:
	    final ImmutableTriple<ICard, Integer, Integer> triple = NetworkUtils.decodeAppendCard(op);
	    mGc.onCardAppend(mPlayerId, triple.getFirst(), triple.getSecond(), triple.getThird());
	    break;

	case VICTORY:
	    mGc.onVictory(mPlayerId);
	    break;

	case GRAVEYARD_PICKUP:
	    mGc.onGraveyardPickup(mPlayerId);
	    break;

	default:
	    throw new UnsupportedProtocolException(
		    "The protocol named " + EC2S.class.getName() + "." + protocol.toString() + " isn't supported.");

	}
    }

}

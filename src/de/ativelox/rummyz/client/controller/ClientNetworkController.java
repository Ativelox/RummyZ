package de.ativelox.rummyz.client.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.ativelox.rummyz.model.ANetworkController;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;
import de.ativelox.rummyz.model.util.ImmutablePair;
import de.ativelox.rummyz.model.util.ImmutableTriple;
import de.ativelox.rummyz.model.util.NetworkUtils;
import de.ativelox.rummyz.network.exception.UnsupportedProtocolException;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * Provides a {@link INetworkController} for the client, which sends the
 * protocol specified by {@link EC2S} to its given {@link OutputStream} and
 * reads the protocol specified by {@link ES2C} from its given
 * {@link InputStream}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see INetworkController
 * @see ANetworkController
 *
 */
public final class ClientNetworkController extends ANetworkController<EC2S, ES2C> {

    /**
     * The controller for the player this controller is associated with, designed to
     * receive data from this class' underlying {@link InputStream}.
     */
    private final IPlayerControllerReceiver mPc;

    /**
     * Creates a new {@link ClientNetworkController}.
     * 
     * @param pc The {@link IPlayerControllerReceiver} that's used to send data read
     *           from the given {@link InputStream} to.
     * @param is The {@link InputStream} from which to read data from in the
     *           protocol specified by {@link ES2C}.
     * @param os The {@link OutputStream} to which to write data in the protool
     *           specified by {@link EC2S}.
     */
    public ClientNetworkController(final IPlayerControllerReceiver pc, final InputStream is, final OutputStream os) {
	super(is, os, ES2C.class);

	mPc = pc;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ANetworkController#send(java.lang.Enum,
     * java.lang.String[])
     */
    @Override
    public void send(final EC2S protocol, final String[] additional) {
	super.send(protocol, additional);

	System.out.println("Sending " + protocol.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ANetworkController#serve(java.lang.Enum,
     * java.lang.String[])
     */
    @Override
    public void serve(final ES2C protocol, final String[] additional) {

	System.out.println("Received " + protocol.toString());

	switch (protocol) {
	case TURN_END:
	    mPc.onTurnEnd();
	    break;

	case TURN_START:
	    mPc.onTurnStart();
	    break;

	case WELCOME:
	    mPc.onServerWelcome(Integer.parseInt(additional[0]));
	    break;

	case BLOCK:
	    mPc.onBlock();
	    break;

	case SEND_CARDS:
	    mPc.onCardsReceived(NetworkUtils.decodeCards(additional));
	    break;

	case CARDS_PLAYED_UPDATE:
	    final ImmutablePair<List<List<ICard>>, String[]> pair = NetworkUtils.decodeCardsPlayed(additional);
	    mPc.onCardsPlayedUpdate(pair.getKey(), pair.getValue());
	    break;

	case GRAVEYARD_UPDATE:
	    mPc.onGraveyardUpdate(NetworkUtils.decodeCard(additional));
	    break;

	case CARD_APPEND_UPDATE:
	    final ImmutableTriple<ICard, Integer, Integer> triple = NetworkUtils.decodeAppendCard(additional);
	    mPc.onCardAppendUpdate(triple.getFirst(), triple.getSecond(), triple.getThird());
	    break;

	case DEFEAT:
	    mPc.onDefeat();
	    break;

	case VICTORY:
	    mPc.onVictory();
	    break;

	case GRAVEYARD_EMPTY:
	    mPc.onGraveyardEmpty();
	    break;

	default:
	    throw new UnsupportedProtocolException(
		    "The protocol named " + ES2C.class.getName() + "." + protocol.toString() + " isn't supported.");

	}

    }
}

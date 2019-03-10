package de.ativelox.rummyz.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ativelox.rummyz.client.view.IClientView;
import de.ativelox.rummyz.client.view.gui.GraphicalView;
import de.ativelox.rummyz.model.ICard;
import de.ativelox.rummyz.model.INetworkController;
import de.ativelox.rummyz.model.IPlayer;
import de.ativelox.rummyz.model.assets.Assets;
import de.ativelox.rummyz.model.property.EIO;
import de.ativelox.rummyz.model.util.NetworkUtils;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * Provides a basic implementation of an {@link IPlayerController}. This
 * controller handles communication between the underlying
 * {@link INetworkController} and the {@link IClientView}. Methods of the
 * interface {@link IPlayerControllerSender} get called by the
 * {@link IClientView} and methods of {@link IPlayerControllerReceiver} get
 * called by {@link INetworkController}.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class PlayerController implements IPlayerController<EC2S, ES2C> {

    /**
     * The player this controller agitates upon.
     */
    private final IPlayer mPlayer;

    /**
     * The current view associated with this controller.
     */
    private final IClientView<EC2S, ES2C> mView;

    /**
     * The current network controller associated with this controller, handles
     * socket communication.
     */
    private INetworkController<EC2S, ES2C> mNetworkController;

    /**
     * A mapping from IDs to sequences of cards currently on the field.
     */
    private Map<Integer, List<ICard>> mOnFieldCards;

    /**
     * Whether the player has already done his initial play. Refer to
     * {@link GameRule} for further information.
     */
    private boolean mDidInitial;

    /**
     * Creates a new {@link PlayerController}. Also calls {@link Assets#init()}.
     * 
     * @param player The player this controller manages.
     */
    public PlayerController(final IPlayer player) {
	mPlayer = player;
	Assets.init();

	mView = new GraphicalView(1, 2);
	mOnFieldCards = new HashMap<>();

	mDidInitial = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#onBlock()
     */
    @Override
    public void onBlock() {
	mView.onBlockStateChange(true);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#
     * onCardAppendUpdate(de.ativelox.rummyz.model.Card)
     */
    @Override
    public void onCardAppendUpdate(final ICard card, final int superIndex, final int insertIndex) {
	this.mOnFieldCards.get(superIndex).add(insertIndex, card);

	mView.appendCard(card, superIndex, insertIndex);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#
     * onCardsPlayedUpdate(java.util.List)
     */
    @Override
    public void onCardsPlayedUpdate(final List<List<ICard>> cards, final String[] ids) {
	int i = 0;

	for (final List<ICard> inner : cards) {
	    mOnFieldCards.put(Integer.parseInt(ids[i]), inner);
	    i++;

	}
	this.mView.addCardsPlayed(cards, ids);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#
     * onCardsReceived(java.util.List)
     */
    @Override
    public void onCardsReceived(final List<ICard> cards) {
	for (final ICard card : cards) {
	    mPlayer.getCards().add(card);
	    mView.addCard(card);

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#onDefeat()
     */
    @Override
    public void onDefeat() {
	mView.onDefeat();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#
     * onGraveyardEmpty()
     */
    @Override
    public void onGraveyardEmpty() {
	mView.onGraveyardEmpty();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#
     * onGraveyardUpdate(de.ativelox.rummyz.model.Card)
     */
    @Override
    public void onGraveyardUpdate(final ICard card) {
	mView.addCardToGraveyard(card);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerController#onServerWelcome(int)
     */
    @Override
    public void onServerWelcome(final int playerId) {
	mView.register(this);
	mPlayer.setId(playerId);

	new Thread(mView).start();

	this.sendReady();

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerController#onTurnEnd()
     */
    @Override
    public void onTurnEnd() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerController#onTurnStart()
     */
    @Override
    public void onTurnStart() {
	mView.onTurnStart();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerReceiver#onVictory()
     */
    @Override
    public void onVictory() {
	mView.onVictory();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#register(de.
     * ativelox.rummyz.model.INetworkController)
     */
    @Override
    public void register(final INetworkController<EC2S, ES2C> networkController) {
	this.mNetworkController = networkController;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendAppendCard(
     * de.ativelox.rummyz.model.Card, int, int)
     */
    @Override
    public boolean sendAppendCard(final ICard card, final int superIndex, final int insertIndex) {
	final int points = GameRule.getAppendPoints(mOnFieldCards.get(superIndex), card, insertIndex);

	if (points <= 0 || !mDidInitial) {
	    mView.invalidPlay();
	    return false;

	}

	mView.addPoints(points);
	mPlayer.getCards().remove(card);
	mView.removeCard(card);

	this.mNetworkController.send(EC2S.CARD_APPEND, NetworkUtils.encodeAppendCard(card, superIndex, insertIndex));

	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendCardsPlayed(
     * java.util.List)
     */
    @Override
    public void sendCardsPlayed(final List<List<ICard>> cards) {
	if (!mDidInitial) {
	    if (GameRule.isValidInitial(cards)) {
		mDidInitial = true;

	    } else {
		mView.invalidPlay();
		return;

	    }
	}

	int points = GameRule.getInstantPoints(cards);

	if (points <= 0) {
	    mView.invalidPlay();
	    return;

	}
	mView.addPoints(points);

	for (final List<ICard> innerCards : cards) {
	    for (final ICard card : innerCards) {
		mPlayer.getCards().remove(card);

		mView.removeCard(card);

	    }
	}
	this.mNetworkController.send(EC2S.CARDS_PLAYED, NetworkUtils.encodeCardsPlayed(cards));

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendDiscardCard(
     * de.ativelox.rummyz.model.Card)
     */
    @Override
    public void sendDiscardCard(final ICard card) {
	this.mPlayer.getCards().remove(card);
	this.mView.removeCard(card);

	this.mNetworkController.send(EC2S.CARD_DISCARD, NetworkUtils.encodeCard(card));

	if (mPlayer.getCards().getAmount() == 0) {
	    this.mNetworkController.send(EC2S.VICTORY);
	    this.mNetworkController.ignore(EIO.WRITE);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendDrawCards(
     * int)
     */
    @Override
    public void sendDrawCards(final int amount) {
	this.mNetworkController.send(EC2S.DRAW_CARDS, new String[] { amount + "" });

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendReady()
     */
    @Override
    public void sendReady() {
	mNetworkController.send(EC2S.READY, new String[] { mPlayer.getId() + "" });

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.controller.IPlayerControllerSender#sendTurnEnd()
     */
    @Override
    public void sendTurnEnd() {
	mNetworkController.send(EC2S.TURN_END, new String[0]);

    }
}

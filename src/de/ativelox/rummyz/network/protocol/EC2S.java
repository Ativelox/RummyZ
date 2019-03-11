package de.ativelox.rummyz.network.protocol;

/**
 * An enumeration that holds all the protocols that the client can send to the
 * server.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public enum EC2S {
    CARDS_PLAYED, TURN_END, READY, DRAW_CARDS, CARD_APPEND, CARD_DISCARD, VICTORY, GRAVEYARD_PICKUP;
}

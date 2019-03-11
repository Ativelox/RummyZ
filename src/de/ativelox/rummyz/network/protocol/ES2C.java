package de.ativelox.rummyz.network.protocol;

/**
 * An enumeration that holds all the protocols that the server can send to the
 * client.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public enum ES2C {

    WELCOME, TURN_START, TURN_END, BLOCK, SEND_CARDS, CARDS_PLAYED_UPDATE, GRAVEYARD_UPDATE, CARD_APPEND_UPDATE, DEFEAT,
    VICTORY, GRAVEYARD_EMPTY, GRAVEYARD_DECREASE;

}

package de.ativelox.rummyz.client.view.gui.property;

/**
 * Provides static access to all the layers present in the game.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class LayerConstants {

    /**
     * The layer used for background rendering.
     */
    public static final int BACKGROUND_LAYER = -10;

    /**
     * The layer used for cards, that do not have any particular action associated
     * with them.
     */
    public static final int DEFAULT_CARD_LAYER = 0;

    /**
     * The layer used for cards that are currently hovered.
     */
    public static final int HOVER_CARD_LAYER = 5;

    /**
     * The layer used for cards that are currently moved.
     */
    public static final int MOVING_CARD_LAYER = 10;

    /**
     * The layer used for buttons.
     */
    public static final int BUTTON_LAYER = 1;

    private LayerConstants() {

    }

}

package de.ativelox.rummyz.client.view.gui.utils;

import java.awt.Color;
import java.awt.Graphics;

import de.ativelox.rummyz.client.view.gui.items.GuiCard;
import de.ativelox.rummyz.client.view.gui.property.ISpatial;

/**
 * Provides static access utility functions for drawing.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class DrawUtils {

    /**
     * Draws a card border for the given element in the given color.
     * 
     * @param spatial The element on which to draw the border.
     * @param g       The graphics object used to draw.
     * @param c       The color used for the border.
     */
    public static void drawCardBorder(final ISpatial spatial, final Graphics g, final Color c) {
	Color old = g.getColor();

	int width = GuiCard.WIDTH;
	int height = GuiCard.HEIGHT;

	g.setColor(c);
	g.drawRoundRect(spatial.getX(), spatial.getY(), width, height, 10, 10);
	g.drawRoundRect(spatial.getX() - 1, spatial.getY() - 1, width + 2, height + 2, 12, 12);
	g.drawRoundRect(spatial.getX() - 2, spatial.getY() - 2, width + 4, height + 4, 14, 14);
	g.drawRoundRect(spatial.getX() - 3, spatial.getY() - 3, width + 6, height + 6, 16, 16);

	g.setColor(old);

    }

}

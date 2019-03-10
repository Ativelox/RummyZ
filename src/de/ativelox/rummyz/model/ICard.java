package de.ativelox.rummyz.model;

import de.ativelox.rummyz.model.property.ECardType;
import de.ativelox.rummyz.model.property.ECardValue;

/**
 * Provides a basic interface for cards.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface ICard {

    /**
     * Gets the {@link ECardType} of this card.
     * 
     * @return The type mentioned.
     */
    ECardType getType();

    /**
     * Gets the {@link ECardValue} of this card.
     * 
     * @return The value mentioned.
     */
    ECardValue getValue();

}

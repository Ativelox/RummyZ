package de.ativelox.rummyz.client.controller;

/**
 * Acts as a container interface, batching both {@link IPlayerControllerSender}
 * and {@link IPlayerControllerReceiver} together.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see IPlayerControllerSender
 * @see IPlayerControllerReceiver
 *
 */
public interface IPlayerController<POut, PIn> extends IPlayerControllerSender<POut, PIn>, IPlayerControllerReceiver {

}

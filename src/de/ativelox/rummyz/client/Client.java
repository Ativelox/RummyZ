package de.ativelox.rummyz.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import de.ativelox.rummyz.client.controller.ClientNetworkController;
import de.ativelox.rummyz.client.controller.IPlayerController;
import de.ativelox.rummyz.client.controller.PlayerController;
import de.ativelox.rummyz.model.INetworkController;
import de.ativelox.rummyz.model.IPlayer;
import de.ativelox.rummyz.model.Player;
import de.ativelox.rummyz.network.protocol.EC2S;
import de.ativelox.rummyz.network.protocol.ES2C;

/**
 * The Client functions as the top-class for the Client, also having the
 * {@link Client#main(String[])} function. It maintains connection to the
 * server, and creates an {@link IPlayerController} with an associated
 * {@link INetworkController} which handles further socket communication.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Client {

    /**
     * Starts a new client by calling {@link Client#startConnection(String, int)}.
     * 
     * @param args The command-line arguments. Unused.
     * @throws UnknownHostException If localhost cannot be resolved.
     * @throws IOException          If an I/O exception occurs when trying to create
     *                              the server socket.
     */
    public static void main(final String[] args) throws UnknownHostException, IOException {
	Client c = new Client(new Player());
	c.startConnection("localhost", 2556);

    }

    /**
     * The controller, handling communication between the socket and the view.
     */
    private final IPlayerController<EC2S, ES2C> mPc;

    /**
     * The socket representing the server.
     */
    private Socket mServer;

    /**
     * The network controller, used by the {@link IPlayerController} to send/receive
     * data to/from the server.
     */
    private INetworkController<EC2S, ES2C> mNetworkController;

    /**
     * Creates a new {@link Client}.
     * 
     * @param player The player this client handles.
     */
    public Client(final IPlayer player) {
	mPc = new PlayerController(player);

    }

    /**
     * Starts the connection with the server, identified by the given <tt>host</tt>
     * and <tt>port</tt>. Behavior is undefined if a server does not exist for the
     * given parameters.
     * 
     * @param host The host associated with the server.
     * @param port The port the server listens on.
     * @throws UnknownHostException If the host cannot be resolved.
     * @throws IOException          If an I/O exception occurs when trying to create
     *                              the server socket.
     */
    public void startConnection(final String host, final int port) throws UnknownHostException, IOException {
	mServer = new Socket(InetAddress.getByName(host), port);
	mNetworkController = new ClientNetworkController(mPc, mServer.getInputStream(), mServer.getOutputStream());
	new Thread(mNetworkController).start();

	mPc.register(mNetworkController);

    }

    /**
     * Properly terminates this connection to the socket. Will also call
     * {@link INetworkController#stop()} to stop the underlying
     * {@link INetworkController}.
     * 
     * @throws IOException if an I/O exception occurs when trying to close the
     *                     socket.
     */
    public void terminateConnection() throws IOException {
	mNetworkController.stop();
	mServer.close();

    }
}

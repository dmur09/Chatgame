import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MTServer.java
 *
 * <p>This program implements a simple multithreaded chat server.	Every client that
 * connects to the server can broadcast data to all other clients.
 * The server stores an ArrayList of sockets to perform the broadcast.
 *
 * <p>The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * <p>To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */

public class MtServer {
	// Maintain list of all client sockets for broadcast
	private ArrayList<Client> clientList;

	public MtServer() {
		clientList = new ArrayList<Client>();
	}

	private void getConnection() {
		// Wait for connection
		try {
			System.out.println("Waiting for client connections on port 9008.");
			ServerSocket serverSock = new ServerSocket(9008);
			while (true) {
				Socket connectSocket = serverSock.accept();
				// Send to ClientHandler the socket and arraylist of all sockets
				ClientHandler handler = new ClientHandler(connectSocket, this.clientList);
				Thread theThread = new Thread(handler);
				theThread.start();
			}
		} catch (IOException e) {	
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		MtServer server = new MtServer();
		server.getConnection();
	}
}
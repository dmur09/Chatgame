import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
* ClientHandler.java
*
* <p>This class handles communication between the client
* and the server.  It runs in a separate thread but has a
* link to a common list of sockets to handle broadcast.
*
*/
public class ClientHandler implements Runnable {
  private Socket connSocket = null;
  private ArrayList<Client> clientList;

  ClientHandler(Socket sock, ArrayList<Client> clientList) {
    this.connSocket = sock;
    this.clientList = clientList;  // Keep reference to master list
  }

  /**
   * A boolean method that returns if a given name is used.
   */
  public boolean nameInUse(String username) {
    return clientList.stream().noneMatch(client -> client.username.equals(username));
  }

  /**
   * sends message to all clients connected to server
   */
  private void sendMessages(String message, Client out) throws IOException {
    synchronized (clientList) {
      for (Client c : clientList) {
        if (c != out) {
          DataOutputStream clientOutput = new DataOutputStream(c.connSocket.getOutputStream());
          clientOutput.writeBytes(message);
        }
      }
    }
  }
  
  /**
   * method to get all connected users.
   */
  private String viewUsers() {
    StringBuilder userList = new StringBuilder("Users connected:\n");
    synchronized (clientList) {
      for (Client c : clientList) {
        if (!(c.connSocket == connSocket)) {
          userList.append(c.username).append("\n");
        }
      }
    }
    return userList.toString();
  }

  private String viewScores(){
	StringBuilder scoreList = new StringBuilder("Scores:\n");
	synchronized (clientList) {
		for (Client c : clientList) {
			if (!(c.connSocket == connSocket)) {
				scoreList.append(c.username).append(": ").append(c.score).append("\n");
			}
		}
  	}
	return scoreList.toString();
}
	private void updateScore(String username){
		synchronized (clientList) {
			for (Client c : clientList) {
				if (c.username.equals(username)) {
					c.score += 1;
				}
			}
		}
	}

  /**
	 * removes the  client from the list of connected clients
	 */
	private void removeClient() {
		Client removedClient = null;
		for (Client c : clientList) {
		if (c.connSocket == connSocket) {
			removedClient = c;
		}
		// socket is closed by client
		}
		if (removedClient != null) {
			synchronized (clientList) {
				clientList.remove(removedClient);
			}
		}
	}


	/**
	 * received input from a client.
	 * sends it to other clients.
	 */
	public void run() {
		try {
			System.out.println("Connection made with socket " + connSocket);
			BufferedReader clientInput = new BufferedReader(new
			InputStreamReader(connSocket.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(connSocket.getOutputStream());
			String username = "";
			int score = 0;
			if(clientList.size()==0){
				clientOutput.writeBytes("You are the Host.\n");
				username = "Host";
			}else{
				clientOutput.writeBytes("Please enter your username:\n");
				username = clientInput.readLine();
				if(!nameInUse(username)){
					while (!nameInUse(username)) {
						clientOutput.writeBytes("This username is in use. Please enter a different username:\n");
						username = clientInput.readLine();
					}
					clientOutput.writeBytes("Username assigned!\n");
				}
				else{
					clientOutput.writeBytes("Username assigned!\n");
				}
			}
			Client client = new Client(connSocket, username, score);
			synchronized (clientList) {
				clientList.add(client);
			}
			String receivedMessage = username + " has joined the chat\n";
			sendMessages(receivedMessage, client);
			String inputText;
			if (username == "Host"){
				clientOutput.writeBytes("Command Options:\n" + "QUIT: Quit the chat\n" + "Who?: View all users connected to the chat\n" 
				+ "SCORES: View all users and their scores\n"
				+ "Update: Update the score of a specific user\n"
				+ "Otherwise, type a TRUE/FALSE question to ask the other connected users\n");
			while((inputText=clientInput.readLine())!=null){
				System.out.println(username + ": " + inputText);
				switch(inputText){
					case "QUIT":
						String leftServerText = username + " has left the chat\n";
						sendMessages(leftServerText, client);
						removeClient();
						break;
					case "Who?":
						String userList = viewUsers();
						clientOutput.writeBytes(userList);
					break;
					case "SCORES":
						String scoreList = viewScores();
						clientOutput.writeBytes(scoreList);
						break;
					case "Update":
						clientOutput.writeBytes("Enter the username of the user you would like to update:\n");
						String userToUpdate = clientInput.readLine();
						updateScore(userToUpdate);
					default:
						String message = username + ": " + inputText + "\n";
						sendMessages(message, client);
					}
				}
			}else{
				clientOutput.writeBytes("Command Options:\n" + "QUIT: Quit the chat\n" + "Who?: View all users connected to the chat\n"
				+ "Otherwise, type in your message to chat with other clients or to answer questions provided by the Host.\n");
				while ((inputText = clientInput.readLine()) != null) {
					System.out.println(username + ": " + inputText);
					switch (inputText) {
					case "QUIT":
						String leftServerText = username + " has left the chat\n";
						sendMessages(leftServerText, client);
						removeClient();
						break;
					case "Who?":
						String userList = viewUsers();
						clientOutput.writeBytes(userList);
						break;
					default:
						String message = username + ": " + inputText + "\n";
						sendMessages(message, client);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
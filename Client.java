import java.net.Socket;

public class Client {

	public Socket connSocket = null;

	public String username = "";

	public int score = 0;

	Client(Socket sock, String username, int score) {
 
		this.connSocket = sock;
 
		this.username = username;

		this.score = score;
	}
}

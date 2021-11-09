import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
	private final Server server;
	private final Socket socket;
	private PrintWriter writer;
	private String userName;

	public Connection(Socket socket, Server server) throws IOException {
		this.server = server;
		this.socket = socket;
		OutputStream output = socket.getOutputStream();
		writer = new PrintWriter(output, true);
	}

	@Override
	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			reader.read();
			int charCount = reader.read();
			char[] chars = new char[charCount];
			reader.read(chars);
			userName = String.valueOf(chars);

			String serverMessage = "New user connected: " + userName;
			server.broadcast(serverMessage, this);

			String clientMessage;

			do {
				charCount = reader.read();
				chars = new char[charCount];
				reader.read(chars);
				clientMessage = String.valueOf(chars);
				serverMessage = userName + ": " + clientMessage;
				server.broadcast(serverMessage, this);

			} while (!clientMessage.equals("/bye"));

			socket.close();

			serverMessage = userName + " has quited.";
			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	void sendMessage(String message) {
		writer.write(message.length());
		writer.write(message);
		writer.flush();
	}
}

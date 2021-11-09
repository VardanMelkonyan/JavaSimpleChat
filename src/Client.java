import java.io.IOException;
import java.net.Socket;

public class Client {
	private final String host;
	private final int port;
	private final String name;
	private Socket socket;

	public Client(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}

	public void connect() {
		try {
			this.socket = new Socket(host, port);

			new ReaderThread(this).start();
			new WriterThread(this).start();

		} catch (IOException ioException) {
			System.out.println("Invalid server");
			System.out.println(ioException.getMessage());
			ioException.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public String getName() {
		return name;
	}
}

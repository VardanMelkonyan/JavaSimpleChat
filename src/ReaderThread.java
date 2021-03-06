import java.io.*;

public class ReaderThread extends Thread {

	private BufferedReader reader;
	private DataInputStream inputStream;

	public ReaderThread(Client client) throws IOException {
		InputStream input = client.getSocket().getInputStream();
		reader = new BufferedReader(new InputStreamReader(input));
		inputStream = new DataInputStream(input);
	}

	@Override
	public void run() {
		while (true) {
			String response = null;
			int length;
			char[] chars;
			try {
				length = inputStream.readInt();
				if (length == -1) continue;

				chars = new char[length];
				reader.read(chars);
				response = String.valueOf(chars);

				if (!response.isEmpty()) {
					System.out.println(response);
					System.out.print("> ");
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}
	}
}

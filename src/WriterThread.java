import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class WriterThread extends Thread {

	private final Client client;
	private PrintWriter pw;
	private OutputStream out;

	public WriterThread(Client client) throws IOException {
		this.client = client;
		out = client.getSocket().getOutputStream();
		pw = new PrintWriter(out, true);
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello " + client.getName());
		try {
			send(client.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true){
			System.out.print("> ");
			String text = scanner.nextLine();
			try {
				send(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void send(String msg) throws IOException {
		out.write(intToBytes(msg.length()));
		pw.write(msg);
		pw.flush();
	}

	private static byte[] intToBytes(final int data) {
		return new byte[] {
				(byte)((data >> 24) & 0xff),
				(byte)((data >> 16) & 0xff),
				(byte)((data >> 8) & 0xff),
				(byte)((data >> 0) & 0xff),
		};
	}
}

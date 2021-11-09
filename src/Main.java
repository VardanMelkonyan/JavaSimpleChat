public class Main {
	public static void main(String[] args) {
		if (args.length < 3) throw new IllegalArgumentException();
		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String username = args[2];

		new Client(host, port, username).connect();
	}
}

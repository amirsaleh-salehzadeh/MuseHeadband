import oscP5.*;

public class MuseOscServer {

	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;
	static int recvPort = 5000;

	public static void main(String[] args) {
		museOscServer = new MuseOscServer();
		museOscServer.museServer = new OscP5(museOscServer, "127.0.0.1", 5000);
	}

	void oscEvent(OscMessage msg) {
		if (msg.checkAddrPattern("/muse/eeg") == true) {
			EEG = new MuseSignalEntity(0, 0, 0, 0, 0, msg.get(0).floatValue(),
					msg.get(1).floatValue(), msg.get(2).floatValue(), msg
							.get(3).floatValue());
			for (int i = 0; i < 4; i++) {
				System.out.print("EEG on channel " + i + ": "
						+ msg.get(i).floatValue() + "\n");
			}
		}
	}
}
package app.headband;
import oscP5.*;

public class MuseOscServer {

	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;

	public static void main(String[] args) {
//		museOscServer = new MuseOscServer();
//		museOscServer.museServer = new OscP5(museOscServer, "localhost", 5001);
//		museOscServer.museServer.stop();
	}

	void oscEvent(OscMessage msg) {
		if (msg.checkAddrPattern("/muse/eeg") == true) {
			EEG = new MuseSignalEntity(0, 0, 0, 0, 0, msg.get(0).floatValue(),
					msg.get(1).floatValue(), msg.get(2).floatValue(), msg
							.get(3).floatValue());
//			for (int i = 0; i < 4; i++) {
//				System.out.print("EEG on channel " + i + ": "
//						+ msg.get(i).floatValue() + "\n");
//			}
		}
	}
}
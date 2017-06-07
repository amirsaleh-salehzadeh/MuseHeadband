package app.headband;

import oscP5.OscMessage;
import oscP5.OscP5;

public class ThreadEEGReceiver implements Runnable {

	public static volatile boolean killer;
	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;

	public ThreadEEGReceiver() {
		this.killer = true;
		 museOscServer = new MuseOscServer();
		 museOscServer.museServer = new OscP5(museOscServer, "localhost",
		 5005);

	}

	@Override
	public void run() {
		while (killer) {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EEG = museOscServer.EEG;
//			for (int j = 0; j < 500; j++) {
//				EEG = new MuseSignalEntity(j, j, j, j, j, j, j, j, j);
//				if (j == 480)
//					j = 10;
//			}
		}
		killer = false;
		Thread.currentThread().interrupt();
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

	public static void main(String[] args) {
		// ThreadSubmitLine ts = new ThreadSubmitLine();
		// ts.run();
		// System.out.println("done");
	}
}
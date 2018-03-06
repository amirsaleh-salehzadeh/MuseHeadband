package app.headband;

import oscP5.OscP5;

public class ThreadEEGReceiver implements Runnable {

	public static volatile boolean killer;
	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;
	public static boolean stopHeadband;

	public ThreadEEGReceiver() {
		this.killer = true;
		// muse-io.exe --device Muse-1E5B
		// muse-io.exe --osc osc.tcp://localhost:4444
		museOscServer = new MuseOscServer();
		museOscServer.museServer = new OscP5(museOscServer, "localhost", 5003);
		stopHeadband = false;
	}

	@Override
	public void run() {
		while (killer) {
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (stopHeadband) {
				EEG = new MuseSignalEntity(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				if (museOscServer.museServer.isBroadcast()) {
					museOscServer.museServer.stop();
				}
			} else {
				if (museOscServer.museServer == null
						&& !museOscServer.museServer.isBroadcast()) {
					museOscServer = new MuseOscServer();
					museOscServer.museServer = new OscP5(museOscServer,
							"localhost", 5003);
				}
				EEG = museOscServer.EEG;
			}
		}
		killer = false;
		Thread.currentThread().interrupt();
	}

	public static void main(String[] args) {
		// ThreadSubmitLine ts = new ThreadSubmitLine();
		// ts.run();
		// System.out.println("done");
	}
}
package app.datastream.eeg;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.websocket.Session;

import org.codehaus.jackson.map.ObjectMapper;

import app.AIengine.dataprepration.DataPreparationAccell;
import app.AIengine.dataprepration.RecordData;
import app.common.MuseSignalEntity;
import app.webservices.GetServiceStreamSocketMediator;
import oscP5.OscP5;

public class ThreadEEGReceiver implements Runnable {

	public static volatile boolean killer;
	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;
	public static boolean stopHeadband;
	// private GetServiceStreamSocketMediator socketMSG;
	private ObjectMapper mapper;
	public static boolean record;
	public static boolean accelRecord;
	public static Charset charset;// = Charset.forName("UTF-8");
	public static CharsetEncoder encoder; // = charset.newEncoder();
	public static DataPreparationAccell dpa;
	private static double[] lastAccels;

	public ThreadEEGReceiver() {
		// muse-io.exe --device Muse-1E5B
		// muse-io.exe --osc osc.tcp://localhost:4444
		// muse-io --device Muse-1E5B --osc osc.udp://localhost:5003
		museOscServer = new MuseOscServer();
		museOscServer.museServer = new OscP5(museOscServer, "localhost", 5003);
		stopHeadband = false;
		// socketMSG = new GetServiceStreamSocketMediator();
		mapper = new ObjectMapper();
		charset = Charset.forName("UTF-8");
		encoder = charset.newEncoder();
		record = false;
		accelRecord = false;
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
				// if (museOscServer.museServer.isBroadcast()) {
				museOscServer.museServer.stop();
				// }
				killer = false;
				Thread.currentThread().interrupt();
			} else {
				if (museOscServer.museServer == null
						&& !museOscServer.museServer.isBroadcast()) {
					museOscServer = new MuseOscServer();
					museOscServer.museServer = new OscP5(museOscServer,
							"localhost", 5003);
				}
				EEG = museOscServer.EEG;
				if (EEG != null) {
					try {
						String jsonVal = mapper.writeValueAsString(EEG);
						if (record)
							RecordData.recordMainTask(jsonVal, true);
						EEG.setIMG("");
						for (Session session : GetServiceStreamSocketMediator.peers) {
							if (session.isOpen())
								session.getAsyncRemote().sendText(jsonVal);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
					if (accelRecord) {
						if (dpa == null)
							dpa = new DataPreparationAccell();
						double[] accels = new double[3];
						accels[0] = EEG.getACC_X();
						accels[1] = EEG.getACC_Y();
						accels[2] = EEG.getACC_Z();
						if (lastAccels == null) {
							lastAccels = accels;
						} else if (!Arrays.equals(lastAccels, accels)) {
							DataPreparationAccell
									.recordAccelOnSpreedSheet(accels);
							lastAccels = accels;
						}
					} else if (dpa != null) {
						DataPreparationAccell.exportExcelforAccels();
					}
				}
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
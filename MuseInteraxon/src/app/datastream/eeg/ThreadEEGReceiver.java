package app.datastream.eeg;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

import javax.websocket.Session;

import org.codehaus.jackson.map.ObjectMapper;

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
	public static Charset charset;// = Charset.forName("UTF-8");
	public static CharsetEncoder encoder; // = charset.newEncoder();

	public ThreadEEGReceiver() {
		this.killer = true;
		// muse-io.exe --device Muse-1E5B
		// muse-io.exe --osc osc.tcp://localhost:4444
		//muse-io --device Muse-1E5B --osc osc.udp://localhost:5003

		museOscServer = new MuseOscServer();
		museOscServer.museServer = new OscP5(museOscServer, "localhost", 5003);
		stopHeadband = false;
		// socketMSG = new GetServiceStreamSocketMediator();
		mapper = new ObjectMapper();
		charset = Charset.forName("UTF-8");
		encoder = charset.newEncoder();
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
				if (EEG != null) {
					try {
						String jsonVal = mapper.writeValueAsString(EEG);
						RecordData.recordMainTask(jsonVal, true);
						EEG.setIMG("");
						for (Session session : GetServiceStreamSocketMediator.peers) {
							if (session.isOpen())
								session.getAsyncRemote().sendText(jsonVal);
						}
					} catch (Throwable e) {
						e.printStackTrace();
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
package app.webservices;

import java.io.EOFException;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.math3.ml.neuralnet.Network;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import app.AIengine.NeuralNetworks.FeedforwardNeuralNetwork;
import app.AIengine.dataprepration.DataPreparation;
import app.datastream.eeg.MuseOscServer;
import app.datastream.eeg.ThreadEEGReceiver;

@ServerEndpoint("/SoundStreamSocket/{client-id}")
public class TrainingStreamSocketMediator {

	public static Set<Session> peers = Collections
			.synchronizedSet(new HashSet<Session>());
	private static DataPreparation dp;

	@OnMessage
	public void onMessage(String imageData,
			@PathParam("client-id") String clientId, Session session) {
		if (imageData == null)
			return;
		String[] inputs = imageData.split(",");
		double[] inputVals = new double[3];
		if (ThreadEEGReceiver.EEG != null) {
			inputVals[0] = ThreadEEGReceiver.EEG.getACC_X();
			inputVals[1] = ThreadEEGReceiver.EEG.getACC_Y();
			inputVals[2] = ThreadEEGReceiver.EEG.getACC_Z();
			double[] idealVals = new double[1];
			idealVals[0] = Double.parseDouble(inputs[1]);
			if (dp != null)
				dp.streamTraining(inputVals, idealVals);
			else
				dp = new DataPreparation(25);
//			for (Session s : NoiseStreamSocketMediator.peers) {
//				if (s.isOpen())
//					s.getAsyncRemote().sendText(evaluate(inputVals));
//			}
		}
		// if (inputs[0].length() > 0)
		// inputVals[0] = (double) Math
		// .round(Double.parseDouble(inputs[0]) * 100) / 100;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("client-id") String clientId) {
		session.setMaxBinaryMessageBufferSize(1920 * 1080);
		peers.add(session);
	}

	@OnClose
	public void onClose(Session session,
			@PathParam("client-id") String clientId, CloseReason closeReason) {
		System.out.println("mediator: closed websocket channel for client "
				+ clientId);
		peers.remove(session);
	}

	@OnError
	public void onError(Throwable t) {
		int count = 0;
		Throwable root = t;
		while (root.getCause() != null && count < 20) {
			root = root.getCause();
			count++;
		}
		if (root instanceof EOFException) {
		} else {
			try {
				throw t;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private static BasicNetwork networks;

	private static String evaluate(double[] input) {
		File f = new File("C:\\TMPFiles\\Networks\\network0");
		if (!f.exists())
			return FeedforwardNeuralNetwork.predictionRes + "";
		if (networks == null)
			networks = (BasicNetwork) EncogDirectoryPersistence
					.loadObject(new File("C:\\TMPFiles\\Networks\\network0"));
		MLData data = new BasicMLData(input);
		final MLData output = networks.compute(data);
		double predictionRes = 0;
		for (int j = 0; j < output.size(); j++) {
			double outp = output.getData(j);
			predictionRes = outp * 100;
		}
		return predictionRes + "";
	}
}

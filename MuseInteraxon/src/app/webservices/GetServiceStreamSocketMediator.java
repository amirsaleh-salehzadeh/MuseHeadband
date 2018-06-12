package app.webservices;

import java.io.EOFException;
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

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import app.datastream.eeg.MuseOscServer;

@ServerEndpoint("/EEGStreamSocket/{client-id}")
public class GetServiceStreamSocketMediator {

	public static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public void onMessage(byte[] imageData, String faceFeatures, @PathParam("client-id") String clientId, Session session) {
		System.out.println(faceFeatures);
		if (imageData != null && imageData.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/png;base64,");
			sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageData, false)));
			if (MuseOscServer.EEG != null && !MuseOscServer.EEG.getIMG().equals(sb.toString()))
				MuseOscServer.EEG.setIMG(sb.toString());
		}
		
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("client-id") String clientId) {
		session.setMaxBinaryMessageBufferSize(1920 * 1080);
		peers.add(session);
	}

	@OnClose
	public void onClose(Session session, @PathParam("client-id") String clientId, CloseReason closeReason) {
		System.out.println("mediator: closed " + "websocket channel for client " + clientId);
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
}

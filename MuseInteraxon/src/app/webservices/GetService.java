package app.webservices;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.multipart.FormDataParam;

import app.AIengine.NeuralNetworks.FeedforwardNeuralNetwork;
import app.common.MuseSignalEntity;
import app.datastream.eeg.MuseOscServer;
import app.datastream.eeg.ThreadEEGReceiver;
import oscP5.OscP5;

@Path("GetWS")
public class GetService extends Application {
	// REST/GetWS/.....
	private static ThreadEEGReceiver t;

	@GET
	@Path("/GetEEG")
	@Produces("application/json")
	public String getEEG() {
		ObjectMapper mapper = new ObjectMapper();
		if (t == null) {
			t = new ThreadEEGReceiver();
			Thread ts = new Thread(t);
			ts.setName("EEGStream");
			ts.setDaemon(true);
			ts.start();
		}
		String json = "[]";
		try {
			json = mapper.writeValueAsString(t.EEG);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}


	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;

	@GET
	@Path("/ConnectHeadband")
	@Produces("application/json")
	public String connectHeadband() {
		if (t == null) {
			t = new ThreadEEGReceiver();
			t.killer = true;
			Thread ts = new Thread(t);
			ts.setName("EEGStream");
			ts.setDaemon(true);
			ts.start();
		}
		if (t != null) {
			t.stopHeadband = false;
		}
		String json = "";
		return json;
	}

	@GET
	@Path("/StartRecording")
	@Produces("application/json")
	public String startRecording() {
		if (t == null) {
			t = new ThreadEEGReceiver();
			Thread ts = new Thread(t);
			ts.setName("EEGStream");
			ts.setDaemon(true);
			ts.start();
		}
		// if (t != null) {
		museOscServer.record = true;
		// }

		String json = "";
		return json;
	}

	@GET
	@Path("/StartMeasuringAccelData")
	@Produces("application/json")
	public String startMeasuringAccelData() {
		if (t == null) {
			t = new ThreadEEGReceiver();
			Thread ts = new Thread(t);
			ts.setName("EEGStream");
			ts.setDaemon(true);
			ts.start();
			t.accelRecord = true;
			t.stopHeadband = false;
		}
		String json = "[]";
		return json;
	}

	@GET
	@Path("/StopMeasuringAccelData")
	@Produces("application/json")
	public String stopMeasuringAccelData() {
		if (t != null) {
			t.stopHeadband = true;
			t.killer = false;
			t = null;
		}
		String json = "";
		return json;
	}

	@GET
	@Path("/StopHeadband")
	@Produces("application/json")
	public String stopHeadband() {
		if (t != null) {
			t.stopHeadband = true;
			t.killer = false;
		}
		museOscServer.record = false;
		String json = "[]";
		return json;
	}

}

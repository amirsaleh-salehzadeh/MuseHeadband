package app.webservices;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import app.AIengine.NeuralNetworks.FeedforwardNeuralNetwork;
import app.datastream.eeg.MuseOscServer;
import app.datastream.eeg.ThreadEEGReceiver;

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

	@GET
	@Path("/StartHeadband")
	@Produces("application/json")
	public String startHeadband() {
		if (t == null) {
			t = new ThreadEEGReceiver();
			Thread ts = new Thread(t);
			ts.setName("EEGStream");
			ts.setDaemon(true);
			ts.start();
		}
		if (t != null) {
			t.stopHeadband = false;
		}
		String json = "[]";
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
		if (t != null) {
			t.record = true;
		}

		String json = "[]";
		return json;
	}

	@GET
	@Path("/StopHeadband")
	@Produces("application/json")
	public String stopHeadband() {
		if (t != null) {
			t.stopHeadband = true;
		}

		String json = "[]";
		return json;
	}

	@GET
	@Path("/StopSoundTraining")
	@Produces("application/json")
	public String stopSoundTraining() {
		FeedforwardNeuralNetwork.trainingFinished = true;
		String json = "[]";
		return json;
	}

}

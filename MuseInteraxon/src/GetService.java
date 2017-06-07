

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


@Path("GetWS")
public class GetService extends Application {
	// REST/GetWS/.....

	@GET
	@Path("/GetEEG")
	@Produces("application/json")
	public String getEEG() {
		ObjectMapper mapper = new ObjectMapper();
		String json = "[]";
		try {
			json = mapper.writeValueAsString(ThreadEEGMuse.EEG);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Event;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventsTest extends FunctionalTest {
    public static final String CONTENT_TYPE_JSON = "application/json";

    @Test
    public void create_ok() {
        Event event = new Event();
        event.tag = "accident";
        event.summary = "car accident";
        event.description = "horrible car crash";
        event.location = "Iowa City, IA";

        String json = new Gson().toJson(event);
        Response response = POST("/events", CONTENT_TYPE_JSON, json);
        assertIsOk(response);
    }
    
    @Test
    public void read_ok() throws UnsupportedEncodingException {
        Event event = new Event();
        event.tag = "accident";
        event.summary = "car accident";
        event.description = "horrible car crash";
        event.location = "Iowa City, IA";
        String json = new Gson().toJson(event);
        POST("/events", CONTENT_TYPE_JSON, json);

        event = new Event();
        event.tag = "accident";
        event.summary = "car accident 2";
        event.description = "horrible car crash 2";
        event.location = "Iowa City, IA";
        json = new Gson().toJson(event);
        POST("/events", CONTENT_TYPE_JSON, json);

        Response response = GET("/events/locations/Iowa City, IA");

        List<Event> events = new Gson().fromJson(readOut(response.out, response.encoding), new TypeToken<ArrayList<Event>>(){}.getType());

        assertEquals(2, events.size());
    }

    private static synchronized String readOut(ByteArrayOutputStream out, String encoding) throws UnsupportedEncodingException {
        return out.toString(encoding);
    }

}
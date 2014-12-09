package controllers;

import com.google.gson.Gson;
import models.Event;
import org.apache.commons.io.IOUtils;
import play.db.jpa.GenericModel;
import play.mvc.Controller;
import play.mvc.Http;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class Events extends Controller {

    public static void create() throws IOException {
        Event event = new Gson().fromJson(readBody(), Event.class);
        event.save();
        response.setHeader("Access-Control-Allow-Origin","*");
        ok();
    }

    public static void list() {
        List<Event> events = Event.all().fetch(0,20);
        response.setHeader("Access-Control-Allow-Origin","*");
        renderJSON(events);
    }

    public static void read(String tag, String location) throws IOException {
        String where = "location LIKE :location ";
        if( tag != null && !tag.isEmpty() ) {
            where += " AND tag = :tag";
        }

        GenericModel.JPAQuery query = Event.find(where).bind("location", "%" + location + "%");
        if( tag != null && !tag.isEmpty() ) {
            query.bind("tag", tag);
        }

        List<Event> event = query.fetch();

        response.setHeader("Access-Control-Allow-Origin","*");
        renderJSON(event);
    }

    private static synchronized String readBody() throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(request.body, writer, request.encoding);
        return writer.toString();
    }
}

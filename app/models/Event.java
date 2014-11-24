package models;

import play.db.jpa.Model;

/**
 * Created by bkazemi on 11/23/2014.
 */
public class Event extends Model {
    public String tag;
    public String summary;
    public String description;
    public String location;
}

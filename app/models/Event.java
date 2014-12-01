package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by bkazemi on 11/23/2014.
 */
@Entity
public class Event extends Model {
    public String tag;
    public String summary;
    public String description;
    public String location;
}

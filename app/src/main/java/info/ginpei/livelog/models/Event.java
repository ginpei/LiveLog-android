package info.ginpei.livelog.models;

import java.util.Date;

public class Event {

    public static final String TAG = "G#Event";

    private Date date;
    private EventType type;
    private String title = "";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Event(Date date, String typeKey) {
        this.date = date;
        type = EventType.find(typeKey);
    }

    public Event(Date date, String typeKey, String title) {
        this(date, typeKey);
        this.title = title;
    }
}

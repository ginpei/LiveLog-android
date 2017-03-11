package info.ginpei.livelog.models;

public class Event {

    public static final String TAG = "G#Event";

    private EventType type;
    private String title;

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

    public Event(String typeKey) {
        type = EventType.find(typeKey);
    }

    public Event(String typeKey, String title) {
        this(typeKey);
        this.title = title;
    }
}

package info.ginpei.livelog.models;

import android.support.annotation.Nullable;

public class EventType {

    public static final String TAG = "G#EventType";

    static final EventType sleep = new EventType(
            "sleep",
            "Sleep",
            "Good night. :)",
            ""
    );

    static final EventType life = new EventType(
            "life",
            "Life",
            "Something you have to do to live. You may enjoy it.",
            "eating, taking a shower, bath"
    );

    final String key;
    final String name;
    final String description;
    final String example;

    private EventType(String key, String name, String description, String example) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.example = example;
    }

    @Nullable
    public static EventType find(String key) {
        switch (key) {
            case "sleep":
                return sleep;

            case "life":
                return life;

            default:
                return null;
        }
    }
}

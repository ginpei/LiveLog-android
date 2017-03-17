package info.ginpei.livelog.views;

import android.annotation.SuppressLint;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import info.ginpei.livelog.models.Event;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SpiralPathTest {
    @SuppressLint("SimpleDateFormat")
    static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SpiralPath.Configuration configuration;
    Event event;
    Date end;
    SpiralPath path;

    @Before
    public void setUp() throws Exception {
        configuration = new SpiralPath.Configuration.Builder()
                .setOriginX(100)
                .setOriginY(100)
                .setRadius(100)
                .setRadiusOffset(50)
                .createData();
        event = new Event(simpleDateFormat.parse("2000-01-01 00:00:00"), "sleep");
        end = simpleDateFormat.parse("2000-01-01 08:00:00");
        path = new SpiralPath(configuration, event, end, 0);
    }

    @Test
    public void convertTickToDegree() throws Exception {
        event.setDate(simpleDateFormat.parse("2000-01-01 00:00:00"));
        path.end = simpleDateFormat.parse("2000-01-03 00:00:00");

        double expected;
        double actual;

        expected = Math.PI / -2;  // 12 o'clock
        actual = SpiralPath.convertTickToDegree(0);
        assertThat("12am", actual, closeTo(expected, 0.00001));

        expected = Math.PI / -2 + Math.PI * 2 * (1 * 4) / (24 * 4);  // 1 o'clock
        actual = SpiralPath.convertTickToDegree(1 * 4);
        assertThat("1am", actual, closeTo(expected, 0.00001));

        expected = Math.PI / -2 + Math.PI * 2 * (1 * 4 + 1) / (24 * 4);  // 01:15
        actual = SpiralPath.convertTickToDegree(1 * 4 + 1);
        assertThat("1am", actual, closeTo(expected, 0.00001));

        expected = 0;  // 3 o'clock
        actual = SpiralPath.convertTickToDegree(6 * 4);
        assertThat("6am", actual, closeTo(expected, 0.00001));

        expected = Math.PI / 2;  // 6 o'clock
        actual = SpiralPath.convertTickToDegree(12 * 4);
        assertThat("12pm", actual, closeTo(expected, 0.00001));

        expected = Math.PI;  // 9 o'clock
        actual = SpiralPath.convertTickToDegree(18 * 4);
        assertThat("6pm", actual, closeTo(expected, 0.00001));

        expected = Math.PI / -2;  // 12 o'clock
        actual = SpiralPath.convertTickToDegree(24 * 4);
        assertThat("12am on the next day", actual, closeTo(expected, 0.00001));

        expected = Math.PI / -2 + Math.PI * 2 * (1 * 4) / (24 * 4);  // 1 o'clock
        actual = SpiralPath.convertTickToDegree(25 * 4);
        assertThat("1am on the next day", actual, closeTo(expected, 0.00001));
    }

    @Test
    public void convertTickToRadius() throws Exception {
        configuration.setRadius(100);
        configuration.setRadiusOffset(50);
        path.event.setDate(simpleDateFormat.parse("2000-01-01 00:00:00"));
        path.end = simpleDateFormat.parse("2000-01-03 00:00:00");

        assertThat("12am on the first day", path.convertTickToRadius(0), closeTo(50, 0.00001));
        assertThat("12am on the second day", path.convertTickToRadius(24 * 4), closeTo(75, 0.00001));
        assertThat("12am just after the second day", path.convertTickToRadius(2 * 24 * 4), closeTo(100, 0.00001));
    }

    @Test
    public void countTicks() throws Exception {
        Date from;
        Date to;

        from = simpleDateFormat.parse("2000-01-01 00:00:00");
        to = simpleDateFormat.parse("2000-01-01 00:15:00");
        assertEquals("15 minutes", 1, SpiralPath.countTicks(from, to));

        from = simpleDateFormat.parse("2000-01-01 00:00:00");
        to = simpleDateFormat.parse("2000-01-01 01:00:00");
        assertEquals("1 hour", 4, SpiralPath.countTicks(from, to));

        from = simpleDateFormat.parse("2000-01-01 00:00:00");
        to = simpleDateFormat.parse("2000-01-02 00:00:00");
        assertEquals("1 day", 24 * 4, SpiralPath.countTicks(from, to));
    }
}
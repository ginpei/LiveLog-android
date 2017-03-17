package info.ginpei.livelog.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.ginpei.livelog.models.Event;

public class SpiralView extends View {

    public static final String TAG = "G#SpiralView";

    private final Paint bgPaint = new Paint();
    private final Paint spiralPaint = new Paint();
    private SpiralPath path;

    private int bgColor = Color.WHITE;
    private int strokeColor = Color.RED;
    private float startOffset = ((float) 2) / 5;

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public float getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(float startOffset) {
        this.startOffset = startOffset;
        invalidate();
    }

    public SpiralView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);

        spiralPaint.setStyle(Paint.Style.STROKE);
        spiralPaint.setColor(strokeColor);
        spiralPaint.setAntiAlias(true);
        spiralPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int MIN_STROKE_WIDTH = 50;

        super.onDraw(canvas);

        SpiralPath path = getSpiralPath();

        // reset
        canvas.drawPaint(bgPaint);

        // parameters
        float radius = path.configuration.getRadius();
        float radiusOffset = path.configuration.getRadiusOffset();
        float strokeWidth = Math.min(MIN_STROKE_WIDTH, (radius - radiusOffset) / (path.getRollings() * 2));

        // styles
        spiralPaint.setStrokeWidth(strokeWidth);

        path.generatePath();

        // then, draw
        canvas.drawPath(path, spiralPaint);
    }

    private SpiralPath getSpiralPath() {
        if (path == null) {
            int halfWidth = getWidth() / 2;
            int halfHeight = getHeight() / 2;
            int radius = Math.min(halfWidth, halfHeight);
            SpiralPath.Configuration configuration = new SpiralPath.Configuration.Builder()
                    .setOriginX(halfWidth)
                    .setOriginY(halfHeight)
                    .setRadius(radius)
                    .setRadiusOffset(((float) radius) * 2 / 5)
                    .createData();
            Event event = null;
            try {
                // TODO replace these dummy data
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                event = new Event(simpleDateFormat.parse("2000-01-01 00:00:00"), "sleep");
                Date end = simpleDateFormat.parse("2000-01-04 00:00:00");
                path = new SpiralPath(configuration, event, end, 0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}

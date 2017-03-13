package info.ginpei.livelog.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SpiralView extends View {

    public static final String TAG = "G#SpiralView";

    private final Paint bgPaint = new Paint();
    private final Paint spiralPaint = new Paint();
    private Path path;

    private int rollings = 1;
    private int bgColor = Color.WHITE;
    private int strokeColor = Color.RED;
    private float startOffset = ((float) 2) / 5;

    public int getRollings() {
        return rollings;
    }

    public void setRollings(int rollings) {
        this.rollings = rollings;
        invalidate();
    }

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
//            spiralPaint.setStrokeJoin(Paint.Join.ROUND);  // not so effective?

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int MIN_STROKE_WIDTH = 50;

        super.onDraw(canvas);

        // reset
        canvas.drawPaint(bgPaint);

        // parameters
        int width = getWidth();
        int height = getHeight();
        float x0 = width / 2;
        float y0 = height / 2;

        int fineness = 60 * rollings;
        float canvasRadius = Math.min(x0, y0);
        float strokeWidth = Math.min(MIN_STROKE_WIDTH, canvasRadius * (1 - startOffset) / (rollings * 2));
        float spiralRadius = canvasRadius - strokeWidth / 2;

        // styles
        spiralPaint.setStrokeWidth(strokeWidth);

        // calculated values
        double wholeDegree = Math.PI * 2 * rollings;

        // loop to draw
        path.reset();
        float[] p0 = pos(x0, y0, spiralRadius, startOffset, wholeDegree, 0);
        path.moveTo(p0[0], p0[1]);
        for (int i = 0; i < fineness; i++) {
            float progress = ((float) i + 1) / fineness;
            float[] pos = pos(x0, y0, spiralRadius, startOffset, wholeDegree, progress);
            path.lineTo(pos[0], pos[1]);
        }

        // then, draw
        canvas.drawPath(path, spiralPaint);
    }

    private float[] pos(float x0, float y0, float radius, float offsetStart, double wholeDegree, float progress) {
        final double degreeOffset = -Math.PI * 2 / 4;  // start from 12 o'clock

        float r = radius * offsetStart + radius * (1 - offsetStart) * progress;
        double d = degreeOffset + wholeDegree * progress;

        return new float[]{
                (float) (x0 + r * Math.cos(d)),
                (float) (y0 + r * Math.sin(d)),
        };
    }
}

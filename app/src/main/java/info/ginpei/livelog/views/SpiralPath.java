package info.ginpei.livelog.views;

import android.graphics.Path;

import java.util.Date;

import info.ginpei.livelog.models.Event;

public class SpiralPath extends Path {

    public static final String TAG = "G#SpiralPath";

    public static final double DEGREE_CIRCLE = Math.PI * 2;
    public static final double DEGREE_OFFSET = DEGREE_CIRCLE / -4;  // start from 12 o'clock
    public static final int TICKS_CIRCLE = 24 * 4;

    public Configuration configuration;
    public Event event;
    public Date end;
    public int ticksOffset;

    public SpiralPath(Configuration configuration, Event event, Date end, int ticksOffset) {
        this.configuration = configuration;
        this.event = event;
        this.end = end;
        this.ticksOffset = ticksOffset;
    }

    public void generatePath() {
        reset();

        double firstRadius = convertTickToRadius(ticksOffset);
        double firstDegree = convertTickToDegree(ticksOffset);
        moveTo(
                calculateX(firstRadius, firstDegree),
                calculateY(firstRadius, firstDegree)
        );

        int ticks = countTicks(event.getDate(), end);
        for (int i = 1; i < ticks; i++) {
            int tick = ticksOffset + i;
            double radius = convertTickToRadius(tick);
            double degree = convertTickToDegree(tick);
            lineTo(
                    calculateX(radius, degree),
                    calculateY(radius, degree)
            );
        }
    }

    private float calculateX(double radius, double degree) {
        return (float) (configuration.getOriginX() + radius * Math.cos(degree));
    }

    private float calculateY(double radius, double degree) {
        return (float) (configuration.getOriginY() + radius * Math.sin(degree));
    }

    /**
     * Calculate where current degree is.
     *
     * @param tick Tick position.
     * @return Degree for the tick.
     */
    public static double convertTickToDegree(int tick) {
        int tickInCircle = tick % TICKS_CIRCLE;
        return DEGREE_OFFSET + DEGREE_CIRCLE * tickInCircle / TICKS_CIRCLE;
    }

    /**
     * Calculate where current radius is.
     *
     * @param tick Tick position.
     * @return Radius for the tick.
     */
    public double convertTickToRadius(int tick) {
        float radiusOffset = configuration.getRadiusOffset();
        float distance = configuration.getRadius() - radiusOffset;
        int ticks = countTicks(event.getDate(), end);
        double progress = ((double) tick) / ticks;
        return radiusOffset + distance * progress;
    }

    /**
     * Count how many ticks the duration has.
     *
     * @param from Start of the duration.
     * @param to   End of the duration.
     * @return Ticks.
     */
    public static int countTicks(Date from, Date to) {
        return (int) ((to.getTime() - from.getTime()) / (15 * 60 * 1000));  // each 15 minutes
    }

    public int getRollings() {
        int ticks = countTicks(event.getDate(), end);
        return ticks / (24 * 4);
    }

    public static class Configuration {
        private static float fineness = TICKS_CIRCLE;  // same as ticks in one day
        private float originX;
        private float originY;
        private float radius;
        private float radiusOffset;

        // getters and setters
        // vvvvvvvvvvvvvvvvvvv

        public float getOriginX() {
            return originX;
        }

        public void setOriginX(float originX) {
            this.originX = originX;
        }

        public float getOriginY() {
            return originY;
        }

        public void setOriginY(float originY) {
            this.originY = originY;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getRadiusOffset() {
            return radiusOffset;
        }

        public void setRadiusOffset(float radiusOffset) {
            this.radiusOffset = radiusOffset;
        }

        // ^^^^^^^^^^^^^^^^^^^
        // getters and setters

        public Configuration(float originX, float originY, float radius, float radiusOffset) {
            this.originX = originX;
            this.originY = originY;
            this.radius = radius;
            this.radiusOffset = radiusOffset;
        }

        public static class Builder {
            private float originX;
            private float originY;
            private float radius;
            private float radiusOffset;

            public Builder setOriginX(float originX) {
                this.originX = originX;
                return this;
            }

            public Builder setOriginY(float originY) {
                this.originY = originY;
                return this;
            }

            public Builder setRadius(float radius) {
                this.radius = radius;
                return this;
            }

            public Builder setRadiusOffset(float radiusOffset) {
                this.radiusOffset = radiusOffset;
                return this;
            }

            public Configuration createData() {
                return new Configuration(originX, originY, radius, radiusOffset);
            }
        }
    }
}

package info.ginpei.livelog.views;

import android.graphics.Path;

import java.util.Date;

import info.ginpei.livelog.models.Event;

public class SpiralPath extends Path {

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

    private void generatePath() {
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
            lineTo(
                    calculateX(convertTickToRadius(tick), convertTickToDegree(tick)),
                    calculateY(convertTickToRadius(tick), convertTickToDegree(tick))
            );
        }
    }

    private float calculateX(double radius, double degree) {
        return (float) (configuration.getOriginX() + radius * Math.cos(degree));
    }

    private float calculateY(double radius, double degree) {
        return (float) (configuration.getOriginX() + radius * Math.sin(degree));
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

    public static class Configuration {
        private static float fineness = TICKS_CIRCLE;  // same as ticks in one hour
        private float originX;
        private float originY;
        private float radius;
        private float radiusOffset;
        private float rollings;

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

        public float getRollings() {
            return rollings;
        }

        public void setRollings(float rollings) {
            this.rollings = rollings;
        }

        // ^^^^^^^^^^^^^^^^^^^
        // getters and setters

        public Configuration(float originX, float originY, float radius, float radiusOffset, float rollings) {
            this.originX = originX;
            this.originY = originY;
            this.radius = radius;
            this.radiusOffset = radiusOffset;
            this.rollings = rollings;
        }

        public float getWholeTicks() {
            return fineness * rollings;
        }

        public static class Builder {
            private float originX;
            private float originY;
            private float radius;
            private float radiusOffset;
            private float rollings;

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

            public Builder setRollings(float rollings) {
                this.rollings = rollings;
                return this;
            }

            public Configuration createData() {
                return new Configuration(originX, originY, radius, radiusOffset, rollings);
            }
        }
    }
}

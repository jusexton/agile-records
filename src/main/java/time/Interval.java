package main.java.time;

import java.time.temporal.Temporal;

/**
 */
public class Interval {
    private Temporal start;
    private Temporal end;

    public Interval(Temporal start, Temporal end) {
        this.start = start;
        this.end = end;
    }

    public Temporal getStart() {
        return start;
    }

    public void setStart(Temporal start) {
        this.start = start;
    }

    public Temporal getEnd() {
        return end;
    }

    public void setEnd(Temporal end) {
        this.end = end;
    }
}

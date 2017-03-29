package time;

import java.time.LocalDate;

/**
 * Entity class that represents a date interval.
 */
public class DateInterval {
    private LocalDate start;
    private LocalDate end;

    public DateInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}

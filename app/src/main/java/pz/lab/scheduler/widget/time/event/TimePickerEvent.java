package pz.lab.scheduler.widget.time.event;

import pz.lab.scheduler.widget.time.DayPart;

/**
 * Created by Hakus on 2017-01-06.
 */

public class TimePickerEvent {
    public enum TimePart{HOUR,MINUTE, BOTH};

    public TimePart selectedTimePart;
    public DayPart selectedDayPart;

    public DayPart getSelectedDayPart() {
        return selectedDayPart;
    }

    public void setSelectedDayPart(DayPart selectedDayPart) {
        this.selectedDayPart = selectedDayPart;
    }

    public TimePart getSelectedTimePart() {
        return selectedTimePart;
    }

    public void setSelectedTimePart(TimePart selectedTimePart) {
        this.selectedTimePart = selectedTimePart;
    }
}

package pz.lab.scheduler.widget.timePicker;

/**
 * Created by Hakus on 2017-01-06.
 */

public class TimePickerEvent {
    public enum TimePart{HOUR,MINUTE};
    public TimePart selectedTimePart;


    public TimePart getSelectedTimePart() {
        return selectedTimePart;
    }

    public void setSelectedTimePart(TimePart selectedTimePart) {
        this.selectedTimePart = selectedTimePart;
    }
}

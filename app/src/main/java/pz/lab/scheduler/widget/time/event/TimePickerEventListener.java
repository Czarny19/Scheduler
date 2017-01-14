package pz.lab.scheduler.widget.time.event;

/**
 * Created by Hakus on 2017-01-06.
 */

public interface TimePickerEventListener {
    void onTimeSelectionChange(TimePickerEvent event);
    void onDayPartChange(TimePickerEvent event);
}

package pz.lab.scheduler.widget;

import java.util.Date;

/**
 * Created by Hakus on 2017-01-06.
 */

public class DayPickerEvent {
    //public enum TimePart{HOUR,MINUTE};
    public Date selectedDay;


    public Date getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Date selectedDay) {
        this.selectedDay = selectedDay;
    }
}

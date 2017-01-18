package pz.lab.scheduler.CalendarWidget.event;

import java.util.Date;

/**
 * Created by Hakus on 2017-01-06.
 */

public class DayPickerEvent {
    //public enum TimePart{HOUR,MINUTE};
    public Date selectedDay;
    public boolean selectedModel;

    public Date getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Date selectedDay) {
        this.selectedDay = selectedDay;
    }

    public boolean getSelectedModel() {return selectedModel;}

    public void setSelectedModel(boolean selectedModel) {
        this.selectedModel = selectedModel;
    }
}

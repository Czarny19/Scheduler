package pz.lab.scheduler.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Hakus on 2017-01-06.
 */
public class DayPickerModel {
    //public enum DayPart{AM,PM};
   // private int hour, minute;
    private Date day;
    private List<DayPickerListener> listeners;

    public DayPickerModel() {
        listeners = new ArrayList<>(4);
    }

    public DayPickerModel(Date date) {
        this();
        setTime(date);
    }

    public void addTimePickerListener(DayPickerListener listener){
        listeners.add(listener);
    }

    public void removeTimePickerListener(DayPickerListener listener){
        listeners.remove(listener);
    }

    public long getDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //TODO przygotować się na 24H, obsługa AM/PM

        return getDay();
    }


    public void setTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    protected void fireTimeChange(Date selectedDate){
        DayPickerEvent event = new DayPickerEvent();
        event.setSelectedDay(selectedDate);
        for (DayPickerListener l : listeners) {
            l.onTimeSelectionChange(event);
        }
    }

}

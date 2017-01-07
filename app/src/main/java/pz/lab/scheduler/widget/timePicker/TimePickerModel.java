package pz.lab.scheduler.widget.timePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Hakus on 2017-01-06.
 */
public class TimePickerModel{
    public enum DayPart{AM,PM};
    private int hour, minute;
    private DayPart dayPart;
    private List<TimePickerListener> listeners;

    public TimePickerModel() {
        listeners = new ArrayList<>(4);
    }

    public TimePickerModel(int hour, int minute, DayPart dayPart) {
        this();
        this.hour = hour;
        this.minute = minute;
        this.dayPart = dayPart;
    }

    public TimePickerModel(Date date) {
        this();
        setTime(date);
    }


    public void addTimePickerListener(TimePickerListener listener){
        listeners.add(listener);
    }

    public void removeTimePickerListener(TimePickerListener listener){
        listeners.remove(listener);
    }

    public long getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //TODO przygotować się na 24H, obsługa AM/PM
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minute);
        return getTime();
    }

    public void setTime(long time) {
        setTime(new Date(time));
    }

    public void setTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    protected void fireTimeChange(TimePickerEvent.TimePart timePart){
        TimePickerEvent event = new TimePickerEvent();
        event.setSelectedTimePart(timePart);
        for (TimePickerListener l : listeners) {
            l.onTimeSelectionChange(event);
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public DayPart getDayPart() {
        return dayPart;
    }

    public void setDayPart(DayPart dayPart) {
        this.dayPart = dayPart;
    }
}

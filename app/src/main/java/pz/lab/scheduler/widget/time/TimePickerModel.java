package pz.lab.scheduler.widget.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pz.lab.scheduler.widget.time.event.TimePickerEvent;
import pz.lab.scheduler.widget.time.event.TimePickerEventListener;

/**
 * Created by Hakus on 2017-01-06.
 */
public class TimePickerModel{

    private int hour, minute;
    private DayPart dayPart;
    private List<TimePickerEventListener> listeners;

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

    public TimePickerModel(long time) {
        this();
        setTime(time);
    }


    public void addTimePickerListener(TimePickerEventListener listener){
        listeners.add(listener);
    }

    public void removeTimePickerListener(TimePickerEventListener listener){
        listeners.remove(listener);
    }

    public long getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR,hour+(dayPart==DayPart.PM?12:0));
        calendar.set(Calendar.MINUTE,minute);
        return getTime();
    }

    public void setTime(long time) {
        setTime(new Date(time));
    }

    public void setTime(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR)%12;
        if(calendar.get(Calendar.AM_PM) == Calendar.PM)
            dayPart=DayPart.PM;
        else
            dayPart=DayPart.AM;
        minute = calendar.get(Calendar.MINUTE);
        fireTimeChange(TimePickerEvent.TimePart.BOTH);
        fireDayPartChange(dayPart);
    }

    protected void fireTimeChange(TimePickerEvent.TimePart timePart){
        TimePickerEvent event = new TimePickerEvent();
        event.setSelectedTimePart(timePart);
        for (TimePickerEventListener l : listeners) {
            l.onTimeSelectionChange(event);
        }
    }
    protected void fireDayPartChange(DayPart selectedDayPart){
        TimePickerEvent event = new TimePickerEvent();
        event.setSelectedDayPart(selectedDayPart);
        for (TimePickerEventListener l : listeners) {
            l.onDayPartChange(event);
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

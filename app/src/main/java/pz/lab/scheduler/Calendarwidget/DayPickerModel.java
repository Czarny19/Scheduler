package pz.lab.scheduler.CalendarWidget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pz.lab.scheduler.CalendarWidget.event.DayPickerEvent;
import pz.lab.scheduler.CalendarWidget.event.DayPickerListener;

/**
 * Created by Hakus on 2017-01-06.
 */
public class DayPickerModel {

    private boolean model;
    private List<DayPickerListener> listeners;

    public DayPickerModel() {
        listeners = new ArrayList<>(4);
    }

    public DayPickerModel(boolean model) {
        this();
        this.model=model;
    }



    public void addTimePickerListener(DayPickerListener listener){
        listeners.add(listener);
    }

    public void removeTimePickerListener(DayPickerListener listener){
        listeners.remove(listener);
    }


    public void fireSelectDate(Date selectedDate){
        DayPickerEvent event = new DayPickerEvent();
        event.setSelectedDay(selectedDate);
        for (DayPickerListener l : listeners) {
            l.onDaySelectionChange(event);
        }
    }
    public void fireSelectModel(boolean model){
        DayPickerEvent event = new DayPickerEvent();
        event.setSelectedModel(model);
        for (DayPickerListener l : listeners) {
            l.onModelSelectionChange(event);
        }
    }



    public boolean getModel() {
        return model;
    }

    public void setModel(boolean model) {
        this.model = model;
    }
}
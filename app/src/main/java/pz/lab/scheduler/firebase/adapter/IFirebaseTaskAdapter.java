package pz.lab.scheduler.firebase.adapter;

import java.util.Date;
import java.util.List;

import pz.lab.scheduler.model.Task;

/**
 * Created by Hakus on 2017-01-15.
 * Wstępna(?) implementaja interfejsu dla firebase
 */

public interface IFirebaseTaskAdapter {

    List<Task> getTasksForDay(Date day);
    List<Task> getTasksForPeriod(Date from, Date to);
    void fireDataChange();
}

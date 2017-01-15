package pz.lab.scheduler.model;

import java.util.Date;

/**
 * Created by Hakus on 2017-01-15.
 */

public class Task {
    String note;
    Date dateStart;
    Date dateEnd;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}

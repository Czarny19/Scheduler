package pz.lab.scheduler.CalendarWidget;

/**
 * Created by Emilia on 08.01.2017.
 */

public class dayNote {
    private int day,month,year;
    private String note;

    dayNote(int xday,int xmonth, int xyear,String xnote){
        day=xday;
        month=xmonth;
        year=xyear;
        note=xnote;
    }
    public int[] getNoteDate(){
        int[] noteDate={day,month,year};
        return noteDate;
    }
    public String getNoteDateString(){
        return note;
    }
    public void setNoteDateString(String xnote){
        note=xnote;
    }
    public void setNoteDate(int xday,int xmonth, int xyear){
        day=xday;
        month=xmonth;
        year=xyear;
    }
}

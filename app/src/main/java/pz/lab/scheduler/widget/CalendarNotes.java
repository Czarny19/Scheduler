package pz.lab.scheduler.widget;

import java.util.LinkedList;

/**
 * Created by Emilia on 08.01.2017.
 */

public class CalendarNotes {
    private LinkedList<dayNote> calendarNotes;

    CalendarNotes(){
        calendarNotes=new LinkedList<dayNote>();
    }
    public void addNote(dayNote note){
        calendarNotes.add(note);
    }
    public void deleteNote(dayNote note){
       int[] noteDate=note.getNoteDate();
        int idx=searchNotes(noteDate[0],noteDate[1],noteDate[2]);
        calendarNotes.remove(idx);
    }
    public dayNote getDayNoteidx(int idx){
        return calendarNotes.get(idx);
    }
    public dayNote getDayNote(int xday,int xmonth, int xyear){
       return getDayNoteidx(searchNotes(xday,xmonth,xyear));
    }

    public int searchNotes(int xday,int xmonth, int xyear){
        int[] noteDate={xday,xmonth,xyear};
        int i=0;
        for( i=0; i<calendarNotes.size();i++){
           dayNote tmp=calendarNotes.get(i);
            if(tmp.getNoteDate().equals(noteDate)){
                break;
            }
        }
        return i;
    }
}

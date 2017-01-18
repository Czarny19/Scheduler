package pz.lab.scheduler.CalendarWidget;

import android.app.Activity;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.R;

/**
 * Created by Emilia on 12.01.2017.
 */

public class DayTaskListAdapter extends ArrayAdapter<String> {//
    private Activity context;
    //private static final int[] DAYS = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
   // private static final String[] MONTHS = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"};
    private Date[] lista={new Date(2016-1900,12,30,2,15),new Date(2016-1900,12,30,2,17),new Date(2017-1900,1,1,3,13),new Date(2017-1900,1,13,15,2)};
   // private ArrayAdapter<String> adapter ;
    private Date gData;
    private String[] hours;
    //private Date[] daty;
   // private Adapter tl;
    //private Date today=new Date();
    private Calendar calendar=Calendar.getInstance();

    public DayTaskListAdapter(Activity context, Date date,String[] hours) {
        super(context, R.layout.zadanie, hours);
        this.context = context;
        this.gData=date;
        this.hours=hours;
        //Calendar cal =Calendar.getInstance();
        //cal.setTime(new Date());
        //today= new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));


    }

    public static class ViewHolder{
        public TextView hourText;
        public TextView task;
        public LinearLayout listaTaskow;

    }

    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.zadanie, null, true);
            viewHolder = new ViewHolder();
            viewHolder.hourText = (TextView) rowView.findViewById(R.id.hour);
            viewHolder.listaTaskow = (LinearLayout) rowView.findViewById(R.id.taskList);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        calendar.setTime(gData);
        viewHolder.hourText.setText(hours[i]);
        for(int x=0;x<lista.length;x++){
            if(gData.getDate()==lista[x].getDate()&&i==lista[x].getHours() ){
                viewHolder.task = new TextView(context);
                viewHolder.task.setText(+ calendar.get(Calendar.DATE) + " "+calendar.get(Calendar.MONTH) +" "+  calendar.get(Calendar.YEAR) );
                viewHolder.listaTaskow.addView(viewHolder.task);}
        }
        return rowView;
    }
}
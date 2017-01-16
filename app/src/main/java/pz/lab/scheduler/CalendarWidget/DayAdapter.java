package pz.lab.scheduler.CalendarWidget;

import android.app.Activity;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pz.lab.scheduler.R;

/**
 * Created by Emilia on 12.01.2017.
 */

public class DayAdapter extends ArrayAdapter<String> {//
    private Activity context;
    private static final String[] DAYS = {"Poniedziałek","Wtorek","Sroda","Czwartek","Piatek","Sobota","Niedziela"};
    private static final String[] MONTHS = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"};
    private Date startData;
    private Date endData;
    private ArrayAdapter<String> adapter ;
    private Date gData;
   // private Adapter tl;
    private Date today=new Date();
    private Calendar calendar=Calendar.getInstance();

    public DayAdapter(Activity context, Date start, Date end) {
        super(context, R.layout.element_listy, DAYS);
        this.context = context;
        this.startData = start;
        this.gData=start;
        this.endData = end;
        Calendar cal =Calendar.getInstance();
        cal.setTime(new Date());
        today= new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));


    }

    public static class ViewHolder{
        public TextView dayText;
        public TextView myText;
        public TextView hour;
        public ListView taskList;
        public LinearLayout linLo;
        //public ListView listaGodzin;

    }

    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.element_listy, null, true);
            viewHolder = new ViewHolder();
            viewHolder.taskList = (ListView) rowView.findViewById(R.id.listaGodzin);
            viewHolder.dayText = (TextView) rowView.findViewById(R.id.dayText);
            viewHolder.myText = (TextView) rowView.findViewById(R.id.myText);
            viewHolder.hour = (TextView) rowView.findViewById(R.id.hour);
            //viewHolder.taskList = (LinearLayout) rowView.findViewById(R.id.taskList);
            viewHolder.linLo = (LinearLayout) rowView.findViewById(R.id.linLo);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        calendar.setTime(gData);
        if(today==gData)rowView.setBackgroundColor(Color.rgb(230, 230, 255));
        viewHolder.dayText.setText(DAYS[i]);
        viewHolder.myText.setText(+ calendar.get(Calendar.DATE) + " "+MONTHS[calendar.get(Calendar.MONTH)] +" "+  calendar.get(Calendar.YEAR) );
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, DAYS);
        adapter.getCount();
        viewHolder.taskList.setAdapter(adapter);
        int totalHeight = 0;
        for (int x = 0; x < adapter.getCount(); x++) {
            View listItem = adapter.getView(i, null, viewHolder.taskList);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = viewHolder.taskList.getLayoutParams();
        params.height = totalHeight + (viewHolder.taskList.getDividerHeight() * (viewHolder.taskList.getCount() - 1));
        viewHolder.taskList.setLayoutParams(params);
        viewHolder.taskList.requestLayout();
        

        gData=new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)+1);
        return rowView;
    }
}
package pz.lab.scheduler.widget.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.R;

/**
 * Created by Emilia on 12.01.2017.
 */

public class DayAdapter extends ArrayAdapter<String> {//
    private Activity context;
    private  String[] days,months,hours;
    private DayTaskListAdapter adapter ;
    private Date gData;
    private float x1,x2;
    private Calendar cal,calendar=Calendar.getInstance();

    public DayAdapter(Activity context, Date start , String[] days, String[] months) {
        super(context, R.layout.element_listy, days);
        this.context = context;
        this.gData=start;
        this.days=days;
        this.months=months;
        cal =Calendar.getInstance();
        cal.setTime(new Date());
        hours = this.context.getResources().getStringArray(R.array.hours);
    }

    public static class ViewHolder{
        public TextView dayText;
        public TextView myText;
        public TextView hour;
        public ListView taskList;
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
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        setElements(rowView,viewHolder,i);
        setPatams(viewHolder);
        return rowView;
    }

    public void setElements(View rowView,ViewHolder viewHolder,int i){
        calendar.setTime(gData);
        viewHolder.dayText.setText(days[i]);
        calendar.set( Calendar.DATE,calendar.get(Calendar.DATE )+i);
        viewHolder.myText.setText( calendar.get(Calendar.DATE ) + " "+months[calendar.get(Calendar.MONTH)] +" "+  calendar.get(Calendar.YEAR) );
        adapter = new DayTaskListAdapter(context, calendar.getTime(),hours);
        viewHolder.taskList.setAdapter(adapter);
        if(calendar.get(Calendar.YEAR)==cal.get(Calendar.YEAR)&&calendar.get(Calendar.MONTH)==cal.get(Calendar.MONTH)&&calendar.get(Calendar.DATE)==cal.get(Calendar.DATE))
             rowView.setBackgroundColor(Color.rgb(230, 240, 255));
        else rowView.setBackgroundColor(Color.rgb(240,240,240));
    }


    public void setPatams(ViewHolder viewHolder){
        int totalHeight = 0;
        for (int x = 0; x < adapter.getCount(); x++) {
            View listItem = adapter.getView(x, null, viewHolder.taskList);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = viewHolder.taskList.getLayoutParams();
        params.height = totalHeight + (viewHolder.taskList.getDividerHeight() * (viewHolder.taskList.getCount() - 1));
        viewHolder.taskList.setLayoutParams(params);
        viewHolder.taskList.requestLayout();
    }
}
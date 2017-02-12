package pz.lab.scheduler.widget.calendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.R;

/**
 * Created by Emilia on 12.01.2017.
 */

public class DayTaskListAdapter extends ArrayAdapter<String> {//
    private Activity context;
    private Date[] lista={new Date(2017-1900,1,23,2,15),new Date(2017-1900,1,23,2,17),new Date(2017-1900,1,23,8,13),new Date(2017-1900,1,24,15,2)};
    private Date gData;
    private String[] hours;
    private Calendar calendar=Calendar.getInstance();

    public DayTaskListAdapter(Activity context, Date date,String[] hours) {
        super(context, R.layout.zadanie, hours);
        this.context = context;
        this.gData=date;
        this.hours=hours;
        //Calendar cal =Calendar.getInstance();
        //cal.setTime(new Date());



    }

    public static class ViewHolder{
        public TextView hourText;
        public TextView task;
        public ListView listaTaskow;
        //public LinearLayout listaTaskow;
    }

    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.zadanie, null, true);
            viewHolder = new ViewHolder();
            viewHolder.hourText = (TextView) rowView.findViewById(R.id.hour);
            viewHolder.listaTaskow = (ListView) rowView.findViewById(R.id.taskList);

            //viewHolder.listaTaskow = (LinearLayout) rowView.findViewById(R.id.taskList);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        ArrayList arrayList=new ArrayList();
        setElements(viewHolder,i,arrayList);
        setPatams(viewHolder, arrayList);
        // gData=new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)+1);
        return rowView;
    }

    public void setElements(ViewHolder viewHolder,int i,ArrayList arrayList){
        calendar.setTime(gData);
        viewHolder.hourText.setText(hours[i]);
        for(int x=0;x<lista.length;x++){
            if(gData.getDate()==lista[x].getDate()&&i==lista[x].getHours() ){
                arrayList.add(calendar.get(Calendar.DATE) + " "+calendar.get(Calendar.MONTH) +" "+  calendar.get(Calendar.YEAR)  +" "+  calendar.get(Calendar.MINUTE));
            }
        }
    }

    public void setPatams(ViewHolder viewHolder,ArrayList arrayList){
        if(arrayList.size()!=0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.test_list_item, arrayList);
            viewHolder.listaTaskow.setAdapter(adapter);
            View childView = adapter.getView(0, null, viewHolder.listaTaskow);
            childView.measure(0, 0);
            int h= childView.getMeasuredHeight();
            int  totalHeight = h*arrayList.size();
            ViewGroup.LayoutParams params = viewHolder.listaTaskow.getLayoutParams();
            params.height = totalHeight ;
            viewHolder.listaTaskow.setLayoutParams(params);
            viewHolder.listaTaskow.requestLayout();
        }
    }
}
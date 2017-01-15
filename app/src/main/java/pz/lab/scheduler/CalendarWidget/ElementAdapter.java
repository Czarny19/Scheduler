package pz.lab.scheduler.CalendarWidget;

import android.app.Activity;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.R;

/**
 * Created by Emilia on 12.01.2017.
 */

public class ElementAdapter extends ArrayAdapter<String> {//
    private Activity context;
    private static final String[] DAYS = {"Poniedziałek","Wtorek","Sroda","Czwartek","Piatek","Sobota","Niedziela"};
    private static final String[] MONTHS = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"};
    private Date startData;
    private Date endData;
    private Date gData;
    private Date today=new Date();
    private Calendar calendar=Calendar.getInstance();

    public ElementAdapter(Activity context, Date start, Date end) {
        super(context, R.layout.element_listy, DAYS);
        this.context = context;
        this.startData = start;
        this.gData=start;
        this.endData = end;
        Calendar cal =Calendar.getInstance();
        cal.setTime(new Date());
        today= new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));

    }

    public static class ViewHolder implements View.OnClickListener{
        public TextView dayText;
        public TextView myText;
       // public ImageView img;


        @Override
        public void onClick(View v) {

        }
    }

    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.element_listy, null, true);
            viewHolder = new ViewHolder();
            viewHolder.dayText = (TextView) rowView.findViewById(R.id.dayText);
            viewHolder.myText = (TextView) rowView.findViewById(R.id.myText);
           // viewHolder.img = (ImageView) rowView.findViewById(R.id.image) ;
            //viewHolder.img.setImageDrawable(getResources().getDrawable(R.drawable.box_green));
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        calendar.setTime(gData);
        if(today.equals(gData))rowView.setBackgroundColor(Color.rgb(230, 230, 255));
        viewHolder.dayText.setText(DAYS[i]);
        viewHolder.myText.setText(+ calendar.get(Calendar.DATE) + " "+MONTHS[calendar.get(Calendar.MONTH)] +" "+  calendar.get(Calendar.YEAR) );
        gData=new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)+1);
        return rowView;
    }
}
package pz.lab.scheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Czarny on 2017-01-09.
 */

public class Timeline extends AppCompatActivity {

    private TextView DateText;
    private LinearLayout Zdarzenia;
    private TextView newZdarzenie;
    private int i;
    private String hour;
    private float hourtofloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        DateText = (TextView) findViewById(R.id.DateText);
        Zdarzenia = (LinearLayout) findViewById(R.id.Zdarzenie);

        //Przykładowe dane, trzeba zrobić pobieranie z kalendarza i zegara
        setDate("09.01.2016");
        addZdarzenie("12.15","Spotkanie z Andrzejem");
        addZdarzenie("08.00","Sniadanie");
        addZdarzenie("16.00","Jezu pomusz z tym projektem");

    }

    private void setDate(String Sel_Date){
        DateText.setText(Sel_Date);
    }

    private void addZdarzenie(String czas,String message) {
        newZdarzenie = (TextView) new TextView(this);
        newZdarzenie.setText(czas + " - " + message);
        TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 130);
        param.setMargins(0,10,0,10);
        newZdarzenie.setLayoutParams(param);
        newZdarzenie.setContentDescription(czas);
        newZdarzenie.setBackgroundColor(getResources().getColor(R.color.BlueBackground));
        newZdarzenie.setTextSize(20);
        newZdarzenie.setTextColor(getResources().getColor(R.color.WhiteFont));
        newZdarzenie.setGravity(30 | Gravity.CENTER_HORIZONTAL);

        // Sortowanie wydarzeń z uwzględnieniem godziny
        i = 0;
        while(Zdarzenia.getChildAt(i) != null)
        {
            hour = (String) Zdarzenia.getChildAt(i).getContentDescription();
            hourtofloat = Float.parseFloat(hour);
            if(Float.parseFloat(czas) < hourtofloat)
                break;
            i++;
        }

        Zdarzenia.addView(newZdarzenie,i);
    }

}

package pz.lab.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import pz.lab.scheduler.model.User;
import pz.lab.scheduler.widget.calendar.CalendarView;
import pz.lab.scheduler.widget.calendar.DayAdapter;
import pz.lab.scheduler.widget.calendar.event.DayPickerEvent;
import pz.lab.scheduler.widget.calendar.event.DayPickerListener;

public class CalendarScreenActivity extends AppCompatActivity implements DayPickerListener {
   // public int NUM_ITEMS_PAGE   = 7;

    private DayAdapter ea;
    private CalendarView calendar;
    float x1=0,x2=0;
    FirebaseAuth auth;
    private ListView list;
    private Date date;
   // Bundle newBundy = new Bundle();
    //private View week;
   // @Override
    public void onDaySelectionChange(DayPickerEvent event) {
        Toast.makeText(this, "Wybrano dzień", Toast.LENGTH_SHORT).show();
        changeSelectedDayActivity(event.getSelectedDay());
    }

    @Override
    public void onModelSelectionChange(DayPickerEvent event) {
    }

    public void taster(){
        Toast.makeText(this, "Scroll", Toast.LENGTH_SHORT).show();
    }

    private void changeSelectedDayActivity(Date selectedDay) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        selectedDay.toString();
        intent.putExtra("date", selectedDay);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calendar_screen);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.addTimePickerListener(this);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = auth.getCurrentUser();
                if(user!=null){
                    Log.i("LOG", "Zalogowano: "+user.getUid());


                }
                else Log.i("LOG", "Niezalogowano");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.signInAnonymously();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final FirebaseUser user = auth.getCurrentUser();
            FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        User u = new User();
                        u.setUserName("Bartek");
                        u.setName("Bartek");
                        u.setSurname("Kowalski");
                        FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).setValue(u);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signInAnonymously() {

        // [START signin_anonymously]
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("DEB", "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("VERB", "signInAnonymously", task.getException());
                            Toast.makeText(CalendarScreenActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        // [END signin_anonymously]
    }
}





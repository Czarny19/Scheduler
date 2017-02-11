package pz.lab.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pz.lab.scheduler.R;
import pz.lab.scheduler.model.User;

/**
 * Created by Hakus on 2017-02-10.
 */

public class UserListAdapter extends ArrayAdapter<User> {
    private List<User> users;
    public UserListAdapter(Context context, int resource) {
        super(context, resource);

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<User>();
                for (DataSnapshot snap :  dataSnapshot.getChildren()) {
                    users.add(snap.getValue(User.class));
                }
                addAll(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.user_list_item_layout, null);
        }
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.userBox);
        checkBox.setText(getItem(position).toString());

        return v;
    }
}

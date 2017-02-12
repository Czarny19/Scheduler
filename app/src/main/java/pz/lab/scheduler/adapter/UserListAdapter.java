package pz.lab.scheduler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
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
    private List<User> selectedUsers;
    private String currentUID;
    public UserListAdapter(Context context, int resource) {
        super(context, resource);
        selectedUsers = new ArrayList<>();

        currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    users = new ArrayList<User>();
                    for (DataSnapshot snap :  dataSnapshot.getChildren()) {

                        if(!snap.exists())
                            continue;
                        User user = snap.getValue(User.class);
                        Log.w("USER", user.getName());
                        if(currentUID.equals(user.getUid()))
                            selectedUsers.add(user);
                        users.add(user);
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

        User user = getItem(position);
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.userBox);
        checkBox.setText(user.toString());
        checkBox.setTag(user);

          if (currentUID.equals(user.getUid())) {
              checkBox.setEnabled(false);
              checkBox.setSelected(true);
          } else
              checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      User user = (User) buttonView.getTag();
                      if (isChecked)
                          selectedUsers.add(user);
                      else
                          selectedUsers.remove(user);
                  }
              });

        return v;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }
}

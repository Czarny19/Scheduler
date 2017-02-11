package pz.lab.scheduler.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import pz.lab.scheduler.R;
import pz.lab.scheduler.adapter.UserListAdapter;

public class UserListDialog extends AppCompatActivity {
    private ListView userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserListAdapter userListAdapter = new UserListAdapter(this, R.layout.user_list_item_layout);
        userList = (ListView) findViewById(R.id.userListView);
        userList.setAdapter(userListAdapter);
    }

}

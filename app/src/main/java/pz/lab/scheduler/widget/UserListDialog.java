package pz.lab.scheduler.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

import pz.lab.scheduler.R;
import pz.lab.scheduler.adapter.UserListAdapter;

public class UserListDialog extends AppCompatActivity {
    private ListView userList;
    private Button ok;
    private UserListAdapter userListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userListAdapter = new UserListAdapter(this, R.layout.user_list_item_layout);
        userList = (ListView) findViewById(R.id.userListView);
        userList.setAdapter(userListAdapter);
        ok = (Button) findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResults();
            }
        });
    }

    private void returnResults(){
        Intent results = new Intent();
        results.putExtra("users", (Serializable) userListAdapter.getSelectedUsers());
        setResult(RESULT_OK, results);
        finish();
    }

}

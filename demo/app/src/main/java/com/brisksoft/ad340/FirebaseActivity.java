package com.brisksoft.ad340;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.brisksoft.ad340.R;

public class FirebaseActivity extends AppCompatActivity {

    private static final String TAG = FirebaseActivity.class.getSimpleName();

    private DatabaseReference myRef;

    ListView userList;
    UserListAdapter listAdapter;
    ArrayList<User> userData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        userList = findViewById(R.id.userList);
        listAdapter = new UserListAdapter(this, userData);
        userList.setAdapter(listAdapter);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("users");
        Query members = myRef.orderByChild("updated");

        // update database
        assert currentUser != null;
        writeNewUser(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail());

        // get list of users
        members.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FIREBASE", "onDataChange");
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Object username = userSnapshot.child("username").getValue();
                    if (username != null) {
                        User user = new User(username.toString(), userSnapshot.child("email").getValue().toString(), userSnapshot.child("updated").getValue().toString());
                        userData.add(user);
                    }
                }
                // Firebase doesn't support sort in descending order. Have to reverse in java
                Collections.reverse(userData);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadUsers:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email, new Date().toString());
        myRef.child(userId).setValue(user);
    }

    @IgnoreExtraProperties
    public static class User {

        public String username;
        public String email;
        public String updated;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        private User(String username, String email, String updated) {
            this.username = username;
            this.email = email;
            this.updated = updated;
        }

    }

    private static class UserListAdapter extends ArrayAdapter<User> {
        private final Context context;
        private final ArrayList<User> values;

        private UserListAdapter(Context context, ArrayList<User> values) {
            super(context, 0, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_2_items, parent, false);
            TextView title = rowView.findViewById(R.id.item_title);
            title.setText(values.get(position).username);
            TextView subtitle = rowView.findViewById(R.id.item_subtitle);
            subtitle.setText(String.format("Updated: %s", values.get(position).updated));

            return rowView;
        }
    }

}

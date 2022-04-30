package com.brisksoft.ad340;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.brisksoft.ad340.R;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.brisksoft.ad340.MESSAGE";
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private TextToSpeech tts;

    // Array of strings...
    String[] demoArray = {"Cities", "Movies 1", "Movies 2", "Traffic Cams (Java)", "Traffic Cams (Kt))", "Location", "Map w/ markers" };

    // helper class to manage writing to SharedPreferences.
    private SharedPreferencesHelper mSharedPreferencesHelper;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Instantiate a SharedPreferencesHelper class
        mSharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        mSharedPreferencesHelper = new SharedPreferencesHelper(mSharedPreferences);

        // text entry fields
        mNameField = findViewById(R.id.userName);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        mNameField.setText(mSharedPreferencesHelper.getEntry("name"));
        mEmailField.setText(mSharedPreferencesHelper.getEntry("email"));
        mPasswordField.setText(mSharedPreferencesHelper.getEntry("password"));

        Button mSignInButton = findViewById(R.id.buttonSignIn);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FIREBASE", "click");
                signIn();
            }
        });

        // setup App Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

        tts = new TextToSpeech ( getApplicationContext () , new TextToSpeech.OnInitListener () {
            @Override
            public void onInit(int status) {

//                if (status != TextToSpeech.ERROR) {
//                    tts.setLanguage ( Locale.ENGLISH );
//
//                }

            }
        } );

        // handle navigation drawer events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_movies:
                                Log.d(TAG, menuItem.toString());
                                intent = new Intent(MainActivity.this, RecyclerActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_map:
                                intent = new Intent(MainActivity.this, MapActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_map_cams:
                                intent = new Intent(MainActivity.this, TrafficCamMap.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(MainActivity.this, AboutActivity.class);
                                startActivity(intent);
                        }

                        return true;
                    }
                });

    }

    private void signIn() {
        Log.d("FIREBASE", "signIn");
        String displayname = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (!validateForm(displayname, email, password)) {
            return;
        }

        // store shared preferences
        mSharedPreferencesHelper.saveEntry("name", displayname);
        mSharedPreferencesHelper.saveEntry("email", email);
        mSharedPreferencesHelper.saveEntry("password", password);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FIREBASE", "signIn:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            // update profile. displayname is the value entered in UI
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayname)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("FIREBASE", "User profile updated.");
                                                // Go to FirebaseActivity
                                                startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
                                            }
                                        }
                                    });

                        } else {
                            Log.d("FIREBASE", "sign-in failed");

                            Toast.makeText(MainActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean inputIsValid(String str) {
        if (str.length() == 0) {
            return false;
        }
        return true;
    }

    public boolean passwordIsValid(String str) {
        return str.length() > 0;
    }

    private boolean validateForm(String userName, String email, String password) {
        boolean result = true;
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;

        public ButtonAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return demoArray.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ButtonView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                button = new Button(mContext);
            } else {
                button = (Button) convertView;
            }

            button.setText(demoArray[position]);
            button.setId(position);
            button.setOnClickListener(new BtnOnClickListener());
            return button;
        }

    }

    class BtnOnClickListener implements View.OnClickListener
    {

        public void onClick(View v)
        {
            Log.d(TAG, "tapped button");
            int id = v.getId();

            Intent intent;
            switch (id) {
                case 0:
                    intent = new Intent(getBaseContext(), ListActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getBaseContext(), MoviesActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getBaseContext(), RecyclerActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getBaseContext(), RecyclerWebActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(getBaseContext(), TrafficCamActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(getBaseContext(), TrafficCamMap.class);
                    startActivity(intent);
                    break;
                default:
                    Button b = (Button) v;
                    String label = b.getText().toString();
                    tts.speak ( label , TextToSpeech.QUEUE_FLUSH , null );
                    Toast.makeText(MainActivity.this, label,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
//        mAuth.signOut();
    }
}

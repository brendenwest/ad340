package brisksoft.com.ad340;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;

    // Array of strings...
    String[] demoArray = {"Cities", "Movies 1", "Movies 2", "Traffic Cams", "Map" };

    // helper class to manage writing to SharedPreferences.
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Instantiate a SharedPreferencesHelper class
        mSharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        mSharedPreferencesHelper = new SharedPreferencesHelper(mSharedPreferences);

        // populate text field w/ saved entry
        EditText editText = findViewById(R.id.editText);
        editText.setText(mSharedPreferencesHelper.getEntry());

        // setup App Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

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

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (inputIsValid(message)) {
            mSharedPreferencesHelper.saveEntry(message);
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
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

        // create a new ImageView for each item referenced by the Adapter
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
                    intent = new Intent(getBaseContext(), MovieListActivity.class);
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
                    intent = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(intent);
                    break;
                default:
                    Button b = (Button) v;
                    String label = b.getText().toString();
                    Toast.makeText(MainActivity.this, label,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}

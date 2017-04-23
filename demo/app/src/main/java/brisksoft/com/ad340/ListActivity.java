package brisksoft.com.ad340;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    // Array of strings...
    String[] demoArray = {"list", "List w/ cursor","GridView","WebView", "RecyclerView" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, demoArray);

        ListView listView = (ListView) findViewById(R.id.demo_list);
        listView.setAdapter(adapter);
        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, listView,
                false);
        listView.addHeaderView(header, null, false);

    }

}

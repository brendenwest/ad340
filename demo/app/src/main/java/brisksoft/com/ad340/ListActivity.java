package brisksoft.com.ad340;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    // Array of strings
    String[] demoArray = {"Seattle", "Spokane","Tacoma","Vancouver", "Walla Walla", "Yakima" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.list_item, demoArray);

        ListView listView = findViewById(R.id.demo_list);
        listView.setAdapter(adapter);
        // add a header to the list
        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, listView,
                false);
        listView.addHeaderView(header, null, false);

    }

}

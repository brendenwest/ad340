package brisksoft.com.ad340;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] subjects =
            {
                    "ANDROID",
                    "PHP",
                    "BLOGGER",
                    "WORDPRESS",
                    "JOOMLA",
                    "ASP.NET",
                    "JAVA",
                    "C++",
                    "MATHS",
                    "HINDI",
                    "ENGLISH"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

//        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recylerViewLayoutManager = new LinearLayoutManager(context);

//        recyclerView.setLayoutManager(recylerViewLayoutManager);

//        recyclerViewAdapter = new CustomAdapter(context, subjects);

//        recyclerView.setAdapter(recyclerViewAdapter);
    }
/*
    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        String[] SubjectValues;
        Context context;
        View view1;
        ViewHolder viewHolder1;
        TextView textView;

        public RecyclerViewAdapter(Context context1,String[] SubjectValues1){

            SubjectValues = SubjectValues1;
            context = context1;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            public TextView textView;

            public ViewHolder(View v){

                super(v);

                textView = (TextView)v.findViewById(R.id.subject_textview);
            }
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            // Inflate the view for this view holder
            View thisItemsView = myInflater.inflate(R.layout.recycler_items,
                    parent, false);
            // Call the view holder's constructor, and pass the view to it;
            // return that new view holder
            return new CustomViewHolder(thisItemsView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position){

            holder.textView.setText(SubjectValues[position]);
        }

        @Override
        public int getItemCount(){

            return SubjectValues.length;
        }
    }
*/
}

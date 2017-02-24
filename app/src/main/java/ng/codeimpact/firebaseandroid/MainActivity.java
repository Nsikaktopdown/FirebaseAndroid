package ng.codeimpact.firebaseandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import ng.codeimpact.firebaseandroid.models.List_item;
import ng.codeimpact.firebaseandroid.viewholder.ListViewHolder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";

    // [define_database_reference]
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<List_item, ListViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // [create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mRecycler = (RecyclerView) findViewById(R.id.messages_list);
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(sglm);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });

        queryData(mDatabase);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public  void queryData(DatabaseReference mDatabase){
        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<List_item, ListViewHolder>(List_item.class, R.layout.item_list,
                ListViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final ListViewHolder viewHolder, final List_item model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                final String title = model.getTitle();
                final String body = model.getBody();
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        addOptionMenu(viewHolder.itemView, postKey, title,body);
                        return false;
                    }
                });


                viewHolder.titleView.setText(model.getTitle());
                viewHolder.bodyView.setText(model.getBody());

            }
        };
        progressBar.setVisibility(View.GONE);
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void addOptionMenu(View view , final String postkey, final String title, final String body){

        final Context mCtx = view.getContext();
        //creating a popup menu
        PopupMenu popup = new PopupMenu(mCtx, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_option);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update:
                        //handle update click
                        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                        intent.putExtra("postkey",postkey);
                        intent.putExtra("title", title);
                        intent.putExtra("body", body);
                        startActivity(intent);
                        break;
                    case R.id.delete:
                        //handle delete click
                        mDatabase.child("users-list").child(postkey).removeValue();
                        Toast.makeText(mCtx, "list deleted", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });
        //displaying the popup
        popup.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("users-list");
    }
}

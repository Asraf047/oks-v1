package com.example.amanullah.myapplication62;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchPlayers extends AppCompatActivity implements PlayerAdapter.PlayerAdapterListener {

    private static final String TAG = SearchPlayers.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Player> playerList;
    private PlayerAdapter mAdapter;
    private SearchView searchView;
    DatabaseReference databaseReference, parentRef, pushRef;

    String[] names = {
            "M.Asifur Rahman",
            "Angkur Mondal",
            "Sohana Akter",
            "S.M. Mohaiminul Islam Rafi",
            "Redwanul Haque Sourave",
            "Md.Rahat-Uz-Zaman",
            "Sakib Reza"
    };

    String[] points = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7"
    };

    String[] details = {
            "M.Asifur Rahman",
            "Angkur Mondal",
            "Sohana Akter",
            "S.M. Mohaiminul",
            "Redwanul Haque",
            "Md.Rahat-Uz-Zaman",
            "Sakib Reza"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(R.string.toolbar_title);
        getSupportActionBar().setTitle((Html.fromHtml(
                "<font color=\"#FFFFFF\">"
                        + getString(R.string.toolbar_title)
                //+ "</font>"
        )));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        playerList = new ArrayList<>();
        mAdapter = new PlayerAdapter(getBaseContext(), playerList, this);

        // white background notification bar
        //whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 20));
        recyclerView.setAdapter(mAdapter);

        //change stutus ber color..
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception ex) {
            Log.i("Exception : ", " FirebaseDatabase.getInstance() " + ex);
        }

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            parentRef = database.getReference();
            databaseReference = parentRef.child("players");
            databaseReference.keepSynced(true);
        } catch (Exception ex) {
            Log.i("Exception : ", " databaseReference " + ex);
        }

        initialize();
    }

    private void initialize() {
        for (int i = 0; i < 7; i++) {
            Player item = new Player();
            item.setName(names[i] + "");
            item.setPoint(points[i] + "");
            item.setPrice(points[i] + "");
            item.setDetails(names[i] + "");
            playerList.add(item);


            pushRef = databaseReference.push();
            pushRef.setValue(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_about) {
            final Context context = this;
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("roll", "47");
//            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onPlayerSelected(Player player) {
        final Context context = this;
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("roll", player.getPhone());
//        startActivity(intent);
        //Toast.makeText(getApplicationContext(), "Selected: " + player.getName() + ", " + player.getPhone(), Toast.LENGTH_LONG).show();
    }
}
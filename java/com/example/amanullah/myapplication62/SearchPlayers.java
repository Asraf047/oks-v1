package com.example.amanullah.myapplication63;

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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private Player player;

    DatabaseReference playerReference, parentRef, pushRef,userReference;

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

        recyclerView = findViewById(R.id.recycler_view);
        playerList = new ArrayList<>();
        mAdapter = new PlayerAdapter(SearchPlayers.this, playerList, this);
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
            playerReference = parentRef.child("players");
            userReference = parentRef.child("users");
            playerReference.keepSynced(true);
            userReference.keepSynced(true);
        } catch (Exception ex) {
            Log.i("Exception : ", " playerReference " + ex);
        }

//        initialize();
        loadFirebaseUser();
    }

    private void initialize() {
        pushRef = userReference.push();
        pushRef.child("email").setValue("amanullahoasraf@gmail.com");
        DatabaseReference user = pushRef.child("playerlist");
        for (int i = 1; i <= 15; i++) {
//            Player item = new Player();
//            item.setName(names[i] + "");
//            item.setPoint(points[i] + "");
//            item.setPrice(points[i] + "");
//            item.setDetails(names[i] + "");
//            playerList.add(item);

//            pushRef = playerReference.push();
            user.child(String.valueOf(i)).setValue("playerId");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPlayerSelected(Player player) {
//        final Context context = this;
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("roll", player.getPhone());
//        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Selected: " + player.getName() + ", " + player.getPrice(), Toast.LENGTH_LONG).show();
    }

    public void loadFirebaseUser() {
        playerReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                player=dataSnapshot.getValue(Player.class);
                Log.e("onChildAdded", "aaddChildEventListener" + player.getName());
                playerList.add(player);
                mAdapter.notifyDataSetChanged();
                Log.e("onChildAdded", "addChildEventListener" + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged", "addChildEventListener" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildRemoved", "addChildEventListener");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildMoved", "addChildEventListener" + s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCalcnelle", databaseError.toString() + "addChildEventListener");
            }
        });
    }
}
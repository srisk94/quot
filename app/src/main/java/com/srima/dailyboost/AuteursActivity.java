package com.srima.dailyboost;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;




public class AuteursActivity extends ActionBarActivity {
    private ArrayList<Quote> imageArry = new ArrayList<Quote>();
    private AuthorsListAdapter adapter;
    private DataBaseHandler db;
    private ListView dataList;
    SearchView searchView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auteurs);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        db = new DataBaseHandler(this);

        List<Quote> authors = db.getAllAuthors("");
        for (Quote cn : authors) {

            imageArry.add(cn);

        }


        adapter = new AuthorsListAdapter(this, R.layout.author_items, imageArry);

        dataList = (ListView) findViewById(R.id.listView1);
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Quote srr = imageArry.get(position);
                Intent i = new Intent(getApplicationContext(),
                        QuotesActivity.class);
                i.putExtra("name", srr.getName());
                i.putExtra("mode", "isAuthor");
                startActivity(i);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auteurs, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
      //  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.search));
        // Configure the search info and add any event listeners
      //  searchView.setSearchableInfo(searchManager
      //          .getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                imageArry.clear();

                List<Quote> authors = db.getAllAuthors(searchView.getQuery());
                for (Quote cn : authors) {

                    imageArry.add(cn);
                }
                dataList.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the
                // search box.
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}

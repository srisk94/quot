package com.srima.dailyboost;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;




public class QuotesActivity extends ActionBarActivity {

    private ArrayList<Quote> imageArry = new ArrayList<Quote>();
    private QuotesListAdapter adapter;
    private String Activitytype;
    private DataBaseHandler db;
    private ListView dataList;
    private int count;
    private ImageView noQuotes;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        db = new DataBaseHandler(this);
        noQuotes = (ImageView)findViewById(R.id.NoQuotes);
        adapter = new QuotesListAdapter(this, R.layout.quote_items, imageArry);
        dataList = (ListView) findViewById(R.id.quotesList);
        Button btnLoadMore = new Button(this);

        btnLoadMore.setBackgroundResource(R.drawable.btn_green);
        btnLoadMore.setText(getResources().getText(R.string.btn_LoadMore));
        btnLoadMore.setTextColor(0xffffffff);
        Activitytype = getIntent().getExtras().getString("mode");

        if (Activitytype.equals("isCategory")) {
            String categoryValue = getIntent().getExtras()
                    .getString("category");
            List<Quote> contacts = db.getQuotesByCategory(categoryValue);
            for (Quote cn : contacts) {

                imageArry.add(cn);

            }

        }
        if (Activitytype.equals("isAuthor")) {
            String authorValue = getIntent().getExtras().getString("name");
            List<Quote> contacts = db.getQuotesByAuthor(authorValue);
            for (Quote cn : contacts) {

                imageArry.add(cn);

            }
            ;

        }

        if (Activitytype.equals("isFavorite")) {
            actionBar.setTitle(getResources().getText(R.string.title_activity_favorites));
            List<Quote> contacts = db.getFavorites();
            for (Quote cn : contacts) {

                imageArry.add(cn);

            }
            ;
            if (imageArry.isEmpty()){

                noQuotes.setVisibility(View.VISIBLE);
            }

        }
        if (Activitytype.equals("allQuotes")) {

            List<Quote> contacts = db.getAllQuotes(" LIMIT 50");
            for (Quote cn : contacts) {

                imageArry.add(cn);

            }
            ;
            dataList.addFooterView(btnLoadMore);
        }


        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Intent i = new Intent(getApplicationContext(),
                        QuoteActivity.class);
                Quote srr = imageArry.get(position);
                i.putExtra("id",position);
                i.putExtra("array", imageArry);
                i.putExtra("mode", "");

                startActivity(i);

            }

        });

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                new loadMoreListView().execute();
            }
        });


    }
    private class loadMoreListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // Before starting background task
            // Show Progress Dialog etc,.
        }

        protected Void doInBackground(Void... unused) {
            runOnUiThread(new Runnable() {
                public void run() {
                    count += 50;
                    List<Quote> contacts = db.getAllQuotes(" LIMIT "+count+ ",50");
                    for (Quote cn : contacts) {

                        imageArry.add(cn);

                    }
                    int currentPosition = dataList.getFirstVisiblePosition();
                    adapter = new QuotesListAdapter(QuotesActivity.this, R.layout.quote_items, imageArry);
                    dataList.setSelectionFromTop(currentPosition + 1, 0);
                }

            });
            return (null);
        }

        protected void onPostExecute(Void unused) {

        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quotes, menu);
        return true;
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

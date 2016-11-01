package com.srima.dailyboost;


import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.srima.dailyboost.R;


public class MainActivity extends ActionBarActivity {

    com.srima.dailyboost.DataBaseHandler db;
    private AlertDialog dialog;
    public static final int IntialQteOfDayId = 8;
    private Button btn_quotes, btn_authors, btn_favorites, btn_categories, btn_qteday ;
    final Context context = this;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        db = new com.srima.dailyboost.DataBaseHandler(this);
        db.openDataBase() ;

        btn_quotes= (Button) findViewById(R.id.btn_quotes);
        btn_authors= (Button) findViewById(R.id.btn_authors);
        btn_favorites= (Button) findViewById(R.id.btn_favorites);
        btn_categories= (Button) findViewById(R.id.btn_categories);
        btn_qteday= (Button) findViewById(R.id.btn_qteday);


        btn_quotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        QuotesActivity.class);
                intent.putExtra("mode", "allQuotes");
                startActivity(intent);
            }
        });

        btn_authors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent author = new Intent(MainActivity.this,
                        AuteursActivity.class);
                startActivity(author);
            }
        });

        btn_favorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent favorites = new Intent(MainActivity.this,
                        QuotesActivity.class);
                favorites.putExtra("mode", "isFavorite");
                startActivity(favorites);
            }
        });

        btn_categories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent category = new Intent(MainActivity.this,
                        CategoryActivity.class);
                startActivity(category);
            }
        });

        btn_qteday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);

                Intent qteDay = new Intent(MainActivity.this,
                        QuoteActivity.class);
                qteDay.putExtra("id",
                        preferences.getInt("id", IntialQteOfDayId));
                qteDay.putExtra("mode", "qteday");
                startActivity(qteDay);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            Intent i = new Intent(this, UserSettingActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
        }

        return super.onOptionsItemSelected(item);
    }
}

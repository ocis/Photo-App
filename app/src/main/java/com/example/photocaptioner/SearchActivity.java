package com.example.photocaptioner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import FilterImages.Filter;

public class SearchActivity extends AppCompatActivity {
    File[] imageList;
    String captionText;
    String dateFromText;
    String dateToText;
    String gpsLatText;
    String gpsLongText;
    Intent intent;
    Bundle b;
    public static final int FILTER_APPLIED = 1; //resultCode for applying a filter
    public static final int FILTER_CLEARED = -1;  //resultCode for clearing a filter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        // Grab file list from Intent
        intent = getIntent();
        b = intent.getBundleExtra("file bundle");
        imageList = (File[])b.getSerializable("file list");

        // Calls filter function and passes in filter parameters.  Sets result to filter applied and finishes activity
        Button filterButton = (Button) findViewById(R.id.btnFilter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = findViewById(R.id.caption_input);
                captionText = edit.getText().toString();

                edit = findViewById(R.id.date_from_input);
                dateFromText = edit.getText().toString();

                edit = findViewById(R.id.date_to_input);
                dateToText = edit.getText().toString();

                edit = findViewById(R.id.gps_lat_input);
                gpsLatText = edit.getText().toString();

                edit = findViewById(R.id.gps_long_input);
                gpsLongText = edit.getText().toString();

                File[] filteredList = Filter.filterImages(imageList, captionText, dateFromText, dateToText, gpsLatText, gpsLongText);

                b.putSerializable("filtered list", filteredList);
                intent = new Intent();
                intent.putExtra("file bundle", b);
                setResult(FILTER_APPLIED, intent);
                finish();
            }
        });

        // Clears all parameters and sets the resultCode to cleared filter
        Button clearButton = (Button) findViewById(R.id.btnClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = findViewById(R.id.caption_input);
                captionText = null;
                edit.setText(captionText);

                edit = findViewById(R.id.date_from_input);
                dateFromText = null;
                edit.setText(dateFromText);

                edit = findViewById(R.id.date_to_input);
                dateToText = null;
                edit.setText(dateToText);

                edit = findViewById(R.id.gps_lat_input);
                gpsLatText = null;
                edit.setText(gpsLatText);

                edit = findViewById(R.id.gps_long_input);
                gpsLongText = null;
                edit.setText(gpsLongText);

                intent = new Intent();
                setResult(FILTER_CLEARED, intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        EditText edit = findViewById(R.id.caption_input);
        edit.setText(captionText);

        edit = findViewById(R.id.date_from_input);
        edit.setText(dateFromText);

        edit = findViewById(R.id.date_to_input);
        edit.setText(dateToText);

        edit = findViewById(R.id.gps_lat_input);
        edit.setText(gpsLatText);

        edit = findViewById(R.id.gps_long_input);
        edit.setText(gpsLongText);

    }

    /** function for cancel button, to stop caption activity */
    public void cancel(View view){
        finish();
    }
}

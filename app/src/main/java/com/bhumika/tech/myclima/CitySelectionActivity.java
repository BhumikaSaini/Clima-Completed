package com.bhumika.tech.myclima;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/*
    Please add " implementation 'com.loopj.android:android-async-http:1.4.9' " to app level build.gradle file
    Please add " <uses-permission android:name="android.permission.INTERNET"/> " to AndroidManifest.xml
    TO DO:
        (1) Get references to all your layout views for this activity in initLayout() function
        (2) Connect this activity with "MainActivity" via "backImageButton" click
            (a) set an onClickListener on the button
            (b) implement the methods of onClickListener interface
            (c) use the intent
        (3) Set onEditorActionListener for "queryEditText"
        (4) Send city as intent extra
        (5) setResult() for the calling activity
 */

public class CitySelectionActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    EditText queryEditText;
    ImageButton backImageButton;
    TextView promptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        initLayout();
        // backImageButton listener here
        backImageButton.setOnClickListener(this);
        // Buttons can have a listener for clicks.
        // EditTexts can have listeners for keyboard presses like hitting the enter key.
        queryEditText.setOnEditorActionListener(this);

    }

    protected void initLayout() {
        queryEditText = findViewById(R.id.queryEditText);
        backImageButton = findViewById(R.id.backImageButton);
        promptTextView = findViewById(R.id.promptTextView);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        // put city name as intent extra
        // send back the result to calling activity
        String city = queryEditText.getText().toString();
        Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
        intent.putExtra("City Name", city);
        setResult(Activity.RESULT_OK, intent);
        finish();

        return true;
    }

}

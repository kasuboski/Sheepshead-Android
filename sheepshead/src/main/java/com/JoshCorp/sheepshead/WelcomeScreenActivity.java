package com.JoshCorp.sheepshead;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WelcomeScreenActivity extends Activity {

    public final static String PLAYER_NAME = "com.JoshCorp.sheepshead.MESSAGE";

    public void submitName(View view) {
        Intent intent = new Intent(this, PlayingActivity.class);
        EditText editText = (EditText) findViewById(R.id.player_name);
        String message = editText.getText().toString();
        intent.putExtra(PLAYER_NAME, message);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_welcome_screen);
    }
}

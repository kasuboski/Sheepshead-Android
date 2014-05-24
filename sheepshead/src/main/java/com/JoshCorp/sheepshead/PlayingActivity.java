package com.JoshCorp.sheepshead;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class PlayingActivity extends ActionBarActivity {

    private final static int[] cardDeck = new int[] {
            R.drawable.clubs_10,
            R.drawable.clubs_9,
            R.drawable.clubs_8,
            R.drawable.clubs_7,
            R.drawable.clubs_a,
            R.drawable.clubs_j,
            R.drawable.clubs_q,
            R.drawable.clubs_k,
            R.drawable.diamonds_10,
            R.drawable.diamonds_9,
            R.drawable.diamonds_8,
            R.drawable.diamonds_7,
            R.drawable.diamonds_a,
            R.drawable.diamonds_k,
            R.drawable.diamonds_q,
            R.drawable.diamonds_j,
            R.drawable.hearts_10,
            R.drawable.hearts_9,
            R.drawable.hearts_8,
            R.drawable.hearts_7,
            R.drawable.hearts_a,
            R.drawable.hearts_k,
            R.drawable.hearts_q,
            R.drawable.hearts_j,
            R.drawable.spades_10,
            R.drawable.spades_9,
            R.drawable.spades_8,
            R.drawable.spades_7,
            R.drawable.spades_a,
            R.drawable.spades_k,
            R.drawable.spades_q,
            R.drawable.spades_j
    };

    private final static Random randy = new Random();

    public void getCard(View view) {
        view.setBackgroundResource(cardDeck[randy.nextInt(cardDeck.length)]);
//        ImageButton card = (ImageButton)findViewById(view.getId());
//        card.setBackgroundResource(cardDeck[randy.nextInt(cardDeck.length)]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_playing);
        Intent intent = getIntent();
        String message = intent.getStringExtra(WelcomeScreenActivity.PLAYER_NAME);
        TextView playerName = (TextView)findViewById(R.id.PlayerName);
        playerName.setText(message);
    }
}

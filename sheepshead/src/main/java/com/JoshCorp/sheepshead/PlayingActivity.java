package com.JoshCorp.sheepshead;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
        //set the card that was just played
        //set picture
        ImageView image = (ImageView)findViewById(R.id.playerPlayed);
        image.setBackground(view.getBackground());
        //move card object
        Card card = (Card)view.getTag();

        //remove imageButton of card
        ((ViewManager)view.getParent()).removeView(view);

        //displayed random new card
//        view.setBackgroundResource(cardDeck[randy.nextInt(cardDeck.length)]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_playing);
        //set player name in textbox
        Intent intent = getIntent();
        String message = intent.getStringExtra(WelcomeScreenActivity.PLAYER_NAME);
        TextView playerName = (TextView)findViewById(R.id.PlayerName);
        playerName.setText(message);

        //get the layout
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.handContainer);

        //create a deck and deal cards
        Deck deck = new Deck();
        for(int i=1;i<=10;i++){
            Card card = deck.draw();
            ImageButton image = new ImageButton(this);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            image.setId(i);
            if(i == 2){
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
                params.setMargins(75,0,0,0);
            }
            else if(i != 1){
                params.addRule(RelativeLayout.RIGHT_OF,i-2);
            }
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            image.setLayoutParams(params);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCard(v);
                }
            });
            image.setAdjustViewBounds(true);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setMinimumWidth(150);
            image.setMinimumHeight(200);
            image.setTag(card);
            image.setBackgroundResource(getResources().getIdentifier(card.getResource() , "drawable", getPackageName()));
            layout.addView(image);
        }
    }
}

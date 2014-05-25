package com.JoshCorp.sheepshead;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class PlayingActivity extends ActionBarActivity {

    private ArrayList<Card> hand = new ArrayList<Card>(10);
    private final static Random randy = new Random();

    public void getCard(View view) {
        //set the card that was just played
        //set picture
        ImageView image = (ImageView)findViewById(R.id.playerPlayed);
        image.setBackground(view.getBackground());
        //move card object
        Card card = (Card)view.getTag();
        hand.remove(card);
//        System.out.println("Removed: " + card);

        updateCards((ArrayList<Card>)hand.clone());

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

        for(int i=0;i<10;i++){
            hand.add(deck.draw());
        }
        Collections.sort(hand);
        updateCards((ArrayList<Card>)hand.clone());
    }
    private void updateCards(ArrayList<Card> cards){
        //get the layout
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.handContainer);
        layout.removeAllViewsInLayout();
        for(int i=1;i<=cards.size();i++){
            Card card = cards.get(i-1);
//            System.out.println("Added: " + card);
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

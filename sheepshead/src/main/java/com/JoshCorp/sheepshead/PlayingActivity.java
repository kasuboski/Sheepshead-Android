package com.JoshCorp.sheepshead;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class PlayingActivity extends ActionBarActivity implements Table.UIListener, NoticeDialogFragment.NoticeDialogListener {

    private Table table;
    public static enum State {DEALING, PICKING, PLAYER, COMP, WAIT};
    private final static Random randy = new Random();
    public static State state;
    private android.os.Handler mHandler = new android.os.Handler();
    public boolean picked = false;

    private TextView messageBox;

    public void getCard(View view) {
        switch(state) {
            case DEALING:
            case COMP:
                System.out.println("Can't pick a card yet");
                break;
            case PICKING:
                System.out.println("picking");
                if(table.getPlayers().get(0).isPicker()) {
                    Card card = (Card)view.getTag();
                    table.getPlayers().get(0).bury(card);
                    if(state == State.WAIT) {
                        table.detOrder();
                    }
                }
                break;
            case PLAYER:
                System.out.println("player picked a card");
                ImageView image = (ImageView)findViewById(R.id.playerPlayed);
                image.setBackground(view.getBackground());
                Card card = (Card)view.getTag();
                table.playerPlayed(card);
                break;
            default:
                break;

        }
        updateCards((ArrayList<Card>) table.getPlayers().get(0).getHand().clone());
    }

    @Override
    //TODO: need to handle sleeping currently a new game is started
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_playing);

        //set player name in textbox
        Intent intent = getIntent();
        String message = intent.getStringExtra(WelcomeScreenActivity.PLAYER_NAME);
        messageBox = (TextView) findViewById(R.id.PlayerName);
        messageBox.setText(message);

        //create the players
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player(true, message));
        p.add(new Player(false, "Bill"));
        p.add(new Player(false, "Bob"));

        //create the table from the players and the game begins
        table = new Table(this, p);
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // store in SharedPreferences
//        editor.putString("savedTable", );
//        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void updateCards(ArrayList<Card> cards){
        //get the layout
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.handContainer);
        layout.removeAllViewsInLayout();
        //TODO: make 0 indexed
        for(int i=1;i<=cards.size();i++){
            Card card = cards.get(i-1);
//            System.out.println("Added: " + card);
            ImageButton image = new ImageButton(this);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            //this isn't correct but seems to work for now
            image.setId(i);
            if(i == 2){
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                //params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
                params.setMargins(dpToPx(75/2),0,0,0);
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
            image.setMinimumWidth(dpToPx(75));
            image.setMinimumHeight(dpToPx(100));
            image.setTag(card);
            image.setBackgroundResource(getResources().getIdentifier(card.getResource() , "drawable", getPackageName()));
            layout.addView(image);
        }
    }
    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public void illegalCard() {
        messageBox.setText("The card you played was illegal.");
    }

    public void playerTurn() {
        messageBox.setText("Your Turn");
    }

    //TODO: cards aren't reset after last hand
    public void endOfTrick(final Player player) {
        if(player.isPlayer()) {
            messageBox.setText("You won.");
        }
        else {
            messageBox.setText(player.getName() + " won.");
        }
        //don't erase results for 1.5 seconds
        mHandler.postDelayed(new Runnable() {
            public void run() {

                //reset trick cards
                ImageView left = (ImageView)findViewById(R.id.leftPlayed);
                ImageView right = (ImageView)findViewById(R.id.rightPlayed);
                ImageView bottom = (ImageView)findViewById(R.id.playerPlayed);

                left.setBackgroundResource(R.drawable.b1fh);
                right.setBackgroundResource(R.drawable.b1fh);
                bottom.setBackgroundResource(R.drawable.b1fv);

                table.detOrder();
            }
        }, 1500);

    }
    public void endOfHand(boolean winner) {
        if(winner) {
            messageBox.setText("The picker won");
        }
        else {
            messageBox.setText("The partners won");
        }

        //don't start a new game for 3 seconds
        mHandler.postDelayed(new Runnable() {
            public void run() {
                table.newGame();
            }
        }, 3000);
    }

    public void computerPlayed(Card card) {
        messageBox.setText(card.getOwner().getName() + " played a card.");
        ImageView img;
        if(card.getOwner().getName().equals("Bill")) {
            img = (ImageView)findViewById(R.id.leftPlayed);
        }
        else {
            img = (ImageView)findViewById(R.id.rightPlayed);
        }
        img.setBackgroundResource(getResources().getIdentifier(card.getResource() , "drawable", getPackageName()));

        //don't play turn for 1 seconds
        mHandler.postDelayed(new Runnable() {
            public void run() {
                table.playTurn();
            }
        }, 1000);

    }

    public void playerPick(Player player) {
        if(player.isPlayer()) {
            messageBox.setText("Select the cards to bury.");
        }
        else {
            messageBox.setText(player.getName() + " picked");
            //don't determine order for 3 seconds
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    table.detOrder();
                }
            }, 3000);

        }
    }

    public void toPick() {
        //TODO: currently prevents new game from being started after old one but is commented
        Fragment prev = getFragmentManager().findFragmentByTag("toPick");
        if(prev == null /*&& !picked*/) {
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new NoticeDialogFragment();
            dialog.show(getFragmentManager(), "toPick");
        }
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        table.pick(true);
        picked = true;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        table.pick(false);
        picked = true;
    }
}

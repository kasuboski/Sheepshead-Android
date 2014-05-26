package com.JoshCorp.sheepshead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Josh on 5/25/14.
 */
public class Table {
    private UIListener listener;
    private ArrayList<Player> players;
    private ArrayList<Card> trick;
    private ArrayList<Card> blind;
    private Deck deck;
    private Player lastWin;
    private boolean playerPicks = false;
    private Random randy = new Random();

    public Table(UIListener listener, ArrayList<Player> players)  {
        this.listener = listener;
        this.players = players;
        deck = new Deck();
        blind = new ArrayList<Card>();
        trick = new ArrayList<Card>(players.size());
        this.lastWin = this.players.get(0);
        newGame();
    }
    private void newGame() {
        deal();
        //determine who picks and pick
        listener.toPick();
        //pick(playerPicks);
        //wait for player to select cards to bury
//        while(!blind.isEmpty()) {
//            continue;
//        }
        //TODO: make everything more event driven
        System.out.println("Made it back");


    }
    public void play() {
        if(PlayingActivity.state == PlayingActivity.State.WAIT) {
            System.out.println("Made it in");
            //build order list
            ArrayList<Player> order = new ArrayList<Player>(players.size());
            order.add(lastWin);
            for(Player player : players) {
                if(!player.equals(lastWin)) {
                    order.add(player);
                }
            }
            //play through the order
            for(Player player : order) {
                if(player.isPlayer()) {
                    PlayingActivity.state = PlayingActivity.State.PLAYER;
                    listener.playerTurn();
                }
                else {
                    PlayingActivity.state = PlayingActivity.State.COMP;
                }
            }
        }
    }
    private void deal() {
        PlayingActivity.state = PlayingActivity.State.DEALING;
        for(Player player : players) {
            for (int i = 0; i < 10; i++) {
                player.addCard(deck.draw());
            }
            Collections.sort(player.getHand());
        }
        for(int i = 0; i < 2; i++) {
            blind.add(deck.draw());
        }
        listener.updateCards((ArrayList<Card>)players.get(0).getHand().clone());
    }

    /**
     * Picks depending on if player wants to pick or not
     * @param player true if player wants to pick
     */
    public void pick(boolean player) {
        Player picker = null;
        if(player) {
            players.get(0).setPicker(true);
            picker = players.get(0);
        }
        else {
            int numToPick = randy.nextInt((8-6) + 1) + 6;
            int total1 = 0;
            for(int p = 1; p < players.size();p++) {
                for(Card card : players.get(p).getHand()) {
                    if(card.isTrump() || card.getIdentifier().equals("A")) {
                        total1++;
                    }
                }
                if(total1 >= numToPick) {
                    players.get(1).setPicker(true);
                    picker = players.get(1);
                }
                else {
                    players.get(2).setPicker(true);
                    picker = players.get(2);
                }
            }
        }

        pick(picker);
    }
    private void pick(Player player) {
        player.addCards(getBlind());
        blind.clear();
        if(player.isPlayer()) {
            listener.updateCards((ArrayList<Card>) players.get(0).getHand().clone());
        }
        else {
            player.bury();
            play();
        }
        PlayingActivity.state = PlayingActivity.State.PICKING;
        listener.playerPick(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getTrick() {
        return trick;
    }

    public ArrayList<Card> getBlind() {
        return blind;
    }

    public Player getLastWin() {
        return lastWin;
    }

    public void setPlayerPicks(boolean playerPicks) {
        this.playerPicks = playerPicks;
    }

    public interface UIListener
    {
        void updateCards(ArrayList<Card> cards);
        void toPick();
        void playerPick(Player player);
        void playerTurn();
    }
}


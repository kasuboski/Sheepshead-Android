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
    private ArrayList<Player> order;
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

    public void newGame() {
        deal();
        //determine who picks and pick
        this.listener.toPick();
        System.out.println("Picking");
    }
    public void detOrder() {
        //if(PlayingActivity.state == PlayingActivity.State.WAIT) {
            System.out.println("Determining Order");
            //build order list
            order = new ArrayList<Player>(players.size());
            //order.add(lastWin);
            int pos = players.indexOf(lastWin) - 1;
            for(int p = 0;p < players.size();p++) {
                pos = (pos + 1) % players.size();
                order.add(players.get(pos));
            }
            //Collections.reverse(order);
            playTurn();

        //}
    }

    public void playTurn() {
        //System.out.println(order);
        if(!order.isEmpty()) {
            Player player = order.get(0);
            order.remove(player);
            if (player.isPlayer()) {
                PlayingActivity.state = PlayingActivity.State.PLAYER;
                System.out.println("Player's turn");
                listener.playerTurn();
            } else {
                PlayingActivity.state = PlayingActivity.State.COMP;
                //play computer turn
                System.out.println("Computer Turn");
                player.playCard(trick);

                //computer card should be last one in trick
                listener.computerPlayed(trick.get(trick.size() - 1));
            }
        }
        else {
            PlayingActivity.state = PlayingActivity.State.WAIT;
            //if still cards to be played
            if(!(players.get(0).getHand().isEmpty())) {
                //move on to next trick
                endTrick();
                listener.endOfTrick(lastWin);
            }
            else {
                //end of hand
                endTrick();
                int picker = 0;
                for(Player player : players) {
                    if(player.isPicker()) {
                        picker = player.getPoints();
                    }
                    player.reset();
                }
                boolean winner = false;
                System.out.println(picker);
                if(picker >= 61) {
                    //picker wins
                    winner = true;
                    System.out.println("The picker won with " + picker + " points.");
                }
                else {
                    System.out.println("The partners won with " + (120-picker) + " points.");
                }
                //reset values
                trick.clear();
                blind.clear();
                deck = new Deck();
                lastWin = players.get(0);

                listener.endOfHand(winner);
                System.out.println("End of Hand");
            }
        }
    }
    private void endTrick() {
        //determine winner and set lastWin
        Card win = Player.getWinCard(trick);
        lastWin = win.getOwner();
        System.out.println(lastWin.getName() + " won with " + win);
        //add the points

        //int pointsAdded = 0;
        for(Card card : trick) {
            //pointsAdded += card.getPoints();
            lastWin.addPts(card.getPoints());
        }
        //System.out.println(pointsAdded + " points added.");
        trick.clear();

        System.out.println("End of trick");

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

    public void playerPlayed(Card card) {
        if(!(players.get(0).playCard(trick, card))){
            //card was illegal
            listener.illegalCard();
        }
        else {
            listener.updateCards((ArrayList<Card>)players.get(0).getHand().clone());
            playTurn();
        }
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

        Collections.sort(player.getHand());

        if(player.isPlayer()) {
            listener.updateCards((ArrayList<Card>) players.get(0).getHand().clone());
        }
        else {
            player.bury();
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
        void illegalCard();
        void computerPlayed(Card card);
        void endOfTrick(Player winner);
        void endOfHand(boolean winner);
    }
}


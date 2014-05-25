package com.JoshCorp.sheepshead;

import java.util.ArrayList;

/**
 * Created by Josh on 5/25/14.
 */
public class Player {
    private int score;//score across all hands
    private int points;//points for this hand
    private ArrayList<Card> hand = new ArrayList<Card>(10);
    private boolean isPlayer;
    private boolean isPicker;

    public Player(boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.score = 0;
        this.points = 0;
    }

    public void playCard(Card card) {
        if (isPlayer){
            //human player logic

        }
        else {
            //computer player logic
        }
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addScore(int amt) {
        score += amt;
    }

    public void addPts(int amt) {
        points += amt;
    }

    public int getScore() {
        return score;
    }

    public int getPoints() {
        return points;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public boolean isPicker() {
        return isPicker;
    }

    public void setPicker(boolean isPicker) {
        this.isPicker = isPicker;
    }
}

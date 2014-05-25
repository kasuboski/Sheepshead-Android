package com.JoshCorp.sheepshead;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Josh on 5/25/14.
 */
public class Player {
    private int score;//score across all hands
    private int points;//points for this hand
    private ArrayList<Card> hand = new ArrayList<Card>(10);
    private boolean isPlayer;
    private boolean isPicker;
    private String name;

    public Player(boolean isPlayer, String name) {
        this.isPlayer = isPlayer;
        this.name = name;
        this.score = 0;
        this.points = 0;
    }

    public boolean playCard(ArrayList<Card> trick) {
        if(!isPlayer){
            //computer player logic
            return true;
        }
        else {
            //only computer uses this
            System.out.println("Error: Only computer uses this");
            return false;
        }
    }

    public boolean playCard(ArrayList<Card> trick, Card card) {
        if (isPlayer){
            //human player logic
            if(isLegal(trick, card)) {
                trick.add(card);
                removeCard(card);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            //only human specifies a card
            System.out.println("Error: Only human uses this");
            return false;
        }
    }

    /**
     * Determines if a card is legal
     * @param card
     * @param trick
     * @return  true if it is
     */
    private boolean isLegal(ArrayList<Card> trick, Card card) {
        //always legal if no cards played yet
        if(!trick.isEmpty()){
            Card led = trick.get(0);
            //leading card is trump
            if(led.isTrump()) {
                //if played card is fail
                if(!card.isTrump()) {
                    //if they have trump then
                    //the played card is illegal
                    for(Card c : hand) {
                        if(c.isTrump()) {
                            return false;
                        }
                    }
                }
            }
            else {
                //card is fail and doesn't match the led card
                if (card.getSuit() != led.getSuit() && !card.isTrump()) {
                    //if they have a card of the correct suit
                    //then card is illegal
                    for (Card c : hand) {
                        if (c.getSuit() == led.getSuit()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void addCard(Card card) {
        card.setOwner(this);
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void bury() {
        points += getLowCard().getPoints();
        points += getLowCard().getPoints();
        PlayingActivity.state = PlayingActivity.State.WAIT;
    }

    public void bury(Card card) {
        points += card.getPoints();
        removeCard(card);
        if(hand.size() == 10) {
            PlayingActivity.state = PlayingActivity.State.WAIT;
        }
    }

    /**
     * Finds the lowest card in the players hand and removes and returns it
     * @return  the lowest card
     */
    private Card getLowCard() {
        Collections.sort(hand);
        Card card = hand.get(0);
        removeCard(card);
        return card;
    }

    /**
     * Finds the highest card in the players hand and removes and returns it
     * @return  the highest card
     */
    private Card getHighCard()  {
        Collections.sort(hand);
        Card card = hand.get(hand.size()-1);
        removeCard(card);
        return card;
    }

    public void addCards(ArrayList<Card> cards) {
        hand.addAll(cards);
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

    public String getName() {
        return name;
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

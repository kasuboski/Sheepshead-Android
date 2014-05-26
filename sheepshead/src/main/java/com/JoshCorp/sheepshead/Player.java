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

    /**
     * Controls the AI for the game
     * Most methods used inside assume the hand is sorted
     * @param trick
     * @return true if called by computer player
     */
    public boolean playCard(ArrayList<Card> trick) {

        Collections.sort(hand);
        if(!isPlayer){
            //computer player logic
            if(isPicker()) {
                //computer is leading
                if(trick.isEmpty()) {
                    //picker plays high card
                    playCard(trick, getHighCard());
                }
                else {
                    //picker will always try to win
                    tryToWin(trick);
                }
            }
            else {
                //computer is leading
                if(trick.isEmpty()) {
                    //play an ace if it has one
                    Card ace = getFailAce();
                    if(!(ace.getIdentifier().equals("F"))) {
                        playCard(trick, ace);
                    }
                    else {
                        //play low card
                        playCard(trick, getLowLegalCard(trick));
                    }
                }
                else {
                    //if partners are winning
                    if(!(pickerWinning(trick))) {
                        //play low card so don't beat partner
                        playCard(trick, getLowLegalCard(trick));
                    }
                    else {
                        //try to win
                        tryToWin(trick);
                    }
                }
            }
            return true;
        }
        else {
            //only computer uses this
            System.out.println("Error: Only computer uses this");
            return false;
        }
    }

    /**
     * Tries to win otherwise plays lowest legal card
     * @param trick
     */
    private void tryToWin(ArrayList<Card> trick) {
        Card beatCard = canBeat(trick);
        //if can win then win
        if(!(beatCard.getIdentifier().equals("F"))) {
            playCard(trick, beatCard);
        }
        else {
            //play lowest legal card
            playCard(trick, getLowLegalCard(trick));
        }
    }
    private Card getLowLegalCard(ArrayList<Card> trick) {
        for(Card c : hand) {
            if(isLegal(trick, c)) {
                return c;
            }
        }
        return new Card("F");
    }
    private Card canBeat(ArrayList<Card> trick) {
        for(Card t : trick) {
            for (Card c : hand) {
                if (isLegal(trick, c) && c.getRank() > t.getRank()) {
                    return c;
                }
            }
        }
        return new Card("F");
    }
    private Card getFailAce() {
        for(Card card : hand) {
            if(card.getRank() == 6) {
                return card;
            }
        }
        return new Card("F");
    }
    private boolean pickerWinning(ArrayList<Card> trick) {
        if(getWinCard(trick).getOwner().isPicker()) {
            return true;
        }
        else {
            return false;
        }
    }
    public static Card getWinCard(ArrayList<Card>trick) {
        int highRank = 0;
        Card highCard = null;
        Card led = trick.get(0);

        for(Card card : trick) {
            //if card is higher and follows suit
            if(card.getRank() > highRank) {
                if((!(led.isTrump()) && (led.getSuit() == card.getSuit() || card.isTrump())) || led.isTrump()) {
                    highRank = card.getRank();
                    highCard = card;
                }
            }
        }
        return highCard;
    }

    public boolean playCard(ArrayList<Card> trick, Card card) {
        //human player logic and implementation of computer player playing a card
        if (isLegal(trick, card)) {
            trick.add(card);
            removeCard(card);
            return true;
        } else {
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
                //led is fail and card suit doesn't match it
                if (card.getSuit() != led.getSuit() || card.isTrump()) {
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
        System.out.println("Computer Buried");
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
        for( Card c : cards) {
            addCard(c);
        }
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

package com.JoshCorp.sheepshead;

/**
 * Created by Josh on 5/24/14.
 */
public class Card implements Comparable<Card> {
    public static enum Suit {CLUBS, SPADES, HEARTS, DIAMONDS}

    private Suit suit;
    private int points,rank;
    private String identifier;
    private String resource;
    private Player owner;

    public Card(String ident) {
        this.identifier = ident;
    }

    public Card(String ident, Suit suit, int points, int rank, String resource){
        this.identifier = ident;
        this.suit = suit;
        this.points = points;
        this.rank = rank;
        this.resource = resource;
    }

    public int compareTo(Card other) {
        //if both trump sort by rank
        if (this.isTrump() && other.isTrump()) {
            if (this.getRank() < other.getRank()) {
                return -1;
            } else if (this.getRank() > other.getRank()) {
                return 1;
            }
            return 0;
        }
        else if (this.isTrump() && !other.isTrump()) {
            return 1;
        }
        else if (!this.isTrump() && other.isTrump()) {
            return -1;
        }
        //if same suit sort by rank
        else if (this.getSuit().ordinal() == other.getSuit().ordinal()) {
            if (this.getRank() < other.getRank()) {
                return -1;
            } else if (this.getRank() > other.getRank()) {
                return 1;
            }
            return 0;
        }
        else {
            return this.getSuit().ordinal() - other.getSuit().ordinal();
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public int getPoints() {
        return points;
    }

    public int getRank() {
        return rank;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getResource() {
        return resource;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isTrump(){
        return rank >= 7;
    }
    @Override
    public String toString(){
        return this.getIdentifier() + " of " + this.getSuit();
    }
}

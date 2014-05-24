package com.JoshCorp.sheepshead;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marvin on 5/24/14.
 */
public class Deck {
    private ArrayList<Card> cards = new ArrayList<Card>(32);

    public Deck() {

        //load the cards
        //starting with fail
        cards.add(new Card("7", Card.Suit.CLUBS, 0, 1, "clubs_7"));
        cards.add(new Card("8", Card.Suit.CLUBS, 0, 2, "clubs_8"));
        cards.add(new Card("9", Card.Suit.CLUBS, 0, 3, "clubs_9"));
        cards.add(new Card("K", Card.Suit.CLUBS, 4, 4, "clubs_k"));
        cards.add(new Card("10", Card.Suit.CLUBS, 10, 5, "clubs_10"));
        cards.add(new Card("A", Card.Suit.CLUBS, 11, 6, "clubs_a"));

        cards.add(new Card("7", Card.Suit.SPADES, 0, 1, "spades_7"));
        cards.add(new Card("8", Card.Suit.SPADES, 0, 2, "spades_8"));
        cards.add(new Card("9", Card.Suit.SPADES, 0, 3, "spades_9"));
        cards.add(new Card("K", Card.Suit.SPADES, 4, 4, "spades_k"));
        cards.add(new Card("10", Card.Suit.SPADES, 10, 5, "spades_10"));
        cards.add(new Card("A", Card.Suit.SPADES, 11, 6, "spades_a"));

        cards.add(new Card("7", Card.Suit.HEARTS, 0, 1, "hearts_7"));
        cards.add(new Card("8", Card.Suit.HEARTS, 0, 2, "hearts_8"));
        cards.add(new Card("9", Card.Suit.HEARTS, 0, 3, "hearts_9"));
        cards.add(new Card("k", Card.Suit.HEARTS, 4, 4, "hearts_k"));
        cards.add(new Card("10", Card.Suit.HEARTS, 10, 5, "hearts_10"));
        cards.add(new Card("A", Card.Suit.HEARTS, 11, 6, "hearts_a"));

        //now trump
        cards.add(new Card("7", Card.Suit.DIAMONDS, 0, 7, "diamonds_7"));
        cards.add(new Card("8", Card.Suit.DIAMONDS, 0, 8, "diamonds_8"));
        cards.add(new Card("9", Card.Suit.DIAMONDS, 0, 9, "diamonds_9"));
        cards.add(new Card("K", Card.Suit.DIAMONDS, 4, 10, "diamonds_k"));
        cards.add(new Card("10", Card.Suit.DIAMONDS, 10, 11, "diamonds_10"));
        cards.add(new Card("A", Card.Suit.DIAMONDS, 11, 12, "diamonds_a"));

        cards.add(new Card("J", Card.Suit.DIAMONDS, 2, 13, "diamonds_j"));
        cards.add(new Card("J", Card.Suit.HEARTS, 2, 14, "hearts_j"));
        cards.add(new Card("J", Card.Suit.SPADES, 2, 15, "spades_j"));
        cards.add(new Card("J", Card.Suit.CLUBS, 2, 16, "clubs_j"));

        cards.add(new Card("Q", Card.Suit.DIAMONDS, 3, 17, "diamonds_q"));
        cards.add(new Card("Q", Card.Suit.HEARTS, 3, 18, "hearts_q"));
        cards.add(new Card("Q", Card.Suit.SPADES, 3, 19, "spades_q"));
        cards.add(new Card("Q", Card.Suit.CLUBS, 3, 20, "clubs_q"));

    }

    public Card draw(){
        Random randy = new Random();
        Card card = cards.get(randy.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }
}

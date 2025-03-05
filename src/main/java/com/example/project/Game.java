package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        int player1 = Utility.getHandRanking(p1Hand);
        int player2 = Utility.getHandRanking(p2Hand);
        if(player1 > player2 || player1 < player2){
            return player1 > player2 ? "Player 1 wins!" : "Player 2 wins!";
        } else {
            player1 = Utility.getRankValue(p1.highCardInHand().getRank());
            player2 = Utility.getRankValue(p2.highCardInHand().getRank());

            if(player1 > player2 || player1 < player2){
                return player1 > player2 ? "Player 1 wins!" : "Player 2 wins!";
            } else {return "Tie!";}
        }
    }
    

    public static void play(){ //simulate card playing
        Deck deck = new Deck();
        ArrayList<Card> community = new ArrayList<>();
        int x = 0;
        while(x == 0){
            Player p1 = new Player();
            Player p2 = new Player();
            deck.initializeDeck();
            deck.shuffleDeck();
            community = new ArrayList<>();
            community.add(deck.drawCard());
            community.add(deck.drawCard());
            community.add(deck.drawCard());
            p1.addCard(deck.drawCard());
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
            System.out.println("Community Cards: ");
            System.out.println(community);
            System.out.println("Player 1 Hand           |        Player 2 Hand");
            System.out.println(p1.getAllCards() + "        |" + p2.getAllCards());
            System.out.println(determineWinner(p1, p2, p1.playHand(community), p2.playHand(community), community));
            x++;
        }
    }
        
    public static void main(String[] args) {
        play();
    }

}
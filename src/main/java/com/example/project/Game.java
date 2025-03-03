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
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("A", "♠"));
        player1.addCard(new Card("A", "♣"));
        
        player2.addCard(new Card("K", "♠"));
        player2.addCard(new Card("K", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("A", "♦"));
        communityCards.add(new Card("K", "♥"));
        communityCards.add(new Card("9", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        System.out.println(winner);
        System.out.println(player2.highCard());
    }

    public static void play(){ //simulate card playing
        
    }
        
        

}
package com.example.project;
import java.util.ArrayList;
import java.util.Scanner;

public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        //the rankings of each player in numbers. 
        int player1 = Utility.getHandRanking(p1Hand);
        int player2 = Utility.getHandRanking(p2Hand);
        //if there's no tie then check to see who's hand rank is bigger. Bigger == winner
        if(player1 > player2 || player1 < player2){
            if(player1 > player2){
                return "Player 1 wins!";
            } else{return "Player 2 wins!";}
        } else {
            //if there is tie, get their highest card in hand and compare them.
            player1 = Utility.getRankValue(p1.highCardInHand().getRank());
            player2 = Utility.getRankValue(p2.highCardInHand().getRank());
            //if its different, Bigger == winner
            if(player1 > player2 || player1 < player2){
                if(player1 > player2){
                    return "Player 1 wins!";
                } else{return "Player 2 wins!";}
            } else {return "Tie!";}//else return tie.
        }
    }
    

    public static void play(){ //simulate card playing
        //initalize deck, players and the community cards. 
        Deck deck = new Deck();
        ArrayList<Card> community = new ArrayList<>();
        Player p1 = new Player();
        Player p2 = new Player();
        
        //add the deck and community cards.
        deck.initializeDeck();
        deck.shuffleDeck();
        community.add(deck.drawCard());
        community.add(deck.drawCard());
        community.add(deck.drawCard());
        
        //adding player cards.
        p1.addCard(deck.drawCard());
        p1.addCard(deck.drawCard());
        p2.addCard(deck.drawCard());
        p2.addCard(deck.drawCard());
        
        //printing the information of community, player 1 and player 2.
        System.out.println("Community Cards: ");
        System.out.println(community + "\n");
        System.out.println("Player 1 Hand");
        System.out.println(p1.getAllCards() + "\n");
        System.out.println("Player 2 Hand");
        System.out.println(p2.getAllCards() + "\n");

        //printing the result
        System.out.println(determineWinner(p1, p2, p1.playHand(community), p2.playHand(community), community));
    
    }
        
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Poker(NOT Real)\nEnter to play");
        //enter to continue
        scan.nextLine();
        //repeats until the loop breaks.
        while(true){
            //plays the game
            play();
            //asks if they want to continue
            System.out.println("Play again?(n to stop)");
            String userInput = scan.nextLine();
            if(userInput.equals("n")){
                break;
            }
            System.out.println("----------------------------------");
        }
        scan.close();
    }

}
package com.example.project;
import java.util.ArrayList;


public class Player{

    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        //adds the card to the hand and allCards
        hand.add(c);
        allCards.add(c);
    }

    public boolean inHand(Card card){
        //checks if card is in hand by using a for loop. 
        for(Card card2 : hand){
            if(card.equals(card2)){
                return true;
            }
        }
        return false;
    }

    public String playHand(ArrayList<Card> communityCards){
        //checks if the community card is already in allCards. If not add it. 
        if(!containsCommunity(communityCards)){        
            for(Card card: communityCards){
                allCards.add(card);
            }
        }
        //sorting cards for less work
        sortAllCards();
        //if statement that checks and returns the result. 
        if(royalFlush()){
            return "Royal Flush";
        } else if(flush()){
            return "Straight Flush";
        } else if(quad()){
            return "Four of a Kind";
        } else if(fullHouse()){
            return "Full House";
        } else if (sameSuit()){
            return "Flush";
        } else if(consecutive()){
            return "Straight";
        } else if(threeOfAKind() != 0){
            return "Three of a Kind";
        } else if(pairs() == 2){
            return "Two Pair";
        } else if(pairs() == 1){
            return "A Pair";
        } else if(inHand(highCard())){
            return "High Card";
        }
        //if the high card is not in hand.
        return "Nothing";
    }

    public void sortAllCards(){
        //using insertion sort
        for(int i = 1; i < allCards.size(); i ++){
            Card card = allCards.get(i);
            int index = i;
            while(index > 0 && Utility.getRankValue(card.getRank()) < Utility.getRankValue(allCards.get(index - 1).getRank())){
                allCards.set(index, allCards.get(index - 1));
                index--;
            }
            allCards.set(index, card);
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> result = new ArrayList<>();
        //initalizing the arraylist
        for(int i = 0; i < 14; i++){
            result.add(0);
        }
        //increase the index of the card Rank by one for each card. 
        for(int i = 0; i<allCards.size();i++){
            Card card = allCards.get(i);
            int index = Utility.getRankValue(card.getRank()) - 1;
            result.set(index, result.get(index) + 1);
        }
        return result; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> result = new ArrayList<>();
        //initalizing the list
        for(int i = 0 ; i <4; i++){
            result.add(0);
        }
        //checking the suit and increasing the frequency in the list. 
        for(Card card : allCards){
            String suite = card.getSuit();
            if(suite.equals("♠")){
                result.set(0,result.get(0) + 1);
            } else if(suite.equals("♥")){
                result.set(1, result.get(1) + 1);
            } else if(suite.equals("♣")){
                result.set(2, result.get(2) + 1);
            } else {
                result.set(3, result.get(3) + 1);
            }
        }
        return result; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public Card highCard(){
        //a low card(doesn't actually exist)
        Card result = new Card("2", "");
        //loop through allCard. If the card is bigger than the result, replace it. 
        for(Card card : allCards){
            if(Utility.getRankValue(card.getRank()) > Utility.getRankValue(result.getRank())){
                result = card;
            }
        }
        return result;
    }
    public Card highCardInHand(){
        //placeholder card
        Card result = new Card("2", "");
        //loop thorugh hand and find the highest card by replacing if the card is higher than result. 
        for(Card card : hand){
            if(Utility.getRankValue(card.getRank()) > Utility.getRankValue(result.getRank())){
                result = card;
            }
        }
        return result;
    }

    public int pairs(){
        //the number of Pairs
        int numberOfPairs = 0;
        //using rank frequency to count the number of pairs.
        ArrayList<Integer> rank = findRankingFrequency();
        //check each rank, and see if the frequency is 2. If true, the count increases. 
        for(int i = 0; i < rank.size(); i++){
            if(findRankingFrequency().get(i) == 2){
                numberOfPairs++;
            }
        }
        return numberOfPairs;
    }

    public int threeOfAKind(){
        //the rank of the three of a kind
        int result = 0;
        //keeping track of how many of the same
        int counter = 1;
        //looping through all cards
        for(int i = 0; i < allCards.size() - 1; i++){
            //if the next card has the same rank, count ++
            if(allCards.get(i).getRank().equals(allCards.get(i+1).getRank())){
                counter++;
            }else{//reset
                counter = 1;
            }
            //when there's 3 of same rank, return it
            if(counter == 3){
                result = Utility.getRankValue(allCards.get(i).getRank());
            }
        }
        return result;
    }
    
    public boolean consecutive(){
        //checks if the rank of the next number is the rank of this number + 1. 
        for(int i = 0; i<allCards.size() - 1; i++){
            if(Utility.getRankValue(allCards.get(i).getRank()) + 1 != Utility.getRankValue(allCards.get(i+1).getRank())){
                return false; 
            }
        }
        return true;
    }
    public boolean sameSuit(){
        //checks if they all have the same suit based on the first card's suit
        String suit = allCards.get(0).getSuit();
        for(int i = 1; i < allCards.size(); i++){
            if(!allCards.get(i).getSuit().equals(suit)){
                return false;
            }
        }
        return true;
    }

    public boolean fullHouse(){
        //checking if there is a three of a kind and one pair
        return threeOfAKind() != 0 && pairs() == 1;
    }

    public boolean quad(){
        //similar to three of a kind but only returns when counter reaches 4.
        int counter = 1;
        for(int i = 0; i < allCards.size() - 1; i++){
            if(allCards.get(i).getRank().equals(allCards.get(i+1).getRank())){
                counter++;
            }else{
                counter = 1;
            }
            if(counter == 4){
                return true;
            }
        }
        return false;
    }

    public boolean flush(){
        //if its both consecutive and has the same suit
        return consecutive() && sameSuit();
    }
    
    public boolean royalFlush(){
        //flush and check the highest card. If it is "A", then it is a royal flush
        return flush() && highCard().getRank().equals("A");
    }

    public boolean containsCommunity(ArrayList<Card> community){
        //counter for the number of cards that are same as community cards in allCards
        int count = 0;
        //looping through each card and checking if it is equal to one of the community cards. If yes then count++
        for(int i = 0; i<allCards.size();i++){
            for(int j = 0; j < community.size(); j++){
                if(allCards.get(i).equals(community.get(j))){
                    count++;
                    //when found, skip to the next card in allCards
                    break;
                }
            }
        }
        //if there's three same cards, then allCards has community cards already
        return count == 3;
    }
}
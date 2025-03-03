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
        hand.add(c);
        allCards.add(c);
    }

    public boolean inHand(Card card){
        for(Card card2 : hand){
            if(card.equals(card2)){
                return true;
            }
        }
        return false;
    }

    public String playHand(ArrayList<Card> communityCards){     
        for(Card card: communityCards){
            allCards.add(card);
        }
        sortAllCards();
        if(royalFlush()){
            return "Royal Flush";
        } 
        if(threeOfAKind() != 0){
            return "Three Of A Kind";
        } else if(pairs() == 2){
            return "Two Pair";
        } else if(pairs() == 1){
            return "A Pair";
        } else if(inHand(highCard())){
            return "High Card";
        }
        return "Nothing";
    }

    public void sortAllCards(){
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
        for(int i = 0; i < 14; i++){
            result.add(0);
        }
        for(int i = 0; i<allCards.size();i++){
            Card card = allCards.get(i);
            int index = Utility.getRankValue(card.getRank()) - 1;
            result.set(index, result.get(index) + 1);
        }
        return result; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0 ; i <4; i++){
            result.add(0);
        }
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
        Card result = new Card("2", "");
        for(Card card : allCards){
            if(Utility.getRankValue(card.getRank()) > Utility.getRankValue(result.getRank())){
                result = card;
            }
        }
        return result;
    }

    public int pairs(){
        int numberOfPairs = 0;
        for(int i = 1; i<allCards.size(); i++){
            if(allCards.get(i).getRank().equals(allCards.get(i-1).getRank())){
                numberOfPairs++;
                i++;
            }
        }
        return numberOfPairs;
    }

    public int threeOfAKind(){
        int result = 0;
        int counter = 1;
        for(int i = 0; i < allCards.size() - 1; i++){
            if(allCards.get(i).getRank().equals(allCards.get(i+1).getRank())){
                counter++;
            }else{
                counter = 1;
            }
            if(counter == 3){
                result = Utility.getRankValue(allCards.get(i).getRank());
            }
        }
        return result;
    }
    
    public boolean consecutive(){
        for(int i = 0; i<allCards.size() - 1; i++){
            if(Utility.getRankValue(allCards.get(i).getRank()) + 1 != Utility.getRankValue(allCards.get(i+1).getRank())){
                return false; 
            }
        }
        return true;
    }
    public boolean sameSuit(){
        String suit = allCards.get(0).getSuit();
        for(int i = 1; i < allCards.size(); i++){
            if(!allCards.get(i).getSuit().equals(suit)){
                return false;
            }
        }
        return true;
    }

    public boolean fullHouse(){
        return threeOfAKind() != 0 && pairs() == 1 ? true : false;
    }

    public boolean quad(){
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
        return consecutive() && sameSuit() ? true : false;
    }
    
    public boolean royalFlush(){
        return flush() && highCard().getRank().equals("A") ? true : false;
    }
}
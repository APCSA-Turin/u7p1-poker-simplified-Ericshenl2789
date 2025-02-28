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

    public String playHand(ArrayList<Card> communityCards){     
        for(Card card: communityCards){
            allCards.add(card);
        }
        sortAllCards();
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
        for(Card card : allCards){
            result.set(Utility.getRankValue(card.getRank()) - 1, result.get(Utility.getHandRanking(card.getRank())) + 1);
        }
        return result; 
    }
    public static void main(String[] args) {
        Player a = new Player();
        System.out.println(a.findRankingFrequency());
    }
    public ArrayList<Integer> findSuitFrequency(){
        return new ArrayList<>(); 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }




}

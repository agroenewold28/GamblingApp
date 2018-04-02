/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingapp;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Opposition {
    private int suitCount;
    public Boolean topPileDrawn;
    public String curSuit;
    public ArrayList<Card> playerCards;
    
    public Opposition(){
        playerCards = new ArrayList();
        suitCount = 0;
        curSuit = null;
    }
    
    public void computeOppositionMove(){
        double r = Math.random();
        double prob = this.computeDiscardPileProbability(GameController.discardPileCard);
        if(prob == 0.5){
            if(r <= 0.5){
                this.drawFromDiscardPile();
                return;
            }
        }
        else if(prob == 0.85){
            if(r <= 0.85){
                this.drawFromDiscardPile();
                return;
            }
        }
        else if(prob == 0.2){
            if(r <= 0.2){
                this.drawFromDiscardPile();
                return;
            }
        }
        else if(prob == 1){
            this.drawFromDiscardPile(); 
            return;
        }
        this.drawFromTopPile();
    }
    
    public void drawFromTopPile(){
         topPileDrawn = true;
         if(GameController.topPileCount == 0)
             SettingSelectorWindow.curGame.reShuffleCards();
         playerCards.add(GameController.topPile.get(GameController.topPileCount - 1));
         GameController.topPileCount--;
         this.setCurSuit();
    }
    
    public void drawFromDiscardPile(){
        topPileDrawn = false;
        playerCards.add(GameController.discardPileCard);
        GameController.oldCard = GameController.discardPileCard;
        this.setCurSuit();
    }
    
    public void discard(Card c){
        if(c == playerCards.get(0)){
            playerCards.remove(0);
        }
        else if(c == playerCards.get(1)){
            playerCards.remove(1);
        }
        else if(c == playerCards.get(2)){
            playerCards.remove(2);
        }
        else
            playerCards.remove(3);
    }
       
    private double computeDiscardPileProbability(Card topCard){
        if(suitCount == 3){
            int lowestVal = 20;
            for(Card b : playerCards){
                if(b.value < lowestVal)                  
                    lowestVal = b.value;
            }
            if(topCard.suit.equals(curSuit)){
                if(topCard.value > lowestVal)
                    return 1;
                else
                    return 0;
            }
            else
                return 0; 
        }
        else if(suitCount == 2){
            int suitSum = 0;
            int highest = 0;
            Card otherSuit = null;
            for(Card c : playerCards){
                if(c.suit.equals(curSuit) && c.value > highest){
                    highest = c.value;
                    suitSum += c.value;
                }
                else if(c.suit.equals(curSuit))
                    suitSum += c.value;
                else if(c.suit != curSuit)
                    otherSuit = c;
            }
            if((topCard.suit.equals(curSuit) && topCard.value > highest) || (topCard.suit.equals(otherSuit.suit) && (topCard.value + otherSuit.value) > suitSum))
                return 1;
            else if(topCard.suit.equals(curSuit)){
                if(topCard.value < 5)
                    return 0.5;
                else
                    return 0.85;
            }
            else
                return 0;               
        }
        else{
            for(Card d : playerCards){
                if(topCard.suit.equals(d.suit) && topCard.value > d.value)
                    return 1;
                else if(topCard.suit.equals(d.suit))
                    return 0.2;
            }
            return 0;
        }
    }
    
    private double computeKnockProbability(){
        if(suitCount == 1)
            return 0;
        else if(suitCount == 2){
            if(this.getTotal() < 21)
                return 0;
            else
                return 0.08;
        }
        else{
            if(this.getTotal() < 21)
                return 0;
            else if(this.getTotal() >= 27)
                return 0.91;
            else if(this.getTotal() >= 24)
                return 0.58;
            else
                return 0.15;
        }
    }
    
    public Boolean setKnockPerformed(){
        double r = Math.random();
        if(r < this.computeKnockProbability())
            return true;
        else
            return false;
    }
    
    public void setCurSuit(){
        int highestVal = 0;
        String highestSuit = null;
        int highest = 0;
        int tie = 0;
        suitCount = 0;
        int suitCounts[] = {0, 0, 0, 0};
        for(Card c : playerCards){
            if(c.value > highestVal){
                highestVal = c.value;
                highestSuit = c.suit;
            }
            if(c.suit.equals(Card.cardSuits.get(0)))
                suitCounts[0]++;
            else if(c.suit.equals(Card.cardSuits.get(1)))
                suitCounts[1]++;
            else if(c.suit.equals(Card.cardSuits.get(2)))
                suitCounts[2]++;
            else
                suitCounts[3]++;
        }
        for(int i = 0; i < 4; i++){
            if(suitCounts[i] > highest){
                highest = suitCounts[i];
                tie = 0;
            }
            else if(suitCounts[i] == highest && highest != 0)
                tie++;
        }
        String temp = null;
        String temp2 = null;
        String highestSuit2 = null;
        int suit1Sum = 0;
        int suit2Sum = 0;
        int highest2 = 0;
        if(highest == 1)
            curSuit = highestSuit;
        else if(tie == 1){
            for(int i = 0; i < playerCards.size(); i++){
                if(i == 0 || playerCards.get(i).suit == temp){
                    temp = playerCards.get(i).suit;
                    suit1Sum += playerCards.get(i).value;
                }
                else if(temp2 == null || playerCards.get(i).suit == temp2){
                    temp2 = playerCards.get(i).suit;
                    suit2Sum += playerCards.get(i).value;
                }
            }
            if(suit1Sum >= suit2Sum)
                curSuit = temp;
            else 
                curSuit = temp2;
        }
        else{
            for(int i = 0; i < 4; i++){
                if(suitCounts[i] > highest2){
                    highest2 = suitCounts[i];
                    highestSuit2 = Card.cardSuits.get(i);
                }
            }
            curSuit = highestSuit2;
        }
        for(Card d : playerCards){
            if(d.suit == curSuit)
                suitCount++;
        }
    }
    
    private int getTotal(){
        int total = 0;
        for(Card c : playerCards){
            if(c.suit.equals(curSuit))
                total += c.value;
        }
        return total;
    }
    
    public Boolean thirtyOnePresent(){
        int total = 0;
        for(Card c : playerCards){
            if(c.suit.equals(curSuit))
                total += c.value;
        }
        if(total == 31)
            return true;
        else 
            return false;
    } 
    
    public void computeDiscard(){
       Card discardCard = null;
       Card badCard1 = null;
       Card badCard2 = null;     
       int x = 0;
       int lowestVal = 20;
       if(suitCount == 4){
           for(Card c : playerCards){
               if(c.value < lowestVal){
                   lowestVal = c.value;
                   discardCard = c;
               }
           }
       }
       else if(suitCount == 3){
           for(Card d : playerCards){
               if(d.suit != curSuit)
                   discardCard = d;
           }
       }
       else if(suitCount == 2){
           for(Card e : playerCards){
               if(e.suit != curSuit){
                   if(x == 1)
                       badCard2 = e;
                   else{
                       badCard1 = e;
                       x++;
                   }
               }
           }
           if(badCard1.value <= badCard2.value)
               discardCard = badCard1;
           else
               discardCard = badCard2;
       }
       else{
           for(Card f : playerCards){
               if(f.value < lowestVal){
                   lowestVal = f.value;
                   discardCard = f;
               }
           }
       }
       GameController.discardPileCard = discardCard;
       this.discard(discardCard);
       SettingSelectorWindow.game.setUpsideDeck();
    }
}


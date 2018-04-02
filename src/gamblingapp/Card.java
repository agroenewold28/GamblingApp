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
public class Card {
    public String suit, faceValue;
    public int value, tempValue;
    public static ArrayList<String> cardSuits;   
    
    public Card(int value, String suit){  
        this.value = value;
        this.tempValue = this.value;
        this.suit = suit;
        if(this.value >= 11 && this.value <= 13){
            this.setFaceValue();
            this.value = 10;
        }
        else if(this.value == 14){
            this.faceValue = "Ace";
            this.value = 11;
        }
    }
    
    public Card(){
        this.setValue();
        this.setSuit();
    }
    
    public Card(String suit, int value){
        this.suit = suit;
        this.value = value;
    }
    
    public static void setCardSuitList(){
        cardSuits = new ArrayList();
    }
    
    public static void setCardSuits(){
        cardSuits.add("Spades");
        cardSuits.add("Clubs");
        cardSuits.add("Diamonds");
        cardSuits.add("Hearts");
    }
    
    private void setFaceValue(){
        if(value == 11)
            faceValue = "Jack";
        else if(value == 12)
            faceValue = "Queen";
        else
            faceValue = "King";
    }
    
    private void setValue(){
        double r = Math.random();
        double temp = 1 / 14;
        for(int i = 2; i < 15; i++){
            if(r <= temp)
                value = i;
            else
                temp += temp;
        }
            
        tempValue = value;
        if(value >= 11 && value <= 13){
            this.setFaceValue();
            value = 10;
        }
        else if(value == 14){
            faceValue = "Ace";
            value = 11;
        }
        else 
            faceValue = null;
    }
    
    private void setSuit(){
        double r = Math.random();
        double temp = 0.25;
        for(String s : cardSuits){
            if(r <= temp){
                suit = s;
                return;
            }
            else
                temp += 0.25;
        }
    }
}
